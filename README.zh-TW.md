# mayan-calc

古典瑪雅曆計算函式庫：卓爾金曆（Tzolk'in）、哈布曆（Haab）、長計曆（Long Count）與夜神（Lord of Night）。
五種原生語言實作，零外部依賴，純整數算術。

[![CI](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml/badge.svg)](https://github.com/C9Jimmy/mayan-calc/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 語言支援

| 語言       | 安裝 / 依賴                                           | 測試數 |
|------------|-------------------------------------------------------|--------|
| Python     | `pip install mayan-calc`                              | 38 ✅  |
| TypeScript | `npm install mayan-calc`                              | 39 ✅  |
| Java       | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`        | 43 ✅  |
| C#         | NuGet: `MayanCalc`                                    | 49 ✅  |
| Kotlin     | JitPack: `io.github.c9jimmy:mayan-calc:0.1.0`        | 66 ✅  |

---

## 快速開始

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

## 輸出欄位

| 欄位 | 型別 | 說明 |
|------|------|------|
| `tzolkin.coefficient` | int (1–13) | 卓爾金曆日係數 |
| `tzolkin.name` | string | 卓爾金曆日名（如 `"Ajaw"`）|
| `tzolkin.day_sign_number` | int (1–20) | 日名在 20 個符號序列中的位置（如 Ajaw = `20`）|
| `haab.day` | int (0–19) | 哈布曆日數 |
| `haab.month_name` | string | 哈布曆月名（如 `"K'ank'in"`）|
| `long_count` | object with `display` | 長計曆組成欄位與記法（如 `display = "13.0.0.0.0"`）|
| `lord_of_night` | string | 夜神循環（如 `"G9"`）|

欄位名稱依循 Python 慣例（`snake_case`）。TypeScript / Kotlin 使用 `camelCase`（`longCount`、`lordOfNight`）；Java 使用 getter（`result.longCount()`）；C# 使用 `PascalCase`（`result.LongCount`）。

完整欄位規格（含各語言存取器名稱）：[`fixtures/output_spec.json`](fixtures/output_spec.json)

---

## 驗算基準

以 GMT 常數 584283 驗算：

| 日期       | Tzolk'in  | Haab         | Long Count    | Lord of Night |
|------------|-----------|--------------|---------------|---------------|
| 2012-12-21 | 4 Ajaw    | 3 K'ank'in   | 13.0.0.0.0    | G9   |
| 2000-01-01 | 11 Ik'    | 10 K'ank'in  | 12.19.6.15.2  | G5   |
| 1988-12-07 | 12 Ajaw   | 3 Mak        | 12.18.15.11.0 | G4   |
| 2026-05-15 | 9 Ben     | 6 Sip        | 13.0.13.10.13 | G6   |

---

## 精確度

計算完全基於整數算術，使用 GMT 常數（584283）。長計曆、卓爾金曆、哈布曆與夜神循環均不含浮點誤差。

---

## 日期輸入

輸入日期使用前推公曆。西元前年份採天文年份編號：`year 0` 代表西元前 1 年，
`year -3113` 代表西元前 3114 年。

無效公曆日期會在進入曆法計算前拋出各語言慣用的範圍錯誤。

---

## 參考資料

- [Smithsonian Maya Calendar Converter](https://maya.nmai.si.edu/calendar/maya-calendar-converter)
- [Maya Numeration, Computation, and Calendrical Astronomy](https://www.encyclopedia.com/science/dictionaries-thesauruses-pictures-and-press-releases/maya-numeration-computation-and-calendrical-astronomy)
- [How to Build a Calendar](https://www.polysyllabic.com/?q=buildacal)
- [Python Vuh: Mayan Calendrical Mathematics with Python](https://legacy.python.org/workshops/1998-11/proceedings/papers/laningham/laningham.pdf)

以上資料支援古典瑪雅曆錨點 `0.0.0.0.0 = 4 Ajaw 8 Kumk'u G9`
與 `13.0.0.0.0 = 4 Ajaw 3 K'ank'in G9`。

---

## 問題回報與貢獻

發現計算錯誤或想新增語言版本？

- **回報問題**：開一個 [GitHub Issue](https://github.com/C9Jimmy/mayan-calc/issues)，請附上輸入日期、實際結果與預期結果
- **新增語言 port**：先開 Issue 討論，再提交 PR（需附 ≥ 30 個測試）
- **文件改善**：歡迎 PR

---

## 引用

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

## 支持專案

如果這個函式庫讓你省去了凌晨自己實作卓爾金曆算術的麻煩，歡迎請我喝杯茶。🍵

[![Ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/c9jimmy)

---

## 授權

[MIT](LICENSE) © 2026 C9Jimmy (https://github.com/C9Jimmy)
