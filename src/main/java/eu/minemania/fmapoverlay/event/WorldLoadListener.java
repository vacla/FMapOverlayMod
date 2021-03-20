package eu.minemania.fmapoverlay.event;

import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.data.DataManager;
import eu.minemania.fmapoverlay.network.ClientPacketChannelHandler;
import eu.minemania.fmapoverlay.network.PluginTownyPacketHandler;
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
            if (worldAfter == null)
            {
                ClientPacketChannelHandler.getInstance().unregisterClientChannelHandler(PluginTownyPacketHandler.INSTANCE);
                PluginTownyPacketHandler.INSTANCE.reset();
            }
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
        if (worldBefore == null && worldAfter != null && Configs.Generic.ENABLED.getBooleanValue())
        {
            ClientPacketChannelHandler.getInstance().registerClientChannelHandler(PluginTownyPacketHandler.INSTANCE);
        }
        if (worldAfter != null)
        {
            DataManager.load();
        }
    }
}