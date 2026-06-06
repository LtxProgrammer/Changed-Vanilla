package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.item.Syringe;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Predicate;

public class ChangedVanillaTabs {
    public static DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChangedVanilla.MODID);

    private static final Predicate<TransfurVariant<?>> CHANGEDVANILLA_ONLY_TRANSFURS = variant -> variant.getFormId().getNamespace().equals(ChangedVanilla.MODID);

    private static RegistryObject<CreativeModeTab> register(String id, Function<CreativeModeTab.Builder, CreativeModeTab> finalizer) {
        return REGISTRY.register(id, () -> finalizer.apply(
                CreativeModeTab.builder().title(Component.translatable("itemGroup.tab_" + ChangedVanilla.MODID + "_" + id))
        ));
    }

    public static RegistryObject<CreativeModeTab> TAB_CHANGEDV_ITEMS = register("items", builder ->
            builder.icon(() -> Syringe.setVariant(new ItemStack(ChangedItems.LATEX_SYRINGE.get()), ChangedVanillaTransfurVariants.LATEX_CREEPER.getId()))
                    .displayItems((params, output) -> {
                        ChangedItems.DARK_LATEX_MASK.get().fillItemList(CHANGEDVANILLA_ONLY_TRANSFURS, params, output);
                        ChangedItems.LATEX_SYRINGE.get().fillItemList(CHANGEDVANILLA_ONLY_TRANSFURS, params, output);
                        ChangedItems.LATEX_FLASK.get().fillItemList(CHANGEDVANILLA_ONLY_TRANSFURS, params, output);
                        ChangedItems.LATEX_TIPPED_ARROW.get().fillItemList(CHANGEDVANILLA_ONLY_TRANSFURS, params, output);
                    })
                    .build());

    public static RegistryObject<CreativeModeTab> TAB_CHANGEDV_ENTITIES = register("entities", builder ->
            builder.icon(() -> new ItemStack(ChangedVanillaEntities.SPAWN_EGGS.get(ChangedVanillaEntities.LATEX_CREEPER).get()))
                    .displayItems((params, output) -> {
                        ChangedVanillaEntities.SPAWN_EGGS.values().stream()
                                .map(RegistryObject::get)
                                .forEach(output::accept);
                    })
                    .build());
}
