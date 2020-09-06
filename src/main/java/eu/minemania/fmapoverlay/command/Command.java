package eu.minemania.fmapoverlay.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

public class Command
{
    public static CommandDispatcher<ServerCommandSource> commandDispatcher;

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        ClientCommandManager.clearClientSideCommands();
        FMapOverlayCommand.register(dispatcher);

        if (MinecraftClient.getInstance().isIntegratedServerRunning())
        {

        }

        commandDispatcher = dispatcher;
    }
}