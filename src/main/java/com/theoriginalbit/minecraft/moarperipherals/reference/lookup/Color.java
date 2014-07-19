package com.theoriginalbit.minecraft.moarperipherals.reference.lookup;

public enum Color {
    BLACK(0x191919, new int[]{0, 0, 0, 90}),
    RED(0xCC4C4C, new int[]{0, 63, 63, 20}),
    GREEN(0x57A64E, new int[]{48, 0, 53, 35}),
    BROWN(0x7F664C, new int[]{0, 20, 40, 50}),
    BLUE(0x3366CC, new int[]{75, 50, 0, 20}),
    PURPLE(0xB266E5, new int[]{22, 55, 0, 10}),
    CYAN(0x4C99B2, new int[]{57, 14, 0, 30}),
    LIGHTGRAY(0x999999, new int[]{0, 0, 0, 40}),
    GRAY(0x4C4C4C, new int[]{0, 0, 0, 70}),
    PINK(0xF2B2CC, new int[]{0, 26, 16, 5}),
    LIME(0x7FCC19, new int[]{38, 0, 88, 20}),
    YELLOW(0xDEDE6C, new int[]{0, 0, 51, 13}),
    LIGHTBLUE(0x99B2F2, new int[]{37, 26, 0, 5}),
    MAGENTA(0xE57FD8, new int[]{0, 45, 6, 10}),
    ORANGE(0xF2B233, new int[]{0, 26, 79, 5}),
    WHITE(0xF0F0F0, new int[]{0, 0, 0, 6});

    private final int hex;
    private final int[] cmyk;

    private Color(int colorHex, int[] colorCMYK) {
        hex = colorHex;
        cmyk = colorCMYK;
    }

    public int getHex() {
        return hex;
    }

    public int[] getCMYK() {
        return cmyk;
    }

}