package io.github.c9jimmy.mayancalc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Constants")
class ConstantsTest {

    @Test fun gmtCorrelationIs584283() = assertEquals(584283L, GMT_CORRELATION)

    @Test fun tzolkinCoeffOriginIs4() = assertEquals(4, TZOLKIN_COEFF_ORIGIN)
    @Test fun tzolkinNameOriginIndexIs19() = assertEquals(19, TZOLKIN_NAME_ORIGIN_INDEX)
    @Test fun lordOfNightOriginIs9() = assertEquals(9, LORD_OF_NIGHT_ORIGIN)

    @Test fun tzolkinNamesHas20Elements() = assertEquals(20, TZOLKIN_DAY_SIGNS.size)
    @Test fun tzolkinFirstNameIsImix() = assertEquals("Imix", TZOLKIN_DAY_SIGNS[0])
    @Test fun tzolkinLastNameIsAjaw() = assertEquals("Ajaw", TZOLKIN_DAY_SIGNS[19])
    @Test fun tzolkinOriginNameIsAjaw() = assertEquals("Ajaw", TZOLKIN_DAY_SIGNS[TZOLKIN_NAME_ORIGIN_INDEX])
    @Test fun tzolkinNamesHaveNoBlankEntries() = assertTrue(TZOLKIN_DAY_SIGNS.none { it.isBlank() })

    @Test fun haabMonthNamesHas19Elements() = assertEquals(19, HAAB_MONTHS.size)
    @Test fun haabFirstMonthIsPop() = assertEquals("Pop", HAAB_MONTHS[0])
    @Test fun haabLastMonthIsWayeb() = assertEquals("Wayeb", HAAB_MONTHS[18])
    @Test fun haabMonthNamesHaveNoBlankEntries() = assertTrue(HAAB_MONTHS.none { it.isBlank() })

    @Test
    fun tzolkinNamesFullOrder() {
        val expected = listOf(
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        )
        assertEquals(expected, TZOLKIN_DAY_SIGNS)
    }

    @Test
    fun haabMonthNamesFullOrder() {
        val expected = listOf(
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        )
        assertEquals(expected, HAAB_MONTHS)
    }
}
