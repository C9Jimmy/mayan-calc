package io.github.c9jimmy.mayancalc;

public final class Constants {
    private Constants() {}

    public static final int GMT_CORRELATION = 584283;

    public static final int TZOLKIN_COEFF_ORIGIN = 4;
    public static final int TZOLKIN_NAME_ORIGIN_IDX = 19;
    public static final int TZOLKIN_CYCLE = 260;
    public static final int TZOLKIN_COEFF_COUNT = 13;
    public static final int TZOLKIN_SIGN_COUNT = 20;

    public static final int HAAB_CYCLE = 365;
    public static final int HAAB_DAYS_PER_MONTH = 20;
    /** Aligns creation date with 8 Kumk'u (17*20+8 = 348). */
    public static final int HAAB_CORRELATION_OFFSET = 348;

    public static final int LC_BAKTUN = 144000;
    public static final int LC_KATUN = 7200;
    public static final int LC_TUN = 360;
    public static final int LC_UINAL = 20;

    public static final int LORD_OF_NIGHT_CYCLE = 9;

    public static final double MEEUS_YEAR_FACTOR = 365.25;
    public static final double MEEUS_MONTH_FACTOR = 30.6001;
    public static final int MEEUS_EPOCH_A = 4716;
    public static final int MEEUS_EPOCH_B = 1524;

    public static final String[] TZOLKIN_DAY_SIGNS = {
        "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
        "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
        "Chuwen", "Eb", "Ben", "Hix", "Men",
        "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
    };

    public static final String[] HAAB_MONTHS = {
        "Pop", "Wo", "Sip", "Sotz'", "Sek",
        "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
        "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
        "Pax", "K'ayab", "Kumk'u", "Wayeb"
    };
}
