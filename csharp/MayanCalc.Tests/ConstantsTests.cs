using MayanCalc;
using System.Collections.Generic;

namespace MayanCalc.Tests;

public class ConstantsTests
{
    [Fact]
    public void TzolkinDaySigns_Has20Elements()
    {
        Assert.Equal(20, Constants.TzolkinDaySigns.Count);
    }

    [Fact]
    public void TzolkinDaySigns_FirstIsImix()
    {
        Assert.Equal("Imix", Constants.TzolkinDaySigns[0]);
    }

    [Fact]
    public void TzolkinDaySigns_LastIsAjaw()
    {
        Assert.Equal("Ajaw", Constants.TzolkinDaySigns[19]);
    }

    [Fact]
    public void HaabMonths_Has19Elements()
    {
        Assert.Equal(19, Constants.HaabMonths.Count);
    }

    [Fact]
    public void GmtCorrelation_Is584283()
    {
        Assert.Equal(584283, Constants.GmtCorrelation);
    }

    [Fact]
    public void TzolkinCoeffOrigin_Is4()
    {
        Assert.Equal(4, Constants.TzolkinCoeffOrigin);
    }

    [Fact]
    public void TzolkinNameOriginIndex_Is19()
    {
        Assert.Equal(19, Constants.TzolkinNameOriginIndex);
    }

    [Fact]
    public void LordOfNightOrigin_Is9()
    {
        Assert.Equal(9, Constants.LordOfNightOrigin);
    }

    [Fact]
    public void TzolkinDaySigns_FullOrder()
    {
        IReadOnlyList<string> expected = new[]
        {
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        };
        Assert.Equal(expected, Constants.TzolkinDaySigns);
    }

    [Fact]
    public void HaabMonths_FullOrder()
    {
        IReadOnlyList<string> expected = new[]
        {
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        };
        Assert.Equal(expected, Constants.HaabMonths);
    }

    [Fact]
    public void NameLists_AreReadOnlyLists()
    {
        Assert.IsAssignableFrom<IReadOnlyList<string>>(Constants.TzolkinDaySigns);
        Assert.IsAssignableFrom<IReadOnlyList<string>>(Constants.HaabMonths);
    }
}
