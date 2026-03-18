package net.ltxprogrammer.changedvanilla.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.client.renderer.animate.AnimatorPresets;
import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.renderer.model.DarkLatexWolfPartialModel;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.LatexFoxPartial;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;

import java.util.List;
import java.util.Map;

public class LatexFoxPartialModel extends AdvancedHumanoidModel<LatexFoxPartial> {
    public static final ModelLayerLocation LAYER_LOCATION_HUMAN = DarkLatexWolfPartialModel.LAYER_LOCATION_HUMAN;
    public static final ModelLayerLocation LAYER_LOCATION_HUMAN_SLIM = DarkLatexWolfPartialModel.LAYER_LOCATION_HUMAN_SLIM;
    public static final ModelLayerLocation LAYER_LOCATION_LATEX = new ModelLayerLocation(ChangedVanilla.modResource("latex_fox_partial"), "latex");
    public static final ModelLayerLocation LAYER_LOCATION_LATEX_SLIM = new ModelLayerLocation(ChangedVanilla.modResource("latex_fox_partial"), "latex_slim");
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart Head;
    private final ModelPart Torso;
    private final ModelPart Tail;

    private final ModelPart RightPants;
    private final ModelPart LeftPants;
    private final ModelPart RightSleeve;
    private final ModelPart LeftSleeve;
    private final ModelPart Hat;
    private final ModelPart Jacket;

    private final ModelPart RightLowerLeg;
    private final ModelPart LeftLowerLeg;
    private final ModelPart RightEar;
    private final ModelPart LeftEar;

    private final boolean latexLayer;

    private final HumanoidAnimator<LatexFoxPartial, LatexFoxPartialModel> animator;

    private static final ModelPart NULL_PART = new ModelPart(List.of(), Map.of());

    public LatexFoxPartialModel(ModelPart root, boolean latexLayer) {
        super(root);
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.Tail = Torso.getChild("Tail");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");

        ModelPart tailPrimary = Tail.getChild("TailPrimary");
        ModelPart tailSecondary = tailPrimary.getChild("TailSecondary");
        ModelPart tailTertiary = tailSecondary.getChild("TailTertiary");

        LeftEar = Head.getChild("LeftEar");
        RightEar = Head.getChild("RightEar");

        LeftLowerLeg = LeftLeg.getChild("LeftLowerLeg");
        ModelPart leftFoot = LeftLowerLeg.getChild("LeftFoot");
        ModelPart leftPad = leftFoot.getChild("LeftPad");
        RightLowerLeg = RightLeg.getChild("RightLowerLeg");
        ModelPart rightFoot = RightLowerLeg.getChild("RightFoot");
        ModelPart rightPad = rightFoot.getChild("RightPad");

        if (latexLayer) {
            RightPants = NULL_PART;
            LeftPants = NULL_PART;
            RightSleeve = NULL_PART;
            LeftSleeve = NULL_PART;
            Hat = NULL_PART;
            Jacket = NULL_PART;
        } else {
            RightPants = RightLeg.getChild("RightPants");
            LeftPants = LeftLeg.getChild("LeftPants");
            RightSleeve = RightArm.getChild("RightSleeve");
            LeftSleeve = LeftArm.getChild("LeftSleeve");
            Hat = Head.getChild("Hat");
            Jacket = Torso.getChild("Jacket");
        }

        this.latexLayer = latexLayer;

        animator = HumanoidAnimator.of(this).hipOffset(-1.5f)
                .addPreset(AnimatorPresets.wolfLike(
                        Head, LeftEar, RightEar,
                        Torso, LeftArm, RightArm,
                        Tail, List.of(tailPrimary, tailSecondary, tailTertiary),
                        LeftLeg, LeftLowerLeg, leftFoot, leftPad, RightLeg, RightLowerLeg, rightFoot, rightPad));
    }

    public static LatexFoxPartialModel human(ModelPart root) {
        return new LatexFoxPartialModel(root, false);
    }

    public static LatexFoxPartialModel latex(ModelPart root) {
        return new LatexFoxPartialModel(root, true);
    }

    public void defaultModelProperties() {
        Hat.visible = true;
        Jacket.visible = true;
        LeftPants.visible = true;
        RightPants.visible = true;
        LeftSleeve.visible = true;
        RightSleeve.visible = true;
    }

    public void setModelProperties(AbstractClientPlayer player) {
        Hat.visible = player.isModelPartShown(PlayerModelPart.HAT);
        Jacket.visible = player.isModelPartShown(PlayerModelPart.JACKET);
        LeftPants.visible = player.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
        RightPants.visible = player.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
        LeftSleeve.visible = player.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
        RightSleeve.visible = player.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
    }

