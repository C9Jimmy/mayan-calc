package io.github.c9jimmy.mayancalc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Constants")
class ConstantsTest {

    @Test
    void gmtCorrelationIs584283() {
        assertEquals(584283, Constants.GMT_CORRELATION);
    }

    @Test
    void tzolkinCoeffOriginIs4() {
        assertEquals(4, Constants.TZOLKIN_COEFF_ORIGIN);
    }

    @Test
    void tzolkinNameOriginIdxIs19() {
        assertEquals(19, Constants.TZOLKIN_NAME_ORIGIN_IDX);
    }

    @Test
    void tzolkinDaySignsHas20Elements() {
        assertEquals(20, Constants.TZOLKIN_DAY_SIGNS.length);
    }

    @Test
    void haabMonthsHas19Elements() {
        assertEquals(19, Constants.HAAB_MONTHS.length);
    }

    @Test
    void tzolkinFirstSignIsImix() {
        assertEquals("Imix", Constants.TZOLKIN_DAY_SIGNS[0]);
    }

    @Test
    void tzolkinLastSignIsAjaw() {
        assertEquals("Ajaw", Constants.TZOLKIN_DAY_SIGNS[19]);
    }

    @Test
    void haabFirstMonthIsPop() {
        assertEquals("Pop", Constants.HAAB_MONTHS[0]);
    }

    @Test
    void haabSeventeenthMonthIsKumku() {
        assertEquals("Kumk'u", Constants.HAAB_MONTHS[17]);
    }

    @Test
    void haabLastMonthIsWayeb() {
        assertEquals("Wayeb", Constants.HAAB_MONTHS[18]);
    }

    @Test
    void tzolkinFullOrder() {
        String[] expected = {
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        };
        assertArrayEquals(expected, Constants.TZOLKIN_DAY_SIGNS);
    }

    @Test
    void haabFullOrder() {
        String[] expected = {
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        };
        assertArrayEquals(expected, Constants.HAAB_MONTHS);
    }
}
