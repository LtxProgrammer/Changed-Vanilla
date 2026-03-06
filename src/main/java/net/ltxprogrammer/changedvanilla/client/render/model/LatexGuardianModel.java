package net.ltxprogrammer.changedvanilla.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.client.animations.Limb;
import net.ltxprogrammer.changed.client.renderer.animate.AnimatorPresets;
import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.renderer.model.LeglessModel;
import net.ltxprogrammer.changed.client.tfanimations.HelperModel;
import net.ltxprogrammer.changed.client.tfanimations.TransfurHelper;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.entity.LatexGuardian;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

import java.util.List;

public class LatexGuardianModel extends AdvancedHumanoidModel<LatexGuardian> implements LeglessModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ChangedVanilla.modResource("latex_guardian"), "main");
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart Head;
    private final ModelPart Torso;
    private final ModelPart Abdomen;
    private final ModelPart LowerAbdomen;
    private final ModelPart Tail;
    private final HumanoidAnimator<LatexGuardian, LatexGuardianModel> animator;

    public LatexGuardianModel(ModelPart root) {
        super(root);
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.Abdomen = root.getChild("Abdomen");
        this.LowerAbdomen = Abdomen.getChild("LowerAbdomen");
        this.Tail = LowerAbdomen.getChild("Tail");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");

        animator = HumanoidAnimator.of(this).hipOffset(-1.5f).torsoLength(9.0f).legLength(9.5f)
                .addPreset(AnimatorPresets.leglessShark(Head, Torso, LeftArm, RightArm, Abdomen, LowerAbdomen, Tail, List.of(
                        Tail.getChild("TailPrimary"),
                        Tail.getChild("TailPrimary").getChild("TailSecondary"),
                        Tail.getChild("TailPrimary").getChild("TailSecondary").getChild("TailTertiary"),
                        Tail.getChild("TailPrimary").getChild("TailSecondary").getChild("TailTertiary").getChild("TailQuaternary"),
                        Tail.getChild("TailPrimary").getChild("TailSecondary").getChild("TailTertiary").getChild("TailQuaternary").getChild("TailQuintary")
                )));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(8, 70).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0101F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition spike_r1 = Head.addOrReplaceChild("spike_r1", CubeListBuilder.create().texOffs(0, 66).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.5F, -7.0F, -2.0F, -0.3491F, 0.0F, -0.1745F));

        PartDefinition spike_r2 = Head.addOrReplaceChild("spike_r2", CubeListBuilder.create().texOffs(62, 65).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(2.5F, -7.0F, -2.0F, -0.3491F, 0.0F, 0.1745F));

        PartDefinition Hair = Head.addOrReplaceChild("Hair", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(4.0F, -2.75F, -1.5F));

        PartDefinition LeftEarPivot = LeftEar.addOrReplaceChild("LeftEarPivot", CubeListBuilder.create().texOffs(8, 59).mirror().addBox(0.0F, -0.5F, -1.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.25F, -0.5F, 0.5F, 0.3448F, 0.5618F, 0.1548F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-4.0F, -2.75F, -1.5F));

        PartDefinition RightEarPivot = RightEar.addOrReplaceChild("RightEarPivot", CubeListBuilder.create().texOffs(8, 59).addBox(0.0F, -0.5F, -1.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.5F, 0.5F, 0.3448F, -0.5618F, -0.1548F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(32, 12).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 46).addBox(0.0F, 0.0F, 2.0F, 0.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition spike_r3 = Torso.addOrReplaceChild("spike_r3", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(3.0F, 3.5F, 1.134F, -0.8727F, 0.4363F, 0.0F));

        PartDefinition spike_r4 = Torso.addOrReplaceChild("spike_r4", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-3.0F, 3.5F, 1.134F, -0.8727F, -0.4363F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(32, 41).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 1.5F, 0.0F, 0.2618F, 0.0F, 0.2618F));

        PartDefinition spike_r5 = RightArm.addOrReplaceChild("spike_r5", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition spike_r6 = RightArm.addOrReplaceChild("spike_r6", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-1.5F, 8.0F, -0.5F, 0.0F, 0.0F, 0.8727F));

        PartDefinition spike_r7 = RightArm.addOrReplaceChild("spike_r7", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.134F, 0.5236F, -0.5236F, 0.0F));

        PartDefinition spike_r8 = RightArm.addOrReplaceChild("spike_r8", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.134F, 0.5236F, -0.1745F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 25).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 1.5F, 0.0F, -0.2618F, 0.0F, -0.2618F));

        PartDefinition spike_r9 = LeftArm.addOrReplaceChild("spike_r9", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

        PartDefinition spike_r10 = LeftArm.addOrReplaceChild("spike_r10", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(1.5F, 8.0F, -0.5F, 0.0F, 0.0F, -0.8727F));

        PartDefinition spike_r11 = LeftArm.addOrReplaceChild("spike_r11", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(2.0F, 1.0F, 0.134F, 0.5236F, 0.5236F, 0.0F));

        PartDefinition spike_r12 = LeftArm.addOrReplaceChild("spike_r12", CubeListBuilder.create().texOffs(56, 19).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.134F, 0.5236F, 0.1745F, 0.0F));

        PartDefinition Abdomen = partdefinition.addOrReplaceChild("Abdomen", CubeListBuilder.create().texOffs(0, 46).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 8.5F, 0.0F, -0.6545F, 0.0F, 0.1309F));

        PartDefinition spike_r13 = Abdomen.addOrReplaceChild("spike_r13", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(3.5F, 2.0F, 0.5F, 0.0F, 0.0F, 0.8727F));

        PartDefinition spike_r14 = Abdomen.addOrReplaceChild("spike_r14", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-3.5F, 2.0F, 0.5F, 0.0F, 0.0F, -0.8727F));

        PartDefinition LowerAbdomen = Abdomen.addOrReplaceChild("LowerAbdomen", CubeListBuilder.create().texOffs(32, 0).addBox(-4.5F, -0.25F, -2.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.25F, 0.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition abfin_r1 = LowerAbdomen.addOrReplaceChild("abfin_r1", CubeListBuilder.create().texOffs(56, 47).mirror().addBox(0.0F, -1.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 2.25F, -1.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition spike_r15 = LowerAbdomen.addOrReplaceChild("spike_r15", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, 4.75F, 1.5F, -1.1272F, -0.6485F, 1.2913F));

        PartDefinition spike_r16 = LowerAbdomen.addOrReplaceChild("spike_r16", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 4.75F, 1.5F, -1.1272F, 0.6485F, -1.2913F));

        PartDefinition spike_r17 = LowerAbdomen.addOrReplaceChild("spike_r17", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 1.5F, -0.6537F, -0.4718F, 1.0353F));

        PartDefinition spike_r18 = LowerAbdomen.addOrReplaceChild("spike_r18", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 1.5F, -0.6537F, 0.4718F, -1.0353F));

        PartDefinition abfin_r2 = LowerAbdomen.addOrReplaceChild("abfin_r2", CubeListBuilder.create().texOffs(56, 47).addBox(-6.0F, -1.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 2.25F, -1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition spike_r19 = LowerAbdomen.addOrReplaceChild("spike_r19", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(3.5F, 3.5F, 1.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition spike_r20 = LowerAbdomen.addOrReplaceChild("spike_r20", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-3.5F, 3.5F, 1.0F, 0.0F, 0.0F, -0.8727F));

        PartDefinition Tail = LowerAbdomen.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(48, 25).addBox(-4.0F, -0.25F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.48F, 0.0F, 0.2618F));

        PartDefinition spike_r21 = Tail.addOrReplaceChild("spike_r21", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(3.0F, 0.75F, 1.25F, -0.3073F, -0.2485F, 0.9113F));

        PartDefinition spike_r22 = Tail.addOrReplaceChild("spike_r22", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-3.0F, 0.75F, 1.25F, -0.3073F, 0.2485F, -0.9113F));

        PartDefinition TailPrimary = Tail.addOrReplaceChild("TailPrimary", CubeListBuilder.create().texOffs(48, 33).addBox(-3.5F, -0.25F, -2.0F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.3054F, 0.0F, 0.1745F));

        PartDefinition spike_r23 = TailPrimary.addOrReplaceChild("spike_r23", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.75F, 0.5F, -1.1272F, -0.6485F, 1.2913F));

        PartDefinition spike_r24 = TailPrimary.addOrReplaceChild("spike_r24", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 0.75F, 0.5F, -1.1272F, 0.6485F, -1.2913F));

        PartDefinition TailSecondary = TailPrimary.addOrReplaceChild("TailSecondary", CubeListBuilder.create().texOffs(48, 40).addBox(-3.0F, -0.25F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 2.75F, 0.0F, 0.1309F, 0.0F, 0.1309F));

        PartDefinition spike_r25 = TailSecondary.addOrReplaceChild("spike_r25", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition spike_r26 = TailSecondary.addOrReplaceChild("spike_r26", CubeListBuilder.create().texOffs(60, 0).addBox(-4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

        PartDefinition TailTertiary = TailSecondary.addOrReplaceChild("TailTertiary", CubeListBuilder.create().texOffs(0, 54).addBox(-2.0F, -0.25F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.1745F, 0.0F, 0.1745F));

        PartDefinition TailQuaternary = TailTertiary.addOrReplaceChild("TailQuaternary", CubeListBuilder.create().texOffs(56, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.75F, 0.0F, 0.2618F, 0.0F, 0.2618F));

        PartDefinition TailQuintary = TailQuaternary.addOrReplaceChild("TailQuintary", CubeListBuilder.create().texOffs(60, 4).addBox(-1.0F, 0.7535F, -0.6366F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.5F, -0.5F, 0.3054F, 0.0F, 0.2182F));

        PartDefinition Base_r1 = TailQuintary.addOrReplaceChild("Base_r1", CubeListBuilder.create().texOffs(44, 57).addBox(-0.5F, 9.4666F, 0.1961F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F))
                .texOffs(14, 54).addBox(-0.5F, 1.5666F, 0.1961F, 1.0F, 8.0F, 3.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 1.8334F, 0.4539F, -0.7418F, 0.0F, 0.0F));

        PartDefinition Base_r2 = TailQuintary.addOrReplaceChild("Base_r2", CubeListBuilder.create().texOffs(30, 67).addBox(-0.5F, -10.3334F, 2.9961F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 47).addBox(-0.5F, -12.3334F, -0.0039F, 1.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.3334F, -1.0461F, -1.9635F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    public ModelPart getArm(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.LeftArm : this.RightArm;
    }

    public ModelPart getLeg(HumanoidArm p_102852_) {
        return null;
    }

    @Override
    public ModelPart getAbdomen() {
        return Abdomen;
    }

    public ModelPart getHead() {
        return this.Head;
    }

    public ModelPart getTorso() {
        return Torso;
    }

    @Override
    public HelperModel getTransfurHelperModel(Limb limb) {
        if (limb == Limb.ABDOMEN)
            return TransfurHelper.getLegless();
        return super.getTransfurHelperModel(limb);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Abdomen.render(poseStack, buffer, packedLight, packedOverlay);
        Head.render(poseStack, buffer, packedLight, packedOverlay);
        Torso.render(poseStack, buffer, packedLight, packedOverlay);
        RightArm.render(poseStack, buffer, packedLight, packedOverlay);
        LeftArm.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public HumanoidAnimator<LatexGuardian, LatexGuardianModel> getAnimator(LatexGuardian entity) {
        return animator;
    }

    @Override
    public boolean shouldModelSit(LatexGuardian entity) {
        return super.shouldModelSit(entity) || LeglessModel.shouldLeglessSit(entity);
    }
}