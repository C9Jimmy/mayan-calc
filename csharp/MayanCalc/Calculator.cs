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
        int kin = FloorMod(jdn - Constants.CorrelationJdn, Constants.TzolkinCycle);
        int coefficient = (kin + 3) % Constants.TzolkinCoeffCount + 1;
        int nameIdx = (kin + 19) % Constants.TzolkinSignCount;
        return new TzolkinDate(coefficient, Constants.TzolkinNames[nameIdx], nameIdx + 1);
    }

    internal static HaabDate JdnToHaab(int jdn)
    {
        int haabKin = FloorMod(jdn - Constants.CorrelationJdn + Constants.HaabCorrelationOffset, Constants.HaabCycle);
        return new HaabDate(haabKin % Constants.HaabDaysPerMonth, Constants.HaabMonthNames[haabKin / Constants.HaabDaysPerMonth]);
    }

    internal static LongCount JdnToLongCount(int jdn)
    {
        int total = jdn - Constants.CorrelationJdn;
        return new LongCount(
            total / Constants.LcBaktun,
            total % Constants.LcBaktun / Constants.LcKatun,
            total % Constants.LcKatun / Constants.LcTun,
            total % Constants.LcTun / Constants.LcUinal,
            total % Constants.LcUinal
        );
    }

    internal static string JdnToLordOfNight(int jdn) =>
        $"G{FloorMod(jdn - Constants.CorrelationJdn, Constants.LordOfNightCycle) + 1}";

    public static MayanDate Calculate(int year, int month, int day)
    {
        int jdn = DateToJdn(year, month, day);
        return new MayanDate(
            JdnToTzolkin(jdn),
            JdnToHaab(jdn),
            JdnToLongCount(jdn),
            JdnToLordOfNight(jdn)
        );
    }
}
