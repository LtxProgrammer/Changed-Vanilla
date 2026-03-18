package net.ltxprogrammer.changedvanilla.client.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleCatModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexCatModel;
import net.ltxprogrammer.changedvanilla.client.render.model.LatexFoxModel;
import net.ltxprogrammer.changedvanilla.entity.LatexCat;
import net.ltxprogrammer.changedvanilla.entity.LatexFox;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.Fox;

import java.util.Arrays;
import java.util.Map;

public class LatexCatRenderer extends AdvancedHumanoidRenderer<LatexCat, LatexCatModel> {
    private final Map<CatVariant, ResourceLocation> skinLocations;

    public LatexCatRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexCatModel(context.bakeLayer(LatexCatModel.LAYER_LOCATION)), ArmorLatexMaleCatModel.MODEL_SET, 0.5f);

        var skinLocationsBuilder = ImmutableMap.<CatVariant, ResourceLocation>builder();
        BuiltInRegistries.CAT_VARIANT.entrySet().forEach(entry -> {
            ResourceLocation id = entry.getKey().location();
            skinLocationsBuilder.put(entry.getValue(), ChangedVanilla.modResource(
                    "textures/entity/latex_cat/%s/%s.png".formatted(id.getNamespace(), id.getPath())));
        });

        this.skinLocations = skinLocationsBuilder.build();
        this.addLayer(new LatexParticlesLayer<>(this, getModel()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(CustomEyesLayer.builder(this, context.getModelSet())
                .withSclera(CustomEyesLayer::irisColorLeft).withIris(Color3.fromInt(0x111111)).build());
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexCat entity) {
        return this.skinLocations.get(entity.getVariant());
    }

    @Override
    protected void scale(LatexCat entity, PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        poseStack.scale(0.9f, 0.9f, 0.9f);
    }
}
