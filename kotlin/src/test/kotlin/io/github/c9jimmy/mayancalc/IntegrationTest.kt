package io.github.c9jimmy.mayancalc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class IntegrationTest {

    // ── 2012-12-21 (Long Count turnover 13.0.0.0.0) ─────────────────────────

    @Nested
    @DisplayName("2012-12-21: Long Count turnover 13.0.0.0.0")
    inner class Maya2012 {
        private val result = Calculator.calculate(2012, 12, 21)

        @Test fun longCount()        = assertEquals("13.0.0.0.0", result.longCount.toString())
        @Test fun tzolkinCoefficient() = assertEquals(4, result.tzolkin.coefficient)
        @Test fun tzolkinName()      = assertEquals("Ajaw", result.tzolkin.name)
        @Test fun haabDay()          = assertEquals(3, result.haab.day)
        @Test fun haabMonth()        = assertEquals("K'ank'in", result.haab.monthName)
        @Test fun lordOfNight()      = assertEquals("G1", result.lordOfNight)
    }

    // ── 2000-01-01 ───────────────────────────────────────────────────────────

    @Nested
    @DisplayName("2000-01-01")
    inner class Y2K {
        private val result = Calculator.calculate(2000, 1, 1)

        @Test fun longCount()        = assertEquals("12.19.6.15.2", result.longCount.toString())
        @Test fun tzolkinCoefficient() = assertEquals(11, result.tzolkin.coefficient)
        @Test fun tzolkinName()      = assertEquals("Ik'", result.tzolkin.name)
        @Test fun haabDay()          = assertEquals(10, result.haab.day)
        @Test fun haabMonth()        = assertEquals("K'ank'in", result.haab.monthName)
        @Test fun lordOfNight()      = assertEquals("G6", result.lordOfNight)
    }

    // ── 1988-12-07 ───────────────────────────────────────────────────────────

    @Nested
    @DisplayName("1988-12-07 (Frankie Fang)")
    inner class Frankie {
        private val result = Calculator.calculate(1988, 12, 7)

        @Test fun longCount()        = assertEquals("12.18.15.11.0", result.longCount.toString())
        @Test fun tzolkinCoefficient() = assertEquals(12, result.tzolkin.coefficient)
        @Test fun tzolkinName()      = assertEquals("Ajaw", result.tzolkin.name)
        @Test fun haabDay()          = assertEquals(3, result.haab.day)
        @Test fun haabMonth()        = assertEquals("Mak", result.haab.monthName)
        @Test fun lordOfNight()      = assertEquals("G5", result.lordOfNight)
    }

    // ── 2026-05-15 ───────────────────────────────────────────────────────────

    @Nested
    @DisplayName("2026-05-15")
    inner class Today {
        private val result = Calculator.calculate(2026, 5, 15)

        @Test fun longCount()        = assertEquals("13.0.13.10.13", result.longCount.toString())
        @Test fun tzolkinCoefficient() = assertEquals(9, result.tzolkin.coefficient)
        @Test fun tzolkinName()      = assertEquals("Ben", result.tzolkin.name)
        @Test fun haabDay()          = assertEquals(6, result.haab.day)
        @Test fun haabMonth()        = assertEquals("Sip", result.haab.monthName)
        @Test fun lordOfNight()      = assertEquals("G7", result.lordOfNight)
    }
}
