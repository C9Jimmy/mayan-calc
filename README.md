![CI](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml/badge.svg)

# mayan-calc

Zero-dependency Maya calendar library — Tzolk'in, Haab, Long Count, Lord of Night.

Part of the [multi-system astronomical calculation library](https://github.com/C9Jimmy/celestial-calc) family.

## Installation

```bash
pip install mayan-calc
```

## Quick start

```python
from mayan_calc import calculate

result = calculate(1988, 12, 7)

print(result.long_count.display)       # 12.18.15.11.0
print(result.tzolkin.number)           # 12
print(result.tzolkin.day_sign)         # Ajaw
print(result.haab.month, result.haab.day)  # Mak 3
print(result.lord_of_night)            # 5
```

## Output format

```json
{
  "tzolkin": { "number": 12, "day_sign": "Ajaw", "day_sign_number": 20 },
  "haab": { "month": "Mak", "day": 3 },
  "long_count": {
    "baktun": 12, "katun": 18, "tun": 15, "uinal": 11, "kin": 0,
    "display": "12.18.15.11.0"
  },
  "lord_of_night": 5
}
```

## Design

- Pure date arithmetic — no astronomical dependencies
- GMT correlation constant: **584283** (Goodman-Martinez-Thompson consensus)
- Tzolk'in offsets calibrated to 4 Ajaw 8 Kumk'u (Maya creation date 0.0.0.0.0)
- All data structures are immutable frozen dataclasses

## Development

```bash
pip install -e ".[dev]"
python -m pytest tests/ -v --cov=src
```

## References

- Meeus, J. (1998). *Astronomical Algorithms* (2nd ed.). Willmann-Bell.
- Thompson, J.E.S. (1927). *A Correlation of the Mayan and European Calendars.*

---

> This library is a pure mathematical implementation of the Maya calendar system for
> educational and research purposes.
> All algorithms are based on published archaeological and astronomical literature.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
