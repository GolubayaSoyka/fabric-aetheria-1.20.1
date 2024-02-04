package net.soyka.aetheriamod.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LetterItem
        extends Item {
    public static final int MAX_TITLE_EDIT_LENGTH = 16;
    public static final int MAX_TITLE_VIEW_LENGTH = 32;
    public static final int MAX_PAGE_EDIT_LENGTH = 1024;
    public static final int MAX_PAGE_VIEW_LENGTH = Short.MAX_VALUE;
    public static final int MAX_PAGES = 100;
    public static final int field_30934 = 2;
    public static final String TITLE_KEY = "title";
    public static final String FILTERED_TITLE_KEY = "filtered_title";
    public static final String AUTHOR_KEY = "author";
    public static final String PAGES_KEY = "pages";
    public static final String FILTERED_PAGES_KEY = "filtered_pages";
    public static final String GENERATION_KEY = "generation";
    public static final String RESOLVED_KEY = "resolved";

    public LetterItem(Item.Settings settings) {super(settings);}

    public static boolean isValid(@Nullable NbtCompound nbt) {
        if (!LetterItem.isValid(nbt)) {
            return false;
        }
        if (!nbt.contains(TITLE_KEY, NbtElement.STRING_TYPE)) {
            return false;
        }
        String string = nbt.getString(TITLE_KEY);
        if (string.length() > 32) {
            return false;
        }
        return nbt.contains(AUTHOR_KEY, NbtElement.STRING_TYPE);
    }

    public static int getGeneration(ItemStack stack) {
        return stack.getNbt().getInt(GENERATION_KEY);
    }

    public static int getPageCount(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null ? nbtCompound.getList(PAGES_KEY, NbtElement.STRING_TYPE).size() : 0;
    }

    @Override
    public Text getName(ItemStack stack) {
        String string;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null && !StringHelper.isEmpty(string = nbtCompound.getString(TITLE_KEY))) {
            return Text.literal(string);
        }
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            NbtCompound nbtCompound = stack.getNbt();
            String string = nbtCompound.getString(AUTHOR_KEY);
            if (!StringHelper.isEmpty(string)) {
                tooltip.add(Text.translatable("letter.byAuthor", string).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.translatable("letter.generation." + nbtCompound.getInt(GENERATION_KEY)).formatted(Formatting.GRAY));
        }
    }

    public static boolean resolve(ItemStack letter, @Nullable ServerCommandSource commandSource, @Nullable PlayerEntity player) {
        NbtCompound nbtCompound = letter.getNbt();
        if (nbtCompound == null || nbtCompound.getBoolean(RESOLVED_KEY)) {
            return false;
        }
        nbtCompound.putBoolean(RESOLVED_KEY, true);
        if (!net.minecraft.item.LetterItem.isValid(nbtCompound)) {
            return false;
        }
        NbtList nbtList = nbtCompound.getList(PAGES_KEY, NbtElement.STRING_TYPE);
        NbtList nbtList2 = new NbtList();
        for (int i = 0; i < nbtList.size(); ++i) {
            String string = net.minecraft.item.LetterItem.textToJson(commandSource, player, nbtList.getString(i));
            if (string.length() > Short.MAX_VALUE) {
                return false;
            }
            nbtList2.add(i, NbtString.of(string));
        }
        if (nbtCompound.contains(FILTERED_PAGES_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound(FILTERED_PAGES_KEY);
            NbtCompound nbtCompound3 = new NbtCompound();
            for (String string2 : nbtCompound2.getKeys()) {
                String string3 = net.minecraft.item.LetterItem.textToJson(commandSource, player, nbtCompound2.getString(string2));
                if (string3.length() > Short.MAX_VALUE) {
                    return false;
                }
                nbtCompound3.putString(string2, string3);
            }
            nbtCompound.put(FILTERED_PAGES_KEY, nbtCompound3);
        }
        nbtCompound.put(PAGES_KEY, nbtList2);
        return true;
    }

    private static String textToJson(@Nullable ServerCommandSource commandSource, @Nullable PlayerEntity player, String text) {
        MutableText text2;
        try {
            text2 = Text.Serializer.fromLenientJson(text);
            text2 = Texts.parse(commandSource, text2, (Entity)player, 0);
        } catch (Exception exception) {
            text2 = Text.literal(text);
        }
        return Text.Serializer.toJson(text2);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
