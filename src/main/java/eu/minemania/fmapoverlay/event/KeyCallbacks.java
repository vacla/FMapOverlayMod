package eu.minemania.fmapoverlay.event;

import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.config.Hotkeys;
import eu.minemania.fmapoverlay.gui.GuiConfigs;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.MinecraftClient;

public class KeyCallbacks
{
    public static void init(MinecraftClient mc)
    {	
        IHotkeyCallback callbackHotkeys = new KeyCallbackHotkeys(mc);

        Hotkeys.NAMES_CUSTOM_ENABLE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.NAMES_CUSTOM_HEIGHT_DOWN.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.NAMES_CUSTOM_HEIGHT_UP.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OPEN_GUI_SETTINGS.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_CUSTOM_HEIGHT_ENABLE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_CUSTOM_HEIGHT_DOWN.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_CUSTOM_HEIGHT_UP.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_CHUNK.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_EDGE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OVERLAY_LINE.getKeybind().setCallback(callbackHotkeys);
    }

    private static class KeyCallbackHotkeys implements IHotkeyCallback
    {
        private final MinecraftClient mc;

        public KeyCallbackHotkeys(MinecraftClient mc)
        {
            this.mc = mc;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key)
        {
            if(this.mc.player == null || this.mc.world == null)
            {
                return false;
            }
            if(key == Hotkeys.OPEN_GUI_SETTINGS.getKeybind())
            {
                GuiBase.openGui(new GuiConfigs());
                return true;
            }
            else if (key == Hotkeys.NAMES_CUSTOM_ENABLE.getKeybind())
            {
                Configs.Generic.NAMES_CUSTOM_HEIGHT_ENABLE.toggleBooleanValue();
                return true;
            }
            else if (key == Hotkeys.NAMES_CUSTOM_HEIGHT_DOWN.getKeybind())
            {
                int height = Configs.Generic.NAMES_CUSTOM_HEIGHT.getIntegerValue() - 1;
                if(height > 0)
                {
                    Configs.Generic.NAMES_CUSTOM_HEIGHT.setIntegerValue(height);
                }
                return true;
            }
            else if (key == Hotkeys.NAMES_CUSTOM_HEIGHT_UP.getKeybind())
            {
                int height = Configs.Generic.NAMES_CUSTOM_HEIGHT.getIntegerValue() + 1;
                if(height < 256)
                {
                    Configs.Generic.NAMES_CUSTOM_HEIGHT.setIntegerValue(height);
                }
                return true;
            }
            else if (key == Hotkeys.OVERLAY_CUSTOM_HEIGHT_ENABLE.getKeybind())
            {
                Configs.Generic.OVERLAY_CUSTOM_HEIGHT_ENABLE.toggleBooleanValue();
                return true;
            }
            else if (key == Hotkeys.OVERLAY_CUSTOM_HEIGHT_DOWN.getKeybind())
            {
                int height = Configs.Generic.OVERLAY_CUSTOM_HEIGHT.getIntegerValue() - 1;
                if(height > 0)
                {
                    Configs.Generic.OVERLAY_CUSTOM_HEIGHT.setIntegerValue(height);
                }
                return true;
            }
            else if (key == Hotkeys.OVERLAY_CUSTOM_HEIGHT_UP.getKeybind())
            {
                int height = Configs.Generic.OVERLAY_CUSTOM_HEIGHT.getIntegerValue() + 1;
                if(height < 256)
                {
                    Configs.Generic.OVERLAY_CUSTOM_HEIGHT.setIntegerValue(height);
                }
                return true;
            }
            else if (key == Hotkeys.OVERLAY_CHUNK.getKeybind())
            {
                Configs.Generic.OVERLAY_CHUNK.toggleBooleanValue();
                return true;
            }
            else if (key == Hotkeys.OVERLAY_EDGE.getKeybind())
            {
                Configs.Generic.OVERLAY_EDGE.toggleBooleanValue();
                return true;
            }
            else if (key == Hotkeys.OVERLAY_LINE.getKeybind())
            {
                Configs.Generic.OVERLAY_LINE.toggleBooleanValue();
                return true;
            }
            return false;
        }
    }
}