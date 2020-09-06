package eu.minemania.fmapoverlay.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

/**
 * Default hotkeys configuration.
 */
public class Hotkeys
{
    public static final ConfigHotkey LOADMAP = new ConfigHotkey("loadMap", "U",  "fmapoverlay.description.hotkey.loadmap");
    public static final ConfigHotkey NAMES_CUSTOM_ENABLE = new ConfigHotkey("namesCustomEnable", "", "fmapoverlay.description.hotkey.custom_names");
    public static final ConfigHotkey NAMES_CUSTOM_HEIGHT_DOWN = new ConfigHotkey("namesCustomHeightDown", "", "fmapoverlay.description.hotkey.names_height_down");
    public static final ConfigHotkey NAMES_CUSTOM_HEIGHT_UP = new ConfigHotkey("namesCustomHeightUp", "", "fmapoverlay.description.hotkey.names_height_up");
    public static final ConfigHotkey OPEN_GUI_SETTINGS = new ConfigHotkey("openGuiSettings", "Y,C",  "fmapoverlay.description.hotkey.open_gui_settings");
    public static final ConfigHotkey OVERLAY_CUSTOM_ENABLE = new ConfigHotkey("overlayCustomEnable", "", "fmapoverlay.description.hotkey.overlay_enabled");
    public static final ConfigHotkey OVERLAY_CUSTOM_HEIGHT_DOWN = new ConfigHotkey("overlayCustomHeightDown", "", "fmapoverlay.description.hotkey.overlay_height_down");
    public static final ConfigHotkey OVERLAY_CUSTOM_HEIGHT_UP = new ConfigHotkey("overlayCustomHeightUp", "", "fmapoverlay.description.hotkey.overlay_height_up");
    public static final ConfigHotkey OVERLAY_CHUNK = new ConfigHotkey("overlayChunk", "", "fmapoverlay.description.hotkey.overlay_chunk");
    public static final ConfigHotkey OVERLAY_LINE = new ConfigHotkey("overlayLine", "", "fmapoverlay.description.hotkey.overlay_line");
    public static final ConfigHotkey RESET = new ConfigHotkey("reset", "J",  "fmapoverlay.description.hotkey.reset");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            LOADMAP,
            NAMES_CUSTOM_ENABLE,
            NAMES_CUSTOM_HEIGHT_DOWN,
            NAMES_CUSTOM_HEIGHT_UP,
            OPEN_GUI_SETTINGS,
            OVERLAY_CUSTOM_ENABLE,
            OVERLAY_CUSTOM_HEIGHT_DOWN,
            OVERLAY_CUSTOM_HEIGHT_UP,
            OVERLAY_CHUNK,
            OVERLAY_LINE,
            RESET
            );
}