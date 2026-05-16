import json
from pathlib import Path

from mayan_calc.calculator import calculate


FIXTURE_PATH = Path(__file__).resolve().parents[2] / "fixtures" / "ground_truth.json"


def test_ground_truth_anchor_dates():
    cases = json.loads(FIXTURE_PATH.read_text(encoding="utf-8"))

    for case in cases:
        result = calculate(**case["input"])
        expected = case["expected"]

        assert result.tzolkin.coefficient == expected["tzolkin"]["coefficient"]
        assert result.tzolkin.name == expected["tzolkin"]["name"]
        assert result.haab.day == expected["haab"]["day"]
        assert result.haab.month_name == expected["haab"]["month_name"]
        assert result.long_count.baktun == expected["long_count"]["baktun"]
        assert result.long_count.katun == expected["long_count"]["katun"]
        assert result.long_count.tun == expected["long_count"]["tun"]
        assert result.long_count.uinal == expected["long_count"]["uinal"]
        assert result.long_count.kin == expected["long_count"]["kin"]
        assert result.long_count.display == expected["long_count"]["display"]
        assert result.lord_of_night == expected["lord_of_night"]
