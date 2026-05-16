namespace MayanCalc;

public static class Calculator
{
    // C# % returns negative for negative dividend; FloorMod always returns non-negative.
    private static int FloorMod(int a, int b) => ((a % b) + b) % b;

    public static int DateToJdn(int year, int month, int day)
    {
        int y = month <= 2 ? year - 1 : year;
        int m = month <= 2 ? month + 12 : month;
        int a = y / 100;
        int b = 2 - a + a / 4;
        return (int)(365.25 * (y + 4716)) + (int)(30.6001 * (m + 1)) + day + b - 1524;
    }

    public static TzolkinDate JdnToTzolkin(int jdn)
    {
        int kin = FloorMod(jdn - Constants.CorrelationJdn, 260);
        int coefficient = (kin + 3) % 13 + 1;
        int nameIdx = (kin + 19) % 20;
        return new TzolkinDate(coefficient, Constants.TzolkinNames[nameIdx], nameIdx + 1);
    }

    public static HaabDate JdnToHaab(int jdn)
    {
        int haabKin = FloorMod(jdn - Constants.CorrelationJdn + 348, 365);
        return new HaabDate(haabKin % 20, Constants.HaabMonthNames[haabKin / 20]);
    }

    public static LongCount JdnToLongCount(int jdn)
    {
        int total = jdn - Constants.CorrelationJdn;
        return new LongCount(
            total / 144000,
            total % 144000 / 7200,
            total % 7200 / 360,
            total % 360 / 20,
            total % 20
        );
    }

    public static string JdnToLordOfNight(int jdn) =>
        $"G{FloorMod(jdn - Constants.CorrelationJdn, 9) + 1}";

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
