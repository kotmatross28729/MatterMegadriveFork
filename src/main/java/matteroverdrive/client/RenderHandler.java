package matteroverdrive.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import matteroverdrive.MatterOverdrive;
import matteroverdrive.api.android.IAndroidStatRenderRegistry;
import matteroverdrive.api.android.IBionicStat;
import matteroverdrive.api.renderer.IBioticStatRenderer;
import matteroverdrive.api.starmap.IStarmapRenderRegistry;
import matteroverdrive.client.model.ModelHulkingScientist;
import matteroverdrive.client.model.ModelTritaniumArmor;
import matteroverdrive.client.render.AndroidStatRenderRegistry;
import matteroverdrive.client.render.IWorldLastRenderer;
import matteroverdrive.client.render.RenderDialogSystem;
import matteroverdrive.client.render.RenderMatterScannerInfoHandler;
import matteroverdrive.client.render.RenderParticlesHandler;
import matteroverdrive.client.render.RenderWeaponsBeam;
import matteroverdrive.client.render.StarmapRenderRegistry;
import matteroverdrive.client.render.WeaponModuleModelRegistry;
import matteroverdrive.client.render.biostat.BioticStatRendererShield;
import matteroverdrive.client.render.biostat.BioticStatRendererTeleporter;
import matteroverdrive.client.render.block.MOBlockRenderer;
import matteroverdrive.client.render.block.RendererBlockChargingStation;
import matteroverdrive.client.render.block.RendererBlockDecorativeVertical;
import matteroverdrive.client.render.block.RendererBlockGravitationalStabilizer;
import matteroverdrive.client.render.block.RendererBlockInscriber;
import matteroverdrive.client.render.block.RendererBlockPatternStorage;
import matteroverdrive.client.render.block.RendererBlockPipe;
import matteroverdrive.client.render.block.RendererBlockReplicator;
import matteroverdrive.client.render.block.RendererBlockTritaniumCrate;
import matteroverdrive.client.render.entity.EntityRendererFailedChicken;
import matteroverdrive.client.render.entity.EntityRendererFailedCow;
import matteroverdrive.client.render.entity.EntityRendererFailedPig;
import matteroverdrive.client.render.entity.EntityRendererFailedSheep;
import matteroverdrive.client.render.entity.EntityRendererMadScientist;
import matteroverdrive.client.render.entity.EntityRendererMutantScientist;
import matteroverdrive.client.render.entity.EntityRendererPhaserFire;
import matteroverdrive.client.render.entity.EntityRendererRangedRogueAndroid;
import matteroverdrive.client.render.entity.EntityRendererRogueAndroid;
import matteroverdrive.client.render.item.ItemRenderPlasmaShotgun;
import matteroverdrive.client.render.item.ItemRendererIonSniper;
import matteroverdrive.client.render.item.ItemRendererOmniTool;
import matteroverdrive.client.render.item.ItemRendererPhaser;
import matteroverdrive.client.render.item.ItemRendererPhaserRifle;
import matteroverdrive.client.render.tileentity.TileEntityRendererAndroidStation;
import matteroverdrive.client.render.tileentity.TileEntityRendererChargingStation;
import matteroverdrive.client.render.tileentity.TileEntityRendererContractMarket;
import matteroverdrive.client.render.tileentity.TileEntityRendererFusionReactorController;
import matteroverdrive.client.render.tileentity.TileEntityRendererGravitationalAnomaly;
import matteroverdrive.client.render.tileentity.TileEntityRendererGravitationalStabilizer;
import matteroverdrive.client.render.tileentity.TileEntityRendererHoloSign;
import matteroverdrive.client.render.tileentity.TileEntityRendererInscriber;
import matteroverdrive.client.render.tileentity.TileEntityRendererMatterPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererNetworkPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererPacketQueue;
import matteroverdrive.client.render.tileentity.TileEntityRendererPatterStorage;
import matteroverdrive.client.render.tileentity.TileEntityRendererPatternMonitor;
import matteroverdrive.client.render.tileentity.TileEntityRendererPipe;
import matteroverdrive.client.render.tileentity.TileEntityRendererReplicator;
import matteroverdrive.client.render.tileentity.TileEntityRendererStarMap;
import matteroverdrive.client.render.tileentity.TileEntityRendererWeaponStation;
import matteroverdrive.client.render.tileentity.starmap.StarMapRenderGalaxy;
import matteroverdrive.client.render.tileentity.starmap.StarMapRenderPlanetStats;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererPlanet;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererQuadrant;
import matteroverdrive.client.render.tileentity.starmap.StarMapRendererStar;
import matteroverdrive.entity.EntityFailedChicken;
import matteroverdrive.entity.EntityFailedCow;
import matteroverdrive.entity.EntityFailedPig;
import matteroverdrive.entity.EntityFailedSheep;
import matteroverdrive.entity.EntityVillagerMadScientist;
import matteroverdrive.entity.monster.EntityMeleeRogueAndroidMob;
import matteroverdrive.entity.monster.EntityMutantScientist;
import matteroverdrive.entity.monster.EntityRangedRogueAndroidMob;
import matteroverdrive.entity.player.AndroidPlayer;
import matteroverdrive.entity.weapon.PlasmaBolt;
import matteroverdrive.handler.ConfigurationHandler;
import matteroverdrive.init.MatterOverdriveBioticStats;
import matteroverdrive.init.MatterOverdriveItems;
import matteroverdrive.machines.fusionReactorController.TileEntityMachineFusionReactorController;
import matteroverdrive.starmap.data.Galaxy;
import matteroverdrive.starmap.data.Planet;
import matteroverdrive.starmap.data.Quadrant;
import matteroverdrive.starmap.data.Star;
import matteroverdrive.tile.TileEntityAndroidStation;
import matteroverdrive.tile.TileEntityGravitationalAnomaly;
import matteroverdrive.tile.TileEntityHoloSign;
import matteroverdrive.tile.TileEntityInscriber;
import matteroverdrive.tile.TileEntityMachineChargingStation;
import matteroverdrive.tile.TileEntityMachineContractMarket;
import matteroverdrive.tile.TileEntityMachineGravitationalStabilizer;
import matteroverdrive.tile.TileEntityMachinePacketQueue;
import matteroverdrive.tile.TileEntityMachinePatternMonitor;
import matteroverdrive.tile.TileEntityMachinePatternStorage;
import matteroverdrive.tile.TileEntityMachineReplicator;
import matteroverdrive.tile.TileEntityMachineStarMap;
import matteroverdrive.tile.TileEntityWeaponStation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RenderHandler {
    private Random random = new Random();
    private RenderMatterScannerInfoHandler matterScannerInfoHandler;
    private RenderParticlesHandler renderParticlesHandler;
    private RenderWeaponsBeam renderWeaponsBeam;
    private List<IWorldLastRenderer> customRenderers;
    private AndroidStatRenderRegistry statRenderRegistry;
    private StarmapRenderRegistry starmapRenderRegistry;
    private RenderDialogSystem renderDialogSystem;
    /**
    private AndroidBionicPartRenderRegistry bionicPartRenderRegistry;
    */
    private WeaponModuleModelRegistry weaponModuleModelRegistry;

    //region Block Renderers
    private MOBlockRenderer blockRenderer;
    private RendererBlockGravitationalStabilizer rendererBlockGravitationalStabilizer;
    private RendererBlockPipe rendererBlockPipe;
    private RendererBlockChargingStation rendererBlockChargingStation;
    private RendererBlockPatternStorage rendererBlockPatternStorage;
    private RendererBlockReplicator rendererBlockReplicator;
    private RendererBlockTritaniumCrate rendererBlockTritaniumCrate;
    private RendererBlockInscriber rendererBlockInscriber;
    private RendererBlockDecorativeVertical rendererBlockDecorativeVertical;
    //endregion
    //region Biostat Renderers
    private BioticStatRendererTeleporter rendererTeleporter;
    private BioticStatRendererShield biostatRendererShield;
    //endregion
    //region Starmap Renderers
    private StarMapRendererPlanet starMapRendererPlanet;
    private StarMapRendererQuadrant starMapRendererQuadrant;
    private StarMapRendererStar starMapRendererStar;
    private StarMapRenderGalaxy starMapRenderGalaxy;
    private StarMapRenderPlanetStats starMapRenderPlanetStats;
    //endregion
    //region Item Renderers
    private static ItemRendererPhaser rendererPhaser;
    private static ItemRendererPhaserRifle rendererPhaserRifle;
    private static ItemRendererOmniTool rendererOmniTool;
    private static ItemRenderPlasmaShotgun renderPlasmaShotgun;
    private static ItemRendererIonSniper rendererIonSniper;
    //endregion
    //region Entity Renderers
    private EntityRendererRogueAndroid rendererRogueAndroid;
    public EntityRendererRogueAndroid rendererRogueAndroidHologram;
    private EntityRendererMadScientist rendererMadScientist;
    private EntityRendererFailedCow rendererFailedCow;
    private EntityRendererFailedChicken rendererFailedChicken;
    private EntityRendererFailedPig rendererFailedPig;
    private EntityRendererFailedSheep rendererFailedSheep;
    private EntityRendererPhaserFire rendererPhaserFire;
    private EntityRendererRangedRogueAndroid rendererRangedRogueAndroid;
    private EntityRendererMutantScientist rendererMutantScientist;
    //endregion
    //region Tile Entity Renderers
    private TileEntityRendererReplicator tileEntityRendererReplicator;
    private TileEntityRendererPipe tileEntityRendererPipe;
    private TileEntityRendererMatterPipe tileEntityRendererMatterPipe;
    private TileEntityRendererNetworkPipe tileEntityRendererNetworkPipe;
    private TileEntityRendererPatterStorage tileEntityRendererPatterStorage;
    private TileEntityRendererWeaponStation tileEntityRendererWeaponStation;
    private TileEntityRendererPatternMonitor tileEntityRendererPatternMonitor;
    private TileEntityRendererGravitationalAnomaly tileEntityRendererGravitationalAnomaly;
    private TileEntityRendererGravitationalStabilizer tileEntityRendererGravitationalStabilizer;
    private TileEntityRendererFusionReactorController tileEntityRendererFusionReactorController;
    private TileEntityRendererAndroidStation tileEntityRendererAndroidStation;
    private TileEntityRendererStarMap tileEntityRendererStarMap;
    private TileEntityRendererChargingStation tileEntityRendererChargingStation;
    private TileEntityRendererHoloSign tileEntityRendererHoloSign;
    private TileEntityRendererPacketQueue tileEntityRendererPacketQueue;
    private TileEntityRendererInscriber tileEntityRendererInscriber;
    private TileEntityRendererContractMarket tileEntityRendererContractMarket;
    //endregion
    //region Models
    public ModelTritaniumArmor modelTritaniumArmor;
    public ModelTritaniumArmor modelTritaniumArmorFeet;
    public ModelBiped modelMeleeRogueAndroidParts;
    public ModelBiped modelRangedRogueAndroidParts;
    //endregion

    public RenderHandler(World world, TextureManager textureManager) {
        customRenderers = new ArrayList<>();
        matterScannerInfoHandler = new RenderMatterScannerInfoHandler();
        renderParticlesHandler = new RenderParticlesHandler(world, textureManager);
        renderWeaponsBeam = new RenderWeaponsBeam();
        statRenderRegistry = new AndroidStatRenderRegistry();
        starmapRenderRegistry = new StarmapRenderRegistry();
        renderDialogSystem = new RenderDialogSystem();
        /**
        bionicPartRenderRegistry = new AndroidBionicPartRenderRegistry();
        */
        weaponModuleModelRegistry = new WeaponModuleModelRegistry();

        addCustomRenderer(matterScannerInfoHandler);
        addCustomRenderer(renderParticlesHandler);
        addCustomRenderer(renderWeaponsBeam);
        addCustomRenderer(renderDialogSystem);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (IWorldLastRenderer renderer : customRenderers) {
            renderer.onRenderWorldLast(this, event);
        }
        for (IBionicStat stat : MatterOverdrive.statRegistry.getStats()) {
            Collection<IBioticStatRenderer> statRendererCollection = statRenderRegistry.getRendererCollection(stat.getClass());
            if (statRendererCollection != null) {
                for (IBioticStatRenderer renderer : statRendererCollection) {
                    renderer.onWorldRender(stat, AndroidPlayer.get(Minecraft.getMinecraft().thePlayer).getUnlockedLevel(stat), event);
                }
            }
        }
    }

    //Called when the client ticks.
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        renderParticlesHandler.onClientTick(event);
    }

    public void createTileEntityRenderers(ConfigurationHandler configHandler) {
        tileEntityRendererReplicator = new TileEntityRendererReplicator();
        tileEntityRendererPipe = new TileEntityRendererPipe();
        tileEntityRendererMatterPipe = new TileEntityRendererMatterPipe();
        tileEntityRendererNetworkPipe = new TileEntityRendererNetworkPipe();
        tileEntityRendererPatterStorage = new TileEntityRendererPatterStorage();
        tileEntityRendererWeaponStation = new TileEntityRendererWeaponStation();
        tileEntityRendererPatternMonitor = new TileEntityRendererPatternMonitor();
        tileEntityRendererGravitationalAnomaly = new TileEntityRendererGravitationalAnomaly();
        tileEntityRendererGravitationalStabilizer = new TileEntityRendererGravitationalStabilizer();
        tileEntityRendererFusionReactorController = new TileEntityRendererFusionReactorController();
        tileEntityRendererAndroidStation = new TileEntityRendererAndroidStation();
        tileEntityRendererStarMap = new TileEntityRendererStarMap();
        tileEntityRendererChargingStation = new TileEntityRendererChargingStation();
        tileEntityRendererHoloSign = new TileEntityRendererHoloSign();
        tileEntityRendererPacketQueue = new TileEntityRendererPacketQueue();
        tileEntityRendererInscriber = new TileEntityRendererInscriber();
        tileEntityRendererContractMarket = new TileEntityRendererContractMarket();
    }

    /**
    @SubscribeEvent
    public void onPlayerRenderPost(RenderPlayerEvent.Post event) {
        //GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glColor3f(1, 1, 1);

        AndroidPlayer androidPlayer = AndroidPlayer.get(event.entityPlayer);
        if (androidPlayer != null && androidPlayer.isAndroid() && !event.entityPlayer.isInvisible()) {
            for (int i = 0; i < 5; i++) {
                ItemStack part = androidPlayer.getStackInSlot(i);
                if (part != null && part.getItem() instanceof IBionicPart) {
                    IBionicPartRenderer renderer = bionicPartRenderRegistry.getRenderer(((IBionicPart) part.getItem()).getClass());
                    if (renderer != null) {
                        try {
                            GL11.glPushMatrix();
                            renderer.renderPart(part, androidPlayer, event.renderer, event.partialRenderTick);
                            GL11.glPopMatrix();
                        } catch (Exception e) {
                            MOLog.log(Level.ERROR, e, "An Error occurred while rendering bionic part");
                        }
                    }
                }
            }
        }
    }
*/
/**
    @SubscribeEvent
    public void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        //GL11.glEnable(GL11.GL_LIGHTING);
        //GL11.glColor3f(1, 1, 1);

        AndroidPlayer androidPlayer = AndroidPlayer.get(event.entityPlayer);
        if (androidPlayer != null && androidPlayer.isAndroid() && !event.entityPlayer.isInvisible()) {
            for (int i = 0; i < 5; i++) {
                ItemStack part = androidPlayer.getStackInSlot(i);
                if (part != null && part.getItem() instanceof IBionicPart) {
                    IBionicPartRenderer renderer = bionicPartRenderRegistry.getRenderer(((IBionicPart) part.getItem()).getClass());
                    if (renderer != null) {
                        renderer.affectPlayerRenderer(part, androidPlayer, event.renderer, event.partialRenderTick);
                    }
                }
            }
        }
    }
*/
    public void createBlockRenderers() {
        blockRenderer = new MOBlockRenderer();
        rendererBlockGravitationalStabilizer = new RendererBlockGravitationalStabilizer();
        rendererBlockPipe = new RendererBlockPipe();
        rendererBlockChargingStation = new RendererBlockChargingStation();
        rendererBlockPatternStorage = new RendererBlockPatternStorage();
        rendererBlockReplicator = new RendererBlockReplicator();
        rendererBlockTritaniumCrate = new RendererBlockTritaniumCrate();
        rendererBlockInscriber = new RendererBlockInscriber();
        rendererBlockDecorativeVertical = new RendererBlockDecorativeVertical();
    }

    public void registerBlockRenderers() {
        RenderingRegistry.registerBlockHandler(blockRenderer);
        RenderingRegistry.registerBlockHandler(rendererBlockGravitationalStabilizer);
        RenderingRegistry.registerBlockHandler(rendererBlockPipe);
        RenderingRegistry.registerBlockHandler(rendererBlockChargingStation);
        RenderingRegistry.registerBlockHandler(rendererBlockPatternStorage);
        RenderingRegistry.registerBlockHandler(rendererBlockReplicator);
        RenderingRegistry.registerBlockHandler(rendererBlockTritaniumCrate);
        RenderingRegistry.registerBlockHandler(rendererBlockInscriber);
        RenderingRegistry.registerBlockHandler(rendererBlockDecorativeVertical);
    }

    public void registerTileEntitySpecialRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReplicator.class, tileEntityRendererReplicator);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePatternStorage.class, tileEntityRendererPatterStorage);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeaponStation.class, tileEntityRendererWeaponStation);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePatternMonitor.class, tileEntityRendererPatternMonitor);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGravitationalAnomaly.class, tileEntityRendererGravitationalAnomaly);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGravitationalStabilizer.class, tileEntityRendererGravitationalStabilizer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFusionReactorController.class, tileEntityRendererFusionReactorController);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAndroidStation.class, tileEntityRendererAndroidStation);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineStarMap.class, tileEntityRendererStarMap);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChargingStation.class, tileEntityRendererChargingStation);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHoloSign.class, tileEntityRendererHoloSign);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePacketQueue.class, tileEntityRendererPacketQueue);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInscriber.class, tileEntityRendererInscriber);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineContractMarket.class, tileEntityRendererContractMarket);
    }

    public void createItemRenderers() {
        rendererPhaser = new ItemRendererPhaser();
        rendererPhaserRifle = new ItemRendererPhaserRifle();
        rendererOmniTool = new ItemRendererOmniTool();
        renderPlasmaShotgun = new ItemRenderPlasmaShotgun();
        rendererIonSniper = new ItemRendererIonSniper();
    }

    public void registerItemRenderers() {
        MinecraftForgeClient.registerItemRenderer(MatterOverdriveItems.phaser, rendererPhaser);
        MinecraftForgeClient.registerItemRenderer(MatterOverdriveItems.phaserRifle, rendererPhaserRifle);
        MinecraftForgeClient.registerItemRenderer(MatterOverdriveItems.omniTool, rendererOmniTool);
        MinecraftForgeClient.registerItemRenderer(MatterOverdriveItems.plasmaShotgun, renderPlasmaShotgun);
        MinecraftForgeClient.registerItemRenderer(MatterOverdriveItems.ionSniper, rendererIonSniper);
    }

    public void createEntityRenderers() {
        rendererRogueAndroid = new EntityRendererRogueAndroid(new ModelBiped(), 0, false);
        rendererMadScientist = new EntityRendererMadScientist();
        rendererFailedPig = new EntityRendererFailedPig(new ModelPig(), new ModelPig(0.5f), 0.7F);
        rendererFailedCow = new EntityRendererFailedCow(new ModelCow(), 0.7f);
        rendererFailedChicken = new EntityRendererFailedChicken(new ModelChicken(), 0.3f);
        rendererFailedSheep = new EntityRendererFailedSheep(new ModelSheep2(), new ModelSheep1(), 0.7f);
        rendererPhaserFire = new EntityRendererPhaserFire();
        rendererRangedRogueAndroid = new EntityRendererRangedRogueAndroid(0);
        rendererRogueAndroidHologram = new EntityRendererRogueAndroid(new ModelBiped(), 0, true);
        rendererMutantScientist = new EntityRendererMutantScientist(new ModelHulkingScientist(), 0, 1);
    }

    public void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMeleeRogueAndroidMob.class, rendererRogueAndroid);
        RenderingRegistry.registerEntityRenderingHandler(EntityFailedPig.class, rendererFailedPig);
        RenderingRegistry.registerEntityRenderingHandler(EntityFailedCow.class, rendererFailedCow);
        RenderingRegistry.registerEntityRenderingHandler(EntityFailedChicken.class, rendererFailedChicken);
        RenderingRegistry.registerEntityRenderingHandler(EntityFailedSheep.class, rendererFailedSheep);
        RenderingRegistry.registerEntityRenderingHandler(EntityVillagerMadScientist.class, rendererMadScientist);
        RenderingRegistry.registerEntityRenderingHandler(PlasmaBolt.class, rendererPhaserFire);
        RenderingRegistry.registerEntityRenderingHandler(EntityRangedRogueAndroidMob.class, rendererRangedRogueAndroid);
        RenderingRegistry.registerEntityRenderingHandler(EntityMutantScientist.class, rendererMutantScientist);
    }

    public void createBioticStatRenderers() {
        rendererTeleporter = new BioticStatRendererTeleporter();
        biostatRendererShield = new BioticStatRendererShield();
    }

    public void registerBioticStatRenderers() {
        statRenderRegistry.registerRenderer(MatterOverdriveBioticStats.shield.getClass(), biostatRendererShield);
        statRenderRegistry.registerRenderer(MatterOverdriveBioticStats.teleport.getClass(), rendererTeleporter);
    }
