package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import com.mojang.math.Axis;
import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.animate.upperbody.AbstractUpperBodyAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.SpringType;
import net.ltxprogrammer.changedvanilla.entity.variant.ChangedVanillaSpringTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GhastUpperBodyInitAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractUpperBodyAnimator<T, M> {
    public GhastUpperBodyInitAnimator(ModelPart head, ModelPart torso, ModelPart leftArm, ModelPart rightArm) {
        super(head, torso, leftArm, rightArm);
    }

    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.INIT;
    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean fallFlying = entity.getFallFlyingTicks() > 4;
        this.torso.yRot = 0.0F;
        this.torso.zRot = 0.0F;
        this.torso.x = 0.0F;
        this.rightArm.z = 0.0F;
        this.rightArm.x = -this.core.torsoWidth;
        this.leftArm.z = 0.0F;
        this.leftArm.x = this.core.torsoWidth;

        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.xRot = 0.0f;
        this.leftArm.xRot = 0.0f;
        this.rightArm.zRot += Mth.lerp(this.core.reachOut, 0.0F, 0.1745329F);
        this.leftArm.zRot += Mth.lerp(this.core.reachOut, 0.0F, -0.1745329F);
        this.rightArm.xRot = Mth.lerp(this.core.reachOut, this.rightArm.xRot, (-(float)Math.PI / 6F));
        this.leftArm.xRot = Mth.lerp(this.core.reachOut, this.leftArm.xRot, (-(float)Math.PI / 6F));

        if (!fallFlying) {
            float tilt = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.FORWARDS, this.core.partialTicks);

            float effectiveAngle = Math.max(Mth.lerp(this.core.swimAmount, tilt, 0.0f), 0.0f);
            this.rightArm.xRot += Mth.DEG_TO_RAD * effectiveAngle * -50f;
            this.leftArm.xRot += Mth.DEG_TO_RAD * effectiveAngle * -50f;
        }
    }
}