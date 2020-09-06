package eu.minemania.fmapoverlay.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.minemania.fmapoverlay.Reference;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.io.File;

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
        public static final ConfigBoolean ENABLED = new ConfigBoolean("enabled", true, "fmapoverlay.description.config.enabled");
        public static final ConfigBoolean NAMES_CUSTOM_ENABLE = new ConfigBoolean("namesCustomEnable", true, "fmapoverlay.description.config.names_enable");
        public static final ConfigInteger NAMES_CUSTOM_HEIGHT = new ConfigInteger("namesCustomHeight", 62, 1, 255, "fmapoverlay.description.config.names_height");
        public static final ConfigBoolean OVERLAY_CUSTOM_ENABLE = new ConfigBoolean("overlayCustomEnable", true, "fmapoverlay.description.config.overlay_enable");
        public static final ConfigInteger OVERLAY_CUSTOM_HEIGHT = new ConfigInteger("overlayCustomHeight", 62, 1, 255, "fmapoverlay.description.config.overlay_height");
        public static final ConfigBoolean OVERLAY_CHUNK = new ConfigBoolean("overlayChunk", false, "fmapoverlay.description.config.overlay_chunk");
        public static final ConfigBoolean OVERLAY_LINE = new ConfigBoolean("overlayLine", false, "fmapoverlay.description.config.overlay_line");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                ENABLED,
                NAMES_CUSTOM_ENABLE,
                NAMES_CUSTOM_HEIGHT,
                OVERLAY_CUSTOM_ENABLE,
                OVERLAY_CUSTOM_HEIGHT,
                OVERLAY_LINE,
                OVERLAY_CHUNK
                );
    }

    /**
     * Loads configurations from configuration file.
     */
    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if(configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if(element != null && element.isJsonObject())
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
        File dir = FileUtils.getConfigDirectory();

        if((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
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