package eu.minemania.fmapoverlay.render;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.minemania.fmapoverlay.FMapOverlay;
import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.data.DataManager;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;

public class OverlayRenderer
{
    private static long loginTime;
    private static boolean canRender;
    private static LinkedList<String> lines;
    private static LinkedList<Chunk> toDraw;
    private static HashMap<Character, String> factions;
    private static boolean isFixed;
    private static double fixedY;
    private static boolean drawNames;
    static HashMap<String, Integer> colors;

    // https://stackoverflow.com/questions/470690/how-to-automatically-generate-n-distinct-colors
    public static final int[] KELLY_COLORS = {
            0xFFB300,    // Vivid Yellow
            0x803E75,    // Strong Purple
            0xFF6800,    // Vivid Orange
            0xA6BDD7,    // Very Light Blue
            0xC10020,    // Vivid Red
            0xCEA262,    // Grayish Yellow
            0x817066,    // Medium Gray
            // The following don't work well for people with defective color vision
            0x007D34,    // Vivid Green
            0xF6768E,    // Strong Purplish Pink
            0x00538A,    // Strong Blue
            0xFF7A5C,    // Strong Yellowish Pink
            0x53377A,    // Strong Violet
            0xFF8E00,    // Vivid Orange Yellow
            0xB32851,    // Strong Purplish Red
            0xF4C800,    // Vivid Greenish Yellow
            0x7F180D,    // Strong Reddish Brown
            0x93AA00,    // Vivid Yellowish Green
            0x593315,    // Deep Yellowish Brown
            0xF13A13,    // Vivid Reddish Orange
            0x232C16     // Dark Olive Green
    };

    public static void resetRenderTimeout()
    {
        canRender = false;
        loginTime = System.currentTimeMillis();
    }

    public static void renderOverlays(MinecraftClient mc, MatrixStack matrices)
    {
        Entity entity = mc.getCameraEntity();

        if (!canRender)
        {
            lines = new LinkedList<>();
            toDraw = new LinkedList<>();
            factions = new HashMap<>();
            isFixed = false;
            fixedY = 64;
            drawNames = false;

            colors = new HashMap<>();
            colors.put("SafeZone", 0xFFAA00);
            colors.put("WarZone", 0xAA0000);
            // Don't render before the player has been placed in the actual proper position,
            // otherwise some of the renderers mess up.
            // The magic 8.5, 65, 8.5 comes from the WorldClient constructor
            if (System.currentTimeMillis() - loginTime >= 5000 || entity.getX() != 8.5 || entity.getY() != 65 || entity.getZ() != 8.5)
            {
                canRender = true;
            }
            else
            {
                return;
            }
        }

        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();

        drawNames(cameraPos.x, cameraPos.y, cameraPos.z);
        drawOverlay(mc, cameraPos.x, cameraPos.y, cameraPos.z, matrices);
    }

    public static void drawOverlay(MinecraftClient mc, double dx, double dy, double dz, MatrixStack matrices)
    {
        mc.getProfiler().push("fmo_entities");
        RenderSystem.pushMatrix();
        RenderUtils.disableDiffuseLighting();
        RenderSystem.disableCull();
        RenderUtils.setupBlend();
        RenderSystem.disableTexture();
        RenderUtils.color(1f, 1f, 1f, 1f);
        RenderSystem.glMultiTexCoord2f(GL13.GL_TEXTURE1, 240.0F, 240.0F);
        RenderSystem.depthMask(false);
        boolean foggy = GL11.glIsEnabled(GL11.GL_FOG);
        RenderSystem.disableFog();

        RenderSystem.translated(-dx, -dy, -dz);
        for (Chunk chunk : toDraw)
        {
            Tessellator tessellator = Tessellator.getInstance();
            if (isFixed)
            {
                chunk.shadeChunk(tessellator, fixedY);
            }
            else
            {
                if (Configs.Generic.OVERLAY_CHUNK.getBooleanValue())
                {
                    chunk.shadeChunk(tessellator);
                }
                if (Configs.Generic.OVERLAY_LINE.getBooleanValue())
                {
                    chunk.lineChunk(tessellator);
                }
            }
        }
        if (foggy)
        {
            RenderSystem.enableFog();
        }
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderUtils.enableDiffuseLightingForLevel(matrices);
        RenderSystem.popMatrix();
        mc.getProfiler().pop();
    }

