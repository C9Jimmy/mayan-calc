package io.github.c9jimmy.mayancalc;

/** Complete Classic Maya calendar date. Immutable. */
public record MayanDate(
    TzolkinDate tzolkin,
    HaabDate haab,
    LongCount longCount,
    String lordOfNight  // "G1"–"G9"
) {}
