from mayan_calc.constants import GMT_CORRELATION, HAAB_MONTHS, TZOLKIN_DAY_SIGNS


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
