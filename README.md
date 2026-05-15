# mayan-calc

Maya calendar calculation library: Tzolk'in, Haab, Long Count, and Lord of Night.
Five native language implementations, zero external dependencies, pure arithmetic.

[![CI](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml/badge.svg)](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Ko-fi](https://img.shields.io/badge/Ko--fi-support-ff5e5b?logo=ko-fi&logoColor=white)](https://ko-fi.com/c9jimmy)

---

## Language Support

| Language   | Install / Dependency                              | Tests |
|------------|---------------------------------------------------|-------|
| Python     | `pip install mayan-calc`                          | 31 ✅  |
| TypeScript | `npm install mayan-calc`                          | 52 ✅  |
| Java       | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`    | 41 ✅  |
| C#         | NuGet: `MayanCalc`                                | 43 ✅  |
| Kotlin     | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`    | 55 ✅  |

---

## Quick Start

**Python**
```python
from mayan_calc import calculate

result = calculate(2012, 12, 21)
print(result.long_count)               # "13.0.0.0.0"
print(result.tzolkin.coefficient)      # 4
print(result.tzolkin.name)             # "Ajaw"
print(result.haab.day)                 # 3
print(result.haab.month_name)          # "K'ank'in"
print(result.lord_of_night)            # "G1"
```

**TypeScript**
```typescript
import { calculate } from 'mayan-calc'

const result = calculate(2012, 12, 21)
console.log(result.longCount)                // "13.0.0.0.0"
console.log(result.tzolkin.coefficient)      // 4
console.log(result.tzolkin.name)             // "Ajaw"
console.log(result.lordOfNight)              // "G1"
```

**Java**
```java
MayanDate result = Calculator.calculate(2012, 12, 21);
System.out.println(result.longCount());             // "13.0.0.0.0"
System.out.println(result.tzolkin().coefficient()); // 4
System.out.println(result.lordOfNight());           // "G1"
```

**C#**
```csharp
var result = Calculator.Calculate(2012, 12, 21);
Console.WriteLine(result.LongCount);              // "13.0.0.0.0"
Console.WriteLine(result.Tzolkin.Coefficient);    // 4
Console.WriteLine(result.LordOfNight);            // "G1"
```

**Kotlin**
```kotlin
val result = Calculator.calculate(2012, 12, 21)
println(result.longCount)                // "13.0.0.0.0"
println(result.tzolkin.coefficient)      // 4
println(result.lordOfNight)              // "G1"
```

---

## Output Format

| Field | Type | Description |
|-------|------|-------------|
| `tzolkin.coefficient` | int (1–13) | Tzolk'in day coefficient |
| `tzolkin.name` | string | Tzolk'in day name (e.g. `"Ajaw"`) |
| `haab.day` | int (0–19) | Haab day number |
| `haab.month_name` | string | Haab month name (e.g. `"K'ank'in"`) |
| `long_count` | string | Long Count notation (e.g. `"13.0.0.0.0"`) |
| `lord_of_night` | string | Lord of Night cycle (e.g. `"G1"`) |

Full field specification (including per-language accessor names): [`fixtures/output_spec.json`](fixtures/output_spec.json)

---

## Ground Truth

Verified against GMT correlation constant 584283:

| Date       | Tzolk'in  | Haab         | Long Count    | Lord |
|------------|-----------|--------------|---------------|------|
| 2012-12-21 | 4 Ajaw    | 3 K'ank'in   | 13.0.0.0.0    | G1   |
| 2000-01-01 | 11 Ik'    | 10 K'ank'in  | 12.19.6.15.2  | G6   |
| 1988-12-07 | 12 Ajaw   | 3 Mak        | 12.18.15.11.0 | G5   |
| 2026-05-15 | 9 Ben     | 6 Sip        | 13.0.13.10.13 | G7   |

---

## Accuracy

Calculation is based on pure integer arithmetic using the GMT correlation constant (584283). Long Count, Tzolk'in, Haab, and Lord of Night contain no floating-point error.

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
  title   = {mayan-calc: Maya Calendar Calculation Library},
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
