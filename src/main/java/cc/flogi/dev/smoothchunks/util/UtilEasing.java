package cc.flogi.dev.smoothchunks.util;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/24/2020
 */
public final class UtilEasing {
    private static final double PI = Math.PI;

    public static double easeInOutSine(double completion) {
        return -((Math.cos(PI * completion) - 1) / 2);
    }

    public static double easeOutSine(double completion) {
        return Math.sin(PI * completion / 2);
    }
}
