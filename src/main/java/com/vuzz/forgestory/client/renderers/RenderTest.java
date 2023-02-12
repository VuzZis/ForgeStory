package com.vuzz.forgestory.client.renderers;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.vuzz.forgestory.ForgeStory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderTest extends IngameGui{

	public static final ResourceLocation NEW_PUMPKIN_LOCATION = new ResourceLocation(ForgeStory.MOD_ID + ":textures/gui/blur.png");
	private int screenWidth;
	private int screenHeight;
	public RenderTest(Minecraft minecraft) {
		super(minecraft);
		
		screenWidth = minecraft.getWindow().getGuiScaledWidth();
	    screenHeight = minecraft.getWindow().getGuiScaledHeight();
	}
	
	@SuppressWarnings("all")
	public void renderTestPumpkin(Minecraft minecraft) {
		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableAlphaTest();
		minecraft.getTextureManager().bind(NEW_PUMPKIN_LOCATION);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(0.0D, (double)screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
		bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex((double)screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
