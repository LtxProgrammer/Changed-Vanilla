package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.SpringType;
import net.ltxprogrammer.changedvanilla.entity.variant.ChangedVanillaSpringTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhastTentaclesInitAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractLeviathan6Animator<T, M> {
    public static final float SWAY_RATE = 0.33333334F * 0.125F;
    public static final float SWAY_OFFSET = 1.0F;
    public static float DESYNC_RATE = 0.01F;

    public GhastTentaclesInitAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.INIT;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leftRoot.y = core.calculateLegPositionY();
        leftRoot.xRot = 0f;
        leftRoot.zRot = 0f;
        rightRoot.y = leftRoot.y;
        rightRoot.xRot = 0f;
        rightRoot.zRot = 0f;

        resetTentacle(frontLeft); // x:[20 -> 80] y:[0 -> 50] z:[0 -> 30]
        resetTentacle(frontRight);
        resetTentacle(sideLeft); // x:[20 -> -20] z:[20 -> 80] y:[-45 -> 45]
        resetTentacle(sideRight);
        resetTentacle(backLeft);
        resetTentacle(backRight);

        frontLeft.get(0).yRot = Mth.DEG_TO_RAD * 25f;
        frontRight.get(0).yRot = Mth.DEG_TO_RAD * -25f;
        backLeft.get(0).yRot = Mth.DEG_TO_RAD * -25f;
        backRight.get(0).yRot = Mth.DEG_TO_RAD * 25f;

        float verticalDrag = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.VERTICAL, this.core.partialTicks) * 30.0f;
        float horizontalDrag = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.FORWARDS, this.core.partialTicks) * 30.0f;
        float idleScale = 1.0f - Mth.abs(horizontalDrag / 15.0f);
        float rotationalDrag = entity.getTailDragAmount(this.core.partialTicks) * 50.0f;

        float offsetFL = Mth.sin(ageInTicks * DESYNC_RATE + 0.4f) * Mth.DEG_TO_RAD * 30;
        float offsetSL = Mth.sin(ageInTicks * DESYNC_RATE + 1.2f) * Mth.DEG_TO_RAD * 30;
        float offsetBL = Mth.sin(ageInTicks * DESYNC_RATE + 2.0f) * Mth.DEG_TO_RAD * 30;
        float offsetFR = Mth.sin(ageInTicks * DESYNC_RATE + 2.4f) * Mth.DEG_TO_RAD * 30;
        float offsetSR = Mth.sin(ageInTicks * DESYNC_RATE + 0.8f) * Mth.DEG_TO_RAD * 30;
        float offsetBR = Mth.sin(ageInTicks * DESYNC_RATE + 0.0f) * Mth.DEG_TO_RAD * 30;

        float offset = 0.0F;
        for (int i = 0; i < frontLeft.size(); ++i) {
            if (i == frontLeft.size() - 1)
                offset -= SWAY_OFFSET;

            float rotationFL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFL)));
            float rotationSL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSL)));
            float rotationBL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBL)));
            float rotationFR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFR)));
            float rotationSR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSR)));
            float rotationBR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBR)));
            offset += SWAY_OFFSET;

            float frontBackSwayScale;
            float leftRightSwayScale;
            float frontBackSwayCenter;
            float leftRightSwayCenter;
            float rotationalDragEffective;
            float horizontalDragEffective;
            if (i == 0) {
                frontBackSwayScale = 30.0f;
                leftRightSwayScale = 30.0f;
                frontBackSwayCenter = 50.0f;
                leftRightSwayCenter = 50.0f;
                rotationalDragEffective = rotationalDrag * 0.25f;
                horizontalDragEffective = horizontalDrag * 0.25f;
            } else {
                frontBackSwayScale = 40.0f * idleScale;
                leftRightSwayScale = 40.0f * idleScale;
                frontBackSwayCenter = (0.0f * idleScale) - verticalDrag;
                leftRightSwayCenter = (0.0f * idleScale) - verticalDrag;
                rotationalDragEffective = rotationalDrag;
                horizontalDragEffective = horizontalDrag;
            }

            frontLeft.get(i).xRot = Mth.DEG_TO_RAD * (-(frontBackSwayScale * rotationFL + frontBackSwayCenter) + horizontalDragEffective);
            frontLeft.get(i).zRot = Mth.DEG_TO_RAD * rotationalDragEffective;
            sideLeft.get(i).xRot = Mth.DEG_TO_RAD * (-rotationalDragEffective + horizontalDragEffective * 1.25f);
            sideLeft.get(i).zRot = Mth.DEG_TO_RAD * (leftRightSwayScale * rotationSL + leftRightSwayCenter);
            backLeft.get(i).xRot = Mth.DEG_TO_RAD * ((frontBackSwayScale * rotationBL + frontBackSwayCenter) + horizontalDragEffective);
            backLeft.get(i).zRot = Mth.DEG_TO_RAD * -rotationalDragEffective;
            backRight.get(i).xRot = Mth.DEG_TO_RAD * ((frontBackSwayScale * rotationBR + frontBackSwayCenter) + horizontalDragEffective);
            backRight.get(i).zRot = Mth.DEG_TO_RAD * -rotationalDragEffective;
            sideRight.get(i).xRot = Mth.DEG_TO_RAD * (rotationalDragEffective + horizontalDragEffective * 1.25f);
            sideRight.get(i).zRot = Mth.DEG_TO_RAD * -(leftRightSwayScale * rotationSR + leftRightSwayCenter);
            frontRight.get(i).xRot = Mth.DEG_TO_RAD * (-(frontBackSwayScale * rotationFR + frontBackSwayCenter) + horizontalDragEffective);
            frontRight.get(i).zRot = Mth.DEG_TO_RAD * rotationalDragEffective;
        }
    }
}
