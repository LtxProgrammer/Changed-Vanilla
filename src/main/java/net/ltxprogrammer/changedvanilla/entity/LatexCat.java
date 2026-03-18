package net.ltxprogrammer.changedvanilla.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.ltxprogrammer.changed.entity.*;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class LatexCat extends ChangedEntity implements VariantHolder<CatVariant>, EntityColorProvider, ModifiableEntity {
    private static final EntityDataAccessor<CatVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(LatexCat.class, EntityDataSerializers.CAT_VARIANT);
    private static final Map<CatVariant, Pair<Color3, Color3>> COAT_COLORS = Util.make(ImmutableMap.builder(), LatexCat::addCoatColors).build();
    private static final Pair<Color3, Color3> DEFAULT_COAT_COLOR = Pair.of(Color3.fromInt(0x161524), Color3.fromInt(0xeaeaea));

    private static void addCoatColors(ImmutableMap.Builder<CatVariant, Pair<Color3, Color3>> builder) {
        var registry = BuiltInRegistries.CAT_VARIANT;

        builder.put(registry.getOrThrow(CatVariant.TABBY), Pair.of(Color3.fromInt(0x87654a), Color3.fromInt(0xf3e9db)))
                .put(registry.getOrThrow(CatVariant.BLACK), Pair.of(Color3.fromInt(0x161524), Color3.fromInt(0xeaeaea)))
                .put(registry.getOrThrow(CatVariant.RED), Pair.of(Color3.fromInt(0xf4b446), Color3.fromInt(0xeaeaea)))
                .put(registry.getOrThrow(CatVariant.SIAMESE), Pair.of(Color3.fromInt(0xe9dcc7), Color3.fromInt(0x312720)))
                .put(registry.getOrThrow(CatVariant.BRITISH_SHORTHAIR), Pair.of(Color3.fromInt(0x868a8a), Color3.fromInt(0xbcbcbc)))
                .put(registry.getOrThrow(CatVariant.CALICO), Pair.of(Color3.fromInt(0xdb9c3e), Color3.fromInt(0xdcdede)))
                .put(registry.getOrThrow(CatVariant.PERSIAN), Pair.of(Color3.fromInt(0xd0a46e), Color3.fromInt(0xffeacb)))
                .put(registry.getOrThrow(CatVariant.RAGDOLL), Pair.of(Color3.fromInt(0xf7f7f7), Color3.fromInt(0x897469)))
                .put(registry.getOrThrow(CatVariant.WHITE), Pair.of(Color3.fromInt(0xfdf9fb), Color3.fromInt(0xd3d3d3)))
                .put(registry.getOrThrow(CatVariant.JELLIE), Pair.of(Color3.fromInt(0x525251), Color3.fromInt(0xf0f0f0)))
                .put(registry.getOrThrow(CatVariant.ALL_BLACK), Pair.of(Color3.fromInt(0x040407), Color3.fromInt(0x161524)));
    }

    private final Map<String, ModificationVector> modificationVectors;

    protected void buildModificationVectors(ImmutableMap.Builder<String, ModificationVector> builder) {
        builder.put("coatColor", ModificationVector.simpleEnum(BuiltInRegistries.CAT_VARIANT, this::getVariant, this::setVariant, "changedvanilla.stasis.modify.coatcolor"));
    }

    @Override
    public Map<String, ModificationVector> getModificationVectors() {
        return modificationVectors;
    }

    public LatexCat(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);

        var builder = ImmutableMap.<String, ModificationVector>builder();
        this.buildModificationVectors(builder);
        this.modificationVectors = builder.build();
    }

    public @NotNull CatVariant getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(@NotNull CatVariant type) {
        this.entityData.set(DATA_VARIANT_ID, type);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.BLACK));
    }

    @Override
    protected void setAttributes(AttributeMap attributes) {
        super.setAttributes(attributes);
        AttributePresets.catLike(attributes);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.REPLICATION;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", BuiltInRegistries.CAT_VARIANT.getKey(this.getVariant()).toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        CatVariant catvariant = BuiltInRegistries.CAT_VARIANT.get(ResourceLocation.tryParse(tag.getString("Type")));
        if (catvariant != null) {
            this.setVariant(catvariant);
        }
    }

    @Override
    public CompoundTag savePlayerVariantData() {
        final var tag = super.savePlayerVariantData();
        tag.putString("Type", BuiltInRegistries.CAT_VARIANT.getKey(this.getVariant()).toString());
        return tag;
    }

    @Override
    public void readPlayerVariantData(CompoundTag tag) {
        super.readPlayerVariantData(tag);
        CatVariant catvariant = BuiltInRegistries.CAT_VARIANT.get(ResourceLocation.tryParse(tag.getString("Type")));
        if (catvariant != null) {
            this.setVariant(catvariant);
        }
    }

    @Override
    public Color3 getTransfurColor(TransfurCause cause) {
        return this.getBackColor();
    }

    @Override
    public Color3 getFrontColor() {
        return COAT_COLORS.getOrDefault(this.getVariant(), DEFAULT_COAT_COLOR).getSecond();
    }

    @Override
    public Color3 getBackColor() {
        return COAT_COLORS.getOrDefault(this.getVariant(), DEFAULT_COAT_COLOR).getFirst();
    }
}
