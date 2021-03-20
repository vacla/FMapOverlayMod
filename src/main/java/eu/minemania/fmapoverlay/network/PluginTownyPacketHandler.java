package eu.minemania.fmapoverlay.network;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import eu.minemania.fmapoverlay.FMapOverlay;
import eu.minemania.fmapoverlay.config.Configs;
import eu.minemania.fmapoverlay.data.DataManager;
import eu.minemania.fmapoverlay.data.TownyData;
import eu.minemania.fmapoverlay.render.OverlayRenderer;
import fi.dy.masa.malilib.network.IPluginChannelHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.*;

public class PluginTownyPacketHandler implements IPluginChannelHandler
{
    public static final List<Identifier> CHANNELS = ImmutableList.of(new Identifier("fmapoverlay:towny"));

    public static final PluginTownyPacketHandler INSTANCE = new PluginTownyPacketHandler();

    private boolean registered;

    public void reset()
    {
        registered = false;
    }

    @Override
    public List<Identifier> getChannels()
    {
        return CHANNELS;
    }

    @Override
    public void onPacketReceived(PacketByteBuf buf)
    {
        boolean debugEnabled = Configs.Generic.DEBUG.getBooleanValue();
        if (debugEnabled)
        {
            System.out.println("not registered");
            System.out.println(buf.capacity());
            System.out.println(buf.toString(Charsets.UTF_8));
        }
        if (!buf.toString(Charsets.UTF_8).isEmpty())
        {
            this.registered = true;
        }

        if (this.registered)
        {
            if (debugEnabled)
            {
                System.out.println("registered");
            }
            CompoundTag compoundTag = buf.readCompoundTag();

            if (compoundTag != null)
            {
                DataManager.setTownyPlugin(new TownyData(compoundTag));
                this.setMapLines(DataManager.getTownyPlugin().getMap(), debugEnabled);
                this.setHelpType(DataManager.getTownyPlugin().getTownHelpTypes(), DataManager.getTownyPlugin().getTownBlockTypes(), debugEnabled);
                OverlayRenderer.setChunkCoords(DataManager.getTownyPlugin().getChunkCoords());
                if (debugEnabled)
                {
                    this.renderMapHelpType();
                    DataManager.getTownyPlugin().debug();
                }
            }
        }
    }

    private void setMapLines(ListTag listTag, boolean debugEnabled)
    {
        if (debugEnabled)
        {
            System.out.println("test MapLines");
            System.out.println("test 1: " + Arrays.toString(listTag.toArray()));
            System.out.println("test 2: " + listTag.toText().getString());
            System.out.println(listTag.size());
            System.out.println("test 3");
            for (int i = 0; i < DataManager.getTownyPlugin().getLineWidth(); i++)
            {
                System.out.println(listTag.get(i));
            }
            System.out.println("endtest 3");
            System.out.println("test 4");
        }
        try
        {
            if (debugEnabled)
            {
                System.out.println("part 1");
                for (int i = DataManager.getTownyPlugin().getLineWidth() - 1; i >= 0; i--)
                {
                    for (int j = 0; j < DataManager.getTownyPlugin().getLineHeight(); j++)
                    {
                        System.out.print(((ListTag) listTag.get(i)).get(j));
                    }
                    System.out.println();
                }
                System.out.println("part 2");
            }
            List<String> listRows = new ArrayList<>();
            for (int i = 0; i < DataManager.getTownyPlugin().getLineHeight(); i++)
            {
                StringBuilder rows = new StringBuilder();
                for (int j = DataManager.getTownyPlugin().getLineWidth() - 1; j >= 0; j--)
                {
                    String row = ((ListTag)listTag.get(j)).get(i).asString();
                    if (j != 0) {
                        row = row.concat(" ");
                    }
                    rows.append(row);
                }
                listRows.add(rows.toString());
                if (debugEnabled)
                {
                    System.out.println(rows.toString());
                    System.out.println();
                }
            }
            OverlayRenderer.setListRows(listRows);
            OverlayRenderer.setIsTowny(true);
        }
        catch (Exception e)
        {
            FMapOverlay.logger.warn(e.getMessage());
            DataManager.logError("Map couldn't get parsed, please enable debug in the config and rerun command, go to FMapOverlay github and make an issue with the latest log");
        }
        if (debugEnabled)
        {
            System.out.println("endtest 4");
            System.out.println("test 5");
            for (String row : OverlayRenderer.getListRows())
            {
                System.out.println(row);
            }
            System.out.println("endtest 5");
            System.out.println("endtest MapLines");
        }
    }

    private void setHelpType(ListTag helpTypes, ListTag blockTypes, boolean debugEnabled)
    {
        if (debugEnabled)
        {
            System.out.println("test HelpType");
        }
        Map<String, String> typeArray = new HashMap<>();
        for (Tag helpType : helpTypes)
        {
            StringTag type = (StringTag) helpType;
            String typeData = type.asString().trim();
            String[] data = typeData.split(" = ");
            typeArray.put(data[0].substring(0, 3), data[1]);
        }
        for (Tag blockType : blockTypes)
        {
            ListTag types = (ListTag) blockType;
            if (!types.getString(1).equals("Spleef"))
            {
                typeArray.put("§a" + types.getString(0), types.getString(1));
                if (debugEnabled)
                {
                    System.out.println("Types: " + types.getString(0) + " " + types.getString(1));
                }
            }
        }
        typeArray.put("§aH", "Home");
        typeArray.put("§6-", "Unclaimed");
        if (debugEnabled)
        {
            System.out.println("test 1");
            for (Map.Entry<String, String> thing : typeArray.entrySet())
            {
                System.out.println("key : " + thing.getKey() + " value: " + thing.getValue());
            }
            System.out.println("endtest 1");
            System.out.println("endtest HelpType");
        }
        OverlayRenderer.setTypeArray(typeArray);
    }

    private void renderMapHelpType()
    {
        List<String> lists = new ArrayList<>();
        for (String row : OverlayRenderer.getListRows())
        {
            StringBuilder rows = new StringBuilder();
            for (String entry : row.split(" "))
            {
                if (entry.contains("§6-"))
                {
                    entry = entry.replace("§6-","§8-");
                }
                if (OverlayRenderer.getTypeArray().get(entry) == null)
                {
                    System.out.println(entry);
                }
                rows.append(OverlayRenderer.getTypeArray().get(entry)).append(" ");
            }
            lists.add(rows.toString().trim());
        }
        for (String list : lists)
        {
            System.out.println(list);
        }
    }
}
