/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.common.blocks.soil;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.config.TFCConfig;


import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DirtBlock extends Block implements IDirtBlock
{
    private final Supplier<? extends Block> grass;
    @Nullable
    private final Supplier<? extends Block> grassPath;
    @Nullable
    private final Supplier<? extends Block> farmland;

    public DirtBlock(Properties properties, SoilBlockType grassType, SoilBlockType.Variant variant)
    {
        this(properties, TFCBlocks.SOIL.get(grassType).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(variant));
    }

    public DirtBlock(Properties properties, Supplier<? extends Block> grass, @Nullable Supplier<? extends Block> grassPath, @Nullable Supplier<? extends Block> farmland)
    {
        super(properties);

        this.grass = grass;
        this.grassPath = grassPath;
        this.farmland = farmland;
    }

    public BlockState getGrass()
    {
        return grass.get().defaultBlockState();
    }

    /*
    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolType toolType)
    {
        if (toolType == ToolType.HOE && TFCConfig.SERVER.enableFarmlandCreation.get() && farmland != null)
        {
            return farmland.get().defaultBlockState();
        }
        else if (toolType == ToolType.SHOVEL && TFCConfig.SERVER.enableGrassPathCreation.get() && grassPath != null)
        {
            return grassPath.get().defaultBlockState();
        }
        return state;
    }
    todo: tool modified states
     */
}