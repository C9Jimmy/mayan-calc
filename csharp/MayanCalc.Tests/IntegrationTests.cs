using MayanCalc;

namespace MayanCalc.Tests;

public class IntegrationTests
{
    [Theory]
    [InlineData(2012, 12, 21, 4, "Ajaw", 3, "K'ank'in", 13, 0, 0, 0, 0, "G1")]
    [InlineData(2000,  1,  1, 11, "Ik'", 10, "K'ank'in", 12, 19, 6, 15, 2, "G6")]
    [InlineData(1988, 12,  7, 12, "Ajaw", 3, "Mak", 12, 18, 15, 11, 0, "G5")]
    [InlineData(2026,  5, 15,  9, "Ben", 6, "Sip", 13, 0, 13, 10, 13, "G7")]
    public void Calculate_AnchorDates(
        int year, int month, int day,
        int tzCoefficient, string tzName,
        int haabDay, string haabMonthName,
        int baktun, int katun, int tun, int uinal, int kin,
        string lord)
    {
        var result = Calculator.Calculate(year, month, day);

        Assert.Equal(tzCoefficient, result.Tzolkin.Coefficient);
        Assert.Equal(tzName, result.Tzolkin.Name);
        Assert.Equal(haabDay, result.Haab.Day);
        Assert.Equal(haabMonthName, result.Haab.MonthName);
        Assert.Equal(baktun, result.LongCount.Baktun);
        Assert.Equal(katun, result.LongCount.Katun);
        Assert.Equal(tun, result.LongCount.Tun);
        Assert.Equal(uinal, result.LongCount.Uinal);
        Assert.Equal(kin, result.LongCount.Kin);
        Assert.Equal(lord, result.LordOfNight);
    }

    [Fact]
    public void LongCountToString_2012_12_21()
    {
        var result = Calculator.Calculate(2012, 12, 21);
        Assert.Equal("13.0.0.0.0", result.LongCount.ToString());
    }

    [Fact]
    public void LongCountToString_2000_01_01()
    {
        var result = Calculator.Calculate(2000, 1, 1);
        Assert.Equal("12.19.6.15.2", result.LongCount.ToString());
    }
}
