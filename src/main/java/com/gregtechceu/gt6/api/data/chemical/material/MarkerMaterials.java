package com.gregtechceu.gt6.api.data.chemical.material;

import com.gregtechceu.gt6.Gregtech;

import net.minecraft.world.item.DyeColor;

import com.google.common.collect.HashBiMap;

public class MarkerMaterials {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void register() {
        Color.Colorless.toString();
        Empty.toString();
    }

    /**
     * Marker materials without category
     */
    public static final MarkerMaterial Empty = new MarkerMaterial(Gregtech.id("empty"));

    /**
     * Color materials
     */
    public static class Color {

        /**
         * Can be used only by direct specifying
         * Means absence of color on TagPrefix
         * Often a default value for color prefixes
         */
        public static final MarkerMaterial Colorless = new MarkerMaterial(Gregtech.id("colorless"));

        public static final MarkerMaterial White = new MarkerMaterial(Gregtech.id("white"));
        public static final MarkerMaterial Orange = new MarkerMaterial(Gregtech.id("orange"));
        public static final MarkerMaterial Magenta = new MarkerMaterial(Gregtech.id("magenta"));
        public static final MarkerMaterial LightBlue = new MarkerMaterial(Gregtech.id("light_blue"));
        public static final MarkerMaterial Yellow = new MarkerMaterial(Gregtech.id("yellow"));
        public static final MarkerMaterial Lime = new MarkerMaterial(Gregtech.id("lime"));
        public static final MarkerMaterial Pink = new MarkerMaterial(Gregtech.id("pink"));
        public static final MarkerMaterial Gray = new MarkerMaterial(Gregtech.id("gray"));
        public static final MarkerMaterial LightGray = new MarkerMaterial(Gregtech.id("light_gray"));
        public static final MarkerMaterial Cyan = new MarkerMaterial(Gregtech.id("cyan"));
        public static final MarkerMaterial Purple = new MarkerMaterial(Gregtech.id("purple"));
        public static final MarkerMaterial Blue = new MarkerMaterial(Gregtech.id("blue"));
        public static final MarkerMaterial Brown = new MarkerMaterial(Gregtech.id("brown"));
        public static final MarkerMaterial Green = new MarkerMaterial(Gregtech.id("green"));
        public static final MarkerMaterial Red = new MarkerMaterial(Gregtech.id("red"));
        public static final MarkerMaterial Black = new MarkerMaterial(Gregtech.id("black"));

        /**
         * Arrays containing all possible color values (without Colorless!)
         */
        public static final MarkerMaterial[] VALUES = new MarkerMaterial[] {
                White, Orange, Magenta, LightBlue, Yellow, Lime, Pink, Gray, LightGray, Cyan, Purple, Blue, Brown,
                Green, Red, Black
        };

        /**
         * Gets color by it's name
         * Name format is equal to DyeColor
         */
        public static MarkerMaterial valueOf(String string) {
            for (MarkerMaterial color : VALUES) {
                if (color.getName().equals(string)) {
                    return color;
                }
            }
            return null;
        }

        /**
         * Contains associations between MC DyeColor and Color MarkerMaterial
         */
        public static final HashBiMap<DyeColor, MarkerMaterial> COLORS = HashBiMap.create();

        static {
            for (var color : DyeColor.values()) {
                COLORS.put(color, Color.valueOf(color.getName()));
            }
        }
    }
}
