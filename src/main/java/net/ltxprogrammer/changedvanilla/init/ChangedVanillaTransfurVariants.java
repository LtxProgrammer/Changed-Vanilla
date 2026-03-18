package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.*;
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
    public static final RegistryObject<TransfurVariant<LatexFox>> LATEX_FOX = register("latex_fox",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_FOX));
    public static final RegistryObject<TransfurVariant<LatexFoxPartial>> LATEX_FOX_PARTIAL = register("latex_fox_partial",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_FOX_PARTIAL).transfurMode(TransfurMode.NONE));
    public static final RegistryObject<TransfurVariant<LatexGhast>> LATEX_GHAST = register("latex_ghast",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_GHAST));
    public static final RegistryObject<TransfurVariant<LatexGuardian>> LATEX_GUARDIAN = register("latex_guardian",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_GUARDIAN).gills().noLegs());
    public static final RegistryObject<TransfurVariant<LatexOcelot>> LATEX_OCELOT = register("latex_ocelot",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_OCELOT).scares(Creeper.class).nightVision().addAbility(ChangedAbilities.TOGGLE_NIGHT_VISION));
    public static final RegistryObject<TransfurVariant<LatexSkeleton>> LATEX_SKELETON = register("latex_skeleton",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SKELETON));
}
