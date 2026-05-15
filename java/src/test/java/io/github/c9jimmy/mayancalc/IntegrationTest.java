/**
 * Layer 2 integration tests: full Gregorian date → MayanDate pipeline.
 * All expected values Python-verified (see Session B / Session C).
 */
package io.github.c9jimmy.mayancalc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    // ── 2012-12-21 (Long Count turnover 13.0.0.0.0) ─────────────────────────

    @Nested
    @DisplayName("2012-12-21: Long Count turnover 13.0.0.0.0")
    class Maya2012 {
        private final MayanDate result = Calculator.calculate(2012, 12, 21);

        @Test
        void longCount() {
            assertEquals("13.0.0.0.0", result.longCount().display());
        }

        @Test
        void tzolkin() {
            assertEquals(4, result.tzolkin().coefficient());
            assertEquals("Ajaw", result.tzolkin().name());
            assertEquals(20, result.tzolkin().daySignNumber());
        }

        @Test
        void haab() {
            assertEquals("K'ank'in", result.haab().monthName());
            assertEquals(3, result.haab().day());
        }

        @Test
        void lordOfNight() {
            assertEquals("G1", result.lordOfNight());
        }
    }

    // ── 2000-01-01 ───────────────────────────────────────────────────────────

    @Nested
    @DisplayName("2000-01-01")
    class Y2K {
        private final MayanDate result = Calculator.calculate(2000, 1, 1);

        @Test
        void longCount() {
            assertEquals("12.19.6.15.2", result.longCount().display());
        }

        @Test
        void tzolkin() {
            assertEquals(11, result.tzolkin().coefficient());
            assertEquals("Ik'", result.tzolkin().name());
            assertEquals(2, result.tzolkin().daySignNumber());
        }

        @Test
        void haab() {
            assertEquals("K'ank'in", result.haab().monthName());
            assertEquals(10, result.haab().day());
        }

        @Test
        void lordOfNight() {
            assertEquals("G6", result.lordOfNight());
        }
    }

    // ── 1988-12-07 (Frankie Fang) ────────────────────────────────────────────

    @Nested
    @DisplayName("1988-12-07 (Frankie Fang)")
    class Frankie {
        private final MayanDate result = Calculator.calculate(1988, 12, 7);

        @Test
        void longCount() {
            assertEquals("12.18.15.11.0", result.longCount().display());
        }

        @Test
        void tzolkin() {
            assertEquals(12, result.tzolkin().coefficient());
            assertEquals("Ajaw", result.tzolkin().name());
            assertEquals(20, result.tzolkin().daySignNumber());
        }

        @Test
        void haab() {
            assertEquals("Mak", result.haab().monthName());
            assertEquals(3, result.haab().day());
        }

        @Test
        void lordOfNight() {
            assertEquals("G5", result.lordOfNight());
        }
    }

    // ── 2026-05-15 ───────────────────────────────────────────────────────────

    @Nested
    @DisplayName("2026-05-15")
    class Today {
        private final MayanDate result = Calculator.calculate(2026, 5, 15);

        @Test
        void longCount() {
            assertEquals("13.0.13.10.13", result.longCount().display());
        }

        @Test
        void tzolkin() {
            assertEquals(9, result.tzolkin().coefficient());
            assertEquals("Ben", result.tzolkin().name());
            assertEquals(13, result.tzolkin().daySignNumber());
        }

        @Test
        void haab() {
            assertEquals("Sip", result.haab().monthName());
            assertEquals(6, result.haab().day());
        }

        @Test
        void lordOfNight() {
            assertEquals("G7", result.lordOfNight());
        }
    }
}
