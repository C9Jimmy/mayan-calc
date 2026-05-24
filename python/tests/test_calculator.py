from mayan_calc.calculator import (
    _date_to_jdn,
    _jdn_to_haab,
    _jdn_to_long_count,
    _jdn_to_lord_of_night,
    _jdn_to_tzolkin,
    calculate,
)
from mayan_calc.models import MayanDate


# ── date_to_jdn ──────────────────────────────────────────────────────────────

def test_date_to_jdn_frankie():
    assert _date_to_jdn(1988, 12, 7) == 2447503


def test_date_to_jdn_j2000():
    """J2000.0 epoch — widely used reference."""
    assert _date_to_jdn(2000, 1, 1) == 2451545


def test_date_to_jdn_jan_boundary():
    """month <= 2 branch: year and month are adjusted internally."""
    assert _date_to_jdn(2000, 2, 28) == 2451603


def test_date_to_jdn_leap_day():
    assert _date_to_jdn(2000, 2, 29) == 2451604


def test_date_to_jdn_creation_epoch():
    """GMT correlation: Maya 0.0.0.0.0 ↔ JDN 584283."""
    assert _date_to_jdn(-3113, 8, 11) == 584283


# ── jdn_to_long_count ────────────────────────────────────────────────────────

def test_long_count_creation_date(creation_jdn):
    lc = _jdn_to_long_count(creation_jdn)
    assert lc.baktun == 0
    assert lc.katun == 0
    assert lc.tun == 0
    assert lc.uinal == 0
    assert lc.kin == 0
    assert lc.display == "0.0.0.0.0"


def test_long_count_1988_12_07(frankie_jdn):
    lc = _jdn_to_long_count(frankie_jdn)
    assert lc.baktun == 12
    assert lc.katun == 18
    assert lc.tun == 15
    assert lc.uinal == 11
    assert lc.kin == 0
    assert lc.display == "12.18.15.11.0"


def test_long_count_display_format(frankie_jdn):
    lc = _jdn_to_long_count(frankie_jdn)
    parts = lc.display.split(".")
    assert len(parts) == 5
    assert all(p.isdigit() for p in parts)


# ── jdn_to_tzolkin ───────────────────────────────────────────────────────────

def test_tzolkin_creation_date(creation_jdn):
    """Creation date must be 4 Ajaw — the archaeological standard."""
    tz = _jdn_to_tzolkin(creation_jdn)
    assert tz.coefficient == 4
    assert tz.name == "Ajaw"
    assert tz.day_sign_number == 20


def test_tzolkin_1988_12_07(frankie_jdn):
    tz = _jdn_to_tzolkin(frankie_jdn)
    assert tz.coefficient == 12
    assert tz.name == "Ajaw"
    assert tz.day_sign_number == 20


def test_tzolkin_number_range(frankie_jdn):
    """Tzolk'in coefficient is always 1-13."""
    for offset in range(260):
        tz = _jdn_to_tzolkin(frankie_jdn + offset)
        assert 1 <= tz.coefficient <= 13


def test_tzolkin_sign_number_range(frankie_jdn):
    """day_sign_number cycles 1-20."""
    for offset in range(260):
        tz = _jdn_to_tzolkin(frankie_jdn + offset)
        assert 1 <= tz.day_sign_number <= 20


def test_tzolkin_260_day_cycle(frankie_jdn):
    """Full 260-day cycle returns to the same date."""
    tz_base = _jdn_to_tzolkin(frankie_jdn)
    tz_cycle = _jdn_to_tzolkin(frankie_jdn + 260)
    assert tz_base == tz_cycle


# ── jdn_to_haab ──────────────────────────────────────────────────────────────

def test_haab_creation_date(creation_jdn):
    """Creation date must be 8 Kumk'u — the archaeological standard."""
    h = _jdn_to_haab(creation_jdn)
    assert h.month_name == "Kumk'u"
    assert h.day == 8


def test_haab_1988_12_07(frankie_jdn):
    h = _jdn_to_haab(frankie_jdn)
    assert h.month_name == "Mak"
    assert h.day == 3


def test_haab_365_day_cycle(frankie_jdn):
    """Full 365-day Haab cycle returns to the same date."""
    h_base = _jdn_to_haab(frankie_jdn)
    h_cycle = _jdn_to_haab(frankie_jdn + 365)
    assert h_base == h_cycle


def test_haab_day_range(frankie_jdn):
    """Haab day is 0-19 (Wayeb has 0-4 but we only test general range)."""
    for offset in range(365):
        h = _jdn_to_haab(frankie_jdn + offset)
        assert 0 <= h.day <= 19


# ── jdn_to_lord_of_night ─────────────────────────────────────────────────────

def test_lord_of_night_creation_date(creation_jdn):
    assert _jdn_to_lord_of_night(creation_jdn) == "G9"


def test_lord_of_night_1988_12_07(frankie_jdn):
    assert _jdn_to_lord_of_night(frankie_jdn) == "G4"


def test_lord_of_night_9_day_cycle(creation_jdn):
    expected = ("G9", "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8")
    for i, lord in enumerate(expected):
        assert _jdn_to_lord_of_night(creation_jdn + i) == lord


def test_lord_of_night_range(frankie_jdn):
    valid = {f"G{i}" for i in range(1, 10)}
    for offset in range(9):
        g = _jdn_to_lord_of_night(frankie_jdn + offset)
        assert g in valid


# ── calculate (integration) ──────────────────────────────────────────────────

def test_calculate_returns_mayan_date():
    result = calculate(1988, 12, 7)
    assert isinstance(result, MayanDate)


def test_calculate_1988_12_07():
    result = calculate(1988, 12, 7)
    assert result.long_count.display == "12.18.15.11.0"
    assert result.tzolkin.coefficient == 12
    assert result.tzolkin.name == "Ajaw"
    assert result.haab.month_name == "Mak"
    assert result.haab.day == 3
    assert result.lord_of_night == "G4"


def test_calculate_creation_date():
    """Full integration: Maya epoch date."""
    result = calculate(-3113, 8, 11)
    assert result.long_count.display == "0.0.0.0.0"
    assert result.tzolkin.coefficient == 4
    assert result.tzolkin.name == "Ajaw"
    assert result.haab.month_name == "Kumk'u"
    assert result.haab.day == 8
    assert result.lord_of_night == "G9"


def test_calculate_accepts_valid_leap_day():
    result = calculate(2024, 2, 29)
    assert isinstance(result, MayanDate)


def test_calculate_rejects_invalid_dates():
    invalid_dates = (
        (2023, 2, 29),
        (2024, 13, 1),
        (2024, 0, 10),
        (2024, 4, 31),
        (2024, 1, 0),
    )
    for year, month, day in invalid_dates:
        try:
            calculate(year, month, day)
        except ValueError:
            pass
        else:
            raise AssertionError(f"Expected ValueError for {year}-{month}-{day}")
