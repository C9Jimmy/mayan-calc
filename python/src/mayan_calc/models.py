from dataclasses import dataclass


@dataclass(frozen=True)
class TzolkinDate:
    number: int          # 1-13
    day_sign: str        # e.g. "Ajaw"
    day_sign_number: int # 1-20, position in the 20-sign cycle


@dataclass(frozen=True)
class HaabDate:
    month: str  # e.g. "Mak"
    day: int    # 0-19 (0-4 for Wayeb)


@dataclass(frozen=True)
class LongCount:
    baktun: int
    katun: int
    tun: int
    uinal: int
    kin: int
    display: str  # e.g. "12.18.15.11.0"


@dataclass(frozen=True)
class MayanDate:
    tzolkin: TzolkinDate
    haab: HaabDate
    long_count: LongCount
    lord_of_night: int  # 1-9 (G1–G9)
