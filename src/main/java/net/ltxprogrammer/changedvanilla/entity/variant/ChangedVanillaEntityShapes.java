package net.ltxprogrammer.changedvanilla.entity.variant;

import net.ltxprogrammer.changed.entity.variant.ClothingShape;
import net.ltxprogrammer.changed.entity.variant.EntityShape;

public class ChangedVanillaEntityShapes {
    public static final EntityShape LEVIATHAN = EntityShape.create("LEVIATHAN", "leviathan",
            ClothingShape.Head.ANTHRO,
            ClothingShape.Torso.ANTHRO,
            ChangedVanillaClothingShapes.Legs.TENTACLES,
            ChangedVanillaClothingShapes.Feet.TENTACLES);
}
