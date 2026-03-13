package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.animate.upperbody.AbstractHeadAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.SpringType;
import net.ltxprogrammer.changedvanilla.entity.variant.ChangedVanillaSpringTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GhastHeadInitAnimator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends AbstractHeadAnimator<T, M> {
    public GhastHeadInitAnimator(ModelPart head) {
        super(head);
    }

    public HumanoidAnimator.AnimateStage preferredStage() {
        return HumanoidAnimator.AnimateStage.INIT;
    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean fallFlying = entity.getFallFlyingTicks() > 4;
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.zRot = 0.0F;
        this.head.x = 0.0F;
        if (fallFlying) {
            this.head.xRot = (-(float)Math.PI / 4F);
        } else if (this.core.swimAmount > 0.0F) {
            if (entity.isVisuallySwimming()) {
                this.head.xRot = HumanoidAnimator.rotlerpRad(this.core.swimAmount, this.head.xRot, (-(float)Math.PI / 4F));
            } else {
                this.head.xRot = HumanoidAnimator.rotlerpRad(this.core.swimAmount, this.head.xRot, headPitch * ((float)Math.PI / 180F));
            }
        } else {
            float tilt = entity.getSimulatedSpring(ChangedVanillaSpringTypes.HEAVY_DAMPEN, SpringType.Direction.FORWARDS, this.core.partialTicks);

            this.head.xRot = headPitch * ((float)Math.PI / 180F) + (Mth.DEG_TO_RAD * tilt * -50f);
        }
    }
}
