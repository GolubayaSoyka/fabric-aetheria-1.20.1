package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;

public class LetterItem {
    public static boolean isValid(NbtCompound nbtCompound) {
        return false;
    };

    public static String textToJson(ServerCommandSource commandSource, PlayerEntity player, String string) {
        return string;
    };
}
