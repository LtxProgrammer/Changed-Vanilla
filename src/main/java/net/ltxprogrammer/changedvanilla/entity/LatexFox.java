package net.ltxprogrammer.changedvanilla.entity;

import com.google.common.collect.ImmutableMap;
import net.ltxprogrammer.changed.ability.IAbstractChangedEntity;
import net.ltxprogrammer.changed.entity.*;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Map;

public class LatexFox extends ChangedEntity implements VariantHolder<Fox.Type>, EntityColorProvider, ModifiableEntity {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(LatexFox.class, EntityDataSerializers.INT);
    private static final Map<Fox.Type, Color3> COAT_COLORS = Util.make(ImmutableMap.builder(), LatexFox::addCoatColors).build();

    private static void addCoatColors(ImmutableMap.Builder<Fox.Type, Color3> builder) {
        builder.put(Fox.Type.RED, Color3.fromInt(0xe38f1b))
                .put(Fox.Type.SNOW, Color3.fromInt(0xadccce));
    }

    private final Map<String, ModificationVector> modificationVectors;

    protected void buildModificationVectors(ImmutableMap.Builder<String, ModificationVector> builder) {
        builder.put("coatColor", ModificationVector.simpleEnum(Fox.Type.class, this::getVariant, this::setVariant, "changedvanilla.stasis.modify.coatcolor"));
    }

    @Override
    public Map<String, ModificationVector> getModificationVectors() {
        return modificationVectors;
    }

    public LatexFox(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);

        var builder = ImmutableMap.<String, ModificationVector>builder();
        this.buildModificationVectors(builder);
        this.modificationVectors = builder.build();
    }

    @Override
    public void onReplicateOther(IAbstractChangedEntity other) {
        super.onReplicateOther(other);
        if (other.getChangedEntity() instanceof LatexFox otherCat)
            otherCat.setVariant(this.getVariant());
    }

    public Fox.Type getVariant() {
        return Fox.Type.byId(this.entityData.get(DATA_TYPE_ID));
    }

    public void setVariant(Fox.Type type) {
        this.entityData.set(DATA_TYPE_ID, type.getId());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE_ID, 0);
    }

    @Override
    protected void setAttributes(AttributeMap attributes) {
        super.setAttributes(attributes);
        AttributePresets.wolfLike(attributes);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.REPLICATION;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", this.getVariant().getSerializedName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Type"))
            this.setVariant(Fox.Type.byName(tag.getString("Type")));
    }

    @Override
    public CompoundTag savePlayerVariantData() {
        final var tag = super.savePlayerVariantData();
        tag.putString("Type", this.getVariant().getSerializedName());
        return tag;
    }

    @Override
    public void readPlayerVariantData(CompoundTag tag) {
        super.readPlayerVariantData(tag);
        if (tag.contains("Type"))
            this.setVariant(Fox.Type.byName(tag.getString("Type")));
    }

    @Override
    public Color3 getTransfurColor(TransfurCause cause) {
        return this.getBackColor();
    }

    @Override
    public Color3 getFrontColor() {
        return Color3.fromInt(0xf2f2ea);
    }

    @Override
    public Color3 getBackColor() {
        return COAT_COLORS.getOrDefault(this.getVariant(), Color3.fromInt(0xe38f1b));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroup, @Nullable CompoundTag tag) {
        Holder<Biome> holder = level.getBiome(this.blockPosition());
        Fox.Type type = Fox.Type.byBiome(holder);
        if (spawnGroup instanceof Fox.FoxGroupData fox$foxgroupdata) {
            type = fox$foxgroupdata.type;
        } else {
            spawnGroup = new Fox.FoxGroupData(type);
        }

        this.setVariant(type);

        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroup, tag);
    }
}
