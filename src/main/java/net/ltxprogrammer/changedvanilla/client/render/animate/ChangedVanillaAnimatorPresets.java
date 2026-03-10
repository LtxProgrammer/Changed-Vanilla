package net.ltxprogrammer.changedvanilla.client.render.animate;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changedvanilla.client.render.animate.leviathan.*;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;
import java.util.function.Consumer;

public class ChangedVanillaAnimatorPresets {
    public static <T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> Consumer<HumanoidAnimator<T, M>> ghastTentacles(ModelPart leftRoot, ModelPart rightRoot,
                                                                                                                                List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft,
                                                                                                                                List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        return animator -> {
            animator.addAnimator(new GhastTentaclesInitAnimator<>(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight))
                    .addAnimator(new GhastTentaclesSwimAnimator<>(leftRoot, rightRoot, frontLeft, sideLeft, backLeft, frontRight, sideRight, backRight));
        };
    }
}
