namespace MayanCalc;

public static class Constants
{
    public const int CorrelationJdn = 584283;

    public const int TzolkinCycle = 260;
    public const int TzolkinCoeffCount = 13;
    public const int TzolkinSignCount = 20;

    public const int HaabCycle = 365;
    public const int HaabDaysPerMonth = 20;
    /// <summary>Aligns creation date with 8 Kumk'u (17*20+8 = 348).</summary>
    public const int HaabCorrelationOffset = 348;

    public const int LcBaktun = 144000;
    public const int LcKatun = 7200;
    public const int LcTun = 360;
    public const int LcUinal = 20;

    public const int LordOfNightCycle = 9;

    public const double MeeusYearFactor = 365.25;
    public const double MeeusMonthFactor = 30.6001;
    public const int MeeusEpochA = 4716;
    public const int MeeusEpochB = 1524;

    public static readonly string[] TzolkinNames =
    {
        "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
        "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
        "Chuwen", "Eb", "Ben", "Hix", "Men",
        "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
    };

    public static readonly string[] HaabMonthNames =
    {
        "Pop", "Wo", "Sip", "Sotz'", "Sek",
        "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
        "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
        "Pax", "K'ayab", "Kumk'u", "Wayeb"
    };
}
