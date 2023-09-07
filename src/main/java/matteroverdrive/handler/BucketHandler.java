package matteroverdrive.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import matteroverdrive.blocks.BlockFluidMatterPlasma;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler {
    public Map<Block, Item> buckets;

    public BucketHandler() {
        buckets = new HashMap<>();
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {

        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block instanceof BlockFluidMatterPlasma) {
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        }
    }
}
