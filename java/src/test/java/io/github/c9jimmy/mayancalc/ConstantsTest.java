package io.github.c9jimmy.mayancalc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void tzolkinNameOriginIndexIs19() {
        assertEquals(19, Constants.TZOLKIN_NAME_ORIGIN_INDEX);
    }

    @Test
    void lordOfNightOriginIs9() {
        assertEquals(9, Constants.LORD_OF_NIGHT_ORIGIN);
    }

    @Test
    void tzolkinDaySignsHas20Elements() {
        assertEquals(20, Constants.TZOLKIN_DAY_SIGNS.size());
    }

    @Test
    void haabMonthsHas19Elements() {
        assertEquals(19, Constants.HAAB_MONTHS.size());
    }

    @Test
    void tzolkinFirstSignIsImix() {
        assertEquals("Imix", Constants.TZOLKIN_DAY_SIGNS.get(0));
    }

    @Test
    void tzolkinLastSignIsAjaw() {
        assertEquals("Ajaw", Constants.TZOLKIN_DAY_SIGNS.get(19));
    }

    @Test
    void haabFirstMonthIsPop() {
        assertEquals("Pop", Constants.HAAB_MONTHS.get(0));
    }

    @Test
    void haabSeventeenthMonthIsKumku() {
        assertEquals("Kumk'u", Constants.HAAB_MONTHS.get(17));
    }

    @Test
    void haabLastMonthIsWayeb() {
        assertEquals("Wayeb", Constants.HAAB_MONTHS.get(18));
    }

    @Test
    void tzolkinFullOrder() {
        List<String> expected = List.of(
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        );
        assertEquals(expected, Constants.TZOLKIN_DAY_SIGNS);
    }

    @Test
    void haabFullOrder() {
        List<String> expected = List.of(
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        );
        assertEquals(expected, Constants.HAAB_MONTHS);
    }

    @Test
    void nameListsAreImmutable() {
        assertThrows(UnsupportedOperationException.class, () -> Constants.TZOLKIN_DAY_SIGNS.add("Other"));
        assertThrows(UnsupportedOperationException.class, () -> Constants.HAAB_MONTHS.add("Other"));
    }
}
