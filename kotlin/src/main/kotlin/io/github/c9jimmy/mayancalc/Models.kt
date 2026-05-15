package io.github.c9jimmy.mayancalc

data class TzolkinDate(val coefficient: Int, val name: String)

data class HaabDate(val day: Int, val monthName: String)

data class LongCount(
    val baktun: Int,
    val katun: Int,
    val tun: Int,
    val uinal: Int,
    val kin: Int
) {
    override fun toString() = "$baktun.$katun.$tun.$uinal.$kin"
}

data class MayanDate(
    val tzolkin: TzolkinDate,
    val haab: HaabDate,
    val longCount: LongCount,
    val lordOfNight: String
)
