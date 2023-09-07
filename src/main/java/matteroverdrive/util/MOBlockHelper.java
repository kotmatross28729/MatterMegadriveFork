package matteroverdrive.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MOBlockHelper {
    public static final int[][] SIDE_COORD_MOD = new int[][]{{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};
    public static final byte[] SIDE_LEFT = new byte[]{(byte) 4, (byte) 5, (byte) 5, (byte) 4, (byte) 2, (byte) 3};
    public static final byte[] SIDE_RIGHT = new byte[]{(byte) 5, (byte) 4, (byte) 4, (byte) 5, (byte) 3, (byte) 2};
    public static final byte[] SIDE_OPPOSITE = new byte[]{(byte) 1, (byte) 0, (byte) 3, (byte) 2, (byte) 5, (byte) 4};
    public static final byte[] SIDE_ABOVE = new byte[]{(byte) 3, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1};
    public static final byte[] SIDE_BELOW = new byte[]{(byte) 2, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 0};

    public static int getLeftSide(int side) {
        return SIDE_LEFT[side];
    }

    public static int getRightSide(int side) {
        return SIDE_RIGHT[side];
    }

    public static int getOppositeSide(int side) {
        return SIDE_OPPOSITE[side];
    }

    public static int getAboveSide(int side) {
        return SIDE_ABOVE[side];
    }

    public static int getBelowSide(int side) {
        return SIDE_BELOW[side];
    }

    public static int determineXZPlaceFacing(EntityLivingBase placer) {
        int rotation = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (rotation) {
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 3;
        }
    }

    public static TileEntity getAdjacentTileEntity(World world, int x, int y, int z, ForgeDirection direction) {
        x += direction.offsetX;
        y += direction.offsetY;
        z += direction.offsetZ;
        return world != null && world.blockExists(x, y, z) ? world.getTileEntity(x, y, z) : null;
    }

    public static TileEntity getAdjacentTileEntity(World world, int x, int y, int z, int side) {
        return world == null ? null : getAdjacentTileEntity(world, x, y, z, ForgeDirection.values()[side]);
    }

    public static TileEntity getAdjacentTileEntity(TileEntity tileEntity, ForgeDirection direction) {
        return tileEntity == null ? null : getAdjacentTileEntity(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, direction);
    }

    public static TileEntity getAdjacentTileEntity(TileEntity tileEntity, int direction) {
        return tileEntity == null ? null : getAdjacentTileEntity(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, ForgeDirection.values()[direction]);
    }

    public static final class RotationType {
        public static final int PREVENT = -1;
        public static final int FOUR_WAY = 1;
        public static final int SIX_WAY = 2;
        public static final int RAIL = 3;
        public static final int PUMPKIN = 4;
        public static final int STAIRS = 5;
        public static final int REDSTONE = 6;
        public static final int LOG = 7;
        public static final int SLAB = 8;
        public static final int CHEST = 9;
        public static final int LEVER = 10;
        public static final int SIGN = 11;

        public RotationType() {
        }
    }
}