    public static void drawNames(double dx, double dy, double dz)
    {
        for (Chunk chunk : toDraw)
        {
            if (isFixed)
            {
                chunk.drawName(fixedY + 1.6, dx, dy, dz);
            }
            else
            {
                chunk.drawName(dx, dy, dz);
            }
        }
    }

    public static void addLine(String line)
    {
        lines.addLast(line);
    }

    public static boolean parseMap()
    {
        if (lines.size() < 10)
        {
            return false;
        }
        String line = lines.get(0);
        int originX = Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(",")));
        int originZ = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf(")")));
        int nameStart = line.indexOf(")") + 1;
        String originFaction = line.substring(nameStart, line.indexOf("]", nameStart));
        originFaction = originFaction.replaceAll("\u00A7.?", "");
        originFaction = originFaction.trim();

        line = lines.get(18);
        line = line.replaceAll("\u00A7.?", "");
        if (line.length() > 2)
        {
            StringBuilder name = new StringBuilder();
            char character = '"';
            String[] words = line.split(" ");
            for (String word : words)
            {
                if (word.matches(".:"))
                {
                    if (name.length() > 0)
                    {
                        factions.put(character, name.toString());
                        name = new StringBuilder();
                    }
                    character = word.charAt(0);
                }
                else
                {
                    name.append(word);
                }
            }
            factions.put(character, name.toString());
            factions.put('-', "Wilderness");
        }

        if (!originFaction.equals("Wilderness") && !factions.containsValue(originFaction))
        {
            factions.put('+', originFaction);
        }

        int n = factions.size();
        if (factions.size() % 2 == 0)
        {
            n++;
        }
        for (int i = 0; i < factions.size(); i++)
        {
            String name = (String) factions.values().toArray()[i];
            if (!colors.containsKey(name) && !name.equals("SafeZone") && !name.equals("WarZone"))
            {
                colors.put(name, 0xFFFFFF / n * (i + 1));
            }
        }

        for (int z = 0; z < 17; z++)
        {
            String currentLine = lines.get(z + 1);
            currentLine = currentLine.replaceAll("\u00A7.?", "");
            if (currentLine.length() < 39)
            {
                FMapOverlay.logger.error("currentLine: " + currentLine);
                return false;
            }

            int currentZ = originZ - 8 + z;
            for (int x = 0; x < 49; x++)
            {
                if (x < 3 && z < 3)
                {
                    continue;
                }
                int currentX = originX - 24 + x;
                char currentChar = currentLine.charAt(x);
                String name = factions.get(currentChar);
                if (currentZ == originZ && currentX == originX)
                {
                    name = originFaction;
                }
                if (colors.get(name) != null)
                {
                    Chunk toAdd = new Chunk(name, currentX, currentZ, colors.get(name));
                    if (!toDraw.contains(toAdd))
                    {
                        toDraw.addFirst(toAdd);
                    }
                }
            }
        }
        return true;
    }

    public static void reset()
    {
        lines.clear();
        toDraw.clear();
    }

    public static void clearLines()
    {
        lines.clear();
    }

    public static void fix()
    {
        isFixed = true;
        fixedY = MinecraftClient.getInstance().player.getY() - 1.6;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        DataManager.logMessage("Locked faction map overlay at Y = " + df.format(fixedY));
    }

    public static void unFix()
    {
        isFixed = false;
        DataManager.logMessage("Unlocked faction map overlay.");
    }

    public static int getSize()
    {
        if (lines == null)
        {
            return 0;
        }
        return lines.size();
    }

    public static boolean getDrawNames()
    {
        return drawNames;
    }

    public static void setDrawNames(boolean drawName)
    {
        drawNames = drawName;
    }
}