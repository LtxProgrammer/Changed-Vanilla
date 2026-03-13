package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhastTentaclesSleepAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractLeviathan6Animator<T, M> {
    public static final float SWAY_RATE = 0.33333334F * 0.125F;
    public static final float SWAY_OFFSET = 1.0F;
    public static float DESYNC_RATE = 0.01F;

    public GhastTentaclesSleepAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.SLEEP;
    }

    protected float sleepFrontTentacle(int index) {
        return switch (index) {
            case 0 -> 20.0f;
            case 1 -> -20.0f;
            case 2 -> -10.0f;
            case 3 -> 0.0f;
            case 4 -> 15.0f;
            default -> 15.0f;
        };
    }

    protected float sleepBackTentacle(int index) {
        return switch (index) {
            case 0 -> 10.0f;
            case 1 -> -10.0f;
            case 2 -> 10.0f;
            case 3 -> -10.0f;
            case 4 -> -15.0f;
            default -> -15.0f;
        };
    }

    protected float sleepSideTentacleX(int index) {
        return switch (index) {
            case 0 -> 0.0f;
            case 1 -> 0.0f;
            case 2 -> 10.0f;
            case 3 -> 10.0f;
            case 4 -> 10.0f;
            default -> 10.0f;
        };
    }

    protected float sleepSideTentacleZ(int index) {
        return switch (index) {
            case 0 -> 30.0f;
            case 1 -> -20.0f;
            case 2 -> -15.0f;
            case 3 -> 10.0f;
            case 4 -> 15.0f;
            default -> 15.0f;
        };
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leftRoot.xRot = 0.0f;
        leftRoot.zRot = 0.0f;
        rightRoot.xRot = 0.0f;
        rightRoot.zRot = 0.0f;

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

            float rotationFL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFL)));
            float rotationSL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSL)));
            float rotationBL = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBL)));
            float rotationFR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetFR)));
            float rotationSR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetSR)));
            float rotationBR = Mth.cos(ageInTicks * SWAY_RATE - (((float)Math.PI / 3.0F) * (offset + offsetBR)));
            offset += SWAY_OFFSET;

            float frontSwayScale;
            float frontTwist;
            float backSwayScale;
            float backTwist;
            float leftRightSwayScale;
            float frontSwayCenter = this.sleepFrontTentacle(i);
            float backSwayCenter = this.sleepBackTentacle(i);
            float leftRightSwayCenterZ = this.sleepSideTentacleZ(i);
            float leftRightSwayCenterX = this.sleepSideTentacleX(i);

            if (i == 0) {
                frontSwayScale = 2.0f;
                frontTwist = 25.0f;
                backSwayScale = 2.0f;
                backTwist = -25.0f;
                leftRightSwayScale = 2.0f;
            } else {
                frontSwayScale = 2.0f;
                frontTwist = i == 1 ? -25.0f : 0.0f;
                backSwayScale = 2.0f;
                backTwist = i == 1 ? 25.0f : 0.0f;
                leftRightSwayScale = 2.0f;
            }

            frontLeft.get(i).xRot = Mth.DEG_TO_RAD * (-(frontSwayScale * rotationFL + frontSwayCenter));
            frontLeft.get(i).yRot = Mth.DEG_TO_RAD * frontTwist;
            frontLeft.get(i).zRot = 0.0f;
            sideLeft.get(i).xRot = Mth.DEG_TO_RAD * leftRightSwayCenterX;
            sideLeft.get(i).yRot = 0.0f;
            sideLeft.get(i).zRot = Mth.DEG_TO_RAD * (leftRightSwayScale * rotationSL + leftRightSwayCenterZ);
            backLeft.get(i).xRot = Mth.DEG_TO_RAD * ((backSwayScale * rotationBL + backSwayCenter));
            backLeft.get(i).yRot = Mth.DEG_TO_RAD * backTwist;
            backLeft.get(i).zRot = 0.0f;
            backRight.get(i).xRot = Mth.DEG_TO_RAD * ((backSwayScale * rotationBR + backSwayCenter));
            backRight.get(i).yRot = Mth.DEG_TO_RAD * -backTwist;
            backRight.get(i).zRot = 0.0f;
            sideRight.get(i).xRot = Mth.DEG_TO_RAD * leftRightSwayCenterX;
            sideRight.get(i).yRot = 0.0f;
            sideRight.get(i).zRot = Mth.DEG_TO_RAD * -(leftRightSwayScale * rotationSR + leftRightSwayCenterZ);
            frontRight.get(i).xRot = Mth.DEG_TO_RAD * (-(frontSwayScale * rotationFR + frontSwayCenter));
            frontRight.get(i).yRot = Mth.DEG_TO_RAD * -frontTwist;
            frontRight.get(i).zRot = 0.0f;
        }
    }
}
