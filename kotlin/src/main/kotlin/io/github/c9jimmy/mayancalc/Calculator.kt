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
        val kin = floorMod(jdn - GMT_CORRELATION, TZOLKIN_CYCLE).toInt()
        val coefficient = (kin + TZOLKIN_COEFF_ORIGIN - 1) % TZOLKIN_COEFF_COUNT + 1
        val nameIdx = (kin + TZOLKIN_NAME_ORIGIN_INDEX) % TZOLKIN_SIGN_COUNT
        return TzolkinDate(coefficient, TZOLKIN_DAY_SIGNS[nameIdx], nameIdx + 1)
    }

    internal fun jdnToHaab(jdn: Long): HaabDate {
        val haabKin = floorMod(jdn - GMT_CORRELATION + HAAB_CORRELATION_OFFSET, HAAB_CYCLE).toInt()
        val monthIdx = haabKin / HAAB_DAYS_PER_MONTH
        return HaabDate(haabKin % HAAB_DAYS_PER_MONTH, HAAB_MONTHS[monthIdx])
    }

    internal fun jdnToLongCount(jdn: Long): LongCount {
        val total = jdn - GMT_CORRELATION
        return LongCount(
            (total / LC_BAKTUN).toInt(),
            ((total % LC_BAKTUN) / LC_KATUN).toInt(),
            ((total % LC_KATUN) / LC_TUN).toInt(),
            ((total % LC_TUN) / LC_UINAL).toInt(),
            (total % LC_UINAL).toInt()
        )
    }

    internal fun jdnToLordOfNight(jdn: Long): String {
        val total = jdn - GMT_CORRELATION
        val g = floorMod(total + LORD_OF_NIGHT_ORIGIN - 1, LORD_OF_NIGHT_CYCLE).toInt() + 1
        return "G$g"
    }

    private fun isLeapYear(year: Int): Boolean =
        year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)

    private fun daysInMonth(year: Int, month: Int): Int {
        val monthLengths = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        return if (month == 2 && isLeapYear(year)) 29 else monthLengths[month - 1]
    }

    private fun validateDate(year: Int, month: Int, day: Int) {
        require(month in 1..12) { "month must be in 1..12" }
        val maxDay = daysInMonth(year, month)
        require(day in 1..maxDay) { "day must be in 1..$maxDay for month $month" }
    }

    fun calculate(year: Int, month: Int, day: Int): MayanDate {
        validateDate(year, month, day)
        val jdn = dateToJdn(year, month, day)
        return MayanDate(
            jdnToTzolkin(jdn),
            jdnToHaab(jdn),
            jdnToLongCount(jdn),
            jdnToLordOfNight(jdn)
        )
    }
}
