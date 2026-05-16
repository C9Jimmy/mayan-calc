package io.github.c9jimmy.mayancalc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Constants")
class ConstantsTest {

    @Test fun gmtCorrelationIs584283() = assertEquals(584283L, CORRELATION_JDN)

    @Test fun tzolkinCoeffOriginIs4() = assertEquals(4, TZOLKIN_COEFF_ORIGIN)
    @Test fun tzolkinNameOriginIdxIs19() = assertEquals(19, TZOLKIN_NAME_ORIGIN_IDX)

    @Test fun tzolkinNamesHas20Elements() = assertEquals(20, TZOLKIN_NAMES.size)
    @Test fun tzolkinFirstNameIsImix() = assertEquals("Imix", TZOLKIN_NAMES[0])
    @Test fun tzolkinLastNameIsAjaw() = assertEquals("Ajaw", TZOLKIN_NAMES[19])
    @Test fun tzolkinOriginNameIsAjaw() = assertEquals("Ajaw", TZOLKIN_NAMES[TZOLKIN_NAME_ORIGIN_IDX])
    @Test fun tzolkinNamesHaveNoBlankEntries() = assertTrue(TZOLKIN_NAMES.none { it.isBlank() })

    @Test fun haabMonthNamesHas19Elements() = assertEquals(19, HAAB_MONTH_NAMES.size)
    @Test fun haabFirstMonthIsPop() = assertEquals("Pop", HAAB_MONTH_NAMES[0])
    @Test fun haabLastMonthIsWayeb() = assertEquals("Wayeb", HAAB_MONTH_NAMES[18])
    @Test fun haabMonthNamesHaveNoBlankEntries() = assertTrue(HAAB_MONTH_NAMES.none { it.isBlank() })

    @Test
    fun tzolkinNamesFullOrder() {
        val expected = listOf(
            "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
            "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
            "Chuwen", "Eb", "Ben", "Hix", "Men",
            "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
        )
        assertEquals(expected, TZOLKIN_NAMES)
    }

    @Test
    fun haabMonthNamesFullOrder() {
        val expected = listOf(
            "Pop", "Wo", "Sip", "Sotz'", "Sek",
            "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
            "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
            "Pax", "K'ayab", "Kumk'u", "Wayeb"
        )
        assertEquals(expected, HAAB_MONTH_NAMES)
    }
}
