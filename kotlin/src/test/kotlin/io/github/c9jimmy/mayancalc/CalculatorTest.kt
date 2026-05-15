package io.github.c9jimmy.mayancalc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CalculatorTest {

    private val FRANKIE_JDN = Calculator.dateToJdn(1988, 12, 7)

    // ── Constants ─────────────────────────────────────────────────────────────

    @Nested
    inner class ConstantsTests {
        @Test fun tzolkinNamesHas20Elements() = assertEquals(20, TZOLKIN_NAMES.size)
        @Test fun haabMonthNamesHas18Elements() = assertEquals(18, HAAB_MONTH_NAMES.size)
        @Test fun correlationJdn() = assertEquals(584283L, CORRELATION_JDN)
        @Test fun tzolkinCoeffOriginIs4() = assertEquals(4, TZOLKIN_COEFF_ORIGIN)
        @Test fun tzolkinNameOriginIsAjaw() = assertEquals("Ajaw", TZOLKIN_NAMES[TZOLKIN_NAME_ORIGIN_IDX])
        @Test fun tzolkinFirstNameIsImix() = assertEquals("Imix", TZOLKIN_NAMES[0])
        @Test fun tzolkinNameOriginIdxIs19() = assertEquals(19, TZOLKIN_NAME_ORIGIN_IDX)
        @Test fun haabFirstMonthIsPop() = assertEquals("Pop", HAAB_MONTH_NAMES[0])
        @Test fun haabLastMonthIsKumku() = assertEquals("Kumk'u", HAAB_MONTH_NAMES[17])
    }

    // ── dateToJdn ─────────────────────────────────────────────────────────────

    @Nested
    inner class DateToJdnTests {

        @ParameterizedTest
        @CsvSource(
            "2012,12,21,2456283",
            "2000,1,1,2451545",
            "1988,12,7,2447503",
            "2026,5,15,2461176"
        )
        fun anchorDates(y: Int, m: Int, d: Int, expected: Long) =
            assertEquals(expected, Calculator.dateToJdn(y, m, d))

        @Test fun creationDate() =
            assertEquals(CORRELATION_JDN, Calculator.dateToJdn(-3113, 8, 11))

        @Test fun leapYearBranch() =
            assertEquals(2451603L, Calculator.dateToJdn(2000, 2, 28))
    }

    // ── jdnToTzolkin ─────────────────────────────────────────────────────────

    @Nested
    inner class TzolkinTests {

        @Test fun numberAlways1to13() {
            for (i in 0 until 260) {
                val n = Calculator.jdnToTzolkin(FRANKIE_JDN + i).coefficient
                assertTrue(n in 1..13, "Expected 1–13, got $n at offset $i")
            }
        }

        @Test fun nameAlwaysInKnownList() {
            for (i in 0 until 260) {
                val name = Calculator.jdnToTzolkin(FRANKIE_JDN + i).name
                assertTrue(name in TZOLKIN_NAMES, "Unknown name '$name' at offset $i")
            }
        }

        @Test fun cycle260() {
            val base = Calculator.jdnToTzolkin(FRANKIE_JDN)
            assertEquals(base, Calculator.jdnToTzolkin(FRANKIE_JDN + 260))
        }

        @Test fun creationDate_coefficientIs4() =
            assertEquals(4, Calculator.jdnToTzolkin(CORRELATION_JDN).coefficient)

        @Test fun creationDate_nameIsAjaw() =
            assertEquals("Ajaw", Calculator.jdnToTzolkin(CORRELATION_JDN).name)
    }

    // ── jdnToHaab ────────────────────────────────────────────────────────────

    @Nested
    inner class HaabTests {

        @Test fun dayAlways0to19() {
            for (i in 0 until 365) {
                val d = Calculator.jdnToHaab(FRANKIE_JDN + i).day
                assertTrue(d in 0..19, "Expected 0–19, got $d at offset $i")
            }
        }

        @Test fun cycle365() {
            val base = Calculator.jdnToHaab(FRANKIE_JDN)
            assertEquals(base, Calculator.jdnToHaab(FRANKIE_JDN + 365))
        }

        @Test fun creationDateIs8Kumku() {
            val haab = Calculator.jdnToHaab(CORRELATION_JDN)
            assertEquals("Kumk'u", haab.monthName)
            assertEquals(8, haab.day)
        }
    }

    // ── jdnToLongCount ───────────────────────────────────────────────────────

    @Nested
    inner class LongCountTests {

        @Test fun oneUinalIs20Kins() {
            val lc = Calculator.jdnToLongCount(CORRELATION_JDN + 20)
            assertEquals(1, lc.uinal)
            assertEquals(0, lc.kin)
        }

        @Test fun oneTunIs18Uinals() {
            val lc = Calculator.jdnToLongCount(CORRELATION_JDN + 360)
            assertEquals(1, lc.tun)
            assertEquals(0, lc.uinal)
            assertEquals(0, lc.kin)
        }

        @Test fun oneKatunIs20Tuns() {
            val lc = Calculator.jdnToLongCount(CORRELATION_JDN + 7200)
            assertEquals(1, lc.katun)
            assertEquals(0, lc.tun)
        }

        @Test fun toStringFormat() =
            assertEquals("0.0.0.0.0", Calculator.jdnToLongCount(CORRELATION_JDN).toString())

        @Test fun creationDateIsAllZero() {
            val lc = Calculator.jdnToLongCount(CORRELATION_JDN)
            assertEquals(0, lc.baktun)
            assertEquals(0, lc.katun)
            assertEquals(0, lc.tun)
            assertEquals(0, lc.uinal)
            assertEquals(0, lc.kin)
        }
    }

    // ── jdnToLordOfNight ─────────────────────────────────────────────────────

    @Nested
    inner class LordOfNightTests {

        @Test fun cycle9() {
            assertEquals(
                Calculator.jdnToLordOfNight(FRANKIE_JDN),
                Calculator.jdnToLordOfNight(FRANKIE_JDN + 9)
            )
        }

        @Test fun valueAlwaysG1toG9() {
            val valid = (1..9).map { "G$it" }.toSet()
            for (i in 0 until 9) {
                val g = Calculator.jdnToLordOfNight(FRANKIE_JDN + i)
                assertTrue(g in valid, "Expected G1–G9, got $g at offset $i")
            }
        }

        @Test fun creationDateIsG1() =
            assertEquals("G1", Calculator.jdnToLordOfNight(CORRELATION_JDN))
    }

    // ── Integration ──────────────────────────────────────────────────────────

    private data class AnchorCase(
        val year: Int, val month: Int, val day: Int,
        val tzolkinCoeff: Int, val tzolkinName: String,
        val haabDay: Int, val haabMonth: String,
        val lc: String, val lord: String
    )

    private val CASES = listOf(
        AnchorCase(2012, 12, 21,  4, "Ajaw",  3, "K'ank'in", "13.0.0.0.0",    "G1"),
        AnchorCase(2000,  1,  1, 11, "Ik'",  10, "K'ank'in", "12.19.6.15.2",  "G6"),
        AnchorCase(1988, 12,  7, 12, "Ajaw",  3, "Mak",      "12.18.15.11.0", "G5"),
        AnchorCase(2026,  5, 15,  9, "Ben",   6, "Sip",      "13.0.13.10.13", "G7"),
    )

    @TestFactory
    fun integrationAnchorDates() = CASES.map { c ->
        val result = Calculator.calculate(c.year, c.month, c.day)
        dynamicContainer("${c.year}-${c.month}-${c.day}", listOf(
            dynamicTest("tzolkin coefficient") { assertEquals(c.tzolkinCoeff, result.tzolkin.coefficient) },
            dynamicTest("tzolkin name")        { assertEquals(c.tzolkinName,  result.tzolkin.name) },
            dynamicTest("haab day")            { assertEquals(c.haabDay,      result.haab.day) },
            dynamicTest("haab month")          { assertEquals(c.haabMonth,    result.haab.monthName) },
            dynamicTest("long count")          { assertEquals(c.lc,           result.longCount.toString()) },
            dynamicTest("lord of night")       { assertEquals(c.lord,         result.lordOfNight) },
        ))
    }
}
