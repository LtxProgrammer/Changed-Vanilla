package net.ltxprogrammer.changedvanilla.entity;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.TransfurCause;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.entity.variant.EntityShape;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.entity.variant.ChangedVanillaEntityShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class LatexGhast extends ChangedEntity {
    public LatexGhast(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createLatexGhastAttributes() {
        return createLatexAttributes()
                .add(ForgeMod.BLOCK_REACH.get())
                .add(ForgeMod.ENTITY_REACH.get());
    }

    @Override
    protected void setAttributes(AttributeMap attributes) {
        super.setAttributes(attributes);
        attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0);
        attributes.getInstance(ForgeMod.SWIM_SPEED.get()).setBaseValue(1.6);
        attributes.getInstance(ForgeMod.BLOCK_REACH.get()).setBaseValue(7.0);
        attributes.getInstance(ForgeMod.ENTITY_REACH.get()).setBaseValue(4.5);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.ABSORPTION;
    }

    public Color3 getTransfurColor(TransfurCause cause) {
        return Color3.fromInt(0xe3e7ea);
    }

    @Override
    public @NotNull EntityShape getEntityShape() {
        return ChangedVanillaEntityShapes.LEVIATHAN;
    }

    @Override
    public boolean canStartSwimming() {
        return true;
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return true;
    }

    @Override
    public boolean isMovingSlowly() {
        return false;
    }

    public static final UUID FLOAT_UP_UUID = UUID.fromString("406cfd8d-ce15-45cf-b35b-fe283c773ab4");
    public static final UUID RISE_UP_UUID = UUID.fromString("a96bda65-59bc-45eb-8e07-148142d104f4");
    public static final UUID FLOAT_DOWN_UUID = UUID.fromString("7a1e0505-b1b3-4fdd-b776-2ba6c0477765");
    public static final UUID SINK_DOWN_UUID = UUID.fromString("18ac32eb-1cd1-47ca-a92f-789f7b9c1b06");
    private static final AttributeModifier FLOAT_UP_MODIFIER = new AttributeModifier(FLOAT_UP_UUID, "Float up off ground", -0.02, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier RISE_UP_MODIFIER = new AttributeModifier(RISE_UP_UUID, "Rise up off ground", -0.04, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier FLOAT_DOWN_MODIFIER = new AttributeModifier(FLOAT_DOWN_UUID, "Float down to ground", 0.02, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier SINK_DOWN_MODIFIER = new AttributeModifier(SINK_DOWN_UUID, "Sink down to ground", 0.04, AttributeModifier.Operation.ADDITION);

    public enum WantedVerticalMovement {
        SINK_DOWN(SINK_DOWN_MODIFIER),
        FLOAT_DOWN(FLOAT_DOWN_MODIFIER),
        IDLE,
        FLOAT_UP(FLOAT_UP_MODIFIER),
        RISE_UP(RISE_UP_MODIFIER);

        public final AttributeModifier modifier;

        WantedVerticalMovement() { this(null); }
        WantedVerticalMovement(AttributeModifier modifier) {
            this.modifier = modifier;
        }
    }

    public WantedVerticalMovement getWantedVerticalMovement(LivingEntity applyEntity) {
        if (applyEntity.hasPose(Pose.SWIMMING))
            return WantedVerticalMovement.IDLE;
        if (applyEntity instanceof Player player && player.hasContainerOpen())
            return WantedVerticalMovement.IDLE;
        if (applyEntity.isSleeping())
            return WantedVerticalMovement.IDLE;

        if (applyEntity.jumping)
            return WantedVerticalMovement.RISE_UP;
        if (applyEntity.hasPose(Pose.CROUCHING))
            return WantedVerticalMovement.SINK_DOWN;

        if (applyEntity.swinging || applyEntity.isUsingItem())
            return WantedVerticalMovement.IDLE;

        if (applyEntity.hasPose(Pose.STANDING)) {
            var level = level();
            var collisionContext = CollisionContext.of(applyEntity);

            {
                var checkBounding = applyEntity.getBoundingBox().move(0.0, -4.0, 0.0);
                boolean nearGround = BlockPos.betweenClosedStream(checkBounding).anyMatch(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    return !state.getCollisionShape(level, blockPos, collisionContext).isEmpty();
                });

                checkBounding = applyEntity.getBoundingBox().move(0.0, 4.0, 0.0);
                boolean hasHeadRoom = BlockPos.betweenClosedStream(checkBounding).noneMatch(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    return !state.getCollisionShape(level, blockPos, collisionContext).isEmpty();
                });

                if (nearGround && hasHeadRoom)
                    return WantedVerticalMovement.FLOAT_UP;
            }

            {
                var checkBounding = applyEntity.getBoundingBox().expandTowards(0.0, -11.0, 0.0).move(0.0, -1.0, 0.0);
                boolean farFromGround = BlockPos.betweenClosedStream(checkBounding).noneMatch(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    return !state.getCollisionShape(level, blockPos, collisionContext).isEmpty();
                });

                if (farFromGround)
                    return WantedVerticalMovement.FLOAT_DOWN;
            }
        }

        return WantedVerticalMovement.IDLE;
    }

    @Override
    public void variantTick(Level level) {
        super.variantTick(level);

        var applyEntity = maybeGetUnderlying();

        if (applyEntity.isSprinting())
            this.setFluidTypeHeight(ForgeMod.EMPTY_TYPE.get(), 1.0);
        else
            this.setFluidTypeHeight(ForgeMod.EMPTY_TYPE.get(), 0.0);
        applyEntity.resetFallDistance();

        var gravityAttrib = applyEntity.getAttributes().getInstance(ForgeMod.ENTITY_GRAVITY.get());

        if (gravityAttrib != null) {
            var vertMovement = this.getWantedVerticalMovement(applyEntity);

            Arrays.stream(WantedVerticalMovement.values()).filter(check -> check != vertMovement).forEach(type -> {
                if (type.modifier != null && gravityAttrib.hasModifier(type.modifier))
                    gravityAttrib.removeModifier(type.modifier);
            });

            if (vertMovement.modifier != null) {
                if (!gravityAttrib.hasModifier(vertMovement.modifier))
                    gravityAttrib.addTransientModifier(vertMovement.modifier);
            }
        }

        applyEntity.setDeltaMovement(applyEntity.getDeltaMovement().multiply(1.0, 0.92, 1.0));
    }
}
