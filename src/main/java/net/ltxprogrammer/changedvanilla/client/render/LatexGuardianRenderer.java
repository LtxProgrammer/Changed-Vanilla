package net.ltxprogrammer.changedvanilla.client.render;

import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorMermaidSharkAbdomenModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorMermaidSharkUpperBodyModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorModelPicker;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexGuardianModel;
import net.ltxprogrammer.changedvanilla.entity.LatexGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexGuardianRenderer extends AdvancedHumanoidRenderer<LatexGuardian, LatexGuardianModel> {
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = ChangedVanilla.modResource("textures/entity/latex_guardian.png");

    public LatexGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexGuardianModel(context.bakeLayer(LatexGuardianModel.LAYER_LOCATION)),
                ArmorModelPicker.legless(context.getModelSet(), ArmorMermaidSharkUpperBodyModel.MODEL_SET, ArmorMermaidSharkAbdomenModel.MODEL_SET), 0.5f);
        this.addLayer(new CustomEyesLayer<>(this, context.getModelSet()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(new LatexParticlesLayer<>(this, this.model));
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexGuardian entity) {
        return DEFAULT_SKIN_LOCATION;
    }
}
