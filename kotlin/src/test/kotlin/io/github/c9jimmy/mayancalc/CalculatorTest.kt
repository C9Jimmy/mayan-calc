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
            assertEquals(GMT_CORRELATION, Calculator.dateToJdn(-3113, 8, 11))

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
                assertTrue(name in TZOLKIN_DAY_SIGNS, "Unknown name '$name' at offset $i")
            }
        }

        @Test fun cycle260() {
            val base = Calculator.jdnToTzolkin(FRANKIE_JDN)
            assertEquals(base, Calculator.jdnToTzolkin(FRANKIE_JDN + 260))
        }

        @Test fun creationDate_coefficientIs4() =
            assertEquals(4, Calculator.jdnToTzolkin(GMT_CORRELATION).coefficient)

        @Test fun creationDate_nameIsAjaw() =
            assertEquals("Ajaw", Calculator.jdnToTzolkin(GMT_CORRELATION).name)

        @Test fun creationDate_daySignNumberIs20() =
            assertEquals(20, Calculator.jdnToTzolkin(GMT_CORRELATION).daySignNumber)
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
            val haab = Calculator.jdnToHaab(GMT_CORRELATION)
            assertEquals("Kumk'u", haab.monthName)
            assertEquals(8, haab.day)
        }
    }

    // ── jdnToLongCount ───────────────────────────────────────────────────────

    @Nested
    inner class LongCountTests {

        @Test fun oneUinalIs20Kins() {
            val lc = Calculator.jdnToLongCount(GMT_CORRELATION + 20)
            assertEquals(1, lc.uinal)
            assertEquals(0, lc.kin)
        }

        @Test fun oneTunIs18Uinals() {
            val lc = Calculator.jdnToLongCount(GMT_CORRELATION + 360)
            assertEquals(1, lc.tun)
            assertEquals(0, lc.uinal)
            assertEquals(0, lc.kin)
        }

        @Test fun oneKatunIs20Tuns() {
            val lc = Calculator.jdnToLongCount(GMT_CORRELATION + 7200)
            assertEquals(1, lc.katun)
            assertEquals(0, lc.tun)
        }

        @Test fun toStringFormat() =
            assertEquals("0.0.0.0.0", Calculator.jdnToLongCount(GMT_CORRELATION).toString())

        @Test fun displayFormat() =
            assertEquals("0.0.0.0.0", Calculator.jdnToLongCount(GMT_CORRELATION).display)

        @Test fun creationDateIsAllZero() {
            val lc = Calculator.jdnToLongCount(GMT_CORRELATION)
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

        @Test fun creationDateIsG9() =
            assertEquals("G9", Calculator.jdnToLordOfNight(GMT_CORRELATION))
    }

    @Nested
    inner class ValidationTests {
        @Test fun validLeapDayAccepted() {
            assertDoesNotThrow { Calculator.calculate(2024, 2, 29) }
        }

        @Test fun invalidDatesRejected() {
            val invalidDates = listOf(
                Triple(2023, 2, 29),
                Triple(2024, 13, 1),
                Triple(2024, 0, 10),
                Triple(2024, 4, 31),
                Triple(2024, 1, 0)
            )
            for ((year, month, day) in invalidDates) {
                assertThrows(IllegalArgumentException::class.java) {
                    Calculator.calculate(year, month, day)
                }
            }
        }
    }

}
