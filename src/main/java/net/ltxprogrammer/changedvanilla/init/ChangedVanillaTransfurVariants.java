package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.entity.ai.EntityAssimilationBehavior;
import net.ltxprogrammer.changed.entity.ai.TransfurDecider;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ChangedVanillaTransfurVariants {
    public static final DeferredRegister<TransfurVariant<?>> REGISTRY = ChangedRegistry.TRANSFUR_VARIANT.createDeferred(ChangedVanilla.MODID);

    private static <T extends ChangedEntity> RegistryObject<TransfurVariant<T>> register(String name, TransfurVariant.Builder<T> builder) {
        return REGISTRY.register(name, builder::build);
    }

    public static final RegistryObject<TransfurVariant<LatexCat>> LATEX_CAT = register("latex_cat",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_CAT).scares(Creeper.class).nightVision().addAbility(ChangedAbilities.TOGGLE_NIGHT_VISION));
    public static final RegistryObject<TransfurVariant<LatexChicken>> LATEX_CHICKEN = register("latex_chicken",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_CHICKEN).extraJumps(2).glide());
    public static final RegistryObject<TransfurVariant<LatexCow>> LATEX_COW = register("latex_cow",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_COW));
    public static final RegistryObject<TransfurVariant<LatexCreeper>> LATEX_CREEPER = register("latex_creeper",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_CREEPER).quadrupedal().cameraZOffset(7.0f / 16.0f).rideable());
    public static final RegistryObject<TransfurVariant<LatexFox>> LATEX_FOX = register("latex_fox",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_FOX));
    public static final RegistryObject<TransfurVariant<LatexFoxPartial>> LATEX_FOX_PARTIAL = register("latex_fox_partial",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_FOX_PARTIAL).transfurMode(TransfurMode.NONE));
    /*public static final RegistryObject<TransfurVariant<LatexGhast>> LATEX_GHAST = register("latex_ghast",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_GHAST));*/
    /*public static final RegistryObject<TransfurVariant<LatexGuardian>> LATEX_GUARDIAN = register("latex_guardian",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_GUARDIAN).gills().noLegs());*/
    public static final RegistryObject<TransfurVariant<LatexOcelot>> LATEX_OCELOT = register("latex_ocelot",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_OCELOT).scares(Creeper.class).nightVision().addAbility(ChangedAbilities.TOGGLE_NIGHT_VISION));
    public static final RegistryObject<TransfurVariant<LatexSheep>> LATEX_SHEEP = register("latex_sheep",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SHEEP));
    public static final RegistryObject<TransfurVariant<LatexPig>> LATEX_PIG = register("latex_pig",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_PIG));
    public static final RegistryObject<TransfurVariant<LatexSkeleton>> LATEX_SKELETON = register("latex_skeleton",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SKELETON));
    public static final RegistryObject<TransfurVariant<LatexSlime>> LATEX_SLIME = register("latex_slime",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SLIME));
    public static final RegistryObject<TransfurVariant<LatexSpider>> LATEX_SPIDER = register("latex_spider",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SPIDER).canClimb().extraHands().nightVision().addAbility(ChangedAbilities.TOGGLE_NIGHT_VISION).addAbility(ChangedAbilities.CREATE_COBWEB));
    public static final RegistryObject<TransfurVariant<LatexZombie>> LATEX_ZOMBIE = register("latex_zombie",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_ZOMBIE));

    public static void registerMobAssimilation(ProcessTransfur.GatherMobAssimilationsEvent event) {
        event.register(EntityType.CAT, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_CAT, 3.0f, (sourceEntity, newEntity) -> {
                    if (newEntity.getChangedEntity() instanceof LatexCat targetEntity)
                        targetEntity.setVariant(sourceEntity.getVariant());
                })));
        event.register(EntityType.CHICKEN, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_CHICKEN, 3.0f)));
        event.register(EntityType.COW, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_COW, 3.0f)));
        event.register(EntityType.CREEPER, EntityAssimilationBehavior.latexAssimilation(1.0D, true,
                TransfurDecider.simpleMobDecider(LATEX_CREEPER, 3.0f)));
        event.register(EntityType.FOX, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_FOX, 3.0f, (sourceEntity, newEntity) -> {
                    if (newEntity.getChangedEntity() instanceof LatexFox targetEntity)
                        targetEntity.setVariant(sourceEntity.getVariant());
                })));
        /*event.register(EntityType.GUARDIAN, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_GUARDIAN, 3.0f)));*/
        event.register(EntityType.OCELOT, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_OCELOT, 3.0f)));
        event.register(EntityType.SHEEP, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_SHEEP, 3.0f)));
        event.register(EntityType.PIG, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_PIG, 3.0f)));
        event.register(EntityType.SKELETON, EntityAssimilationBehavior.uniqueVariant(LATEX_SKELETON));
        event.register(EntityType.SPIDER, EntityAssimilationBehavior.latexAssimilation(1.2D, true,
                TransfurDecider.simpleMobDecider(LATEX_SPIDER, 3.0f)));
        event.register(EntityType.ZOMBIE, EntityAssimilationBehavior.uniqueVariant(LATEX_ZOMBIE));
    }
}
