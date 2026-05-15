package io.github.c9jimmy.mayancalc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final int FRANKIE_JDN = 2447503; // 1988-12-07

    // ── dateToJdn ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("dateToJdn")
    class DateToJdnTests {

        @Test
        void j2000Epoch() {
            assertEquals(2451545, Calculator.dateToJdn(2000, 1, 1));
        }

        @Test
        void maya_2012_12_21() {
            assertEquals(2456283, Calculator.dateToJdn(2012, 12, 21));
        }

        @Test
        void frankie_1988_12_07() {
            assertEquals(2447503, Calculator.dateToJdn(1988, 12, 7));
        }

        @Test
        void today_2026_05_15() {
            assertEquals(2461176, Calculator.dateToJdn(2026, 5, 15));
        }

        @Test
        void creationDate_minus3113_08_11() {
            assertEquals(584283, Calculator.dateToJdn(-3113, 8, 11));
        }

        @Test
        void monthLessThan2Branch_2000_02_28() {
            assertEquals(2451603, Calculator.dateToJdn(2000, 2, 28));
        }
    }

    // ── jdnToTzolkin ─────────────────────────────────────────────────────────

    @Nested
    @DisplayName("jdnToTzolkin — cyclic properties")
    class TzolkinCyclicTests {

        @Test
        void coefficientIsAlways1to13() {
            for (int i = 0; i < 260; i++) {
                int n = Calculator.jdnToTzolkin(FRANKIE_JDN + i).coefficient();
                assertTrue(n >= 1 && n <= 13, "Expected 1–13, got " + n + " at offset " + i);
            }
        }

        @Test
        void daySignNumberIsAlways1to20() {
            for (int i = 0; i < 260; i++) {
                int dsn = Calculator.jdnToTzolkin(FRANKIE_JDN + i).daySignNumber();
                assertTrue(dsn >= 1 && dsn <= 20, "Expected 1–20, got " + dsn + " at offset " + i);
            }
        }

        @Test
        void cycle260DaysIsIdempotent() {
            TzolkinDate base = Calculator.jdnToTzolkin(FRANKIE_JDN);
            assertEquals(base, Calculator.jdnToTzolkin(FRANKIE_JDN + 260));
        }
    }

    // ── jdnToHaab ────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("jdnToHaab — cyclic properties")
    class HaabCyclicTests {

        @Test
        void dayIsAlways0to19() {
            for (int i = 0; i < 365; i++) {
                int d = Calculator.jdnToHaab(FRANKIE_JDN + i).day();
                assertTrue(d >= 0 && d <= 19, "Expected 0–19, got " + d + " at offset " + i);
            }
        }

        @Test
        void cycle365DaysIsIdempotent() {
            HaabDate base = Calculator.jdnToHaab(FRANKIE_JDN);
            assertEquals(base, Calculator.jdnToHaab(FRANKIE_JDN + 365));
        }
    }

    // ── jdnToLongCount ───────────────────────────────────────────────────────

    @Nested
    @DisplayName("jdnToLongCount — place-value arithmetic")
    class LongCountTests {

        @Test
        void oneUinalIs20Kins() {
            LongCount lc = Calculator.jdnToLongCount(Constants.GMT_CORRELATION + 20);
            assertEquals(1, lc.uinal());
            assertEquals(0, lc.kin());
        }

        @Test
        void oneTunIs18Uinals() {
            LongCount lc = Calculator.jdnToLongCount(Constants.GMT_CORRELATION + 360);
            assertEquals(1, lc.tun());
            assertEquals(0, lc.uinal());
            assertEquals(0, lc.kin());
        }

        @Test
        void oneKatunIs20Tuns() {
            LongCount lc = Calculator.jdnToLongCount(Constants.GMT_CORRELATION + 7200);
            assertEquals(1, lc.katun());
            assertEquals(0, lc.tun());
        }
    }

    // ── jdnToLordOfNight ─────────────────────────────────────────────────────

    @Nested
    @DisplayName("jdnToLordOfNight — cyclic properties")
    class LordOfNightTests {

        @Test
        void cycle9DaysIsIdempotent() {
            assertEquals(
                Calculator.jdnToLordOfNight(FRANKIE_JDN),
                Calculator.jdnToLordOfNight(FRANKIE_JDN + 9)
            );
        }

        @Test
        void valueIsAlwaysG1toG9() {
            java.util.Set<String> valid = new java.util.HashSet<>();
            for (int i = 1; i <= 9; i++) valid.add("G" + i);
            for (int i = 0; i < 9; i++) {
                String g = Calculator.jdnToLordOfNight(FRANKIE_JDN + i);
                assertTrue(valid.contains(g), "Expected G1–G9, got " + g);
            }
        }

        @Test
        void creationDateIsG1() {
            assertEquals("G1", Calculator.jdnToLordOfNight(Constants.GMT_CORRELATION));
        }
    }
}
