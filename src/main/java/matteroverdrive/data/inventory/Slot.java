package matteroverdrive.data.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import matteroverdrive.client.render.HoloIcon;
import net.minecraft.item.ItemStack;

public class Slot {
    private ItemStack item;
    private int id;
    private boolean drops = true;
    private boolean isMainSlot = false;
    private boolean keepOnDismante = false;
    private boolean sendToClient = false;

    public Slot(boolean isMainSlot) {
        this.isMainSlot = isMainSlot;
    }

    public boolean isValidForSlot(ItemStack item) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public HoloIcon getHoloIcon() {
        return null;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean drops() {
        return drops;
    }

    public void setDrops(boolean drops) {
        this.drops = drops;
    }

    public boolean keepOnDismantle() {
        return keepOnDismante;
    }

    public boolean isMainSlot() {
        return isMainSlot;
    }

    public void setMainSlot(boolean mainSlot) {
        this.isMainSlot = mainSlot;
    }

    public void setKeepOnDismante(boolean keepOnDismante) {
        this.keepOnDismante = keepOnDismante;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public String getUnlocalizedTooltip() {
        return null;
    }

    public Slot setSendToClient(boolean sendToClient) {
        this.sendToClient = sendToClient;
        return this;
    }

    public boolean sendsToClient() {
        return sendToClient;
    }
}