/**
    public void registerBionicPartRenderers() {
        bionicPartRenderRegistry.register(TritaniumSpine.class, new TritaniumSpineRenderer());
        bionicPartRenderRegistry.register(RogueAndroidParts.class, new RogueAndroidPartsRender());
    }
*/
    public void registerWeaponModuleModels() {
        weaponModuleModelRegistry.registerModule(MatterOverdriveItems.sniperScope);
    }

    public void createStarmapRenderers() {
        starMapRendererPlanet = new StarMapRendererPlanet();
        starMapRendererQuadrant = new StarMapRendererQuadrant();
        starMapRendererStar = new StarMapRendererStar();
        starMapRenderGalaxy = new StarMapRenderGalaxy();
        starMapRenderPlanetStats = new StarMapRenderPlanetStats();
    }

    public void registerStarmapRenderers() {
        starmapRenderRegistry.registerRenderer(Planet.class, starMapRendererPlanet);
        starmapRenderRegistry.registerRenderer(Quadrant.class, starMapRendererQuadrant);
        starmapRenderRegistry.registerRenderer(Star.class, starMapRendererStar);
        starmapRenderRegistry.registerRenderer(Galaxy.class, starMapRenderGalaxy);
        starmapRenderRegistry.registerRenderer(Planet.class, starMapRenderPlanetStats);
    }

    public void createModels() {
        modelTritaniumArmor = new ModelTritaniumArmor(0);
        modelTritaniumArmorFeet = new ModelTritaniumArmor(0.5f);
        modelMeleeRogueAndroidParts = new ModelBiped(0);
        modelRangedRogueAndroidParts = new ModelBiped(0, 0, 96, 64);
    }

    public RenderParticlesHandler getRenderParticlesHandler() {
        return renderParticlesHandler;
    }

    public TileEntityRendererStarMap getTileEntityRendererStarMap() {
        return tileEntityRendererStarMap;
    }

    public IAndroidStatRenderRegistry getStatRenderRegistry() {
        return statRenderRegistry;
    }

    public IStarmapRenderRegistry getStarmapRenderRegistry() {
        return starmapRenderRegistry;
    }

    public ItemRendererOmniTool getRendererOmniTool() {
        return rendererOmniTool;
    }
/**
    public AndroidBionicPartRenderRegistry getBionicPartRenderRegistry() {
        return bionicPartRenderRegistry;
    }
*/
    public WeaponModuleModelRegistry getWeaponModuleModelRegistry() {
        return weaponModuleModelRegistry;
    }

    public Random getRandom() {
        return random;
    }

    public void addCustomRenderer(IWorldLastRenderer renderer) {
        customRenderers.add(renderer);
    }
}
