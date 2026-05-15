package io.github.c9jimmy.mayancalc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CalculatorTest {

    private val FRANKIE_JDN = Calculator.dateToJdn(1988, 12, 7)

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

}
