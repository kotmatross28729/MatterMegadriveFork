package matteroverdrive.entity.monster;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import matteroverdrive.Reference;
import matteroverdrive.api.entity.IPathableMob;
import matteroverdrive.client.data.Color;
import matteroverdrive.data.BlockPos;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.tile.TileEntityAndroidSpawner;
import matteroverdrive.util.MOStringHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityRogueAndroidMob extends EntityMob implements IEntityAdditionalSpawnData, IPathableMob<EntityRogueAndroidMob> {
    private static ResourceLocation androidNames = new ResourceLocation(Reference.PATH_INFO + "android_names.txt");
    private static String[] names = MOStringHelper.readTextFile(androidNames).split(",");
    boolean fromSpawner;
    private BlockPos spawnerPosition;
    private int currentPathIndex;
    private Vec3[] path;
    private int maxPathTargetRangeSq;
    private int visorColor;
    private ScorePlayerTeam team;
    private boolean legendary;
    int androidLevel;

    public EntityRogueAndroidMob(World world) {
        super(world);
        if (!world.isRemote) {
            androidLevel = (int) (MathHelper.clamp_double(Math.abs(rand.nextGaussian() * (1 + world.difficultySetting.getDifficultyId() * 0.25)), 0, 3));
            boolean isLegendary = rand.nextDouble() < 0.05 * androidLevel;
            setLegendary(isLegendary);
            init();
            getNavigator().setAvoidsWater(true);
            getNavigator().setCanSwim(false);
        }
    }

    public EntityRogueAndroidMob(World world, int level, boolean legendary) {
        super(world);
        androidLevel = level;
        setLegendary(legendary);
        init();
    }

    private void init() {
        if (EntityRogueAndroid.ANDROID_NAMES) {
            String name = getIsLegendary() ? EnumChatFormatting.GOLD + String.format("%s %s ", Reference.UNICODE_LEGENDARY, MOStringHelper.translateToLocal("rarity.legendary")) : "";
            name += names[rand.nextInt(names.length)];
            setCustomNameTag(name);
        }

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getIsLegendary() ? 128 : androidLevel * 10 + 32);
        this.setHealth(this.getMaxHealth());

        if (fromSpawner) {
            if (!addToSpawner(spawnerPosition)) {
                setDead();
            }
        }

        if (getIsLegendary()) {
            setVisorColor(Reference.COLOR_HOLO_RED.getColor());
        } else {
            switch (androidLevel) {
                case 0:
                    setVisorColor(new Color(42, 42, 42).getColor()); // Gray
                    break;
                case 1:
                    setVisorColor(new Color(0, 170, 170).getColor()); // Dark Aqua
                    break;
                case 2:
                    setVisorColor(new Color(170, 0, 170).getColor()); // Dark Purple
                    break;
                default:
                    setVisorColor(new Color(255, 255, 255).getColor()); // White
            }
        }
    }

    public EnumChatFormatting getNameColor() {
        if (getIsLegendary()) {
            return EnumChatFormatting.GOLD;
        } else {
            switch (androidLevel) {
                case 0:
                    return EnumChatFormatting.GRAY;
                case 1:
                    return EnumChatFormatting.DARK_AQUA;
                case 2:
                    return EnumChatFormatting.DARK_PURPLE;
                default:
                    return null;
            }

        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        setLegendary(nbtTagCompound.getBoolean("Legendary"));
        androidLevel = nbtTagCompound.getByte("Level");
        setVisorColor(nbtTagCompound.getInteger("VisorColor"));
        if (nbtTagCompound.hasKey("Team", Constants.NBT.TAG_STRING)) {
            ScorePlayerTeam team = worldObj.getScoreboard().getTeam(nbtTagCompound.getString("Team"));
            if (team != null) {
                setTeam(team);
            } else {
                setDead();
            }
        }
        if (nbtTagCompound.hasKey("SpawnerPos", Constants.NBT.TAG_COMPOUND)) {
            spawnerPosition = new BlockPos(nbtTagCompound.getCompoundTag("SpawnerPos"));
            this.fromSpawner = true;
        }
        currentPathIndex = nbtTagCompound.getInteger("CurrentPathIndex");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setByte("Level", (byte) androidLevel);
        nbtTagCompound.setBoolean("Legendary", getIsLegendary());
        nbtTagCompound.setInteger("VisorColor", getVisorColor());
        if (getTeam() != null) {
            nbtTagCompound.setString("Team", getTeam().getRegisteredName());
        }
        if (spawnerPosition != null) {
            NBTTagCompound spawnerPos = new NBTTagCompound();
            spawnerPosition.writeToNBT(spawnerPos);
            nbtTagCompound.setTag("SpawnerPos", spawnerPos);
        }
        nbtTagCompound.setInteger("CurrentPathIndex", currentPathIndex);
    }

    private boolean addToSpawner(BlockPos position) {
        this.spawnerPosition = position;
        TileEntityAndroidSpawner spawnerEntity = position.getTileEntity(worldObj, TileEntityAndroidSpawner.class);
        if (spawnerEntity != null) {
            spawnerEntity.addSpawnedAndroid(this);
            return true;
        }
        return false;
    }

    public void setAttackTarget(EntityLivingBase target) {
        if (target != null && target.getTeam() != null) {
            if (!target.getTeam().isSameTeam(getTeam()))
                super.setAttackTarget(target);
        } else {
            super.setAttackTarget(target);
        }
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potion) {
        return false;
    }

    @Override
    public boolean getCanSpawnHere() {
        return getCanSpawnHere(false, false, false) && !hasToManyAndroids();
    }

    public boolean hasToManyAndroids() {
        Chunk chunk = worldObj.getChunkFromChunkCoords(chunkCoordX, chunkCoordZ);
        int androidCount = 0;
        for (int i = 0; i < chunk.entityLists.length; i++) {
            for (int c = 0; c < chunk.entityLists[i].size(); c++) {
                if (chunk.entityLists[i].get(c) instanceof EntityRogueAndroidMob) {
                    androidCount++;
                    if (androidCount > EntityRogueAndroid.MAX_ANDROIDS_PER_CHUNK)
                        return true;
                }
            }

        }
        return false;
    }

    public boolean getCanSpawnHere(boolean ignoreEntityCollision, boolean ignoreLight, boolean ignoreDimension) {
        if (!ignoreDimension) {
            if (EntityRogueAndroid.dimensionWhitelist.size() > 0) {
                return EntityRogueAndroid.dimensionWhitelist.contains(worldObj.provider.dimensionId) && inDimensionBlacklist();
            }
            if (inDimensionBlacklist()) {
                return false;
            }
        }
        boolean light = ignoreLight || isValidLightLevel();
        boolean entityCollison = ignoreEntityCollision || this.worldObj.checkNoEntityCollision(this.boundingBox);
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && light && entityCollison && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_) {
        float weight = 1 - this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_);
        weight *= this.worldObj.isSideSolid(p_70783_1_, p_70783_2_, p_70783_3_, ForgeDirection.UP) ? 0 : 1;
        weight /= Math.abs(p_70783_2_ - posX);
        return weight;
    }

    protected void addRandomArmor() {
        if (this.rand.nextFloat() < 0.15F) {
            int i = this.rand.nextInt(2);
            float f = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.1F : 0.25F;

            if (this.rand.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F) {
                ++i;
            }

            if (this.rand.nextFloat() < 0.095F) {
                ++i;
            }

            for (int j = 3; j >= 0; --j) {
                ItemStack itemstack = this.func_130225_q(j);

                if (j < 3 && this.rand.nextFloat() < f) {
                    break;
                }

                if (itemstack == null) {
                    Item item = null;

                    if (i == 3) {
                        if (rand.nextBoolean()) {
                            switch (j + 1) {
                                case 4:
                                    item = MatterOverdriveItems.tritaniumHelemet;
                                    break;
                                case 3:
                                    item = MatterOverdriveItems.tritaniumChestplate;
                                    break;
                                case 2:
                                    item = MatterOverdriveItems.tritaniumLeggings;
                                    break;
                                case 1:
                                    item = MatterOverdriveItems.tritaniumBoots;
                                    break;
                            }
                        } else {
                            item = getArmorItemForSlot(j + 1, i);
                        }
                    } else {
                        item = getArmorItemForSlot(j + 1, i);
                    }

                    if (item != null) {
                        this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
                    }
                }
            }
        }
    }

    @Override
    public boolean isWithinHomeDistance(int p_110176_1_, int p_110176_2_, int p_110176_3_) {
        return true;
    }

    @Override
    public boolean hasHome() {

        return getCurrentTarget() != null;
    }

    @Override
    public ChunkCoordinates getHomePosition() {
        Vec3 currentTarget = getCurrentTarget();
        return new ChunkCoordinates((int) currentTarget.xCoord, (int) currentTarget.yCoord, (int) currentTarget.zCoord);
    }

    private boolean inDimensionBlacklist() {
        return EntityRogueAndroid.dimensionBlacklist.contains(worldObj.provider.dimensionId);
    }

    public void setLegendary(boolean legendary) {
        this.legendary = legendary;
        if (legendary) {
            this.height = 1.8f * 1.6f;
        } else {
            this.height = 1.8f;
        }
    }

    public boolean getIsLegendary() {
        return legendary;
    }

    public void setTeam(ScorePlayerTeam team) {
        this.team = team;
    }

    @Override
    public String getCustomNameTag() {
        if (hasTeam()) {
            return getTeam().formatString(this.dataWatcher.getWatchableObjectString(10));
        } else {
            EnumChatFormatting color = getNameColor();
            if (color != null) {
                return color + this.dataWatcher.getWatchableObjectString(10);
            }
        }
        return this.dataWatcher.getWatchableObjectString(10);
    }

    @Override
    public ScorePlayerTeam getTeam() {
        return team;
    }

    public boolean hasTeam() {
        return getTeam() != null;
    }

    public boolean wasSpawnedFrom(TileEntityAndroidSpawner spawner) {
        if (spawnerPosition != null) {
            TileEntity tileEntity = spawnerPosition.getTileEntity(worldObj);
            return tileEntity == spawner;
        }
        return false;
    }

    public void setSpawnerPosition(BlockPos position) {
        this.spawnerPosition = position;
        this.fromSpawner = true;
    }

    @Override
    public void setDead() {
        if (spawnerPosition != null) {
            TileEntityAndroidSpawner spawner = spawnerPosition.getTileEntity(worldObj, TileEntityAndroidSpawner.class);
            if (spawner != null) {
                spawner.removeAndroid(this);
            }
        }
        this.isDead = true;
    }

    public int getVisorColor() {
        return visorColor;
    }

    public void setVisorColor(int color) {
        visorColor = color;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeByte(androidLevel);
        buffer.writeBoolean(legendary);
        buffer.writeInt(visorColor);
        buffer.writeBoolean(hasTeam());
        if (hasTeam()) {
            ByteBufUtils.writeUTF8String(buffer, getTeam().getRegisteredName());
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        androidLevel = additionalData.readByte();
        setLegendary(additionalData.readBoolean());
        setVisorColor(additionalData.readInt());
        if (additionalData.readBoolean()) {
            String teamName = ByteBufUtils.readUTF8String(additionalData);
            ScorePlayerTeam team = worldObj.getScoreboard().getTeam(teamName);
            if (team != null)
                setTeam(team);
        }
    }

    @Override
    public Vec3 getCurrentTarget() {
        if (path != null && currentPathIndex < path.length) {
            return path[currentPathIndex];
        }
        return null;
    }

    @Override
    public void onTargetReached(Vec3 pos) {
        if (currentPathIndex < path.length - 1) {
            currentPathIndex++;
        }
    }

    @Override
    public boolean isNearTarget(Vec3 pos) {
        return pos.squareDistanceTo(posX, posY, posZ) < maxPathTargetRangeSq;
    }

    @Override
    public EntityRogueAndroidMob getEntity() {
        return this;
    }

    public void setPath(Vec3[] path, int range) {
        this.path = path;
        maxPathTargetRangeSq = range * range;
    }

    @Override
    protected String getLivingSound() {
        return Reference.MOD_ID + ":" + "rogue_android_say";
    }

    @Override
    protected String getDeathSound() {
        return Reference.MOD_ID + ":" + "rogue_android_death";
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    public int getTalkInterval() {
        return 20 * 24;
    }
}
