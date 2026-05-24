# mayan-calc

Classic Maya calendar calculation library: Tzolk'in, Haab, Long Count, and Lord of Night.
Five native language implementations, zero external dependencies, pure arithmetic.

[![CI](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml/badge.svg)](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## Language Support

| Language   | Install / Dependency                              | Tests |
|------------|---------------------------------------------------|-------|
| Python     | `pip install mayan-calc`                          | 38 ✅  |
| TypeScript | `npm install mayan-calc`                          | 39 ✅  |
| Java       | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`    | 43 ✅  |
| C#         | NuGet: `MayanCalc`                                | 49 ✅  |
| Kotlin     | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`    | 66 ✅  |

---

## Quick Start

**Python**
```python
from mayan_calc import calculate

result = calculate(2012, 12, 21)
print(result.long_count.display)       # "13.0.0.0.0"
print(result.tzolkin.coefficient)      # 4
print(result.tzolkin.name)             # "Ajaw"
print(result.haab.day)                 # 3
print(result.haab.month_name)          # "K'ank'in"
print(result.lord_of_night)            # "G9"
```

**TypeScript**
```typescript
import { calculate } from 'mayan-calc'

const result = calculate(2012, 12, 21)
console.log(result.longCount.display)        // "13.0.0.0.0"
console.log(result.tzolkin.coefficient)      // 4
console.log(result.tzolkin.name)             // "Ajaw"
console.log(result.lordOfNight)              // "G9"
```

**Java**
```java
MayanDate result = Calculator.calculate(2012, 12, 21);
System.out.println(result.longCount().display());   // "13.0.0.0.0"
System.out.println(result.tzolkin().coefficient()); // 4
System.out.println(result.lordOfNight());           // "G9"
```

**C#**
```csharp
var result = Calculator.Calculate(2012, 12, 21);
Console.WriteLine(result.LongCount.Display);      // "13.0.0.0.0"
Console.WriteLine(result.Tzolkin.Coefficient);    // 4
Console.WriteLine(result.LordOfNight);            // "G9"
```

**Kotlin**
```kotlin
val result = Calculator.calculate(2012, 12, 21)
println(result.longCount.display)        // "13.0.0.0.0"
println(result.tzolkin.coefficient)      // 4
println(result.lordOfNight)              // "G9"
```

---

## Output Format

| Field | Type | Description |
|-------|------|-------------|
| `tzolkin.coefficient` | int (1–13) | Tzolk'in day coefficient |
| `tzolkin.name` | string | Tzolk'in day name (e.g. `"Ajaw"`) |
| `tzolkin.day_sign_number` | int (1–20) | Position in the 20-sign cycle (e.g. `20` for Ajaw) |
| `haab.day` | int (0–19) | Haab day number |
| `haab.month_name` | string | Haab month name (e.g. `"K'ank'in"`) |
| `long_count` | object with `display` | Long Count components and notation (e.g. `display = "13.0.0.0.0"`) |
| `lord_of_night` | string | Lord of Night cycle (e.g. `"G9"`) |

Field names above follow Python conventions (`snake_case`). TypeScript / Kotlin use `camelCase` (`longCount`, `lordOfNight`); Java uses getters (`result.longCount()`); C# uses `PascalCase` (`result.LongCount`).

Full field specification (including per-language accessor names): [`fixtures/output_spec.json`](fixtures/output_spec.json)

---

## Ground Truth

Verified against GMT correlation constant 584283:

| Date       | Tzolk'in  | Haab         | Long Count    | Lord of Night |
|------------|-----------|--------------|---------------|---------------|
| 2012-12-21 | 4 Ajaw    | 3 K'ank'in   | 13.0.0.0.0    | G9   |
| 2000-01-01 | 11 Ik'    | 10 K'ank'in  | 12.19.6.15.2  | G5   |
| 1988-12-07 | 12 Ajaw   | 3 Mak        | 12.18.15.11.0 | G4   |
| 2026-05-15 | 9 Ben     | 6 Sip        | 13.0.13.10.13 | G6   |

---

## Accuracy

Calculation is based on pure integer arithmetic using the GMT correlation constant (584283). Long Count, Tzolk'in, Haab, and Lord of Night contain no floating-point error.

---

## Date Input

Input dates use the proleptic Gregorian calendar. BCE years use astronomical
year numbering: `year 0` is 1 BCE, and `year -3113` is 3114 BCE.

Invalid Gregorian dates throw the language-native range exception before any
calendar arithmetic is performed.

---

## References

### GMT Correlation Constant

The GMT constant (584283) establishes the correspondence between Maya Long Count
and Julian Day Number. Archaeological consensus from three independent researchers:

- **Goodman, J. T.** (1905). *Maya Dates.*
- **Martínez Hernández, J.** (1926). *Diccionario de Motul.*
- **Thompson, J. E. S.** (1927). *A Correlation of the Mayan and European Calendars.*

Public domain knowledge; the constant itself is a mathematical fact.

### Calendar Arithmetic

- **Meeus, J.** (1998). *Astronomical Algorithms* (2nd ed.). Willmann-Bell, Inc.  
  Julian Day Number conversion algorithm foundation.

### Maya Verification Sources

- [Smithsonian Maya Calendar Converter](https://maya.nmai.si.edu/calendar/maya-calendar-converter)
- [Maya Numeration, Computation, and Calendrical Astronomy](https://www.encyclopedia.com/science/dictionaries-thesauruses-pictures-and-press-releases/maya-numeration-computation-and-calendrical-astronomy)
- [How to Build a Calendar](https://www.polysyllabic.com/?q=buildacal)
- [Python Vuh: Mayan Calendrical Mathematics with Python](https://legacy.python.org/workshops/1998-11/proceedings/papers/laningham/laningham.pdf)

These sources support the Classic Maya anchors `0.0.0.0.0 = 4 Ajaw 8 Kumk'u G9`
and `13.0.0.0.0 = 4 Ajaw 3 K'ank'in G9`.

---

## Issues & Contributing

Found a calculation error or want to add a new language port?

- **Bug report**: Open a [GitHub Issue](https://github.com/C9Jimmy/mayan-calc/issues) with input date, actual result, and expected result
- **New language port**: Open an Issue to discuss first, then submit a PR with ≥ 30 tests
- **Documentation**: PRs welcome

---

## Citation

```bibtex
@software{mayan-calc,
  author  = {C9Jimmy},
  title   = {mayan-calc: Classic Maya Calendar Calculation Library},
  year    = {2026},
  url     = {https://github.com/C9Jimmy/mayan-calc},
  license = {MIT}
}
```

---

## Support

If this library saved you from implementing your own Tzolk'in calendar
arithmetic at 2am, consider buying me a tea. 🍵

[![Ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/c9jimmy)

---

## License

[MIT](LICENSE) © 2026 C9Jimmy (https://github.com/C9Jimmy)
