namespace MayanCalc;

public record TzolkinDate(int Number, string DaySign);

public record HaabDate(int Day, string Month);

public record LongCount(int Baktun, int Katun, int Tun, int Uinal, int Kin)
{
    public override string ToString() =>
        $"{Baktun}.{Katun}.{Tun}.{Uinal}.{Kin}";
}

public record MayanDate(
    TzolkinDate Tzolkin,
    HaabDate Haab,
    LongCount LongCount,
    int LordOfNight
);
