/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.joml.Matrix4f;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.common.blockentities.ScrapingBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;

public enum ScrapingBlockModel implements SimpleStaticBlockEntityModel<ScrapingBlockModel, ScrapingBlockEntity>
{
    INSTANCE;

    @Override
    public TextureAtlasSprite render(ScrapingBlockEntity scraping, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay)
    {
        if (scraping.getInputTexture() != null && scraping.getOutputTexture() != null)
        {
            final short positions = scraping.getScrapedPositions();
            drawTiles(buffer, poseStack, scraping.getInputTexture(), positions, 0, packedLight, packedOverlay, scraping.getColor1());
            drawTiles(buffer, poseStack, scraping.getOutputTexture(), positions, 1, packedLight, packedOverlay, scraping.getColor2());
        }
        if (scraping.getOutputTexture() != null)
        {
            return Minecraft.getInstance().getTextureAtlas(RenderHelpers.BLOCKS_ATLAS).apply(scraping.getOutputTexture());
        }
        return RenderHelpers.missingTexture();
    }

    @Override
    public BlockEntityType<ScrapingBlockEntity> type()
    {
        return TFCBlockEntities.SCRAPING.get();
    }

    @Override
    public int faces(ScrapingBlockEntity blockEntity)
    {
        return 16;
    }

    private void drawTiles(VertexConsumer buffer, PoseStack poseStack, ResourceLocation texture, short positions, int condition, int packedLight, int packedOverlay, int color)
    {
        Matrix4f mat = poseStack.last().pose();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(RenderHelpers.BLOCKS_ATLAS).apply(texture);
        for (int xOffset = 0; xOffset < 4; xOffset++)
        {
            for (int zOffset = 0; zOffset < 4; zOffset++)
            {
                // Checks the nth bit of positions
                if ((((positions >> (xOffset + 4 * zOffset)) & 1) == condition))
                {
                    buffer.addVertex(mat, xOffset / 4.0F, 0.01F, zOffset / 4.0F).setColor(color).setUv(sprite.getU(xOffset * 4f), sprite.getV(zOffset * 4f)).setOverlay(packedOverlay).setLight(packedLight).setNormal(0, 0, 1);
                    buffer.addVertex(mat, xOffset / 4.0F, 0.01F, zOffset / 4.0F + 0.25F).setColor(color).setUv(sprite.getU(xOffset * 4f), sprite.getV(zOffset * 4f + 4f)).setOverlay(packedOverlay).setLight(packedLight).setNormal(0, 0, 1);
                    buffer.addVertex(mat, xOffset / 4.0F + 0.25F, 0.01F, zOffset / 4.0F + 0.25F).setColor(color).setUv(sprite.getU(xOffset * 4f + 4f), sprite.getV(zOffset * 4f + 4f)).setOverlay(packedOverlay).setLight(packedLight).setNormal(0, 0, 1);
                    buffer.addVertex(mat, xOffset / 4.0F + 0.25F, 0.01F, zOffset / 4.0F).setColor(color).setUv(sprite.getU(xOffset * 4f + 4f), sprite.getV(zOffset * 4f)).setOverlay(packedOverlay).setLight(packedLight).setNormal(0, 0, 1);
                }
            }
        }
    }
}
