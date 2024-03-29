package com.magnesnasamice.cnana.foundation;

import com.magnesnasamice.cnana.foundation.datagen.DatagenAdvancementProvider;
import com.magnesnasamice.cnana.foundation.datagen.DatagenModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class InitDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		// pack.addProvider(DatagenModelProvider::new);
		pack.addProvider(DatagenAdvancementProvider::new);
	}
}
