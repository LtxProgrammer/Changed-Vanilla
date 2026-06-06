package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ChangedVanillaEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChangedVanilla.MODID);
    public static final Map<RegistryObject<? extends EntityType<?>>, RegistryObject<ForgeSpawnEggItem>> SPAWN_EGGS = new HashMap<>();

    public static final RegistryObject<EntityType<LatexCat>> LATEX_CAT = registerWithEgg("latex_cat", 0x161524, 0xeaeaea,
            EntityType.Builder.of(LatexCat::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F * 0.9F, 1.93F * 0.9F));
    public static final RegistryObject<EntityType<LatexChicken>> LATEX_CHICKEN = registerWithEgg("latex_chicken", 0xe4e5dd, 0xe5d013,
            EntityType.Builder.of(LatexChicken::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F * 0.95F, 1.93F * 0.95F));
    public static final RegistryObject<EntityType<LatexCow>> LATEX_COW = registerWithEgg("latex_cow", 0x815f46, 0x605b58,
            EntityType.Builder.of(LatexCow::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexCreeper>> LATEX_CREEPER = registerWithEgg("latex_creeper", 0x478f4d, 0x2e5830,
            EntityType.Builder.of(LatexCreeper::new, MobCategory.MONSTER).clientTrackingRange(10).sized(1.25F, 2.0F));
    public static final RegistryObject<EntityType<LatexFox>> LATEX_FOX = registerWithEgg("latex_fox", 0xe38f1b, 0xf2f2ea,
            EntityType.Builder.of(LatexFox::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexFoxPartial>> LATEX_FOX_PARTIAL = registerNoEgg("latex_fox_partial", 0xe38f1b, 0xf2f2ea,
            EntityType.Builder.of(LatexFoxPartial::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    /*public static final RegistryObject<EntityType<LatexGhast>> LATEX_GHAST = registerWithEgg("latex_ghast", 0xe3e7ea, 0xc6cacd,
            EntityType.Builder.of(LatexGhast::new, MobCategory.MONSTER).clientTrackingRange(10).sized(1.2F, 3.6F));*/
    /*public static final RegistryObject<EntityType<LatexGuardian>> LATEX_GUARDIAN = registerWithEgg("latex_guardian", 0x509286, 0xfd783d,
            EntityType.Builder.of(LatexGuardian::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.58625F));*/
    public static final RegistryObject<EntityType<LatexOcelot>> LATEX_OCELOT = registerWithEgg("latex_ocelot", 0xffd573, 0xa75b21,
            EntityType.Builder.of(LatexOcelot::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexPig>> LATEX_PIG = registerWithEgg("latex_pig", 0xdd998e, 0xeeb298,
            EntityType.Builder.of(LatexPig::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexSheep>> LATEX_SHEEP = registerWithEgg("latex_sheep", 0xffeadc, 0x5c4a3e,
            EntityType.Builder.of(LatexSheep::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexSkeleton>> LATEX_SKELETON = registerWithEgg("latex_skeleton", 0x26252a, 0xd7d7d7,
            EntityType.Builder.of(LatexSkeleton::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexSlime>> LATEX_SLIME = registerWithEgg("latex_slime", 0x0ad500, 0x62eb03,
            EntityType.Builder.of(LatexSlime::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexSpider>> LATEX_SPIDER = registerWithEgg("latex_spider", 0x3b2d20, 0x3d3938,
            EntityType.Builder.of(LatexSpider::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));
    public static final RegistryObject<EntityType<LatexZombie>> LATEX_ZOMBIE = registerWithEgg("latex_zombie", 0x61865b, 0x436445,
            EntityType.Builder.of(LatexZombie::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));

    private static <T extends ChangedEntity> RegistryObject<EntityType<T>> registerNoEgg(
            String name,
            int eggBack,
            int eggHighlight,
            EntityType.Builder<T> builder) {
        String regName = ChangedVanilla.modResourceStr(name);
        RegistryObject<EntityType<T>> entityType = REGISTRY.register(name, () -> builder.build(regName));

        ChangedEntities.registerEntityColor(entityType.getId(), eggBack, eggHighlight);

        return entityType;
    }

    private static <T extends ChangedEntity> RegistryObject<EntityType<T>> registerWithEgg(
            String name,
            int eggBack,
            int eggHighlight,
            EntityType.Builder<T> builder) {
        String regName = ChangedVanilla.modResourceStr(name);
        RegistryObject<EntityType<T>> entityType = REGISTRY.register(name, () -> builder.build(regName));
        RegistryObject<ForgeSpawnEggItem> spawnEggItem = ChangedVanillaItems.REGISTRY.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(entityType, eggBack, eggHighlight, new Item.Properties()));
        SPAWN_EGGS.put(entityType, spawnEggItem);

        ChangedEntities.registerEntityColor(entityType.getId(), eggBack, eggHighlight);

        return entityType;
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(LATEX_CAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexCat::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexChicken::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexCow::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexCreeper::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexFox::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        /*event.register(LATEX_GHAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexGhast::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);*/
        /*event.register(LATEX_GUARDIAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexGuardian::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);*/
        event.register(LATEX_OCELOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexOcelot::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexPig::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexSheep::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexSkeleton::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexSlime::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexSpider::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexZombie::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(LATEX_CAT.get(), LatexCat.createLatexAttributes().build());
        event.put(LATEX_CHICKEN.get(), LatexChicken.createLatexAttributes().build());
        event.put(LATEX_COW.get(), LatexCow.createLatexAttributes().build());
        event.put(LATEX_CREEPER.get(), LatexCreeper.createLatexAttributes().build());
        event.put(LATEX_FOX.get(), LatexFox.createLatexAttributes().build());
        event.put(LATEX_FOX_PARTIAL.get(), LatexFoxPartial.createLatexAttributes().build());
        /*event.put(LATEX_GHAST.get(), LatexGhast.createLatexGhastAttributes().build());*/
        /*event.put(LATEX_GUARDIAN.get(), LatexGuardian.createLatexAttributes().build());*/
        event.put(LATEX_OCELOT.get(), LatexOcelot.createLatexAttributes().build());
        event.put(LATEX_PIG.get(), LatexPig.createLatexAttributes().build());
        event.put(LATEX_SHEEP.get(), LatexSheep.createLatexAttributes().build());
        event.put(LATEX_SKELETON.get(), LatexSkeleton.createLatexAttributes().build());
        event.put(LATEX_SLIME.get(), LatexSlime.createLatexAttributes().build());
        event.put(LATEX_SPIDER.get(), LatexSpider.createLatexAttributes().build());
        event.put(LATEX_ZOMBIE.get(), LatexZombie.createLatexAttributes().build());
    }
}
