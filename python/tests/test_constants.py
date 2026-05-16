from mayan_calc.constants import (
    GMT_CORRELATION,
    HAAB_MONTHS,
    TZOLKIN_DAY_SIGNS,
)


def test_tzolkin_sign_count():
    assert len(TZOLKIN_DAY_SIGNS) == 20


def test_haab_month_count():
    assert len(HAAB_MONTHS) == 19  # 18 months + Wayeb


def test_gmt_correlation_value():
    assert GMT_CORRELATION == 584283


def test_tzolkin_starts_with_imix():
    assert TZOLKIN_DAY_SIGNS[0] == "Imix"


def test_tzolkin_ends_with_ajaw():
    assert TZOLKIN_DAY_SIGNS[19] == "Ajaw"


def test_haab_ends_with_wayeb():
    assert HAAB_MONTHS[18] == "Wayeb"


def test_haab_includes_kumku():
    assert "Kumk'u" in HAAB_MONTHS


def test_tzolkin_full_order():
    expected = [
        "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
        "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
        "Chuwen", "Eb", "Ben", "Hix", "Men",
        "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw",
    ]
    assert TZOLKIN_DAY_SIGNS == expected


def test_haab_full_order():
    expected = [
        "Pop", "Wo", "Sip", "Sotz'", "Sek",
        "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
        "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
        "Pax", "K'ayab", "Kumk'u", "Wayeb",
    ]
    assert HAAB_MONTHS == expected
