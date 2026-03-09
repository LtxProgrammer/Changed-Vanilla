package net.ltxprogrammer.changedvanilla.init;

import net.ltxprogrammer.changed.client.RegisterComplexRenderersEvent;
import net.ltxprogrammer.changed.init.ChangedEntityRenderers;
import net.ltxprogrammer.changedvanilla.client.render.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedVanillaEntityRenderers {
    @SubscribeEvent
    public static void registerComplexEntityRenderers(RegisterComplexRenderersEvent event) {
        event.registerEntityRenderer(ChangedVanillaEntities.LATEX_FOX_PARTIAL.get(), "default", LatexFoxPartialRenderer.forModelSize(false));
        event.registerEntityRenderer(ChangedVanillaEntities.LATEX_FOX_PARTIAL.get(), "slim", LatexFoxPartialRenderer.forModelSize(true));
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ChangedEntityRenderers.registerHumanoid(event, ChangedVanillaEntities.LATEX_FOX.get(), LatexFoxRenderer::new);
        ChangedEntityRenderers.registerHumanoid(event, ChangedVanillaEntities.LATEX_GHAST.get(), LatexGhastRenderer::new);
        ChangedEntityRenderers.registerHumanoid(event, ChangedVanillaEntities.LATEX_GUARDIAN.get(), LatexGuardianRenderer::new);
        ChangedEntityRenderers.registerHumanoid(event, ChangedVanillaEntities.LATEX_SKELETON.get(), LatexSkeletonRenderer::new);
    }
}
