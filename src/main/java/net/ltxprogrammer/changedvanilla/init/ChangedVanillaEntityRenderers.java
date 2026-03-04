package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.init.ChangedEntityRenderers;
import net.ltxprogrammer.changedvanilla.client.render.LatexSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedVanillaEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ChangedEntityRenderers.registerHumanoid(event, ChangedVanillaEntities.LATEX_SKELETON.get(), LatexSkeletonRenderer::new);
    }
}
