# mayan-calc Code Review: Classic Maya Calendar Corrections

Date: 2026-05-24

Status: completed and ready for Claude handoff.

## Summary

This review covers the current `mayan-calc` project across Python, TypeScript,
Java, Kotlin, and C# implementations.

The project should be described as a Classic Maya calendar calculation library,
not a generic "Maya calendar" package. Its current supported outputs are:

- Tzolk'in
- Haab
- Long Count
- Lord of Night

The package/repository name should remain `mayan-calc` for compatibility. The
README and documentation should clarify the project scope as "Classic Maya
calendar" / "古典瑪雅曆".

## Verification Sources

The Lord of Night correction and Classic Maya calendar scope are supported by:

- [Smithsonian Maya Calendar Converter](https://maya.nmai.si.edu/calendar/maya-calendar-converter)
- [Maya Numeration, Computation, and Calendrical Astronomy](https://www.encyclopedia.com/science/dictionaries-thesauruses-pictures-and-press-releases/maya-numeration-computation-and-calendrical-astronomy)
- [How to Build a Calendar](https://www.polysyllabic.com/?q=buildacal)
- [Python Vuh: Mayan Calendrical Mathematics with Python](https://legacy.python.org/workshops/1998-11/proceedings/papers/laningham/laningham.pdf)

These sources support:

- GMT correlation constant: `584283`
- `0.0.0.0.0 = 4 Ajaw 8 Kumk'u G9`
- `13.0.0.0.0 = 4 Ajaw 3 K'ank'in G9`

## High Priority Findings

### 1. Lord of Night epoch is offset incorrectly

Current behavior treats the Long Count epoch as:

```text
0.0.0.0.0 = G1
```

The verified Classic Maya calendar references support:

```text
0.0.0.0.0 = G9
```

All five implementations should use the same formula:

```text
daysSinceEpoch = jdn - GMT_CORRELATION
lordNumber = floorMod(daysSinceEpoch + LORD_OF_NIGHT_ORIGIN - 1, LORD_OF_NIGHT_CYCLE) + 1
```

Where:

```text
LORD_OF_NIGHT_ORIGIN = 9
LORD_OF_NIGHT_CYCLE = 9
```

Anchor date updates:

```text
2012-12-21: G1 -> G9
2000-01-01: G6 -> G5
1988-12-07: G5 -> G4
2026-05-15: G7 -> G6
```

Files to update:

- `python/src/mayan_calc/constants.py`
- `python/src/mayan_calc/calculator.py`
- `typescript/src/constants.ts`
- `typescript/src/calculator.ts`
- `java/src/main/java/io/github/c9jimmy/mayancalc/Constants.java`
- `java/src/main/java/io/github/c9jimmy/mayancalc/Calculator.java`
- `kotlin/src/main/kotlin/io/github/c9jimmy/mayancalc/Constants.kt`
- `kotlin/src/main/kotlin/io/github/c9jimmy/mayancalc/Calculator.kt`
- `csharp/MayanCalc/Constants.cs`
- `csharp/MayanCalc/Calculator.cs`

### 2. Invalid Gregorian dates are accepted

The public `calculate(year, month, day)` entrypoint currently allows invalid
dates to enter the arithmetic flow.

Examples that must become invalid:

```text
2023-02-29
2024-13-01
2024-00-10
2024-04-31
day = 0
```

Expected behavior:

- Python: raise `ValueError`
- TypeScript: throw `RangeError`
- Java: throw `IllegalArgumentException`
- Kotlin: throw `IllegalArgumentException`
- C#: throw `ArgumentOutOfRangeException`

Valid date rules:

- Input dates use the proleptic Gregorian calendar.
- BCE years use astronomical year numbering.
- `year 0 = 1 BCE`
- `year -3113 = 3114 BCE`
- `-3113-08-11` remains valid as the Classic Maya creation date.

## Medium Priority Findings

### 3. Cross-language constant names should be unified

Formula changes must avoid language drift. API and constants should use the same
conceptual names across languages.

Recommended canonical names:

| Concept | Python / TypeScript / Java / Kotlin | C# |
| --- | --- | --- |
| GMT correlation | `GMT_CORRELATION` | `GmtCorrelation` |
| Tzolk'in coefficient origin | `TZOLKIN_COEFF_ORIGIN` | `TzolkinCoeffOrigin` |
| Tzolk'in name origin index | `TZOLKIN_NAME_ORIGIN_INDEX` | `TzolkinNameOriginIndex` |
| Tzolk'in day signs | `TZOLKIN_DAY_SIGNS` | `TzolkinDaySigns` |
| Haab months | `HAAB_MONTHS` | `HaabMonths` |
| Lord of Night origin | `LORD_OF_NIGHT_ORIGIN` | `LordOfNightOrigin` |
| Lord of Night cycle | `LORD_OF_NIGHT_CYCLE` | `LordOfNightCycle` |

Notes:

- Prefer `INDEX` over abbreviated `IDX` for new canonical names.
- Existing public names may remain as compatibility aliases if already exposed.
- Internal calculator code should use canonical constants, not inline numbers.

### 4. Fixtures should remain the canonical expected output

Update:

- `fixtures/ground_truth.json`
- `fixtures/output_spec.json`

Tests should derive expected values from the fixture where practical.

Current recommendation:

- Python and TypeScript integration tests should read `fixtures/ground_truth.json`.
- Java, Kotlin, and C# may keep parameterized hardcoded values if avoiding test
  dependencies is preferred, but comments should state that
  `fixtures/ground_truth.json` is canonical.
- TypeScript integration tests must include `2026-05-15`.

### 5. Documentation should use Classic Maya calendar terminology

Do not rename package or repository identifiers:

- `mayan-calc`
- `@c9jimmy/mayan-calc`
- `MayanCalc`
- `io.github.c9jimmy:mayan-calc`

Update documentation descriptions:

- English: `Classic Maya calendar calculation library`
- Traditional Chinese: `古典瑪雅曆計算函式庫`

Update citation title:

```bibtex
title = {mayan-calc: Classic Maya Calendar Calculation Library}
```

The README should use text hyperlinks for sources, not bare URLs.

## Low / Clean Code Findings

### 6. Immutable constants

Public name lists should be immutable where possible:

- Python: tuple
- TypeScript: `as const` and/or frozen runtime export
- Java: `List.of`
- Kotlin: immutable `listOf`
- C#: `IReadOnlyList<string>` / readonly backing

### 7. Helper visibility

Current package/internal helper visibility is acceptable if it is needed for
unit testing. Public API should still be centered on `calculate(year, month, day)`.

## Required Test Updates

Add or update tests for:

```text
-3113-08-11 -> 0.0.0.0.0 / 4 Ajaw / 8 Kumk'u / G9
2012-12-21 -> 13.0.0.0.0 / 4 Ajaw / 3 K'ank'in / G9
2000-01-01 -> Lord of Night G5
1988-12-07 -> Lord of Night G4
2026-05-15 -> Lord of Night G6
2024-02-29 -> valid
2023-02-29 -> invalid
2024-13-01 -> invalid
2024-00-10 -> invalid
2024-04-31 -> invalid
day=0 -> invalid
```

## Verification Commands

Run all language test suites after implementation:

```bash
cd python && python3 -m pytest
cd typescript && npm test -- --run
cd java && mvn test
cd kotlin && mvn test
cd csharp && dotnet test
```

Expected result:

- All tests pass.
- Lord of Night anchor values match `G9`, `G5`, `G4`, and `G6`.
- Invalid date tests fail before implementation and pass after implementation.
- README and fixtures agree with implemented output.

Completed verification:

```text
Python: 41 passed
TypeScript: 46 passed
Java: 49 passed
Kotlin: 69 passed
C#: 56 passed
git diff --check: pass
```

## Implementation Status

Completed changes:

```text
1. Lord of Night epoch was corrected from G1 to G9 in all five language implementations.
2. Strict proleptic Gregorian date validation was added to public calculate() entrypoints.
3. Fixtures, output spec, tests, README.md, and README.zh-TW.md were updated.
4. Package/repository names remain unchanged for compatibility.
5. Documentation scope now uses Classic Maya calendar / 古典瑪雅曆.
6. Verified Maya reference links were added as text hyperlinks.
7. thirteen_moon / Dreamspell remains out of scope.
8. Cross-language constant/API naming was normalized with compatibility aliases where useful.
```

Handoff note for Claude:

```text
The code review findings in this document have been implemented and verified.
Use this document as the completed review record, not as a pending task list.
Future Claude work should start from any new review findings or release/commit/PR tasks.
```

## Repeat Confirmation

Final confirmation:

```text
1. Lord of Night epoch correction from G1 to G9 is complete.
2. Strict proleptic Gregorian date validation is complete.
3. Fixtures, output spec, tests, and README files are updated.
4. Package/repo names remain unchanged.
5. Documentation scope is Classic Maya calendar / 古典瑪雅曆.
6. Verified Maya reference links are included as text hyperlinks.
7. thirteen_moon / Dreamspell remains out of this implementation.
8. Cross-language constant/API naming is normalized to reduce confusion when switching implementations.
```
