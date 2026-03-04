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
        event.registerLayerDefinition(LatexGuardianModel.LAYER_LOCATION, LatexGuardianModel::createBodyLayer);
        event.registerLayerDefinition(LatexSkeletonModel.LAYER_LOCATION, LatexSkeletonModel::createBodyLayer);
    }
}
