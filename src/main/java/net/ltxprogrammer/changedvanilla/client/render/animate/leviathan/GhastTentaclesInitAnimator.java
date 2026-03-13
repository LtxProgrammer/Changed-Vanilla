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

    protected float traverseFrontTentacle(int index) {
        return switch (index) {
            case 0 -> 20.0f;
            case 1 -> -30.0f;
            case 2 -> -40.0f;
            case 3 -> 20.0f;
            case 4 -> 25.0f;
            default -> 25.0f;
        };
    }

    protected float traverseBackTentacle(int index) {
        return switch (index) {
            case 0 -> 30.0f;
            case 1 -> 10.0f;
            case 2 -> 10.0f;
            case 3 -> 20.0f;
            case 4 -> 20.0f;
            default -> 20.0f;
        };
    }

    protected float traverseSideTentacleX(int index) {
        return switch (index) {
            case 0 -> 10.0f;
            case 1 -> 20.0f;
            case 2 -> 10.0f;
            case 3 -> -10.0f;
            case 4 -> -10.0f;
            default -> -10.0f;
        };
    }

    protected float traverseSideTentacleZ(int index) {
        return switch (index) {
            case 0 -> 30.0f;
            case 1 -> -10.0f;
            case 2 -> -10.0f;
            case 3 -> 10.0f;
            case 4 -> 20.0f;
            default -> 20.0f;
        };
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetTentacle(frontLeft); // x:[20 -> 80] y:[0 -> 50] z:[0 -> 30]
        resetTentacle(frontRight);
        resetTentacle(sideLeft); // x:[20 -> -20] z:[20 -> 80] y:[-45 -> 45]
        resetTentacle(sideRight);
        resetTentacle(backLeft);
        resetTentacle(backRight);

        float verticalDrag = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.VERTICAL, this.core.partialTicks) * 30.0f;
        float horizontalDrag = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.FORWARDS, this.core.partialTicks) * 30.0f;
        boolean invertFrontBack = horizontalDrag < 0.0f;
        float vertScale = 1.0f - Mth.abs(verticalDrag / 15.0f);
        float horiScale = 1.0f - Mth.abs(horizontalDrag / 15.0f);
        float rotationalDrag = entity.getTailDragAmount(this.core.partialTicks) * 50.0f;

        if (verticalDrag < 0.0f)
            verticalDrag *= Mth.lerp(horiScale, 0.75f, 1.5f);
        else
            verticalDrag *= 0.75f;

        float offsetFL = Mth.sin(ageInTicks * DESYNC_RATE + 0.4f) * Mth.DEG_TO_RAD * 30;
        float offsetSL = Mth.sin(ageInTicks * DESYNC_RATE + 1.2f) * Mth.DEG_TO_RAD * 30 + ((float)Math.PI * 0.5f);
        float offsetBL = Mth.sin(ageInTicks * DESYNC_RATE + 2.0f) * Mth.DEG_TO_RAD * 30;
        float offsetFR = Mth.sin(ageInTicks * DESYNC_RATE + 2.4f) * Mth.DEG_TO_RAD * 30 + ((float)Math.PI * 0.5f);
        float offsetSR = Mth.sin(ageInTicks * DESYNC_RATE + 0.8f) * Mth.DEG_TO_RAD * 30;
        float offsetBR = Mth.sin(ageInTicks * DESYNC_RATE + 0.0f) * Mth.DEG_TO_RAD * 30 + ((float)Math.PI * 0.5f);

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
            float frontRotationalDrag;
            float backSwayScale;
            float backTwist;
            float backRotationalDrag;
            float leftRightSwayScale;
            float frontSwayCenter;
            float backSwayCenter;
            float leftRightSwayCenterZ;
            float leftRightSwayCenterX;
            float leftRightRotationalDragZ;
            float leftRightRotationalDragX;

            if (i == 0) {
                frontSwayScale = Mth.lerp(horiScale * vertScale, 5.0f, 30.0f);
                frontTwist = 25.0f;
                backSwayScale = Mth.lerp(horiScale * vertScale, 5.0f, 30.0f);
                backTwist = -25.0f;
                leftRightSwayScale = Mth.lerp(horiScale * vertScale, 5.0f, 30.0f);
                frontSwayCenter = Mth.lerp(horiScale, invertFrontBack ? this.traverseBackTentacle(0) : this.traverseFrontTentacle(0), 50.0f + (core.crouching ? 10.0f : 0.0f))
                        + Mth.lerp(horiScale, verticalDrag * (invertFrontBack ? -1 : 1), -verticalDrag);
                backSwayCenter = Mth.lerp(horiScale, invertFrontBack ? this.traverseFrontTentacle(0) : this.traverseBackTentacle(0), 50.0f + (core.crouching ? 10.0f : 0.0f))
                        + Mth.lerp(horiScale, verticalDrag * (invertFrontBack ? 1 : -1), -verticalDrag);
                leftRightSwayCenterZ = Mth.lerp(horiScale, this.traverseSideTentacleZ(0), 50.0f - verticalDrag + (core.crouching ? 10.0f : 0.0f));
                leftRightSwayCenterX = Mth.lerp(horiScale, (this.traverseSideTentacleX(0) - verticalDrag) * (invertFrontBack ? -1 : 1), 0.0f);

                frontRotationalDrag = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? 1 : -1), rotationalDrag) * 0.25f;
                backRotationalDrag = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? -1 : 1), rotationalDrag) * 0.25f;
                leftRightRotationalDragZ = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? 1 : -1), 0.0f) * 0.25f;
                leftRightRotationalDragX = Mth.lerp(horiScale, 0.0f, rotationalDrag) * 0.25f;
            } else {
                frontSwayScale = Mth.lerp(horiScale * vertScale, 10.0f, 40.0f);
                frontTwist = Mth.lerp(horiScale, i == 1 ? -25.0f : 0.0f, 0.0f);
                backSwayScale = Mth.lerp(horiScale * vertScale, 10.0f, 40.0f);
                backTwist = Mth.lerp(horiScale, i == 1 ? 25.0f : 0.0f, 0.0f);
                leftRightSwayScale = Mth.lerp(horiScale * vertScale, 5.0f, 40.0f);
                frontSwayCenter = Mth.lerp(horiScale, invertFrontBack ? this.traverseBackTentacle(i) : this.traverseFrontTentacle(i), 0.0f)
                        + Mth.lerp(horiScale, verticalDrag * (invertFrontBack ? -1 : 1), -verticalDrag);
                backSwayCenter = Mth.lerp(horiScale, invertFrontBack ? this.traverseFrontTentacle(i) : this.traverseBackTentacle(i), 0.0f)
                        + Mth.lerp(horiScale, verticalDrag * (invertFrontBack ? 1 : -1), -verticalDrag);
                leftRightSwayCenterZ = Mth.lerp(horiScale, this.traverseSideTentacleZ(i), -verticalDrag);
                leftRightSwayCenterX = Mth.lerp(horiScale, (this.traverseSideTentacleX(i) - verticalDrag) * (invertFrontBack ? -1 : 1), 0.0f);

                frontRotationalDrag = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? 1 : -1), rotationalDrag);
                backRotationalDrag = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? -1 : 1), rotationalDrag);
                leftRightRotationalDragZ = Mth.lerp(horiScale, rotationalDrag * (invertFrontBack ? 1 : -1), 0.0f);
                leftRightRotationalDragX = Mth.lerp(horiScale, 0.0f, rotationalDrag);
            }

            frontLeft.get(i).xRot = Mth.DEG_TO_RAD * (-(frontSwayScale * rotationFL + frontSwayCenter));
            frontLeft.get(i).yRot = Mth.DEG_TO_RAD * frontTwist;
            frontLeft.get(i).zRot = Mth.DEG_TO_RAD * frontRotationalDrag;
            sideLeft.get(i).xRot = Mth.DEG_TO_RAD * ((leftRightRotationalDragX) + leftRightSwayCenterX);
            sideLeft.get(i).zRot = Mth.DEG_TO_RAD * (leftRightSwayScale * rotationSL + leftRightSwayCenterZ + leftRightRotationalDragZ);
            backLeft.get(i).xRot = Mth.DEG_TO_RAD * ((backSwayScale * rotationBL + backSwayCenter));
            backLeft.get(i).yRot = Mth.DEG_TO_RAD * backTwist;
            backLeft.get(i).zRot = Mth.DEG_TO_RAD * -backRotationalDrag;
            backRight.get(i).xRot = Mth.DEG_TO_RAD * ((backSwayScale * rotationBR + backSwayCenter));
            backRight.get(i).yRot = Mth.DEG_TO_RAD * -backTwist;
            backRight.get(i).zRot = Mth.DEG_TO_RAD * -backRotationalDrag;
            sideRight.get(i).xRot = Mth.DEG_TO_RAD * ((-leftRightRotationalDragX) + leftRightSwayCenterX);
            sideRight.get(i).zRot = Mth.DEG_TO_RAD * -(leftRightSwayScale * rotationSR + leftRightSwayCenterZ - leftRightRotationalDragZ);
            frontRight.get(i).xRot = Mth.DEG_TO_RAD * (-(frontSwayScale * rotationFR + frontSwayCenter));
            frontRight.get(i).yRot = Mth.DEG_TO_RAD * -frontTwist;
            frontRight.get(i).zRot = Mth.DEG_TO_RAD * frontRotationalDrag;
        }
    }
}
