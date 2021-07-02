package eu.minemania.fmapoverlay.render;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.minemania.fmapoverlay.config.Configs;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.render.*;

public class Edge
{
    private int minX;
    private int maxX;
    private int minZ;
    private int maxZ;

    public Edge(int x1, int z1, int x2, int z2)
    {
        this.minX = Math.min(x1, x2);
        this.maxX = Math.max(x1, x2);
        this.minZ = Math.min(z1, z2);
        this.maxZ = Math.max(z1, z2);
    }

    public void drawEdge(Tessellator tessellator, double y, int color)
    {
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesShader);
        buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        RenderSystem.lineWidth(5.0f);
        Color4f internalColor = Color4f.fromColor(color);
        int alpha = 200;
        if (Configs.Generic.OVERLAY_CUSTOM_ALPHA_ENABLE.getBooleanValue())
        {
            alpha = Configs.Generic.OVERLAY_CUSTOM_ALPHA_EDGE.getIntegerValue();
        }
        buffer.vertex(minX, y, minZ).color(internalColor.r, internalColor.g, internalColor.b, alpha).normal(0,0,0).next();
        buffer.vertex(maxX, y, maxZ).color(internalColor.r, internalColor.g, internalColor.b, alpha).normal(0,0,0).next();
        tessellator.draw();
    }
}
