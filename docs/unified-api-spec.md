# mayan-calc Unified Classic Maya API Spec

## Goal

Make all five implementations smaller, clearer, and behaviorally equivalent.

This library is a pure arithmetic Classic Maya calendar calculator:

Gregorian date -> JDN -> Tzolk'in / Haab / Long Count / Lord of Night

All language ports must expose the same concepts, use language-native naming
style, and pass the same ground-truth fixtures.

## Public API

Each language should expose only one main calculation entrypoint:

| Language | Public function |
| --- | --- |
| Python | `calculate(year, month, day)` |
| TypeScript | `calculate(year, month, day)` |
| Java | `Calculator.calculate(year, month, day)` |
| C# | `Calculator.Calculate(year, month, day)` |
| Kotlin | `Calculator.calculate(year, month, day)` |

Internal helpers such as Gregorian-to-JDN, Tzolk'in conversion, Haab
conversion, Long Count conversion, and Lord of Night conversion must not be
part of the public API.

Recommended visibility:

| Language | Helper visibility |
| --- | --- |
| Python | private-style `_date_to_jdn`, `_jdn_to_tzolkin`, etc. |
| TypeScript | non-exported module-level functions |
| Java | `private static` |
| C# | `private static` |
| Kotlin | `private` functions inside `Calculator` or private top-level functions |

## Canonical Output Concepts

All languages must return these concepts:

```json
{
  "tzolkin": {
    "coefficient": 4,
    "name": "Ajaw",
    "day_sign_number": 20
  },
  "haab": {
    "day": 3,
    "month_name": "K'ank'in"
  },
  "long_count": {
    "baktun": 13,
    "katun": 0,
    "tun": 0,
    "uinal": 0,
    "kin": 0,
    "display": "13.0.0.0.0"
  },
  "lord_of_night": "G9"
}
```

Language-specific naming:

| Concept | Python | TypeScript | Java | C# | Kotlin |
| --- | --- | --- | --- | --- | --- |
| tzolkin | `tzolkin` | `tzolkin` | `tzolkin()` | `Tzolkin` | `tzolkin` |
| coefficient | `coefficient` | `coefficient` | `coefficient()` | `Coefficient` | `coefficient` |
| name | `name` | `name` | `name()` | `Name` | `name` |
| day sign number | `day_sign_number` | `daySignNumber` | `daySignNumber()` | `DaySignNumber` | `daySignNumber` |
| haab | `haab` | `haab` | `haab()` | `Haab` | `haab` |
| month name | `month_name` | `monthName` | `monthName()` | `MonthName` | `monthName` |
| long count | `long_count` | `longCount` | `longCount()` | `LongCount` | `longCount` |
| display | `display` | `display` | `display()` | `Display` | `display` |
| lord of night | `lord_of_night` | `lordOfNight` | `lordOfNight()` | `LordOfNight` | `lordOfNight` |

## Constants

All arithmetic constants must live in each language's constants file.

Required constants:

- GMT correlation: `584283`
- Tzolk'in cycle length: `260`
- Tzolk'in coefficient cycle: `13`
- Tzolk'in day sign cycle: `20`
- Tzolk'in coefficient origin: `4`
- Tzolk'in name origin index: `19`
- Haab cycle length: `365`
- Haab month length: `20`
- Haab creation offset: `348`
- Lord of Night cycle length: `9`
- Lord of Night origin: `9` (`0.0.0.0.0 = G9`)
- Long Count units:
  - baktun: `144000`
  - katun: `7200`
  - tun: `360`
  - uinal: `20`
- Meeus JDN constants:
  - `365.25`
  - `30.6001`
  - `4716`
  - `1524`

Required name lists:

- `TZOLKIN_DAY_SIGNS`: exactly 20 entries, same order in all languages.
- `HAAB_MONTHS`: exactly 19 entries, same order in all languages, including
  `Wayeb`.

Canonical constant names:

| Concept | Python / TypeScript / Java / Kotlin | C# |
| --- | --- | --- |
| GMT correlation | `GMT_CORRELATION` | `GmtCorrelation` |
| Tzolk'in coefficient origin | `TZOLKIN_COEFF_ORIGIN` | `TzolkinCoeffOrigin` |
| Tzolk'in name origin index | `TZOLKIN_NAME_ORIGIN_INDEX` | `TzolkinNameOriginIndex` |
| Tzolk'in day signs | `TZOLKIN_DAY_SIGNS` | `TzolkinDaySigns` |
| Haab months | `HAAB_MONTHS` | `HaabMonths` |
| Lord of Night origin | `LORD_OF_NIGHT_ORIGIN` | `LordOfNightOrigin` |
| Lord of Night cycle | `LORD_OF_NIGHT_CYCLE` | `LordOfNightCycle` |

Kotlin must not special-case `Wayeb`; it must store `Wayeb` in the canonical
Haab month list.

## Immutability

| Language | Required model style |
| --- | --- |
| Python | `@dataclass(frozen=True)` |
| TypeScript | `readonly` interfaces |
| Java | `record` |
| C# | `record` or init-only immutable properties |
| Kotlin | `data class` with `val` |

## Invalid Dates

The library validates public `calculate()` inputs as proleptic Gregorian dates.
BCE dates use astronomical year numbering (`year 0 = 1 BCE`,
`year -3113 = 3114 BCE`).

Required behavior:

- `calculate()` validates Gregorian month/day ranges.
- Invalid dates throw the language-native clear exception:
  - Python: `ValueError`
  - TypeScript: `RangeError`
  - Java: `IllegalArgumentException`
  - C#: `ArgumentOutOfRangeException`
  - Kotlin: `IllegalArgumentException`

Because this is a public library, silent arithmetic on invalid dates is not
acceptable.

## Tests

Each language should have exactly these test categories:

1. `CalculatorTest`
   - internal arithmetic behavior
   - cycle behavior
   - date-to-JDN behavior
2. `ConstantsTest`
   - GMT constant
   - full Tzolk'in name list
   - full Haab month list
   - exact ordering, not only first/last values
3. `IntegrationTest`
   - must validate all four dates from `fixtures/ground_truth.json`:
     - `2012-12-21`
     - `2000-01-01`
     - `1988-12-07`
     - `2026-05-15`

Integration tests must compare every output field, including:

- Tzolk'in coefficient
- Tzolk'in name
- Tzolk'in day sign number
- Haab day
- Haab month name
- Long Count components
- Long Count display
- Lord of Night

Anchor Lord of Night values must use the verified `G9` epoch:

- `2012-12-21`: `G9`
- `2000-01-01`: `G5`
- `1988-12-07`: `G4`
- `2026-05-15`: `G6`

## README Updates

README examples must match the actual model shape.

Long Count is an object, not only a string.

Correct examples should use:

- Python: `result.long_count.display`
- TypeScript: `result.longCount.display`
- Java: `result.longCount().display()`
- C#: `result.LongCount.Display`
- Kotlin: `result.longCount.display`

## Acceptance Criteria

The refactor is complete when:

1. All five languages expose only the intended public calculation API.
2. All five languages have the same constants and same name ordering.
3. C# and Kotlin include `DaySignNumber`.
4. C# and Kotlin include Long Count `Display`.
5. Kotlin stores all 19 Haab months, including `Wayeb`.
6. Magic numbers are moved out of calculator logic.
7. Invalid date behavior is validated or explicitly documented.
8. All five language test suites pass.
9. README and fixtures agree with the implemented API.
10. README uses Classic Maya calendar terminology and cites verification
    sources with text hyperlinks.
