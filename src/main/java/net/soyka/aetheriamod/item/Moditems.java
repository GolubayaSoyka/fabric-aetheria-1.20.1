package net.soyka.aetheriamod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.soyka.aetheriamod.AetheriaMod;

public class Moditems {
    public static final Item PAPPER_AND_QUILL = registerItem("paper_and_quill", new Item(new FabricItemSettings()));
    public static final Item ENVELOPE = registerItem("envelope", new Item (new FabricItemSettings()));
    public static final Item EMPTY_LETTER = registerItem("empty_letter", new Item(new FabricItemSettings()));
    public static final Item LETTER = registerItem("letter", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(PAPPER_AND_QUILL);
        entries.add(ENVELOPE);
        entries.add(EMPTY_LETTER);
        entries.add(LETTER);

    }

    private static Item registerItem(String name,Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AetheriaMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AetheriaMod.LOGGER.info("Registering Mod Items for" + AetheriaMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(Moditems::addItemsToIngredientItemGroup);
    }
}
