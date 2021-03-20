package eu.minemania.fmapoverlay.data;

import fi.dy.masa.malilib.util.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Arrays;

public class TownyData
{
    String ownerStatus, town;
    int lineHeight, lineWidth;
    int[] chunkCoords;
    ListTag townHelpTypes, townBlockTypes, map;

    public TownyData(CompoundTag compoundTag)
    {
        ownerStatus = compoundTag.getString("ownerStatus");
        town = compoundTag.getString("town");
        lineHeight = compoundTag.getInt("lineHeight");
        lineWidth = compoundTag.getInt("lineWidth");
        map = compoundTag.getList("map", Constants.NBT.TAG_LIST);
        townBlockTypes = compoundTag.getList("townBlockTypes", Constants.NBT.TAG_LIST);
        townHelpTypes = compoundTag.getList("townHelpTypes", Constants.NBT.TAG_STRING);
        chunkCoords = compoundTag.getIntArray("chunkCoords");
    }

    public int getLineHeight()
    {
        return lineHeight;
    }

    public int getLineWidth()
    {
        return lineWidth;
    }

    public String getTown()
    {
        return town;
    }

    public String getOwnerStatus()
    {
        return ownerStatus;
    }

    public ListTag getTownHelpTypes()
    {
        return townHelpTypes;
    }

    public ListTag getTownBlockTypes()
    {
        return townBlockTypes;
    }

    public ListTag getMap()
    {
        return map;
    }

    public int[] getChunkCoords()
    {
        return chunkCoords;
    }

    /**
     * Debug only
     */
    public void debug()
    {
        System.out.println("ownerStatus: "+getOwnerStatus());
        System.out.println("town: "+getTown());
        System.out.println("map: "+getMap());
        System.out.println("townHelpType: "+getTownHelpTypes());
        System.out.println("townBlockType: "+getTownBlockTypes());
        System.out.println("lineWidth: "+getLineWidth());
        System.out.println("lineHeight: "+getLineHeight());
        System.out.println("chunkCoords: "+ Arrays.toString(getChunkCoords()));
    }
}
