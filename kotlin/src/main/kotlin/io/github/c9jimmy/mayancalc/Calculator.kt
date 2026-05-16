package io.github.c9jimmy.mayancalc

private fun floorMod(a: Long, b: Long): Long = ((a % b) + b) % b

object Calculator {

    fun dateToJdn(year: Int, month: Int, day: Int): Long {
        val y = if (month <= 2) year - 1 else year
        val m = if (month <= 2) month + 12 else month
        val a = y / 100
        val b = 2 - a + a / 4
        return (365.25 * (y + 4716)).toLong() +
               (30.6001 * (m + 1)).toLong() +
               day + b - 1524
    }

    fun jdnToTzolkin(jdn: Long): TzolkinDate {
        val kin = floorMod(jdn - CORRELATION_JDN, 260L).toInt()
        val coefficient = (kin + TZOLKIN_COEFF_ORIGIN - 1) % 13 + 1
        val nameIdx = (kin + TZOLKIN_NAME_ORIGIN_IDX) % 20
        return TzolkinDate(coefficient, TZOLKIN_NAMES[nameIdx], nameIdx + 1)
    }

    fun jdnToHaab(jdn: Long): HaabDate {
        val haabKin = floorMod(jdn - CORRELATION_JDN + 348L, 365L).toInt()
        val monthIdx = haabKin / 20
        return HaabDate(haabKin % 20, HAAB_MONTH_NAMES[monthIdx])
    }

    fun jdnToLongCount(jdn: Long): LongCount {
        val total = jdn - CORRELATION_JDN
        return LongCount(
            (total / 144000L).toInt(),
            ((total % 144000L) / 7200L).toInt(),
            ((total % 7200L) / 360L).toInt(),
            ((total % 360L) / 20L).toInt(),
            (total % 20L).toInt()
        )
    }

    fun jdnToLordOfNight(jdn: Long): String {
        val g = floorMod(jdn - CORRELATION_JDN, 9L).toInt() + 1
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
