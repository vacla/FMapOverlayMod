package eu.minemania.fmapoverlay.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import eu.minemania.fmapoverlay.Reference;
import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.render.OverlayRenderer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Map;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FMapOverlayCommand extends FMapOverlayCommandBase
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        ClientCommandManager.addClientSideCommand("fmo");
        LiteralArgumentBuilder<ServerCommandSource> fmo = literal("fmo").executes(FMapOverlayCommand::info)
                .then(literal("on").executes(FMapOverlayCommand::on))
                .then(literal("off").executes(FMapOverlayCommand::off))
                .then(literal("display").executes(FMapOverlayCommand::display))
                .then(literal("clear").executes(FMapOverlayCommand::clear))
                .then(literal("lock").executes(FMapOverlayCommand::fix))
                .then(literal("fix").executes(FMapOverlayCommand::fix))
                .then(literal("unlock").executes(FMapOverlayCommand::unfix))
                .then(literal("unfix").executes(FMapOverlayCommand::unfix))
                .then(literal("names").executes(FMapOverlayCommand::names)
                    .then(argument("enable", bool()).executes(FMapOverlayCommand::names)))
                .then(literal("help").executes(FMapOverlayCommand::help))
                .then(literal("custom")
                    .then(literal("names").executes(FMapOverlayCommand::customNamesEnable)
                        .then(argument("enable", bool()).executes(FMapOverlayCommand::customNamesEnable))
                        .then(argument("height", integer()).executes(FMapOverlayCommand::customNamesHeight)))
                    .then(literal("overlay").executes(FMapOverlayCommand::customOverlayEnable)
                        .then(argument("enable", bool()).executes(FMapOverlayCommand::customOverlayEnable))
                        .then(argument("height", integer()).executes(FMapOverlayCommand::customOverlayHeight))))
                .then(literal("lines").executes(FMapOverlayCommand::lines)
                        .then(argument("enable", bool()).executes(FMapOverlayCommand::lines)))
                .then(literal("chunk").executes(FMapOverlayCommand::chunk)
                        .then(argument("enable", bool()).executes(FMapOverlayCommand::chunk)));
        dispatcher.register(fmo);
    }

    private static int info(CommandContext<ServerCommandSource> context)
    {
        localOutput(context.getSource(), Reference.MOD_NAME + " ["+ Reference.MOD_VERSION+"]");
        localOutputT(context.getSource(), "fmapoverlay.message.command.info");

        return 1;
    }

    private static int on(CommandContext<ServerCommandSource> context)
    {
        Configs.Generic.ENABLED.setBooleanValue(true);
        localOutput(context.getSource(), "Faction Map overlay: ON");
        return 1;
    }

    private static int off(CommandContext<ServerCommandSource> context)
    {
        Configs.Generic.ENABLED.setBooleanValue(false);
        localOutput(context.getSource(), "Faction Map overlay: OFF");
        return 1;
    }

    private static int display(CommandContext<ServerCommandSource> context)
    {
        if(OverlayRenderer.parseMap())
        {
            localOutput(context.getSource(), "Displaying faction map overlay");
        }
        else
        {
            localError(context.getSource(), "Unable to display overlay! Run /f map first");
        }
        return 1;
    }

    private static int fix(CommandContext<ServerCommandSource> context)
    {
        OverlayRenderer.fix();
        return 1;
    }

    private static int unfix(CommandContext<ServerCommandSource> context)
    {
        OverlayRenderer.unFix();
        return 1;
    }

    private static int clear(CommandContext<ServerCommandSource> context)
    {
        OverlayRenderer.reset();
        localOutput(context.getSource(), "Faction map overlay cleared");
        return 1;
    }

    private static int names(CommandContext<ServerCommandSource> context)
    {
        boolean enabled;
        try
        {
            enabled = getBool(context, "enable");
            OverlayRenderer.setDrawNames(enabled);
        }
        catch (Exception e)
        {
            enabled = !OverlayRenderer.getDrawNames();
            OverlayRenderer.setDrawNames(enabled);
        }
        localOutput(context.getSource(), String.format("Drawing names %s", enabled ? "enabled" : "disabled"));
        return 1;
    }

    private static int customNamesEnable(CommandContext<ServerCommandSource> context)
    {
        boolean enabled;
        try
        {
            enabled = getBool(context, "enable");
            Configs.Generic.NAMES_CUSTOM_ENABLE.setBooleanValue(enabled);
        }
        catch (Exception e)
        {
            Configs.Generic.NAMES_CUSTOM_ENABLE.toggleBooleanValue();
            enabled = Configs.Generic.NAMES_CUSTOM_ENABLE.getBooleanValue();
        }
        localOutput(context.getSource(), String.format("custom names %s", enabled ? "enabled" : "disabled"));
        return 1;
    }

    private static int customNamesHeight(CommandContext<ServerCommandSource> context)
    {
        int height;
        try
        {
            height = getInteger(context, "height");
            Configs.Generic.NAMES_CUSTOM_HEIGHT.setIntegerValue(height);
        }
        catch (Exception e)
        {
            height = Configs.Generic.NAMES_CUSTOM_HEIGHT.getIntegerValue();
        }

        localOutput(context.getSource(), "custom names height: " + height);
        return 1;
    }

    private static int customOverlayEnable(CommandContext<ServerCommandSource> context)
    {
        boolean enabled;
        try
        {
            enabled = getBool(context, "enable");
            Configs.Generic.OVERLAY_CUSTOM_ENABLE.setBooleanValue(enabled);
        }
        catch (Exception e)
        {
            Configs.Generic.OVERLAY_CUSTOM_ENABLE.toggleBooleanValue();
            enabled = Configs.Generic.OVERLAY_CUSTOM_ENABLE.getBooleanValue();
        }
        localOutput(context.getSource(), String.format("custom overlay %s", enabled ? "enabled" : "disabled"));
        return 1;
    }

    private static int customOverlayHeight(CommandContext<ServerCommandSource> context)
    {
        int height;
        try
        {
            height = getInteger(context, "height");
            Configs.Generic.OVERLAY_CUSTOM_HEIGHT.setIntegerValue(height);
        }
        catch (Exception e)
        {
            height = Configs.Generic.OVERLAY_CUSTOM_HEIGHT.getIntegerValue();
        }

        localOutput(context.getSource(), "custom overlay height: " + height);
        return 1;
    }

    private static int lines(CommandContext<ServerCommandSource> context)
    {
        boolean enabled;
        try
        {
            enabled = getBool(context, "enable");
            Configs.Generic.OVERLAY_LINE.setBooleanValue(enabled);
        }
        catch (Exception e)
        {
            enabled = !Configs.Generic.OVERLAY_LINE.getBooleanValue();
            Configs.Generic.OVERLAY_LINE.toggleBooleanValue();
        }
        localOutput(context.getSource(), String.format("Overlay lines %s", enabled ? "enabled" : "disabled"));
        return 1;
    }

    private static int chunk(CommandContext<ServerCommandSource> context)
    {
        boolean enabled;
        try
        {
            enabled = getBool(context, "enable");
            Configs.Generic.OVERLAY_CHUNK.setBooleanValue(enabled);
        }
        catch (Exception e)
        {
            enabled = !Configs.Generic.OVERLAY_CHUNK.getBooleanValue();
            Configs.Generic.OVERLAY_CHUNK.toggleBooleanValue();
        }
        localOutput(context.getSource(), String.format("Overlay chunk %s", enabled ? "enabled" : "disabled"));
        return 1;
    }

    private static int help(CommandContext<ServerCommandSource> context)
    {
        localOutputT(context.getSource(), "fmapoverlay.message.command.help", Reference.MOD_NAME, Reference.MOD_VERSION);
        int cmdCount = 0;
        CommandDispatcher<ServerCommandSource> dispatcher = Command.commandDispatcher;
        for(CommandNode<ServerCommandSource> command : dispatcher.getRoot().getChildren())
        {
            String cmdName = command.getName();
            if(ClientCommandManager.isClientSideCommand(cmdName))
            {
                Map<CommandNode<ServerCommandSource>, String> usage = dispatcher.getSmartUsage(command, context.getSource());
                for(String u : usage.values())
                {
                    ClientCommandManager.sendFeedback(new LiteralText("/" + cmdName + " " + u));
                }
                cmdCount += usage.size();
                if(usage.size() == 0)
                {
                    ClientCommandManager.sendFeedback(new LiteralText("/" + cmdName));
                    cmdCount++;
                }
            }
        }
        return cmdCount;
    }
}