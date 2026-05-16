package io.github.c9jimmy.mayancalc

private fun floorMod(a: Long, b: Long): Long = ((a % b) + b) % b

object Calculator {

    internal fun dateToJdn(year: Int, month: Int, day: Int): Long {
        val y = if (month <= 2) year - 1 else year
        val m = if (month <= 2) month + 12 else month
        val a = y / 100
        val b = 2 - a + a / 4
        return (MEEUS_YEAR_FACTOR * (y + MEEUS_EPOCH_A)).toLong() +
               (MEEUS_MONTH_FACTOR * (m + 1)).toLong() +
               day + b - MEEUS_EPOCH_B
    }

    internal fun jdnToTzolkin(jdn: Long): TzolkinDate {
        val kin = floorMod(jdn - CORRELATION_JDN, TZOLKIN_CYCLE).toInt()
        val coefficient = (kin + TZOLKIN_COEFF_ORIGIN - 1) % TZOLKIN_COEFF_COUNT + 1
        val nameIdx = (kin + TZOLKIN_NAME_ORIGIN_IDX) % TZOLKIN_SIGN_COUNT
        return TzolkinDate(coefficient, TZOLKIN_NAMES[nameIdx], nameIdx + 1)
    }

    internal fun jdnToHaab(jdn: Long): HaabDate {
        val haabKin = floorMod(jdn - CORRELATION_JDN + HAAB_CORRELATION_OFFSET, HAAB_CYCLE).toInt()
        val monthIdx = haabKin / HAAB_DAYS_PER_MONTH
        return HaabDate(haabKin % HAAB_DAYS_PER_MONTH, HAAB_MONTH_NAMES[monthIdx])
    }

    internal fun jdnToLongCount(jdn: Long): LongCount {
        val total = jdn - CORRELATION_JDN
        return LongCount(
            (total / LC_BAKTUN).toInt(),
            ((total % LC_BAKTUN) / LC_KATUN).toInt(),
            ((total % LC_KATUN) / LC_TUN).toInt(),
            ((total % LC_TUN) / LC_UINAL).toInt(),
            (total % LC_UINAL).toInt()
        )
    }

    internal fun jdnToLordOfNight(jdn: Long): String {
        val g = floorMod(jdn - CORRELATION_JDN, LORD_OF_NIGHT_CYCLE).toInt() + 1
        return "G$g"
    }

    fun calculate(year: Int, month: Int, day: Int): MayanDate {
        val jdn = dateToJdn(year, month, day)
        return MayanDate(
            jdnToTzolkin(jdn),
            jdnToHaab(jdn),
            jdnToLongCount(jdn),
            jdnToLordOfNight(jdn)
        )
    }
}
