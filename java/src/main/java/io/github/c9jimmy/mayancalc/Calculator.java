package io.github.c9jimmy.mayancalc;

public final class Calculator {
    private Calculator() {}

    /** Gregorian date → Julian Day Number (Meeus algorithm). */
    static int dateToJdn(int year, int month, int day) {
        int y = (month <= 2) ? year - 1 : year;
        int m = (month <= 2) ? month + 12 : month;
        int a = y / 100;
        int b = 2 - a + a / 4;
        return (int)(Constants.MEEUS_YEAR_FACTOR * (y + Constants.MEEUS_EPOCH_A)) + (int)(Constants.MEEUS_MONTH_FACTOR * (m + 1)) + day + b - Constants.MEEUS_EPOCH_B;
    }

    /**
     * JDN → Tzolk'in date.
     *
     * Math.floorMod handles pre-epoch dates (total < 0) correctly.
     * Tzolk'in origin constants align creation date (0.0.0.0.0) with 4 Ajaw.
     */
    static TzolkinDate jdnToTzolkin(int jdn) {
        int kin = Math.floorMod(jdn - Constants.GMT_CORRELATION, Constants.TZOLKIN_CYCLE);
        int coefficient = ((kin + Constants.TZOLKIN_COEFF_ORIGIN - 1) % Constants.TZOLKIN_COEFF_COUNT) + 1;
        int signIdx = (kin + Constants.TZOLKIN_NAME_ORIGIN_INDEX) % Constants.TZOLKIN_SIGN_COUNT;
        return new TzolkinDate(coefficient, Constants.TZOLKIN_DAY_SIGNS.get(signIdx), signIdx + 1);
    }

    /**
     * JDN → Haab date.
     * +348 aligns creation date with 8 Kumk'u (position 17×20+8 = 348).
     */
    static HaabDate jdnToHaab(int jdn) {
        int haabKin = Math.floorMod(jdn - Constants.GMT_CORRELATION + Constants.HAAB_CORRELATION_OFFSET, Constants.HAAB_CYCLE);
        return new HaabDate(haabKin % Constants.HAAB_DAYS_PER_MONTH, Constants.HAAB_MONTHS.get(haabKin / Constants.HAAB_DAYS_PER_MONTH));
    }

    /** JDN → Long Count (baktun.katun.tun.uinal.kin). */
    static LongCount jdnToLongCount(int jdn) {
        int total = jdn - Constants.GMT_CORRELATION;
        return new LongCount(
            total / Constants.LC_BAKTUN,
            (total % Constants.LC_BAKTUN) / Constants.LC_KATUN,
            (total % Constants.LC_KATUN)  / Constants.LC_TUN,
            (total % Constants.LC_TUN)    / Constants.LC_UINAL,
            total % Constants.LC_UINAL
        );
    }

    /** JDN → Lord of Night G1–G9 (9-day cycle). */
    static String jdnToLordOfNight(int jdn) {
        int total = jdn - Constants.GMT_CORRELATION;
        int lord = Math.floorMod(total + Constants.LORD_OF_NIGHT_ORIGIN - 1, Constants.LORD_OF_NIGHT_CYCLE) + 1;
        return "G" + lord;
    }

    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    private static int daysInMonth(int year, int month) {
        int[] monthLengths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return monthLengths[month - 1];
    }

    private static void validateDate(int year, int month, int day) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month must be in 1..12");
        }
        int maxDay = daysInMonth(year, month);
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("day must be in 1.." + maxDay + " for month " + month);
        }
    }

    /** Gregorian date → complete Classic Maya calendar output. */
    public static MayanDate calculate(int year, int month, int day) {
        validateDate(year, month, day);
        int jdn = dateToJdn(year, month, day);
        return new MayanDate(
            jdnToTzolkin(jdn),
            jdnToHaab(jdn),
            jdnToLongCount(jdn),
            jdnToLordOfNight(jdn)
        );
    }
}
