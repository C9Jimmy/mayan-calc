package io.github.c9jimmy.mayancalc

const val CORRELATION_JDN = 584283L

const val TZOLKIN_COEFF_ORIGIN = 4
const val TZOLKIN_NAME_ORIGIN_IDX = 19
const val TZOLKIN_CYCLE = 260L
const val TZOLKIN_COEFF_COUNT = 13
const val TZOLKIN_SIGN_COUNT = 20

const val HAAB_CYCLE = 365L
const val HAAB_DAYS_PER_MONTH = 20
const val HAAB_CORRELATION_OFFSET = 348L  // aligns creation date with 8 Kumk'u (17*20+8)

const val LC_BAKTUN = 144000L
const val LC_KATUN = 7200L
const val LC_TUN = 360L
const val LC_UINAL = 20L

const val LORD_OF_NIGHT_CYCLE = 9L

const val MEEUS_YEAR_FACTOR = 365.25
const val MEEUS_MONTH_FACTOR = 30.6001
const val MEEUS_EPOCH_A = 4716
const val MEEUS_EPOCH_B = 1524

val TZOLKIN_NAMES: List<String> = listOf(
    "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
    "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
    "Chuwen", "Eb", "Ben", "Hix", "Men",
    "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw"
)

val HAAB_MONTH_NAMES: List<String> = listOf(
    "Pop", "Wo", "Sip", "Sotz'", "Sek",
    "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
    "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
    "Pax", "K'ayab", "Kumk'u", "Wayeb"
)
