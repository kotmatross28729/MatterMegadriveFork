package matteroverdrive.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.blocks.includes.MOBlock;
import matteroverdrive.client.render.IconConnectedTexture;
import matteroverdrive.util.MOBlockHelper;
import matteroverdrive.util.math.MOMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockCT extends MOBlock {
    protected IconConnectedTexture iconConnectedTexture;

    public BlockCT(Material material, String name) {
        super(material, name);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        this.iconConnectedTexture = new IconConnectedTexture(this.blockIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        iconConnectedTexture.setType(0);
        return iconConnectedTexture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int sideId) {
        int type = 0;
        if (isSideCT(world, x, y, z, sideId)) {
            ForgeDirection side = ForgeDirection.getOrientation(sideId);
            if (side != ForgeDirection.UP && side != ForgeDirection.DOWN) {
                ForgeDirection direction = ForgeDirection.getOrientation(MOBlockHelper.getAboveSide(sideId));
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 0, true);
                direction = ForgeDirection.getOrientation(MOBlockHelper.getBelowSide(sideId));
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 1, true);
                direction = ForgeDirection.getOrientation(MOBlockHelper.getLeftSide(sideId));
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 2, true);
                direction = ForgeDirection.getOrientation(MOBlockHelper.getRightSide(sideId));
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 3, true);
            } else {
                ForgeDirection direction = ForgeDirection.NORTH;
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 0, true);
                direction = ForgeDirection.SOUTH;
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 1, true);
                direction = ForgeDirection.WEST;
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 2, true);
                direction = ForgeDirection.EAST;
                if (canConnect(world, world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ), x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ))
                    type = MOMathHelper.setBoolean(type, 3, true);
            }
        }

        IconConnectedTexture icon = getIconConnectedTexture(world.getBlockMetadata(x, y, z), sideId);
        if (icon != null)
            icon.setType(type);
        return icon;
    }

    public abstract boolean canConnect(IBlockAccess world, Block block, int x, int y, int z);

    public abstract boolean isSideCT(IBlockAccess world, int x, int y, int z, int side);

    @SideOnly(Side.CLIENT)
    public IconConnectedTexture getIconConnectedTexture(int meta, int side) {
        return iconConnectedTexture;
    }

}
