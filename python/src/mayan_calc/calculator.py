from mayan_calc.constants import GMT_CORRELATION, HAAB_MONTHS, TZOLKIN_DAY_SIGNS
from mayan_calc.models import HaabDate, LongCount, MayanDate, TzolkinDate


def _date_to_jdn(year: int, month: int, day: int) -> int:
    """Gregorian date → Julian Day Number (Meeus algorithm)."""
    y, m = (year - 1, month + 12) if month <= 2 else (year, month)
    a = y // 100
    b = 2 - a + a // 4
    return int(365.25 * (y + 4716)) + int(30.6001 * (m + 1)) + day + b - 1524


def _jdn_to_tzolkin(jdn: int) -> TzolkinDate:
    """JDN → Tzolk'in date.

    Offsets align with the archaeological consensus: Maya creation date
    0.0.0.0.0 (JDN 584283) = 4 Ajaw.
    +3 on number: kin=0 → 4 (not 1).
    +19 on sign: kin=0 → Ajaw at index 19 (not Imix at index 0).
    """
    kin = (jdn - GMT_CORRELATION) % 260
    number = ((kin + 3) % 13) + 1
    sign_idx = (kin + 19) % 20
    return TzolkinDate(
        coefficient=number,
        name=TZOLKIN_DAY_SIGNS[sign_idx],
        day_sign_number=sign_idx + 1,
    )


def _jdn_to_haab(jdn: int) -> HaabDate:
    """JDN → Haab date.

    +348 aligns with creation date 8 Kumk'u: position 17*20+8 = 348.
    """
    haab_kin = (jdn - GMT_CORRELATION + 348) % 365
    month_idx = haab_kin // 20
    return HaabDate(day=haab_kin % 20, month_name=HAAB_MONTHS[month_idx])


def _jdn_to_long_count(jdn: int) -> LongCount:
    """JDN → Long Count (baktun.katun.tun.uinal.kin)."""
    total = jdn - GMT_CORRELATION
    baktun = total // 144000
    katun = (total % 144000) // 7200
    tun = (total % 7200) // 360
    uinal = (total % 360) // 20
    kin = total % 20
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
    return f"G{((jdn - GMT_CORRELATION) % 9) + 1}"


def calculate(year: int, month: int, day: int) -> MayanDate:
    """Gregorian date → complete Maya calendar output."""
    jdn = _date_to_jdn(year, month, day)
    return MayanDate(
        tzolkin=_jdn_to_tzolkin(jdn),
        haab=_jdn_to_haab(jdn),
        long_count=_jdn_to_long_count(jdn),
        lord_of_night=_jdn_to_lord_of_night(jdn),
    )
