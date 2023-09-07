package matteroverdrive.api.matter;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Used by any {@link net.minecraft.tileentity.TileEntity} that provides matter
 */
public interface IMatterProvider {
    /**
     * @return stored matter.
     */
    int getMatterStored();

    /**
     * @return the capacity
     */
    int getMatterCapacity();

    /**
     * Used to extract matter.
     *
     * @param direction the extraction side.
     * @param amount    amount of extracted matter.
     * @param simulate  is this a simulation ?
     *                  No matter will be extracted.
     *                  Used to check if the specified amount can be extracted.
     * @return the extracted amount of matter.
     */
    int extractMatter(ForgeDirection direction, int amount, boolean simulate);
}
