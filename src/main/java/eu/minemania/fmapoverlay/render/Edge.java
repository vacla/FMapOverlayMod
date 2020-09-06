package eu.minemania.fmapoverlay.render;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

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
		GL11.glLineWidth(5.0f);
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_LINE, VertexFormats.POSITION_COLOR);
		Color4f internalColor = Color4f.fromColor(color);
		buffer.color(internalColor.r, internalColor.g, internalColor.b, 200);
		buffer.vertex(minX, y, minZ);
		buffer.vertex(maxX, y, maxZ);
		tessellator.draw();
	}
}
