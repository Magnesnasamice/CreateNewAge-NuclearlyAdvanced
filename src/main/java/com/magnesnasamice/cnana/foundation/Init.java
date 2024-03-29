package com.magnesnasamice.cnana.foundation;

import com.magnesnasamice.cnana.foundation.block.ModBlockEntities;
import com.magnesnasamice.cnana.foundation.block.ModBlocks;
import com.magnesnasamice.cnana.foundation.effect.ModEffects;
import com.magnesnasamice.cnana.foundation.event.ModEvents;
import com.magnesnasamice.cnana.foundation.fluid.ModFluids;
import com.magnesnasamice.cnana.foundation.item.ModItemGroups;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import com.magnesnasamice.cnana.foundation.network.ModPackets;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Init implements ModInitializer {

	public static final String MOD_ID = "cnana";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.register();
		ModItems.register();

		ModBlocks.register();
		ModBlockEntities.register();

		ModFluids.register();
		ModEffects.register();

		ModEvents.register();
		ModPackets.registerC2SPackets(); // client to server packets
	}
}