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
    public static final float SWAY_OFFSET = 1.0F;
    public static float DESYNC_RATE = 0.01F;

    public GhastTentaclesSwimAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.SWIM;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float rotationalDrag = entity.getTailDragAmount(this.core.partialTicks) * 30.0f;

        leftRoot.xRot = Mth.lerp(core.swimAmount, leftRoot.xRot, 0.0f);
        leftRoot.zRot = Mth.lerp(core.swimAmount, leftRoot.zRot, Mth.DEG_TO_RAD * -rotationalDrag * 0.5f);
        rightRoot.xRot = Mth.lerp(core.swimAmount, rightRoot.xRot, 0.0f);
        rightRoot.zRot = Mth.lerp(core.swimAmount, rightRoot.zRot, Mth.DEG_TO_RAD * -rotationalDrag * 0.5f);

        float offsetFL = Mth.sin(ageInTicks * DESYNC_RATE + 0.4f) * Mth.DEG_TO_RAD * 15;
        float offsetSL = Mth.sin(ageInTicks * DESYNC_RATE + 1.2f) * Mth.DEG_TO_RAD * 15;
        float offsetBL = Mth.sin(ageInTicks * DESYNC_RATE + 2.0f) * Mth.DEG_TO_RAD * 15;
        float offsetFR = Mth.sin(ageInTicks * DESYNC_RATE + 2.4f) * Mth.DEG_TO_RAD * 15;
        float offsetSR = Mth.sin(ageInTicks * DESYNC_RATE + 0.8f) * Mth.DEG_TO_RAD * 15;
        float offsetBR = Mth.sin(ageInTicks * DESYNC_RATE + 0.0f) * Mth.DEG_TO_RAD * 15;

        float offset = 0.0F;
        for (int i = 0; i < frontLeft.size(); ++i) {
            if (i == frontLeft.size() - 1)
                offset -= SWAY_OFFSET;

            float rotationFL = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFL)));
            float rotationSL = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSL)));
            float rotationBL = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBL)));
            float rotationFR = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFR)));
            float rotationSR = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSR)));
            float rotationBR = Mth.cos(limbSwing * SWIM_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBR)));
            offset += SWAY_OFFSET;

            float frontBackSwayScale;
            float leftRightSwayScale;
            float frontBackSwayOffset;
            float leftRightSwayOffset;
            float twist;
            if (i == 0) {
                frontBackSwayScale = 10.0f;
                leftRightSwayScale = 10.0f;
                frontBackSwayOffset = 30.0f;
                leftRightSwayOffset = 30.0f;
                twist = 25.0f;
            } else if (i == 1) {
                frontBackSwayScale = 17.5f;
                leftRightSwayScale = 17.5f;
                frontBackSwayOffset = 0.0f;
                leftRightSwayOffset = 0.0f;
                twist = 0f;
            } else {
                frontBackSwayScale = 17.5f;
                leftRightSwayScale = 17.5f;
                frontBackSwayOffset = 0.0f;
                leftRightSwayOffset = 0.0f;
                twist = 0f;
            }

            frontLeft.get(i).xRot = Mth.lerp(core.swimAmount, frontLeft.get(i).xRot, Mth.DEG_TO_RAD * -(frontBackSwayScale * rotationFL + frontBackSwayOffset));
            frontLeft.get(i).yRot = Mth.lerp(core.swimAmount, frontLeft.get(i).yRot, Mth.DEG_TO_RAD * twist);
            frontLeft.get(i).zRot = Mth.lerp(core.swimAmount, frontLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideLeft.get(i).xRot = Mth.lerp(core.swimAmount, sideLeft.get(i).xRot, 0.0f);
            sideLeft.get(i).yRot = Mth.lerp(core.swimAmount, sideLeft.get(i).yRot, 0.0f);
            sideLeft.get(i).zRot = Mth.lerp(core.swimAmount, sideLeft.get(i).zRot, Mth.DEG_TO_RAD * (leftRightSwayScale * rotationSL + leftRightSwayOffset) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            backLeft.get(i).xRot = Mth.lerp(core.swimAmount, backLeft.get(i).xRot, Mth.DEG_TO_RAD * (frontBackSwayScale * rotationBL + frontBackSwayOffset));
            backLeft.get(i).yRot = Mth.lerp(core.swimAmount, backLeft.get(i).yRot, Mth.DEG_TO_RAD * -twist);
            backLeft.get(i).zRot = Mth.lerp(core.swimAmount, backLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            backRight.get(i).xRot = Mth.lerp(core.swimAmount, backRight.get(i).xRot, Mth.DEG_TO_RAD * (frontBackSwayScale * rotationBR + frontBackSwayOffset));
            backRight.get(i).yRot = Mth.lerp(core.swimAmount, backRight.get(i).yRot, Mth.DEG_TO_RAD * twist);
            backRight.get(i).zRot = Mth.lerp(core.swimAmount, backRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideRight.get(i).xRot = Mth.lerp(core.swimAmount, sideRight.get(i).xRot, 0.0f);
            sideRight.get(i).yRot = Mth.lerp(core.swimAmount, sideRight.get(i).yRot, 0.0f);
            sideRight.get(i).zRot = Mth.lerp(core.swimAmount, sideRight.get(i).zRot, Mth.DEG_TO_RAD * -(leftRightSwayScale * rotationSR + leftRightSwayOffset) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            frontRight.get(i).xRot = Mth.lerp(core.swimAmount, frontRight.get(i).xRot, Mth.DEG_TO_RAD * -(frontBackSwayScale * rotationFR + frontBackSwayOffset));
            frontRight.get(i).yRot = Mth.lerp(core.swimAmount, frontRight.get(i).yRot, Mth.DEG_TO_RAD * -twist);
            frontRight.get(i).zRot = Mth.lerp(core.swimAmount, frontRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);
        }
    }
}
