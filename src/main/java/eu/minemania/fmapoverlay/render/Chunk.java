package eu.minemania.fmapoverlay.render;

import eu.minemania.fmapoverlay.config.Configs;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class Chunk
{
    public String name;
    private int x;
    private int z;
    private int color;
    private Edge edge;

    public Chunk(String name, int x, int z, int color)
    {
        this.name = name;
        this.x = x;
        this.z = z;
        this.color = color;
        this.edge = new Edge(this.x * 16, this.z * 16, this.x * 16 + 16, this.z * 16 + 16);
    }

    public void shadeChunk(Tessellator tessellator)
    {
        double y = MinecraftClient.getInstance().player.getY();
        if (Configs.Generic.OVERLAY_CUSTOM_HEIGHT_ENABLE.getBooleanValue())
        {
            y = Configs.Generic.OVERLAY_CUSTOM_HEIGHT.getIntegerValue();
        }
        this.shadeChunk(tessellator, y - 1.6);
    }

    public void shadeChunk(Tessellator tessellator, double y)
    {
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_POLYGON, VertexFormats.POSITION_COLOR);
        Color4f internalColor = Color4f.fromColor(color);
        int alpha = 80;
        if (Configs.Generic.OVERLAY_CUSTOM_ALPHA_ENABLE.getBooleanValue())
        {
            alpha = Configs.Generic.OVERLAY_CUSTOM_ALPHA_CHUNK.getIntegerValue();
        }
        buffer.vertex(this.x * 16, y, this.z * 16).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16, y, this.z * 16 + 16).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16 + 16, y, this.z * 16 + 16).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16 + 16, y, this.z * 16).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();

        tessellator.draw();
        if (Configs.Generic.OVERLAY_EDGE.getBooleanValue())
        {
            edge.drawEdge(tessellator, y, color);
        }
    }

    public void drawName(double dx, double dy, double dz)
    {
        this.drawName(MinecraftClient.getInstance().player.getY(), dx, dy, dz);
    }

    public void drawName(double y, double dx, double dy, double dz)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        Entity entity = mc.getCameraEntity();
        if (entity != null)
        {
            if (Configs.Generic.NAMES_CUSTOM_HEIGHT_ENABLE.getBooleanValue())
            {
                y = Configs.Generic.NAMES_CUSTOM_HEIGHT.getIntegerValue();
            }
            RenderUtils.drawTextPlate(Arrays.asList(this.name), this.x * 16 - dx + 8, y - dy, this.z * 16 - dz + 8, entity.yaw, entity.pitch, 0.04f, 0xFFFFFFFF, 0x80000000, false);
        }
    }

    public void lineChunk(Tessellator tessellator)
    {
        double y = MinecraftClient.getInstance().player.getY();
        if (Configs.Generic.OVERLAY_CUSTOM_HEIGHT_ENABLE.getBooleanValue())
        {
            y = Configs.Generic.OVERLAY_CUSTOM_HEIGHT.getIntegerValue();
        }
        y -= 1.6;
        BufferBuilder buffer = tessellator.getBuffer();
        GL11.glLineWidth(3.0f);
        buffer.begin(GL11.GL_LINE_LOOP, VertexFormats.POSITION_COLOR);
        Color4f internalColor = Color4f.fromColor(color);
        int alpha = 200;
        if (Configs.Generic.OVERLAY_CUSTOM_ALPHA_ENABLE.getBooleanValue())
        {
            alpha = Configs.Generic.OVERLAY_CUSTOM_ALPHA_LINE.getIntegerValue();
        }
        buffer.vertex(this.x * 16 + 0.1, y, this.z * 16 + 0.1).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16 + 0.1, y, this.z * 16 + 15.9).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16 + 15.9, y, this.z * 16 + 15.9).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        buffer.vertex(this.x * 16 + 15.9, y, this.z * 16 + 0.1).color(internalColor.r, internalColor.g, internalColor.b, alpha).next();
        tessellator.draw();
        if (Configs.Generic.OVERLAY_EDGE.getBooleanValue())
        {
            edge.drawEdge(tessellator, y, color);
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!this.name.equals(((Chunk) obj).name))
        {
            return false;
        }
        if (this.x != ((Chunk) obj).x)
        {
            return false;
        }
        if (this.z != ((Chunk) obj).z)
        {
            return false;
        }
        return true;
    }
}
