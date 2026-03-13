package net.ltxprogrammer.changedvanilla.client.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.*;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexFoxPartialModel;
import net.ltxprogrammer.changedvanilla.entity.LatexFoxPartial;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class LatexFoxPartialRenderer extends AdvancedHumanoidRenderer<LatexFoxPartial, LatexFoxPartialModel> {
    private final Map<Fox.Type, ResourceLocation> skinLocations;

    public LatexFoxPartialRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, LatexFoxPartialModel.human(context.bakeLayer(
                slim ? LatexFoxPartialModel.LAYER_LOCATION_HUMAN_SLIM : LatexFoxPartialModel.LAYER_LOCATION_HUMAN)), ArmorLatexMaleWolfModel.MODEL_SET, 0.5f);

        var skinLocationsBuilder = ImmutableMap.<Fox.Type, ResourceLocation>builder();
        Arrays.stream(Fox.Type.values()).forEach(type -> {
            skinLocationsBuilder.put(type, ChangedVanilla.modResource((slim ?
                    "textures/entity/latex_fox_partial/%s_slim.png" :
                    "textures/entity/latex_fox_partial/%s.png").formatted(type.getSerializedName())));
        });
        this.skinLocations = skinLocationsBuilder.build();

        var partialModel = new LatexPartialLayer<>(this, LatexFoxPartialModel.latex(
                context.bakeLayer(slim ? LatexFoxPartialModel.LAYER_LOCATION_LATEX_SLIM : LatexFoxPartialModel.LAYER_LOCATION_LATEX)),
                this::getPartialTextureLocation);
        this.addLayer(partialModel);
        this.addLayer(new LatexParticlesLayer<>(this).addModel(partialModel.getModel(), partialModel.getTextureFunction()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(new DarkLatexMaskLayer<>(this, context.getModelSet()));
        this.addLayer(new GasMaskLayer<>(this, context.getModelSet()));
    }

    public static EntityRendererProvider<LatexFoxPartial> forModelSize(boolean slim) {
        return (context) -> new LatexFoxPartialRenderer(context, slim);
    }

    @Override
    public ResourceLocation getTextureLocation(LatexFoxPartial partial) {
        return partial.getSkinTextureLocation();
    }

    public ResourceLocation getPartialTextureLocation(LatexFoxPartial partial) {
        return this.skinLocations.get(partial.getVariant());
    }

    @Override
    public void render(LatexFoxPartial latex, float yRot, float p_115457_, PoseStack p_115458_, MultiBufferSource bufferSource, int p_115460_) {
        if (latex.getUnderlyingPlayer() instanceof AbstractClientPlayer clientPlayer)
            this.model.setModelProperties(clientPlayer);
        else
            this.model.defaultModelProperties();
        super.render(latex, yRot, p_115457_, p_115458_, bufferSource, p_115460_);
    }

    @Override
    protected void scale(LatexFoxPartial entity, PoseStack pose, float partialTick) {
        float f = 0.9375F;
        pose.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected void scaleForBPI(@NotNull LatexFoxPartial entity, BasicPlayerInfo bpi, PoseStack poseStack) {

    }
}
