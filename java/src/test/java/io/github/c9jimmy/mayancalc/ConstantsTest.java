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
}
