namespace MayanCalc;

public static class Calculator
{
    // C# % returns negative for negative dividend; FloorMod always returns non-negative.
    private static int FloorMod(int a, int b) => ((a % b) + b) % b;

    internal static int DateToJdn(int year, int month, int day)
    {
        int y = month <= 2 ? year - 1 : year;
        int m = month <= 2 ? month + 12 : month;
        int a = y / 100;
        int b = 2 - a + a / 4;
        return (int)(Constants.MeeusYearFactor * (y + Constants.MeeusEpochA)) + (int)(Constants.MeeusMonthFactor * (m + 1)) + day + b - Constants.MeeusEpochB;
    }

    internal static TzolkinDate JdnToTzolkin(int jdn)
    {
        int kin = FloorMod(jdn - Constants.GmtCorrelation, Constants.TzolkinCycle);
        int coefficient = (kin + Constants.TzolkinCoeffOrigin - 1) % Constants.TzolkinCoeffCount + 1;
        int nameIdx = (kin + Constants.TzolkinNameOriginIndex) % Constants.TzolkinSignCount;
        return new TzolkinDate(coefficient, Constants.TzolkinDaySigns[nameIdx], nameIdx + 1);
    }

    internal static HaabDate JdnToHaab(int jdn)
    {
        int haabKin = FloorMod(jdn - Constants.GmtCorrelation + Constants.HaabCorrelationOffset, Constants.HaabCycle);
        return new HaabDate(haabKin % Constants.HaabDaysPerMonth, Constants.HaabMonths[haabKin / Constants.HaabDaysPerMonth]);
    }

    internal static LongCount JdnToLongCount(int jdn)
    {
        int total = jdn - Constants.GmtCorrelation;
        return new LongCount(
            total / Constants.LcBaktun,
            total % Constants.LcBaktun / Constants.LcKatun,
            total % Constants.LcKatun / Constants.LcTun,
            total % Constants.LcTun / Constants.LcUinal,
            total % Constants.LcUinal
        );
    }

    internal static string JdnToLordOfNight(int jdn) =>
        $"G{FloorMod(jdn - Constants.GmtCorrelation + Constants.LordOfNightOrigin - 1, Constants.LordOfNightCycle) + 1}";

    private static bool IsLeapYear(int year) =>
        year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);

    private static int DaysInMonth(int year, int month)
    {
        int[] monthLengths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        return month == 2 && IsLeapYear(year) ? 29 : monthLengths[month - 1];
    }

    private static void ValidateDate(int year, int month, int day)
    {
        if (month is < 1 or > 12)
            throw new System.ArgumentOutOfRangeException(nameof(month), "month must be in 1..12");

        int maxDay = DaysInMonth(year, month);
        if (day < 1 || day > maxDay)
            throw new System.ArgumentOutOfRangeException(nameof(day), $"day must be in 1..{maxDay} for month {month}");
    }

    public static MayanDate Calculate(int year, int month, int day)
    {
        ValidateDate(year, month, day);
        int jdn = DateToJdn(year, month, day);
        return new MayanDate(
            JdnToTzolkin(jdn),
            JdnToHaab(jdn),
            JdnToLongCount(jdn),
            JdnToLordOfNight(jdn)
        );
    }
}
