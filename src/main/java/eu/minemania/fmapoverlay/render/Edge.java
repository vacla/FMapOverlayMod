package eu.minemania.fmapoverlay.render;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.minemania.fmapoverlay.config.Configs;
import fi.dy.masa.malilib.util.data.Color4f;
import net.minecraft.client.render.*;

public class Edge
{
    private final int minX;
    private final int maxX;
    private final int minZ;
    private final int maxZ;

    public Edge(int x1, int z1, int x2, int z2)
    {
        this.minX = Math.min(x1, x2);
        this.maxX = Math.max(x1, x2);
        this.minZ = Math.min(z1, z2);
        this.maxZ = Math.max(z1, z2);
    }

    public void drawEdge(Tessellator tessellator, double y, int color)
    {
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        BuiltBuffer builtBuffer;
        RenderSystem.lineWidth(5.0f);
        Color4f internalColor = Color4f.fromColor(color);
        int alpha = 200;
        if (Configs.Generic.OVERLAY_CUSTOM_ALPHA_ENABLE.getBooleanValue())
        {
            alpha = Configs.Generic.OVERLAY_CUSTOM_ALPHA_EDGE.getIntegerValue();
        }
        buffer.vertex(minX, (float) y, minZ).color(internalColor.r, internalColor.g, internalColor.b, alpha).normal(0,0,0);
        buffer.vertex(maxX, (float) y, maxZ).color(internalColor.r, internalColor.g, internalColor.b, alpha).normal(0,0,0);
        try {
            builtBuffer = buffer.end();
            BufferRenderer.drawWithGlobalProgram(builtBuffer);
            builtBuffer.close();
        } catch (Exception e) {
            // Ignored
        }
    }
}
