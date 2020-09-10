package eu.minemania.fmapoverlay.event;

import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.data.DataManager;
import eu.minemania.fmapoverlay.render.OverlayRenderer;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class WorldLoadListener implements IWorldLoadListener
{
    @Override
    public void onWorldLoadPre(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        // Save the settings before the integrated server gets shut down
        if (worldBefore != null)
        {
            DataManager.save();
        }
        else
        {
            if (worldAfter != null)
            {
                OverlayRenderer.resetRenderTimeout();
            }
        }
    }

    @Override
    public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc)
    {
        if (worldAfter != null)
        {
            DataManager.load();
        }
    }
}