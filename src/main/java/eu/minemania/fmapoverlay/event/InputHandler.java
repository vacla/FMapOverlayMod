package eu.minemania.fmapoverlay.event;

import eu.minemania.fmapoverlay.Reference;
import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.config.Hotkeys;
import eu.minemania.fmapoverlay.data.DataManager;
import eu.minemania.fmapoverlay.render.OverlayRenderer;
import fi.dy.masa.malilib.hotkeys.*;
import net.minecraft.client.MinecraftClient;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler
{
    private static final InputHandler INSTANCE = new InputHandler();
    private boolean justPressed;

    private InputHandler()
    {
    }

    public static InputHandler getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager)
    {
        for(IHotkey hotkey : Hotkeys.HOTKEY_LIST)
        {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager)
    {
        manager.addHotkeysForCategory(Reference.MOD_NAME, "fmapoverlay.hotkeys.category.generic_hotkeys", Hotkeys.HOTKEY_LIST);
    }

    @Override
    public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState)
    {
        if (eventKeyState)
        {
            MinecraftClient mc = MinecraftClient.getInstance();
            if(mc.player == null || mc.world == null)
            {
                return false;
            }
            if(Configs.Generic.ENABLED.getBooleanValue())
            {
                if (Hotkeys.LOADMAP.getKeybind().isPressed())
                {
                    if(Hotkeys.RESET.getKeybind().isKeybindHeld())
                    {
                        OverlayRenderer.reset();
                    }
                    else
                    {
                        if(!this.justPressed)
                        {
                            DataManager.logMessage("Auto-running /f map... press again to display overlay");
                            mc.player.sendChatMessage("/f map");
                            this.justPressed = true;
                        }
                        else
                        {
                            DataManager.logMessage("Displaying faction map overlay...");
                            if(!OverlayRenderer.parseMap())
                            {
                                DataManager.logError("Error in displaying faction map overlay!");
                            }
                            this.justPressed = false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}