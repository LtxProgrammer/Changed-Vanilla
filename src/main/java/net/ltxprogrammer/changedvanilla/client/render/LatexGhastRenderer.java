package net.ltxprogrammer.changedvanilla.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorNoneModel;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexGhastModel;
import net.ltxprogrammer.changedvanilla.entity.LatexGhast;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LatexGhastRenderer extends AdvancedHumanoidRenderer<LatexGhast, LatexGhastModel> {
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = ChangedVanilla.modResource("textures/entity/latex_ghast.png");

    public LatexGhastRenderer(EntityRendererProvider.Context context) {
        // TODO tentacle armor model
        super(context, new LatexGhastModel(context.bakeLayer(LatexGhastModel.LAYER_LOCATION)), ArmorNoneModel.MODEL_SET, 1.0f);
        this.addLayer(new LatexParticlesLayer<>(this, this.model));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(CustomEyesLayer.builder(this, context.getModelSet())
                .withSclera(Color3.fromInt(0x1a0b00)).build());
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexGhast entity) {
        return DEFAULT_SKIN_LOCATION;
    }

    @Override
    protected void scale(LatexGhast entity, PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        poseStack.scale(2.0f, 2.0f, 2.0f);
    }

    @Override
    protected void setupRotations(@NotNull LatexGhast entity, PoseStack poseStack, float bob, float bodyYRot, float partialTicks) {
        super.setupRotations(entity, poseStack, bob, bodyYRot, partialTicks);
        // TODO tilt forward/backward when in STANDING pose and moving
    }
}