package net.ltxprogrammer.changedvanilla.client.render.animate.leviathan;

import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.model.geom.ModelPart;

import java.util.List;

public abstract class AbstractLeviathan6Animator<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>> extends HumanoidAnimator.Animator<T, M> {
    protected final ModelPart leftRoot;
    protected final ModelPart rightRoot;
    protected final List<ModelPart> frontLeft;
    protected final List<ModelPart> sideLeft;
    protected final List<ModelPart> backLeft;
    protected final List<ModelPart> frontRight;
    protected final List<ModelPart> sideRight;
    protected final List<ModelPart> backRight;

    protected AbstractLeviathan6Animator(ModelPart leftRoot, ModelPart rightRoot,
                                         List<ModelPart> frontLeft, List<ModelPart> sideLeft, List<ModelPart> backLeft,
                                         List<ModelPart> frontRight, List<ModelPart> sideRight, List<ModelPart> backRight) {
        this.leftRoot = leftRoot;
        this.rightRoot = rightRoot;
        this.frontLeft = frontLeft;
        this.sideLeft = sideLeft;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.sideRight = sideRight;
        this.backRight = backRight;
    }

    protected void resetTentacle(List<ModelPart> tentacle) {
        for (ModelPart joint : tentacle) {
            joint.xRot = 0.0f;
            joint.yRot = 0.0f;
            joint.zRot = 0.0f;
        }
    }
}