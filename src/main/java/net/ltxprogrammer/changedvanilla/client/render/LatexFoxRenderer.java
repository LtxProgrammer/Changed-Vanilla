package net.ltxprogrammer.changedvanilla.client.render;

import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexFoxModel;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexSkeletonModel;
import net.ltxprogrammer.changedvanilla.entity.LatexFox;
import net.ltxprogrammer.changedvanilla.entity.LatexSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexFoxRenderer extends AdvancedHumanoidRenderer<LatexFox, LatexFoxModel> {
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = ChangedVanilla.modResource("textures/entity/latex_fox.png");

    public LatexFoxRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexFoxModel(context.bakeLayer(LatexFoxModel.LAYER_LOCATION)), ArmorLatexMaleWolfModel.MODEL_SET, 0.5f);
        this.addLayer(new LatexParticlesLayer<>(this, getModel()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(CustomEyesLayer.builder(this, context.getModelSet())
                .withSclera(Color3.fromInt(0x242424)).build());
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexFox entity) {
        return DEFAULT_SKIN_LOCATION;
    }
}
