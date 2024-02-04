package net.soyka.aetheriamod;

import net.fabricmc.api.ModInitializer;

import net.soyka.aetheriamod.block.ModBlocks;
import net.soyka.aetheriamod.item.ModItemGroups;
import net.soyka.aetheriamod.item.Moditems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AetheriaMod implements ModInitializer {
	public static final String MOD_ID = "aetheriamod";
    public static final Logger LOGGER = LoggerFactory.getLogger("aetheriamod");

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		Moditems.registerModItems();
		ModBlocks.registerModBlocks();
	}

}