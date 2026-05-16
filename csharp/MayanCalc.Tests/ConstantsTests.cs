using MayanCalc;

namespace MayanCalc.Tests;

public class ConstantsTests
{
    [Fact]
    public void TzolkinNames_Has20Elements()
    {
        Assert.Equal(20, Constants.TzolkinNames.Length);
    }

    [Fact]
    public void TzolkinNames_FirstIsImix()
    {
        Assert.Equal("Imix", Constants.TzolkinNames[0]);
    }

    [Fact]
    public void TzolkinNames_LastIsAjaw()
    {
        Assert.Equal("Ajaw", Constants.TzolkinNames[19]);
    }

    [Fact]
    public void HaabMonthNames_Has19Elements()
    {
        Assert.Equal(19, Constants.HaabMonthNames.Length);
    }

    [Fact]
    public void CorrelationJdn_Is584283()
    {
        Assert.Equal(584283, Constants.CorrelationJdn);
    }

    [Fact]
    public void TzolkinNames_FullOrder()
    {
        string[] expected =
        {
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        };
        Assert.Equal(expected, Constants.TzolkinNames);
    }

    [Fact]
    public void HaabMonthNames_FullOrder()
    {
        string[] expected =
        {
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        };
        Assert.Equal(expected, Constants.HaabMonthNames);
    }
}
