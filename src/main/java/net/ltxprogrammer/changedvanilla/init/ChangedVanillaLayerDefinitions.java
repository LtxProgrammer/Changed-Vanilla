package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changedvanilla.client.render.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedVanillaLayerDefinitions {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LatexCatModel.LAYER_LOCATION, LatexCatModel::createBodyLayer);
        event.registerLayerDefinition(LatexFoxModel.LAYER_LOCATION, LatexFoxModel::createBodyLayer);
        event.registerLayerDefinition(LatexFoxPartialModel.LAYER_LOCATION_LATEX, () -> LatexFoxPartialModel.createLatexLayer(false));
        event.registerLayerDefinition(LatexFoxPartialModel.LAYER_LOCATION_LATEX_SLIM, () -> LatexFoxPartialModel.createLatexLayer(true));
        event.registerLayerDefinition(LatexGhastModel.LAYER_LOCATION, LatexGhastModel::createBodyLayer);
        event.registerLayerDefinition(LatexGuardianModel.LAYER_LOCATION, LatexGuardianModel::createBodyLayer);
        event.registerLayerDefinition(LatexSkeletonModel.LAYER_LOCATION, LatexSkeletonModel::createBodyLayer);
    }
}
