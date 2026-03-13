package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.animate.upperbody.AbstractUpperBodyAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

public class GhastUpperBodyCrouchAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractUpperBodyAnimator<T, M> {
    public GhastUpperBodyCrouchAnimator(ModelPart head, ModelPart torso, ModelPart leftArm, ModelPart rightArm) {
        super(head, torso, leftArm, rightArm);
    }

    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.CROUCH;
    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.torso.z = 0.0F;
        this.head.z = this.torso.z;
        this.leftArm.z = this.torso.z;
        this.rightArm.z = this.torso.z;
        this.torso.xRot = 0.0F;
        this.torso.y = 3.2f + this.core.calculateTorsoPositionY();
        this.head.y = this.torso.y;
        this.leftArm.y = this.torso.y + 2.0F;
        this.rightArm.y = this.torso.y + 2.0F;
    }
}