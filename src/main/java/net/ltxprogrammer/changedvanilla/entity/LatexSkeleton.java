package net.ltxprogrammer.changedvanilla.entity;

import net.ltxprogrammer.changed.entity.AttributePresets;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.TransfurCause;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.item.LatexSyringe;
import net.ltxprogrammer.changed.item.Syringe;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changedvanilla.init.ChangedVanillaTransfurVariants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Objects;

public class LatexSkeleton extends AbstractLatexMonster implements RangedAttackMob {
    private @Nullable Goal meleeGoal = null;
    private final RangedBowAttackGoal<LatexSkeleton> bowGoal = new RangedBowAttackGoal<>(this, 0.15D, 20, 15.0F);

    public LatexSkeleton(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
        this.moveControl = new MoveControl(this) {
            @Override
            public void strafe(float strafeForwards, float strafeRight) {
                this.operation = MoveControl.Operation.STRAFE;
                this.strafeForwards = strafeForwards;
                this.strafeRight = strafeRight;
                this.speedModifier = 0.035D;
            }
        };
    }

    @Override
    protected @Nullable Goal makeMeleeTransfurGoal() {
        this.meleeGoal = super.makeMeleeTransfurGoal();
        return this.meleeGoal;
    }

    @Override
    protected void setAttributes(AttributeMap attributes) {
        super.setAttributes(attributes);
        AttributePresets.wolfLike(attributes);
    }

    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevel, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroup, @Nullable CompoundTag tag) {
        spawnGroup = super.finalizeSpawn(serverLevel, difficulty, spawnType, spawnGroup, tag);
        RandomSource randomsource = serverLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, difficulty);
        this.reassessWeaponGoal();
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());

        return spawnGroup;
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.REPLICATION;
    }

    @Override
    public Color3 getTransfurColor(TransfurCause cause) {
        return Color3.fromInt(0x26252a);
    }

    public void reassessWeaponGoal() {
        if (this.getUnderlyingPlayer() != null)
            return;

        if (this.level() != null && !this.level().isClientSide) {
            if (this.meleeGoal != null)
                this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 30;
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = 60;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(1, this.bowGoal);
            } else if (this.meleeGoal != null) {
                this.goalSelector.addGoal(1, this.meleeGoal);
            }

        }
    }

    @Override
    public ItemStack getProjectile(ItemStack weapon) {
        if (weapon.getItem() instanceof BowItem) {
            var arrow = Syringe.setUnpureVariant(new ItemStack(ChangedItems.LATEX_TIPPED_ARROW.get()), ChangedVanillaTransfurVariants.LATEX_SKELETON.getId());
            if (this.getUnderlyingPlayer() != null)
                arrow = Syringe.setOwner(arrow, this.getUnderlyingPlayer());
            return arrow;
        }

        return super.getProjectile(weapon);
    }

    public void performRangedAttack(LivingEntity target, float power) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, power);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractarrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrow);
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(abstractarrow);
    }

    protected AbstractArrow getArrow(ItemStack arrowItem, float power) {
        return ProjectileUtil.getMobArrow(this, arrowItem, power);
    }

    public boolean canFireProjectileWeapon(ProjectileWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.reassessWeaponGoal();
    }

    public void setItemSlot(EquipmentSlot slot, ItemStack item) {
        super.setItemSlot(slot, item);
        if (!this.level().isClientSide) {
            this.reassessWeaponGoal();
        }

    }
}
