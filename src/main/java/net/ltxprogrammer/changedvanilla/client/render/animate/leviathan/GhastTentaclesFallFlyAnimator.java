package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhastTentaclesFallFlyAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractLeviathan6Animator<T, M> {
    public static final float SWAY_RATE = 0.33333334F * 0.125F;
    public static final float SWAY_OFFSET = 1.0F;
    public static float DESYNC_RATE = 0.01F;

    public GhastTentaclesFallFlyAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.FALL_FLY;
    }

    protected float fallFlyFrontTentacle(int index) {
        return switch (index) {
            case 0 -> 20.0f;
            case 1 -> -20.0f;
            case 2 -> -10.0f;
            case 3 -> 0.0f;
            case 4 -> 15.0f;
            default -> 15.0f;
        };
    }

    protected float fallFlyBackTentacle(int index) {
        return switch (index) {
            case 0 -> 10.0f;
            case 1 -> -10.0f;
            case 2 -> 10.0f;
            case 3 -> -10.0f;
            case 4 -> -15.0f;
            default -> -15.0f;
        };
    }

    protected float fallFlySideTentacleX(int index) {
        return switch (index) {
            case 0 -> 0.0f;
            case 1 -> 0.0f;
            case 2 -> 10.0f;
            case 3 -> 10.0f;
            case 4 -> 10.0f;
            default -> 10.0f;
        };
    }

    protected float fallFlySideTentacleZ(int index) {
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
        float fallFlyingTicks = (float)entity.getFallFlyingTicks();
        float fallFlyingAmount = Mth.clamp(fallFlyingTicks * fallFlyingTicks / 100.0F, 0.0F, 1.0F);
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

            float rotationFL = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetFL)));
            float rotationSL = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetSL)));
            float rotationBL = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetBL)));
            float rotationFR = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetFR)));
            float rotationSR = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetSR)));
            float rotationBR = Mth.cos(limbSwing * SWAY_RATE - (((float) Math.PI / 3.0F) * (offset + offsetBR)));
            offset += SWAY_OFFSET;

            float frontSwayScale;
            float backSwayScale;
            float leftRightSwayScale;
            float frontSwayCenter = this.fallFlyFrontTentacle(i);
            float backSwayCenter = this.fallFlyBackTentacle(i);
            float leftRightSwayCenterZ = this.fallFlySideTentacleZ(i);
            float leftRightSwayCenterX = this.fallFlySideTentacleX(i);
            float frontTwist;
            float backTwist;
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

            frontLeft.get(i).xRot = Mth.lerp(fallFlyingAmount, frontLeft.get(i).xRot, Mth.DEG_TO_RAD * -(frontSwayScale * rotationFL + frontSwayCenter));
            frontLeft.get(i).yRot = Mth.lerp(fallFlyingAmount, frontLeft.get(i).yRot, Mth.DEG_TO_RAD * frontTwist);
            frontLeft.get(i).zRot = Mth.lerp(fallFlyingAmount, frontLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideLeft.get(i).xRot = Mth.lerp(fallFlyingAmount, sideLeft.get(i).xRot, Mth.DEG_TO_RAD * leftRightSwayCenterX);
            sideLeft.get(i).yRot = Mth.lerp(fallFlyingAmount, sideLeft.get(i).yRot, 0.0f);
            sideLeft.get(i).zRot = Mth.lerp(fallFlyingAmount, sideLeft.get(i).zRot, Mth.DEG_TO_RAD * (leftRightSwayScale * rotationSL + leftRightSwayCenterZ) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            backLeft.get(i).xRot = Mth.lerp(fallFlyingAmount, backLeft.get(i).xRot, Mth.DEG_TO_RAD * (backSwayScale * rotationBL + backSwayCenter));
            backLeft.get(i).yRot = Mth.lerp(fallFlyingAmount, backLeft.get(i).yRot, Mth.DEG_TO_RAD * backTwist);
            backLeft.get(i).zRot = Mth.lerp(fallFlyingAmount, backLeft.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            backRight.get(i).xRot = Mth.lerp(fallFlyingAmount, backRight.get(i).xRot, Mth.DEG_TO_RAD * (backSwayScale * rotationBR + backSwayCenter));
            backRight.get(i).yRot = Mth.lerp(fallFlyingAmount, backRight.get(i).yRot, Mth.DEG_TO_RAD * -backTwist);
            backRight.get(i).zRot = Mth.lerp(fallFlyingAmount, backRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);

            sideRight.get(i).xRot = Mth.lerp(fallFlyingAmount, sideRight.get(i).xRot, Mth.DEG_TO_RAD * leftRightSwayCenterX);
            sideRight.get(i).yRot = Mth.lerp(fallFlyingAmount, sideRight.get(i).yRot, 0.0f);
            sideRight.get(i).zRot = Mth.lerp(fallFlyingAmount, sideRight.get(i).zRot, Mth.DEG_TO_RAD * -(leftRightSwayScale * rotationSR + leftRightSwayCenterZ) +
                    Mth.DEG_TO_RAD * -rotationalDrag);

            frontRight.get(i).xRot = Mth.lerp(fallFlyingAmount, frontRight.get(i).xRot, Mth.DEG_TO_RAD * -(frontSwayScale * rotationFR + frontSwayCenter));
            frontRight.get(i).yRot = Mth.lerp(fallFlyingAmount, frontRight.get(i).yRot, Mth.DEG_TO_RAD * -frontTwist);
            frontRight.get(i).zRot = Mth.lerp(fallFlyingAmount, frontRight.get(i).zRot, Mth.DEG_TO_RAD * -rotationalDrag);
        }
    }
}
