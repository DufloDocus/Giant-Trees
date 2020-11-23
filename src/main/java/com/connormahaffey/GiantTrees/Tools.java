package com.connormahaffey.GiantTrees;

public class Tools {

    /**
     * Converts a string to an int
     *
     * @param s the string
     * @return the int
     */
    public static int stringToInt(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * Get a double from a string
     *
     * @param s string
     * @return double
     */
    public static double getDouble(final String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            GiantTrees.logError("Corrupt File Entry: " + s, e);
        }
        return 0;
    }
}
