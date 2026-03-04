package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.LatexGuardian;
import net.ltxprogrammer.changedvanilla.entity.LatexSkeleton;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ChangedVanillaTransfurVariants {
    public static final DeferredRegister<TransfurVariant<?>> REGISTRY = ChangedRegistry.TRANSFUR_VARIANT.createDeferred(ChangedVanilla.MODID);

    private static <T extends ChangedEntity> RegistryObject<TransfurVariant<T>> register(String name, TransfurVariant.Builder<T> builder) {
        return REGISTRY.register(name, builder::build);
    }

    public static final RegistryObject<TransfurVariant<LatexGuardian>> LATEX_GUARDIAN = register("latex_guardian",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_GUARDIAN));
    public static final RegistryObject<TransfurVariant<LatexSkeleton>> LATEX_SKELETON = register("latex_skeleton",
            TransfurVariant.Builder.of(ChangedVanillaEntities.LATEX_SKELETON));
}
