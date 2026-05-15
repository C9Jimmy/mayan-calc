package io.github.c9jimmy.mayancalc;

/** Maya Long Count date. Immutable. */
public record LongCount(int baktun, int katun, int tun, int uinal, int kin) {

    public String display() {
        return baktun + "." + katun + "." + tun + "." + uinal + "." + kin;
    }
}
