package net.soyka.aetheriamod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.soyka.aetheriamod.AetheriaMod;
import net.soyka.aetheriamod.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup LETTERS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(AetheriaMod.MOD_ID, "letters"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.letters"))
                    .icon(() -> new ItemStack(Moditems.LETTER)).entries((displayContext, entries) -> {
                        entries.add(Moditems.ENVELOPE);
                        entries.add(Moditems.PAPPER_AND_QUILL);
                        entries.add(Moditems.EMPTY_LETTER);
                        entries.add(Moditems.LETTER);


                    }).build());


    public static void registerItemGroups() {
        AetheriaMod.LOGGER.info("Registering Item Groups for " + AetheriaMod.MOD_ID);
    }
}
