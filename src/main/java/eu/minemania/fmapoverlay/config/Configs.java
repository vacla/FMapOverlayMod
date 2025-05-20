package eu.minemania.fmapoverlay.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.minemania.fmapoverlay.Reference;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class Configs implements IConfigHandler
{
    /**
     * Config file for mod.
     */
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    /**
     * Default Generic configuration.
     */
    public static class Generic
    {
        public static final ConfigBoolean DEBUG = new ConfigBoolean("debug", false, "fmapoverlay.config.debug.description");
        public static final ConfigBoolean ENABLED = new ConfigBoolean("enabled", true, "fmapoverlay.config.enabled.description");
        public static final ConfigInteger NAMES_CUSTOM_HEIGHT = new ConfigInteger("namesCustomHeight", 79, 1, 255, "fmapoverlay.config.names_height.description");
        public static final ConfigBoolean NAMES_CUSTOM_HEIGHT_ENABLE = new ConfigBoolean("namesCustomHeightEnable", true, "fmapoverlay.config.names_height_enable.description");
        public static final ConfigInteger OVERLAY_CUSTOM_ALPHA_CHUNK = new ConfigInteger("overlayCustomAlphaChunk", 80, 0, 255, "fmapoverlay.config.overlay_alpha_chunk.description");
        public static final ConfigInteger OVERLAY_CUSTOM_ALPHA_EDGE = new ConfigInteger("overlayCustomAlphaEdge", 200, 0, 255, "fmapoverlay.config.overlay_alpha_edge.description");
        public static final ConfigBoolean OVERLAY_CUSTOM_ALPHA_ENABLE = new ConfigBoolean("overlayCustomAlphaEnable", false, "fmapoverlay.config.overlay_alpha_enable.description");
        public static final ConfigInteger OVERLAY_CUSTOM_ALPHA_LINE = new ConfigInteger("overlayCustomAlphaLine", 200, 0, 255, "fmapoverlay.config.overlay_alpha_line.description");
        public static final ConfigInteger OVERLAY_CUSTOM_HEIGHT = new ConfigInteger("overlayCustomHeight", 80, 1, 255, "fmapoverlay.config.overlay_height.description");
        public static final ConfigBoolean OVERLAY_CUSTOM_HEIGHT_ENABLE = new ConfigBoolean("overlayCustomHeightEnable", true, "fmapoverlay.config.overlay_height_enable.description");
        public static final ConfigBoolean OVERLAY_CHUNK = new ConfigBoolean("overlayChunk", true, "fmapoverlay.config.overlay_chunk.description");
        public static final ConfigBoolean OVERLAY_EDGE = new ConfigBoolean("overlayEdge", false, "fmapoverlay.config.overlay_edge.description");
        public static final ConfigBoolean OVERLAY_LINE = new ConfigBoolean("overlayLine", false, "fmapoverlay.config.overlay_line.description");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                DEBUG,
                ENABLED,
                NAMES_CUSTOM_HEIGHT,
                NAMES_CUSTOM_HEIGHT_ENABLE,
                OVERLAY_CUSTOM_ALPHA_CHUNK,
                OVERLAY_CUSTOM_ALPHA_EDGE,
                OVERLAY_CUSTOM_ALPHA_ENABLE,
                OVERLAY_CUSTOM_ALPHA_LINE,
                OVERLAY_CUSTOM_HEIGHT_ENABLE,
                OVERLAY_CUSTOM_HEIGHT,
                OVERLAY_CHUNK,
                OVERLAY_EDGE,
                OVERLAY_LINE
        );
    }

    /**
     * Loads configurations from configuration file.
     */
    public static void loadFromFile()
    {
        Path configFile = FileUtils.getConfigDirectoryAsPath().resolve(CONFIG_FILE_NAME);

        if (Files.exists(configFile) && Files.isReadable(configFile))
        {
            JsonElement element = JsonUtils.parseJsonFileAsPath(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
            }
        }
    }

    /**
     * Saves configurations to configuration file.
     */
    public static void saveToFile()
    {
        Path dir = FileUtils.getConfigDirectoryAsPath();

        if (!Files.exists(dir))
        {
            FileUtils.createDirectoriesIfMissing(dir);
        }

        if (Files.isDirectory(dir))
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFileAsPath(root, dir.resolve(CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }
}