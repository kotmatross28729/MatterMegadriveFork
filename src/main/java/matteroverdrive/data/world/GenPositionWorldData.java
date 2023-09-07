package matteroverdrive.data.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenPositionWorldData extends WorldSavedData {
    Map<String, List<WorldPosition2D>> positions;

    public GenPositionWorldData(String name) {
        super(name);
        positions = new HashMap<>();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        for (Object key : nbtTagCompound.func_150296_c()) {
            List<WorldPosition2D> pos2D = new ArrayList<>();
            NBTTagList tagList = nbtTagCompound.getTagList(key.toString(), Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < tagList.tagCount(); i++) {
                pos2D.add(new WorldPosition2D(tagList.getCompoundTagAt(i)));
            }
            positions.put(key.toString(), pos2D);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        for (Map.Entry<String, List<WorldPosition2D>> entry : positions.entrySet()) {
            NBTTagList tagList = new NBTTagList();
            for (WorldPosition2D worldPosition2D : entry.getValue()) {
                NBTTagCompound worldPositionTag = new NBTTagCompound();
                worldPosition2D.writeToNBT(worldPositionTag);
                tagList.appendTag(worldPositionTag);
            }
            nbtTagCompound.setTag(entry.getKey(), tagList);
        }
    }

    public boolean isFarEnough(String name, int x, int y, int distance) {
        List<WorldPosition2D> positions = this.positions.get(name);
        if (positions != null) {
            for (WorldPosition2D worldPosition2D : positions) {
                if (worldPosition2D.manhattanDistance(x, y) < distance) {
                    return false;
                }
            }
        }
        return true;
    }

    public double getNearestDistance(String name, Vec3 pos) {
        List<WorldPosition2D> positions = this.positions.get(name);
        double lastDistance = -1;
        double tempDist;

        if (positions != null) {
            for (WorldPosition2D worldPosition2D : positions) {
                tempDist = Vec3.createVectorHelper(worldPosition2D.x, pos.yCoord, worldPosition2D.z).distanceTo(pos);
                if (lastDistance < 0 || tempDist < lastDistance) {
                    lastDistance = tempDist;
                }
            }
        }
        return lastDistance;
    }

    public void addPosition(String name, WorldPosition2D position2D) {
        List<WorldPosition2D> pos = positions.get(name);
        if (pos == null) {
            pos = new ArrayList<>();
            positions.put(name, pos);
        }
        pos.add(position2D);
        markDirty();
    }

    public List<WorldPosition2D> getPositions(String name) {
        return positions.get(name);
    }
}