    public static LayerDefinition createLatexLayer(boolean slim) {
        float armWidth = slim ? 3.0f : 4.0f;
        float rightArmOffset = slim ? 1.0f : 0.0f;

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 10.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition RightThigh_r1 = RightLeg.addOrReplaceChild("RightThigh_r1", CubeListBuilder.create().texOffs(48, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0025f)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition RightLowerLeg = RightLeg.addOrReplaceChild("RightLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.375F, -3.45F));

        PartDefinition RightCalf_r1 = RightLowerLeg.addOrReplaceChild("RightCalf_r1", CubeListBuilder.create().texOffs(48, 27).addBox(-1.99F, -0.125F, -2.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.125F, 1.95F, 0.8727F, 0.0F, 0.0F));

        PartDefinition RightFoot = RightLowerLeg.addOrReplaceChild("RightFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 7.175F));

        PartDefinition RightArch_r1 = RightFoot.addOrReplaceChild("RightArch_r1", CubeListBuilder.create().texOffs(0, 59).addBox(-2.0F, -8.45F, -0.725F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, 7.075F, -4.975F, -0.3491F, 0.0F, 0.0F));

        PartDefinition RightPad = RightFoot.addOrReplaceChild("RightPad", CubeListBuilder.create().texOffs(52, 46).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.325F, -4.425F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 10.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition LeftThigh_r1 = LeftLeg.addOrReplaceChild("LeftThigh_r1", CubeListBuilder.create().texOffs(20, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0025f)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition LeftLowerLeg = LeftLeg.addOrReplaceChild("LeftLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.375F, -3.45F));

        PartDefinition LeftCalf_r1 = LeftLowerLeg.addOrReplaceChild("LeftCalf_r1", CubeListBuilder.create().texOffs(36, 48).addBox(-2.01F, -0.125F, -2.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.125F, 1.95F, 0.8727F, 0.0F, 0.0F));

        PartDefinition LeftFoot = LeftLowerLeg.addOrReplaceChild("LeftFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 7.175F));

        PartDefinition LeftArch_r1 = LeftFoot.addOrReplaceChild("LeftArch_r1", CubeListBuilder.create().texOffs(36, 58).addBox(-2.0F, -8.45F, -0.725F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, 7.075F, -4.975F, -0.3491F, 0.0F, 0.0F));

        PartDefinition LeftPad = LeftFoot.addOrReplaceChild("LeftPad", CubeListBuilder.create().texOffs(52, 53).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.325F, -4.425F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0025f)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-3.0F, -7.5F, 1.0F));

        PartDefinition RightEarPivot = RightEar.addOrReplaceChild("RightEarPivot", CubeListBuilder.create().texOffs(24, 59).addBox(-1.9F, -3.2F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.5F, -1.25F, 0.0F, -0.1309F, 0.5236F, -0.3491F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(3.0F, -7.5F, 1.0F));

        PartDefinition LeftEarPivot = LeftEar.addOrReplaceChild("LeftEarPivot", CubeListBuilder.create().texOffs(14, 60).addBox(-1.1F, -3.2F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.5F, -1.25F, 0.0F, -0.1309F, -0.5236F, 0.3491F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0025f))
                .texOffs(0, 69).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition Tail = Torso.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, 0.0F));

        PartDefinition TailPrimary = Tail.addOrReplaceChild("TailPrimary", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition Base_r1 = TailPrimary.addOrReplaceChild("Base_r1", CubeListBuilder.create().texOffs(48, 37).addBox(-2.0F, 0.75F, -1.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

        PartDefinition TailSecondary = TailPrimary.addOrReplaceChild("TailSecondary", CubeListBuilder.create(), PartPose.offset(0.0F, 1.25F, 4.5F));

        PartDefinition Base_r2 = TailSecondary.addOrReplaceChild("Base_r2", CubeListBuilder.create().texOffs(0, 46).addBox(-2.5F, -0.45F, -2.1F, 5.0F, 8.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 1.4835F, 0.0F, 0.0F));

        PartDefinition TailTertiary = TailSecondary.addOrReplaceChild("TailTertiary", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, 3.5F));

        PartDefinition Base_r3 = TailTertiary.addOrReplaceChild("Base_r3", CubeListBuilder.create().texOffs(56, 0).addBox(-2.0F, -1.2F, -1.95F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 1.8326F, 0.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(32, 32).addBox(-3.0F + rightArmOffset, -2.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.0025f)), PartPose.offsetAndRotation(-5.0F, 1.5F, 0.0F, 0.2618F, 0.0F, 0.2618F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 16).addBox(-1.0F, -2.0F, -2.0F, armWidth, 12.0F, 4.0F, new CubeDeformation(0.0025f)), PartPose.offsetAndRotation(5.0F, 1.5F, 0.0F, -0.2618F, 0.0F, -0.2618F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    @Override
    public void prepareMobModel(LatexFoxPartial entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);

        if (!this.latexLayer) {
            Tail.visible = false;
            LeftLowerLeg.visible = false;
            RightLowerLeg.visible = false;
            LeftEar.visible = false;
            RightEar.visible = false;
        }
    }

    public ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.LeftArm : this.RightArm;
    }

    public ModelPart getLeg(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.LeftLeg : this.RightLeg;
    }

    public ModelPart getHead() {
        return this.Head;
    }

    public ModelPart getTorso() {
        return Torso;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        RightLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        Torso.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public HumanoidAnimator<LatexFoxPartial, LatexFoxPartialModel> getAnimator(LatexFoxPartial entity) {
        return animator;
    }
}
