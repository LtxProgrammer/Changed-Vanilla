package net.ltxprogrammer.changedvanilla.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.client.animations.Limb;
import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.animate.arm.ArmBobAnimator;
import net.ltxprogrammer.changed.client.renderer.animate.arm.ArmRideAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.tfanimations.HelperModel;
import net.ltxprogrammer.changedvanilla.ChangedVanilla;
import net.ltxprogrammer.changedvanilla.client.render.animate.ChangedVanillaAnimatorPresets;
import net.ltxprogrammer.changedvanilla.client.render.animate.leviathan.GhastHeadInitAnimator;
import net.ltxprogrammer.changedvanilla.entity.LatexGhast;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LatexGhastModel extends AdvancedHumanoidModel<LatexGhast> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ChangedVanilla.modResource("latex_ghast"), "main");
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart Head;
    private final ModelPart Torso;
    private final ModelPart LeftTentacles;
    private final ModelPart RightTentacles;
    private final HumanoidAnimator<LatexGhast, LatexGhastModel> animator;

    public LatexGhastModel(ModelPart root) {
        super(root);
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.RightTentacles = root.getChild("TentaclesRight");
        this.LeftTentacles = root.getChild("TentaclesLeft");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");

        var tentacleFL = new ArrayList<ModelPart>();
        tentacleFL.add(LeftTentacles.getChild("FrontL"));
        tentacleFL.add(last(tentacleFL).getChild("FL1"));
        tentacleFL.add(last(tentacleFL).getChild("FL2"));
        tentacleFL.add(last(tentacleFL).getChild("FL3"));
        tentacleFL.add(last(tentacleFL).getChild("FL4"));
        var tentacleSL = new ArrayList<ModelPart>();
        tentacleSL.add(LeftTentacles.getChild("SideL"));
        tentacleSL.add(last(tentacleSL).getChild("SL1"));
        tentacleSL.add(last(tentacleSL).getChild("SL2"));
        tentacleSL.add(last(tentacleSL).getChild("SL3"));
        tentacleSL.add(last(tentacleSL).getChild("SL4"));
        var tentacleBL = new ArrayList<ModelPart>();
        tentacleBL.add(LeftTentacles.getChild("BackL"));
        tentacleBL.add(last(tentacleBL).getChild("BL1"));
        tentacleBL.add(last(tentacleBL).getChild("BL2"));
        tentacleBL.add(last(tentacleBL).getChild("BL3"));
        tentacleBL.add(last(tentacleBL).getChild("BL4"));
        var tentacleFR = new ArrayList<ModelPart>();
        tentacleFR.add(RightTentacles.getChild("FrontR"));
        tentacleFR.add(last(tentacleFR).getChild("FR1"));
        tentacleFR.add(last(tentacleFR).getChild("FR2"));
        tentacleFR.add(last(tentacleFR).getChild("FR3"));
        tentacleFR.add(last(tentacleFR).getChild("FR4"));
        var tentacleSR = new ArrayList<ModelPart>();
        tentacleSR.add(RightTentacles.getChild("SideR"));
        tentacleSR.add(last(tentacleSR).getChild("SR1"));
        tentacleSR.add(last(tentacleSR).getChild("SR2"));
        tentacleSR.add(last(tentacleSR).getChild("SR3"));
        tentacleSR.add(last(tentacleSR).getChild("SR4"));
        var tentacleBR = new ArrayList<ModelPart>();
        tentacleBR.add(RightTentacles.getChild("BackR"));
        tentacleBR.add(last(tentacleBR).getChild("BR1"));
        tentacleBR.add(last(tentacleBR).getChild("BR2"));
        tentacleBR.add(last(tentacleBR).getChild("BR3"));
        tentacleBR.add(last(tentacleBR).getChild("BR4"));

        animator = HumanoidAnimator.of(this).hipOffset(-1.5f)
                .addPreset(ChangedVanillaAnimatorPresets.ghastUpperBody(
                        Head, Torso, LeftArm, RightArm))
                .addPreset(ChangedVanillaAnimatorPresets.ghastTentacles(LeftTentacles, RightTentacles,
                        tentacleFL, tentacleSL, tentacleBL, tentacleFR, tentacleSR, tentacleBR))
                .addAnimator(new GhastHeadInitAnimator<>(Head))
                .addAnimator(new ArmBobAnimator<>(LeftArm, RightArm))
                .addAnimator(new ArmRideAnimator<>(LeftArm, RightArm));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition TentaclesRight = partdefinition.addOrReplaceChild("TentaclesRight", CubeListBuilder.create(), PartPose.offset(2.0F, 11.5F, 0.0F));

        PartDefinition FrontR = TentaclesRight.addOrReplaceChild("FrontR", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.5F, -0.5F, -1.3963F, -0.4363F, 0.0F));

        PartDefinition cube_r1 = FrontR.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 37).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition FR1 = FrontR.addOrReplaceChild("FR1", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 0.0F));

        PartDefinition cube_r2 = FR1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 16).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.5F, 2.0F, -0.5F, 0.0F, 0.2182F, 0.0F));

        PartDefinition FR2 = FR1.addOrReplaceChild("FR2", CubeListBuilder.create().texOffs(48, 56).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition FR3 = FR2.addOrReplaceChild("FR3", CubeListBuilder.create().texOffs(12, 72).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition FR4 = FR3.addOrReplaceChild("FR4", CubeListBuilder.create().texOffs(20, 72).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition BackR = TentaclesRight.addOrReplaceChild("BackR", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, 0.5F, 0.5F, 1.3963F, 0.4363F, 0.0F));

        PartDefinition cube_r3 = BackR.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition BR1 = BackR.addOrReplaceChild("BR1", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 0.0F));

        PartDefinition cube_r4 = BR1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 60).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.5F, 2.0F, -0.5F, 0.0F, -0.2182F, 0.0F));

        PartDefinition BR2 = BR1.addOrReplaceChild("BR2", CubeListBuilder.create().texOffs(60, 56).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition BR3 = BR2.addOrReplaceChild("BR3", CubeListBuilder.create().texOffs(72, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition BR4 = BR3.addOrReplaceChild("BR4", CubeListBuilder.create().texOffs(68, 75).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SideR = TentaclesRight.addOrReplaceChild("SideR", CubeListBuilder.create().texOffs(48, 32).addBox(-2.0F, 0.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, -1.3963F));

        PartDefinition SR1 = SideR.addOrReplaceChild("SR1", CubeListBuilder.create().texOffs(16, 61).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 8.5F, 0.0F));

        PartDefinition SR2 = SR1.addOrReplaceChild("SR2", CubeListBuilder.create().texOffs(64, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SR3 = SR2.addOrReplaceChild("SR3", CubeListBuilder.create().texOffs(0, 76).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SR4 = SR3.addOrReplaceChild("SR4", CubeListBuilder.create().texOffs(76, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition TentaclesLeft = partdefinition.addOrReplaceChild("TentaclesLeft", CubeListBuilder.create(), PartPose.offset(-2.0F, 11.5F, 0.0F));

        PartDefinition FrontL = TentaclesLeft.addOrReplaceChild("FrontL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.5F, -0.5F, -1.3963F, 0.4363F, 0.0F));

        PartDefinition cube_r5 = FrontL.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 37).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition FL1 = FrontL.addOrReplaceChild("FL1", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 0.0F));

        PartDefinition cube_r6 = FL1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(56, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 2.0F, -0.5F, 0.0F, -0.2182F, 0.0F));

        PartDefinition FL2 = FL1.addOrReplaceChild("FL2", CubeListBuilder.create().texOffs(48, 56).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition FL3 = FL2.addOrReplaceChild("FL3", CubeListBuilder.create().texOffs(12, 72).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition FL4 = FL3.addOrReplaceChild("FL4", CubeListBuilder.create().texOffs(20, 72).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition BackL = TentaclesLeft.addOrReplaceChild("BackL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 0.5F, 0.5F, 1.3963F, -0.4363F, 0.0F));

        PartDefinition cube_r7 = BackL.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 48).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition BL1 = BackL.addOrReplaceChild("BL1", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 0.0F));

        PartDefinition cube_r8 = BL1.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(32, 60).mirror().addBox(-2.0F, -2.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.5F, 2.0F, -0.5F, 0.0F, 0.2182F, 0.0F));

        PartDefinition BL2 = BL1.addOrReplaceChild("BL2", CubeListBuilder.create().texOffs(60, 56).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition BL3 = BL2.addOrReplaceChild("BL3", CubeListBuilder.create().texOffs(72, 49).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition BL4 = BL3.addOrReplaceChild("BL4", CubeListBuilder.create().texOffs(68, 75).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SideL = TentaclesLeft.addOrReplaceChild("SideL", CubeListBuilder.create().texOffs(48, 32).mirror().addBox(-2.0F, 0.5F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition SL1 = SideL.addOrReplaceChild("SL1", CubeListBuilder.create().texOffs(16, 61).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 8.5F, 0.0F));

        PartDefinition SL2 = SL1.addOrReplaceChild("SL2", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SL3 = SL2.addOrReplaceChild("SL3", CubeListBuilder.create().texOffs(0, 76).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition SL4 = SL3.addOrReplaceChild("SL4", CubeListBuilder.create().texOffs(76, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 1.5F, 0.0F, -0.2618F, 0.0F, -0.2618F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 37).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 1.5F, 0.0F, 0.2618F, 0.0F, 0.2618F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(32, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(29, 80).addBox(-1.5F, -3.0F, -5.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition Hair = Head.addOrReplaceChild("Hair", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(0, 19).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(4.0F, -6.0F, 0.5F));

        PartDefinition cube_r9 = LeftEar.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(68, 67).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.475F, 2.55F, 0.0F, 0.4287F, 0.4011F, 0.1766F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, 0.5F));

        PartDefinition cube_r10 = RightEar.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(68, 67).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.475F, 2.55F, 0.0F, 0.4287F, -0.4011F, -0.1766F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    public ModelPart getArm(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.LeftArm : this.RightArm;
    }

    public ModelPart getLeg(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.LeftTentacles : this.RightTentacles;
    }

    public ModelPart getHead() {
        return this.Head;
    }

    public ModelPart getTorso() {
        return Torso;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        LeftTentacles.render(poseStack, buffer, packedLight, packedOverlay);
        RightTentacles.render(poseStack, buffer, packedLight, packedOverlay);
        Head.render(poseStack, buffer, packedLight, packedOverlay);
        Torso.render(poseStack, buffer, packedLight, packedOverlay);
        RightArm.render(poseStack, buffer, packedLight, packedOverlay);
        LeftArm.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public @Nullable HelperModel getTransfurHelperModel(Limb limb) {
        return null;
    }

    @Override
    public HumanoidAnimator<LatexGhast, LatexGhastModel> getAnimator(LatexGhast entity) {
        return animator;
    }
}
