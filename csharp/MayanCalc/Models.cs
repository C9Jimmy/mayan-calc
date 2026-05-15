namespace MayanCalc;

public record TzolkinDate(int Coefficient, string Name);

public record HaabDate(int Day, string MonthName);

public record LongCount(int Baktun, int Katun, int Tun, int Uinal, int Kin)
{
    public override string ToString() =>
        $"{Baktun}.{Katun}.{Tun}.{Uinal}.{Kin}";
}

public record MayanDate(
    TzolkinDate Tzolkin,
    HaabDate Haab,
    LongCount LongCount,
    string LordOfNight  // "G1"–"G9"
);
