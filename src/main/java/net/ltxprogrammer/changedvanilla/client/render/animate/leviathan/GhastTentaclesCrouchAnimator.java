package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GhastTentaclesCrouchAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractLeviathan6Animator<T, M> {
    public GhastTentaclesCrouchAnimator(ModelPart leftRoot, ModelPart rightRoot, List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft, List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        super(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight);
    }

    @Override
    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.CROUCH;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        leftRoot.y = 3.2f + core.calculateLegPositionY();
        leftRoot.xRot = 0f;
        leftRoot.zRot = 0f;
        rightRoot.y = leftRoot.y;
        rightRoot.xRot = 0f;
        rightRoot.zRot = 0f;
    }
}
