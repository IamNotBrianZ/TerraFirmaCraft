/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.common.blocks.plant;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class ShortGrassBlock extends PlantBlock
{
    protected static final VoxelShape GRASS_SHAPE = box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape SHORTER_GRASS_SHAPE = box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);
    protected static final VoxelShape SHORT_GRASS_SHAPE = box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    protected static final VoxelShape SHORTEST_GRASS_SHAPE = box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

    public static ShortGrassBlock create(IPlant plant, Properties properties)
    {
        return new ShortGrassBlock(properties)
        {
            @Override
            public IPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected ShortGrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(AGE))
            {
                case 0 -> SHORTEST_GRASS_SHAPE;
                case 1 -> SHORTER_GRASS_SHAPE;
                case 2 -> SHORT_GRASS_SHAPE;
                default -> GRASS_SHAPE;
            };
    }

    @Override
    public OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}