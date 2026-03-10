package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhastTentaclesSwimAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractLeviathan6Animator<T, M> {
    public static final float SWIM_RATE = 0.333333334f * 0.55f;
    public static final float DRAG_SCALE = 0.75F;

    public GhastTentaclesSwimAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.SWIM;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float overallSwim = Mth.cos(limbSwing * SWIM_RATE);
        float rotationalDrag = entity.getTailDragAmount(this.core.partialTicks) * 30.0f;

        leftRoot.xRot = Mth.lerp(core.swimAmount, leftRoot.xRot, 0.0f);
        leftRoot.zRot = Mth.lerp(core.swimAmount, leftRoot.zRot, Mth.DEG_TO_RAD * -rotationalDrag * 0.5f);
        rightRoot.xRot = Mth.lerp(core.swimAmount, rightRoot.xRot, 0.0f);
        rightRoot.zRot = Mth.lerp(core.swimAmount, rightRoot.zRot, Mth.DEG_TO_RAD * -rotationalDrag * 0.5f);

        float offset = 0.0F;
        for (int i = 0; i < frontLeft.size(); ++i) {
            float rotation1 = Mth.cos(limbSwing * SWIM_RATE -
                    (((float)Math.PI / 3.0F) * offset));
            float rotation2 = Mth.cos(limbSwing * SWIM_RATE -
                    (((float)Math.PI / 3.0F) * (offset + 0.10f)));
            float rotation3 = Mth.cos(limbSwing * SWIM_RATE -
                    (((float)Math.PI / 3.0F) * (offset + 0.30f)));
            float rotation4 = Mth.cos(limbSwing * SWIM_RATE -
                    (((float)Math.PI / 3.0F) * (offset + 0.20f)));
            offset += 0.75F;

            if (rotation1 > 0.0f)
                rotation1 *= 0.5f;
            if (rotation2 > 0.0f)
                rotation2 *= 0.5f;
            if (rotation3 > 0.0f)
                rotation3 *= 0.5f;
            if (rotation4 > 0.0f)
                rotation4 *= 0.5f;

            float frontBackSwayScale;
            float leftRightSwayScale;
            float frontBackSwayOffset;
            float leftRightSwayOffset;
            if (i == 0) {
                frontBackSwayScale = 7.5f;
                leftRightSwayScale = 5.0f;
                frontBackSwayOffset = 35.0f + Math.max(overallSwim, 0.0f) * 20.0f;
                leftRightSwayOffset = 30.0f + Math.max(overallSwim, 0.0f) * 20.0f;
            } else if (i == 1) {
                frontBackSwayScale = 17.5f;
                leftRightSwayScale = 17.5f;
                frontBackSwayOffset = Math.max(overallSwim, 0.0f) * -25.0f;
                leftRightSwayOffset = 5.0f + Math.max(overallSwim, 0.0f) * -25.0f;
            } else {
                frontBackSwayScale = 17.5f;
                leftRightSwayScale = 17.5f;
                frontBackSwayOffset = 0.0f;
                leftRightSwayOffset = 0.0f;
            }

            frontLeft.get(i).xRot = Mth.lerp(core.swimAmount, frontLeft.get(i).xRot, Mth.DEG_TO_RAD * -(frontBackSwayScale * rotation1 + frontBackSwayOffset));
            frontLeft.get(i).yRot = Mth.lerp(core.swimAmount, frontLeft.get(i).yRot, 0.0f);
            frontLeft.get(i).zRot = Mth.lerp(core.swimAmount, frontLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideLeft.get(i).xRot = Mth.lerp(core.swimAmount, sideLeft.get(i).xRot, 0.0f);
            sideLeft.get(i).yRot = Mth.lerp(core.swimAmount, sideLeft.get(i).yRot, 0.0f);
            sideLeft.get(i).zRot = Mth.lerp(core.swimAmount, sideLeft.get(i).zRot, Mth.DEG_TO_RAD * -(leftRightSwayScale * rotation2 + leftRightSwayOffset) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            backLeft.get(i).xRot = Mth.lerp(core.swimAmount, backLeft.get(i).xRot, Mth.DEG_TO_RAD * (frontBackSwayScale * rotation4 + frontBackSwayOffset));
            backLeft.get(i).yRot = Mth.lerp(core.swimAmount, backLeft.get(i).yRot, 0.0f);
            backLeft.get(i).zRot = Mth.lerp(core.swimAmount, backLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            backRight.get(i).xRot = Mth.lerp(core.swimAmount, backRight.get(i).xRot, Mth.DEG_TO_RAD * (frontBackSwayScale * rotation1 + frontBackSwayOffset));
            backRight.get(i).yRot = Mth.lerp(core.swimAmount, backRight.get(i).yRot, 0.0f);
            backRight.get(i).zRot = Mth.lerp(core.swimAmount, backRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideRight.get(i).xRot = Mth.lerp(core.swimAmount, sideRight.get(i).xRot, 0.0f);
            sideRight.get(i).yRot = Mth.lerp(core.swimAmount, sideRight.get(i).yRot, 0.0f);
            sideRight.get(i).zRot = Mth.lerp(core.swimAmount, sideRight.get(i).zRot, Mth.DEG_TO_RAD * (leftRightSwayScale * rotation3 + leftRightSwayOffset) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            frontRight.get(i).xRot = Mth.lerp(core.swimAmount, frontRight.get(i).xRot, Mth.DEG_TO_RAD * -(frontBackSwayScale * rotation4 + frontBackSwayOffset));
            frontRight.get(i).yRot = Mth.lerp(core.swimAmount, frontRight.get(i).yRot, 0.0f);
            frontRight.get(i).zRot = Mth.lerp(core.swimAmount, frontRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);
        }
    }
}
