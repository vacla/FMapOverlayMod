package eu.minemania.fmapoverlay.event;

import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.render.OverlayRenderer;
import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class RenderHandler implements IRenderer
{
    private static final RenderHandler INSTANCE = new RenderHandler();

    public static RenderHandler getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void onRenderWorldLast(float partialTicks, MatrixStack matrixStack)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (Configs.Generic.ENABLED.getBooleanValue() && mc.world != null && mc.player != null)
        {
            OverlayRenderer.renderOverlays(mc, matrixStack);
        }
    }
}
