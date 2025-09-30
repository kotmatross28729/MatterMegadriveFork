package matteroverdrive.client.render;

import matteroverdrive.Reference;
import matteroverdrive.client.render.utils.MO_WavefrontObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
public class TE_Resource_Manager {
	
	public static final ResourceLocation VENT_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "vent.png");
	public static final ResourceLocation BASE_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "base.png");
	public static final ResourceLocation NETWORK_PORT_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "network_port.png");
	
	public static final IModelCustom CHARGING_STATION_MODEL = new MO_WavefrontObject(new ResourceLocation(Reference.MODEL_CHARGING_STATION)).asVBO();
	public static final ResourceLocation CHARGING_STATION_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "charging_station.png");
	
	public static final IModelCustom INSCRIBER_MODEL = new MO_WavefrontObject(new ResourceLocation(Reference.MODEL_INSCRIBER)).asVBO();
	public static final ResourceLocation INSCRIBER_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "inscriber.png");
	
	//TODO: Top lid brightness?
	public static final IModelCustom PATTERN_STORAGE_MODEL = new MO_WavefrontObject(new ResourceLocation(Reference.MODEL_PATTERN_STORAGE)).asVBO();
	public static final ResourceLocation PATTERN_STORAGE_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "pattern_storage.png");
	
	public static final IModelCustom REPLICATOR_MODEL = new MO_WavefrontObject(new ResourceLocation(Reference.MODEL_REPLICATOR)).asVBO();
	public static final ResourceLocation REPLICATOR_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "replicator.png");
	
	public static final IModelCustom TRITANIUM_CRATE_MODEL = new MO_WavefrontObject(new ResourceLocation(Reference.MODEL_TRITANIUM_CRATE)).asVBO();
	public static final ResourceLocation TRITANIUM_CRATE_BASE_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "tritanium_crate_base.png");
	public static final ResourceLocation TRITANIUM_CRATE_OVERLAY_TEXTURE = new ResourceLocation(Reference.PATH_BLOCKS + "tritanium_crate_overlay.png");

}
