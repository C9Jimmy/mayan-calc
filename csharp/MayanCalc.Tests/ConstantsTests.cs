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
}
