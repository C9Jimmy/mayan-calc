package io.github.c9jimmy.mayancalc;

public final class Calculator {
    private Calculator() {}

    /** Gregorian date → Julian Day Number (Meeus algorithm). */
    public static int dateToJdn(int year, int month, int day) {
        int y = (month <= 2) ? year - 1 : year;
        int m = (month <= 2) ? month + 12 : month;
        int a = y / 100;
        int b = 2 - a + a / 4;
        return (int)(365.25 * (y + 4716)) + (int)(30.6001 * (m + 1)) + day + b - 1524;
    }

    /**
     * JDN → Tzolk'in date.
     *
     * Math.floorMod handles pre-epoch dates (total < 0) correctly.
     * +3 on number, +19 on sign: aligns creation date (0.0.0.0.0) with 4 Ajaw.
     */
    public static TzolkinDate jdnToTzolkin(int jdn) {
        int kin = Math.floorMod(jdn - Constants.GMT_CORRELATION, 260);
        int coefficient = ((kin + 3) % 13) + 1;
        int signIdx = (kin + 19) % 20;
        return new TzolkinDate(coefficient, Constants.TZOLKIN_DAY_SIGNS[signIdx], signIdx + 1);
    }

    /**
     * JDN → Haab date.
     * +348 aligns creation date with 8 Kumk'u (position 17×20+8 = 348).
     */
    public static HaabDate jdnToHaab(int jdn) {
        int haabKin = Math.floorMod(jdn - Constants.GMT_CORRELATION + 348, 365);
        return new HaabDate(haabKin % 20, Constants.HAAB_MONTHS[haabKin / 20]);
    }

    /** JDN → Long Count (baktun.katun.tun.uinal.kin). */
    public static LongCount jdnToLongCount(int jdn) {
        int total = jdn - Constants.GMT_CORRELATION;
        return new LongCount(
            total / 144000,
            (total % 144000) / 7200,
            (total % 7200)   / 360,
            (total % 360)    / 20,
            total % 20
        );
    }

    /** JDN → Lord of Night G1–G9 (9-day cycle). */
    public static String jdnToLordOfNight(int jdn) {
        return "G" + (Math.floorMod(jdn - Constants.GMT_CORRELATION, 9) + 1);
    }

    /** Gregorian date → complete Maya calendar output. */
    public static MayanDate calculate(int year, int month, int day) {
        int jdn = dateToJdn(year, month, day);
        return new MayanDate(
            jdnToTzolkin(jdn),
            jdnToHaab(jdn),
            jdnToLongCount(jdn),
            jdnToLordOfNight(jdn)
        );
    }
}
