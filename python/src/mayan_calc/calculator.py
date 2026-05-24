from mayan_calc.constants import (
    GMT_CORRELATION,
    HAAB_CORRELATION_OFFSET,
    HAAB_CYCLE,
    HAAB_DAYS_PER_MONTH,
    HAAB_MONTHS,
    LC_BAKTUN,
    LC_KATUN,
    LC_TUN,
    LC_UINAL,
    LORD_OF_NIGHT_CYCLE,
    LORD_OF_NIGHT_ORIGIN,
    MEEUS_EPOCH_A,
    MEEUS_EPOCH_B,
    MEEUS_MONTH_FACTOR,
    MEEUS_YEAR_FACTOR,
    TZOLKIN_COEFF_COUNT,
    TZOLKIN_COEFF_ORIGIN,
    TZOLKIN_CYCLE,
    TZOLKIN_DAY_SIGNS,
    TZOLKIN_NAME_ORIGIN_INDEX,
    TZOLKIN_SIGN_COUNT,
)
from mayan_calc.models import HaabDate, LongCount, MayanDate, TzolkinDate


def _date_to_jdn(year: int, month: int, day: int) -> int:
    """Gregorian date → Julian Day Number (Meeus algorithm)."""
    y, m = (year - 1, month + 12) if month <= 2 else (year, month)
    a = y // 100
    b = 2 - a + a // 4
    return int(MEEUS_YEAR_FACTOR * (y + MEEUS_EPOCH_A)) + int(MEEUS_MONTH_FACTOR * (m + 1)) + day + b - MEEUS_EPOCH_B


def _jdn_to_tzolkin(jdn: int) -> TzolkinDate:
    """JDN → Tzolk'in date.

    Offsets align with the archaeological consensus: Maya creation date
    0.0.0.0.0 (JDN 584283) = 4 Ajaw.
    TZOLKIN_COEFF_ORIGIN - 1 on number: kin=0 → 4 (not 1).
    TZOLKIN_NAME_ORIGIN_INDEX on sign: kin=0 → Ajaw at index 19.
    """
    kin = (jdn - GMT_CORRELATION) % TZOLKIN_CYCLE
    number = ((kin + TZOLKIN_COEFF_ORIGIN - 1) % TZOLKIN_COEFF_COUNT) + 1
    sign_idx = (kin + TZOLKIN_NAME_ORIGIN_INDEX) % TZOLKIN_SIGN_COUNT
    return TzolkinDate(
        coefficient=number,
        name=TZOLKIN_DAY_SIGNS[sign_idx],
        day_sign_number=sign_idx + 1,
    )


def _jdn_to_haab(jdn: int) -> HaabDate:
    """JDN → Haab date.

    +348 aligns with creation date 8 Kumk'u: position 17*20+8 = 348.
    """
    haab_kin = (jdn - GMT_CORRELATION + HAAB_CORRELATION_OFFSET) % HAAB_CYCLE
    month_idx = haab_kin // HAAB_DAYS_PER_MONTH
    return HaabDate(day=haab_kin % HAAB_DAYS_PER_MONTH, month_name=HAAB_MONTHS[month_idx])


def _jdn_to_long_count(jdn: int) -> LongCount:
    """JDN → Long Count (baktun.katun.tun.uinal.kin)."""
    total = jdn - GMT_CORRELATION
    baktun = total // LC_BAKTUN
    katun = (total % LC_BAKTUN) // LC_KATUN
    tun = (total % LC_KATUN) // LC_TUN
    uinal = (total % LC_TUN) // LC_UINAL
    kin = total % LC_UINAL
    return LongCount(
        baktun=baktun,
        katun=katun,
        tun=tun,
        uinal=uinal,
        kin=kin,
        display=f"{baktun}.{katun}.{tun}.{uinal}.{kin}",
    )


def _jdn_to_lord_of_night(jdn: int) -> str:
    """JDN → Lord of Night G1–G9 (9-day cycle)."""
    total = jdn - GMT_CORRELATION
    lord = ((total + LORD_OF_NIGHT_ORIGIN - 1) % LORD_OF_NIGHT_CYCLE) + 1
    return f"G{lord}"


def _is_leap_year(year: int) -> bool:
    return year % 4 == 0 and (year % 100 != 0 or year % 400 == 0)


def _days_in_month(year: int, month: int) -> int:
    month_lengths = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    if month == 2 and _is_leap_year(year):
        return 29
    return month_lengths[month - 1]


def _validate_date(year: int, month: int, day: int) -> None:
    if not 1 <= month <= 12:
        raise ValueError("month must be in 1..12")
    max_day = _days_in_month(year, month)
    if not 1 <= day <= max_day:
        raise ValueError(f"day must be in 1..{max_day} for month {month}")


def calculate(year: int, month: int, day: int) -> MayanDate:
    """Gregorian date → complete Classic Maya calendar output."""
    _validate_date(year, month, day)
    jdn = _date_to_jdn(year, month, day)
    return MayanDate(
        tzolkin=_jdn_to_tzolkin(jdn),
        haab=_jdn_to_haab(jdn),
        long_count=_jdn_to_long_count(jdn),
        lord_of_night=_jdn_to_lord_of_night(jdn),
    )
