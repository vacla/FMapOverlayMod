package eu.minemania.fmapoverlay.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

/**
 * Default hotkeys configuration.
 */
public class Hotkeys
{
    public static final ConfigHotkey LOADMAP = new ConfigHotkey("loadMap", "U", "fmapoverlay.hotkey.loadmap.description");
    public static final ConfigHotkey NAMES_CUSTOM_ENABLE = new ConfigHotkey("namesCustomEnable", "", "fmapoverlay.hotkey.custom_names.description");
    public static final ConfigHotkey NAMES_CUSTOM_HEIGHT_DOWN = new ConfigHotkey("namesCustomHeightDown", "", "fmapoverlay.hotkey.names_height_down.description");
    public static final ConfigHotkey NAMES_CUSTOM_HEIGHT_UP = new ConfigHotkey("namesCustomHeightUp", "", "fmapoverlay.hotkey.names_height_up.description");
    public static final ConfigHotkey OPEN_GUI_SETTINGS = new ConfigHotkey("openGuiSettings", "Y,C", "fmapoverlay.hotkey.open_gui_settings.description");
    public static final ConfigHotkey OVERLAY_CUSTOM_HEIGHT_ENABLE = new ConfigHotkey("overlayCustomHeightEnable", "", "fmapoverlay.hotkey.overlay_height_enable.description");
    public static final ConfigHotkey OVERLAY_CUSTOM_HEIGHT_DOWN = new ConfigHotkey("overlayCustomHeightDown", "", "fmapoverlay.hotkey.overlay_height_down.description");
    public static final ConfigHotkey OVERLAY_CUSTOM_HEIGHT_UP = new ConfigHotkey("overlayCustomHeightUp", "", "fmapoverlay.hotkey.overlay_height_up.description");
    public static final ConfigHotkey OVERLAY_CHUNK = new ConfigHotkey("overlayChunk", "", "fmapoverlay.hotkey.overlay_chunk.description");
    public static final ConfigHotkey OVERLAY_EDGE = new ConfigHotkey("overlayEdge", "", "fmapoverlay.hotkey.overlay_edge.description");
    public static final ConfigHotkey OVERLAY_LINE = new ConfigHotkey("overlayLine", "", "fmapoverlay.hotkey.overlay_line.description");
    public static final ConfigHotkey RESET = new ConfigHotkey("reset", "J", "fmapoverlay.hotkey.reset.description");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            LOADMAP,
            NAMES_CUSTOM_ENABLE,
            NAMES_CUSTOM_HEIGHT_DOWN,
            NAMES_CUSTOM_HEIGHT_UP,
            OPEN_GUI_SETTINGS,
            OVERLAY_CUSTOM_HEIGHT_ENABLE,
            OVERLAY_CUSTOM_HEIGHT_DOWN,
            OVERLAY_CUSTOM_HEIGHT_UP,
            OVERLAY_CHUNK,
            OVERLAY_EDGE,
            OVERLAY_LINE,
            RESET
    );
}