package net.ltxprogrammer.changedvanilla.init;

import com.mojang.datafixers.util.Pair;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedVanillaEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChangedVanilla.MODID);
    public static final Map<RegistryObject<? extends EntityType<?>>, RegistryObject<ForgeSpawnEggItem>> SPAWN_EGGS = new HashMap<>();

    public static final RegistryObject<EntityType<LatexGuardian>> LATEX_GUARDIAN = registerWithEgg("latex_guardian", 0x509286, 0xfd783d,
            EntityType.Builder.of(LatexGuardian::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.58625F));
    public static final RegistryObject<EntityType<LatexSkeleton>> LATEX_SKELETON = registerWithEgg("latex_skeleton", 0x26252a, 0xd7d7d7,
            EntityType.Builder.of(LatexSkeleton::new, MobCategory.MONSTER).clientTrackingRange(10).sized(0.7F, 1.93F));

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

    @SubscribeEvent
    public static void init(SpawnPlacementRegisterEvent event) {
        event.register(LATEX_GUARDIAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexGuardian::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(LATEX_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LatexSkeleton::checkEntitySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(LATEX_GUARDIAN.get(), LatexGuardian.createLatexAttributes().build());
        event.put(LATEX_SKELETON.get(), LatexSkeleton.createLatexAttributes().build());
    }
}
