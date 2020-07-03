package com.haris.meal4u.FontUtil;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by hp on 5/20/2018.
 */

public class Font {

    static String ubuntu_regular = "Fonts/Ubuntu-Regular.ttf";
    static String ubuntu_light = "Fonts/Ubuntu-Light.ttf";
    static String ubuntu_medium = "Fonts/Ubuntu-Medium.ttf";
    static String ubuntu_bold = "Fonts/Ubuntu-Bold.ttf";
    static String lemon_milk = "Fonts/LemonMilk.otf";
    static String neuro_political = "Fonts/neuro_political.ttf";
    static String prime_light = "Fonts/Prime Light.otf";
    static String prime_regular = "Fonts/Prime Regular.otf";
    static String aquatico_regular = "Fonts/Aquatico-Regular.otf";
    static String raleway_regular = "Fonts/Raleway-Regular.ttf";
    static String raleway_semi_bold = "Fonts/Raleway-SemiBold.ttf";
    static String raleway_light = "Fonts/Raleway-Light.ttf";


    public static Typeface ubuntu_regular_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_regular);
    }

    public static Typeface ubuntu_medium_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_medium);
    }

    public static Typeface ubuntu_bold_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_bold);
    }

    public static Typeface ubuntu_light_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), ubuntu_light);
    }

    public static Typeface lemon_milk_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), lemon_milk);
    }

    public static Typeface neuro_political_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), neuro_political);
    }

    public static Typeface prime_light_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), prime_light);
    }

    public static Typeface prime_regular_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), prime_regular);
    }

    public static Typeface aquatico_regular_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), aquatico_regular);
    }

    public static Typeface raleway_regular_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), raleway_regular);
    }

    public static Typeface raleway_light_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), raleway_light);
    }

    public static Typeface raleway_semi_bold_font(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(), raleway_semi_bold);
    }

}
