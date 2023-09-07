package matteroverdrive.api.gravity;

import net.minecraft.nbt.NBTTagCompound;

public class AnomalySuppressor {
    int x, y, z;
    int time;
    float amount;

    public AnomalySuppressor(NBTTagCompound tagCompound) {

    }

    public AnomalySuppressor(int x, int y, int z, int time, float amount) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
        this.amount = amount;
    }

    public boolean update(AnomalySuppressor suppressor) {
        if (suppressor.x == x && suppressor.y == y && suppressor.z == z) {
            if (time < suppressor.time) {
                this.time = suppressor.time;
            }
            this.amount = suppressor.amount;
            return true;
        }
        return false;
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("block_x", x);
        tagCompound.setInteger("block_y", y);
        tagCompound.setInteger("block_z", z);
        tagCompound.setByte("time", (byte) time);
        tagCompound.setFloat("amount", amount);
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        x = tagCompound.getInteger("block_x");
        y = tagCompound.getInteger("block_y");
        z = tagCompound.getInteger("block_z");
        time = tagCompound.getByte("time");
        amount = tagCompound.getFloat("amount");
    }

    public void tick() {
        time--;
    }

    public boolean isValid() {
        return time > 0;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
