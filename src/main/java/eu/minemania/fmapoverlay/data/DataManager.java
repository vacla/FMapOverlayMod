package eu.minemania.fmapoverlay.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import eu.minemania.fmapoverlay.FMapOverlay;
import eu.minemania.fmapoverlay.Reference;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import eu.minemania.fmapoverlay.gui.GuiConfigs.ConfigGuiTab;

import java.nio.file.Files;
import java.nio.file.Path;

public class DataManager
{
    private static boolean canSave;

    private static ConfigGuiTab configGuiTab = ConfigGuiTab.GENERIC;

    public static ConfigGuiTab getConfigGuiTab()
    {
        return configGuiTab;
    }

    public static void setConfigGuiTab(ConfigGuiTab tab)
    {
        configGuiTab = tab;
    }

    private static boolean justPressed;

    /**
     * Logs the message to the user
     *
     * @param message The message to log
     */
    public static void logMessage(String message)
    {
        MutableText displayMessage = Text.literal(message);
        displayMessage.formatted(Formatting.GREEN);
        MinecraftClient.getInstance().player.sendMessage(displayMessage, false);
    }

    /**
     * Logs the error message to the user
     *
     * @param message The error message to log
     */
    public static void logError(String message)
    {
        MutableText displayMessage = Text.literal(message);
        displayMessage.formatted(Formatting.RED);
        MinecraftClient.getInstance().player.sendMessage(displayMessage, false);
    }

    public static void load()
    {
        Path file = getCurrentStorageFile();

        JsonElement element = JsonUtils.parseJsonFileAsPath(file);

        if (element != null && element.isJsonObject())
        {

            JsonObject root = element.getAsJsonObject();

            if (JsonUtils.hasString(root, "config_gui_tab"))
            {
                try
                {
                    configGuiTab = ConfigGuiTab.valueOf(root.get("config_gui_tab").getAsString());
                }
                catch (Exception ignored)
                {
                }

                if (configGuiTab == null)
                {
                    configGuiTab = ConfigGuiTab.GENERIC;
                }
            }
        }

        canSave = true;
    }

    public static void save()
    {
        save(false);
    }

    public static void save(boolean forceSave)
    {
        if (!canSave && !forceSave)
        {
            return;
        }

        JsonObject root = new JsonObject();

        root.add("config_gui_tab", new JsonPrimitive(configGuiTab.name()));

        Path file = getCurrentStorageFile();
        JsonUtils.writeJsonToFileAsPath(root, file);

        canSave = false;
    }

    private static Path getCurrentStorageFile()
    {
        Path dir = getCurrentConfigDirectory();

        if (!Files.exists(dir))
        {
            FileUtils.createDirectoriesIfMissing(dir);
        }

        if (!Files.isDirectory(dir))
        {
            FMapOverlay.logger.warn("Failed to create the config directory '{}'", dir.toAbsolutePath());
        }

        return dir.resolve(getStorageFileName());
    }

    private static String getStorageFileName()
    {
        String name = StringUtils.getWorldOrServerName();

        if (name == null)
        {
            return Reference.MOD_ID + "_default.json";
        }

        return Reference.MOD_ID + "_" + name + ".json";
    }

    public static Path getCurrentConfigDirectory()
    {
        return FileUtils.getConfigDirectoryAsPath().resolve(Reference.MOD_ID);
    }

    public static boolean getJustPressed()
    {
        return justPressed;
    }

    public static void setJustPressed(boolean pressed)
    {
        justPressed = pressed;
    }
}
