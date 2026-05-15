using MayanCalc;

namespace MayanCalc.Tests;

public class CalculatorTests
{
    private const int FrankieJdn = 2447503; // 1988-12-07

    // ── dateToJdn ────────────────────────────────────────────────────────────

    public class DateToJdnTests
    {
        [Fact]
        public void J2000Epoch()
        {
            Assert.Equal(2451545, Calculator.DateToJdn(2000, 1, 1));
        }

        [Fact]
        public void Maya_2012_12_21()
        {
            Assert.Equal(2456283, Calculator.DateToJdn(2012, 12, 21));
        }

        [Fact]
        public void Frankie_1988_12_07()
        {
            Assert.Equal(2447503, Calculator.DateToJdn(1988, 12, 7));
        }

        [Fact]
        public void Today_2026_05_15()
        {
            Assert.Equal(2461176, Calculator.DateToJdn(2026, 5, 15));
        }

        [Fact]
        public void CreationDate_Minus3113_08_11()
        {
            Assert.Equal(584283, Calculator.DateToJdn(-3113, 8, 11));
        }

        [Fact]
        public void MonthLessThan2Branch_2000_02_28()
        {
            Assert.Equal(2451603, Calculator.DateToJdn(2000, 2, 28));
        }
    }

    // ── jdnToTzolkin ─────────────────────────────────────────────────────────

    public class TzolkinTests
    {
        [Theory]
        [InlineData(2456283, 4, "Ajaw")]   // 2012-12-21
        [InlineData(2451545, 11, "Ik'")]   // 2000-01-01
        [InlineData(2447503, 12, "Ajaw")]  // 1988-12-07
        [InlineData(2461176, 9, "Ben")]    // 2026-05-15
        public void AnchorDates(int jdn, int expectedCoefficient, string expectedName)
        {
            var result = Calculator.JdnToTzolkin(jdn);
            Assert.Equal(expectedCoefficient, result.Coefficient);
            Assert.Equal(expectedName, result.Name);
        }

        [Fact]
        public void CoefficientIsAlways1To13()
        {
            for (int i = 0; i < 260; i++)
            {
                int n = Calculator.JdnToTzolkin(FrankieJdn + i).Coefficient;
                Assert.InRange(n, 1, 13);
            }
        }

        [Fact]
        public void Cycle260DaysIsIdempotent()
        {
            var baseDate = Calculator.JdnToTzolkin(FrankieJdn);
            Assert.Equal(baseDate, Calculator.JdnToTzolkin(FrankieJdn + 260));
        }
    }

    // ── jdnToHaab ────────────────────────────────────────────────────────────

    public class HaabTests
    {
        [Theory]
        [InlineData(2456283, 3, "K'ank'in")]   // 2012-12-21
        [InlineData(2451545, 10, "K'ank'in")]  // 2000-01-01
        [InlineData(2447503, 3, "Mak")]        // 1988-12-07
        [InlineData(2461176, 6, "Sip")]        // 2026-05-15
        public void AnchorDates(int jdn, int expectedDay, string expectedMonthName)
        {
            var result = Calculator.JdnToHaab(jdn);
            Assert.Equal(expectedDay, result.Day);
            Assert.Equal(expectedMonthName, result.MonthName);
        }

        [Fact]
        public void DayIsAlways0To19()
        {
            for (int i = 0; i < 365; i++)
            {
                int d = Calculator.JdnToHaab(FrankieJdn + i).Day;
                Assert.InRange(d, 0, 19);
            }
        }

        [Fact]
        public void Cycle365DaysIsIdempotent()
        {
            var baseDate = Calculator.JdnToHaab(FrankieJdn);
            Assert.Equal(baseDate, Calculator.JdnToHaab(FrankieJdn + 365));
        }
    }

    // ── jdnToLongCount ───────────────────────────────────────────────────────

    public class LongCountTests
    {
        [Theory]
        [InlineData(2456283, 13, 0, 0, 0, 0)]    // 2012-12-21
        [InlineData(2451545, 12, 19, 6, 15, 2)]  // 2000-01-01
        [InlineData(2447503, 12, 18, 15, 11, 0)] // 1988-12-07
        [InlineData(2461176, 13, 0, 13, 10, 13)] // 2026-05-15
        public void AnchorDates(int jdn, int baktun, int katun, int tun, int uinal, int kin)
        {
            var result = Calculator.JdnToLongCount(jdn);
            Assert.Equal(baktun, result.Baktun);
            Assert.Equal(katun, result.Katun);
            Assert.Equal(tun, result.Tun);
            Assert.Equal(uinal, result.Uinal);
            Assert.Equal(kin, result.Kin);
        }

        [Fact]
        public void OneUinalIs20Kins()
        {
            var lc = Calculator.JdnToLongCount(Constants.CorrelationJdn + 20);
            Assert.Equal(1, lc.Uinal);
            Assert.Equal(0, lc.Kin);
        }

        [Fact]
        public void OneTunIs18Uinals()
        {
            var lc = Calculator.JdnToLongCount(Constants.CorrelationJdn + 360);
            Assert.Equal(1, lc.Tun);
            Assert.Equal(0, lc.Uinal);
            Assert.Equal(0, lc.Kin);
        }

        [Fact]
        public void ToString_FormattedCorrectly()
        {
            var lc = Calculator.JdnToLongCount(2456283);
            Assert.Equal("13.0.0.0.0", lc.ToString());
        }
    }

    // ── jdnToLordOfNight ─────────────────────────────────────────────────────

    public class LordOfNightTests
    {
        [Theory]
        [InlineData(2456283, "G1")]  // 2012-12-21 → G1
        [InlineData(2451545, "G6")]  // 2000-01-01 → G6
        [InlineData(2447503, "G5")]  // 1988-12-07 → G5
        [InlineData(2461176, "G7")]  // 2026-05-15 → G7
        public void AnchorDates(int jdn, string expected)
        {
            Assert.Equal(expected, Calculator.JdnToLordOfNight(jdn));
        }

        [Fact]
        public void Cycle9DaysIsIdempotent()
        {
            Assert.Equal(
                Calculator.JdnToLordOfNight(FrankieJdn),
                Calculator.JdnToLordOfNight(FrankieJdn + 9)
            );
        }

        [Fact]
        public void ValueIsAlwaysG1ToG9()
        {
            var valid = new System.Collections.Generic.HashSet<string>(
                System.Linq.Enumerable.Range(1, 9).Select(i => $"G{i}"));
            for (int i = 0; i < 9; i++)
                Assert.Contains(Calculator.JdnToLordOfNight(FrankieJdn + i), valid);
        }

        [Fact]
        public void CreationDateIsG1()
        {
            Assert.Equal("G1", Calculator.JdnToLordOfNight(Constants.CorrelationJdn));
        }
    }
}
