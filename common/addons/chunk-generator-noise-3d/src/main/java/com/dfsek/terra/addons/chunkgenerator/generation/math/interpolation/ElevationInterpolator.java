/*
 * Copyright (c) 2020-2021 Polyhedral Development
 *
 * The Terra Core Addons are licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in this module's root directory.
 */

package com.dfsek.terra.addons.chunkgenerator.generation.math.interpolation;

import com.dfsek.terra.addons.chunkgenerator.config.noise.BiomeNoiseProperties;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;


public class ElevationInterpolator {
    private final double[][] values = new double[18][18];
    
    public ElevationInterpolator(long seed, int chunkX, int chunkZ, BiomeProvider provider, int smooth) {
        int xOrigin = chunkX << 4;
        int zOrigin = chunkZ << 4;
        
        double[][] noiseStorage = new double[18 + 2 * smooth][18 + 2 * smooth];
        double[][] weightStorage = new double[18 + 2 * smooth][18 + 2 * smooth];
        
        // Precompute generators.
        for(int x = -1 - smooth; x <= 16 + smooth; x++) {
            for(int z = -1 - smooth; z <= 16 + smooth; z++) {
                BiomeNoiseProperties noiseProperties = provider.getBiome(xOrigin + x, zOrigin + z, seed).getContext().get(
                        BiomeNoiseProperties.class);
                noiseStorage[x + 1 + smooth][z + 1 + smooth] = noiseProperties.elevation().noise(seed, xOrigin + x, zOrigin + z)
                                                               * noiseProperties.elevationWeight();
                weightStorage[x + 1 + smooth][z + 1 + smooth] = noiseProperties.elevationWeight();
            }
        }
        
        for(int x = -1; x <= 16; x++) {
            for(int z = -1; z <= 16; z++) {
                double noise = 0;
                double div = 0;
                for(int xi = -smooth; xi <= smooth; xi++) {
                    for(int zi = -smooth; zi <= smooth; zi++) {
                        noise += noiseStorage[x + 1 + smooth + xi][z + 1 + smooth + zi];
                        div += weightStorage[x + 1 + smooth + xi][z + 1 + smooth + zi];
                    }
                }
                values[x + 1][z + 1] = noise / div;
            }
        }
    }
    
    public double getElevation(int x, int z) {
        return values[x + 1][z + 1];
    }
}
