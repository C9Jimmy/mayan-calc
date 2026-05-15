package io.github.c9jimmy.mayancalc;

/** Complete Maya calendar date. Immutable. */
public record MayanDate(
    TzolkinDate tzolkin,
    HaabDate haab,
    LongCount longCount,
    int lordOfNight  // 1–9 (G1–G9)
) {}
