/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.compat.jade;

import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import mcjty.theoneprobe.api.*;
import net.dries007.tfc.compat.jade.common.*;
import net.dries007.tfc.util.Helpers;

public class TheOneProbeIntegration implements Function<ITheOneProbe, Void>
{
    @Override
    public Void apply(ITheOneProbe registry)
    {
        Tooltips.register((tooltip, aClass) -> register(registry, tooltip, aClass), (tooltip, aClass) -> register(registry, tooltip, aClass));
        return null;
    }

    private void register(ITheOneProbe top, BlockEntityTooltip tooltip, Class<? extends Block> blockClass)
    {
        top.registerProvider(new IProbeInfoProvider() {
            @Override
            public ResourceLocation getID()
            {
                return Helpers.identifier(blockClass.getSimpleName());
            }

            @Override
            public void addProbeInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState blockState, IProbeHitData data)
            {
                if (data.getPos() != null && blockState.getBlock().getClass().isInstance(blockClass))
                {
                    tooltip.display(level, blockState, level.getBlockEntity(data.getPos()), info::text);
                }
            }
        });
    }

    private void register(ITheOneProbe top, EntityTooltip tooltip, Class<? extends Entity> entityClass)
    {
        top.registerEntityProvider(new IProbeInfoEntityProvider() {
            @Override
            public String getID()
            {
                return Helpers.identifier(entityClass.getSimpleName()).toString();
            }

            @Override
            public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, Entity entity, IProbeHitEntityData data)
            {
                if (entity.getClass().isInstance(entityClass))
                {
                    tooltip.display(level, entity, info::text);
                }
            }
        });
    }
}
