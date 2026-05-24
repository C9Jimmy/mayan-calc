import { describe, expect, test } from 'vitest'
import { readFileSync } from 'node:fs'
import { calculate } from '../src/calculator'

interface GroundTruthCase {
  readonly input: { readonly year: number; readonly month: number; readonly day: number };
  readonly expected: {
    readonly tzolkin: { readonly coefficient: number; readonly name: string; readonly day_sign_number: number };
    readonly haab: { readonly day: number; readonly month_name: string };
    readonly long_count: {
      readonly baktun: number;
      readonly katun: number;
      readonly tun: number;
      readonly uinal: number;
      readonly kin: number;
      readonly display: string;
    };
    readonly lord_of_night: string;
  };
}

const groundTruth = JSON.parse(
  readFileSync(new URL('../../fixtures/ground_truth.json', import.meta.url), 'utf8')
) as readonly GroundTruthCase[]

describe('calculate — ground truth fixture', () => {
  test.each(groundTruth)('$input.year-$input.month-$input.day matches canonical output', ({ input, expected }) => {
    const result = calculate(input.year, input.month, input.day)

    expect(result.tzolkin).toEqual({
      coefficient: expected.tzolkin.coefficient,
      name: expected.tzolkin.name,
      daySignNumber: expected.tzolkin.day_sign_number,
    })
    expect(result.haab).toEqual({
      day: expected.haab.day,
      monthName: expected.haab.month_name,
    })
    expect(result.longCount).toEqual({
      baktun: expected.long_count.baktun,
      katun: expected.long_count.katun,
      tun: expected.long_count.tun,
      uinal: expected.long_count.uinal,
      kin: expected.long_count.kin,
      display: expected.long_count.display,
    })
    expect(result.lordOfNight).toBe(expected.lord_of_night)
  })
})

describe('calculate — 1988-12-07 (Frankie Fang)', () => {
  const result = calculate(1988, 12, 7)

  test('Long Count 12.18.15.11.0', () => {
    expect(result.longCount.display).toBe('12.18.15.11.0')
  })

  test('Tzolk\'in 12 Ajaw', () => {
    expect(result.tzolkin.coefficient).toBe(12)
    expect(result.tzolkin.name).toBe('Ajaw')
    expect(result.tzolkin.daySignNumber).toBe(20)
  })

  test('Haab 3 Mak', () => {
    expect(result.haab.monthName).toBe('Mak')
    expect(result.haab.day).toBe(3)
  })

  test('Lord of Night G4', () => {
    expect(result.lordOfNight).toBe('G4')
  })
})

describe('calculate — creation date (-3113/8/11)', () => {
  const result = calculate(-3113, 8, 11)

  test('Long Count 0.0.0.0.0', () => {
    expect(result.longCount.display).toBe('0.0.0.0.0')
    expect(result.longCount.baktun).toBe(0)
  })

  test('Tzolk\'in 4 Ajaw', () => {
    expect(result.tzolkin.coefficient).toBe(4)
    expect(result.tzolkin.name).toBe('Ajaw')
  })

  test("Haab 8 Kumk'u", () => {
    expect(result.haab.monthName).toBe("Kumk'u")
    expect(result.haab.day).toBe(8)
  })

  test('Lord of Night G9', () => {
    expect(result.lordOfNight).toBe('G9')
  })
})

describe('calculate — 2012-12-21 (Classic Maya calendar turnover)', () => {
  const result = calculate(2012, 12, 21)

  test('Long Count 13.0.0.0.0', () => {
    expect(result.longCount.display).toBe('13.0.0.0.0')
    expect(result.longCount.baktun).toBe(13)
    expect(result.longCount.katun).toBe(0)
    expect(result.longCount.kin).toBe(0)
  })

  test('Tzolk\'in 4 Ajaw', () => {
    expect(result.tzolkin.coefficient).toBe(4)
    expect(result.tzolkin.name).toBe('Ajaw')
  })

  test("Haab 3 K'ank'in", () => {
    expect(result.haab.monthName).toBe("K'ank'in")
    expect(result.haab.day).toBe(3)
  })
})

describe('calculate — 2000-01-01', () => {
  const result = calculate(2000, 1, 1)

  test('Long Count 12.19.6.15.2', () => {
    expect(result.longCount.display).toBe('12.19.6.15.2')
  })

  test("Tzolk'in 11 Ik'", () => {
    expect(result.tzolkin.coefficient).toBe(11)
    expect(result.tzolkin.name).toBe("Ik'")
  })

  test("Haab 10 K'ank'in", () => {
    expect(result.haab.monthName).toBe("K'ank'in")
    expect(result.haab.day).toBe(10)
  })

  test('Lord of Night G5', () => {
    expect(result.lordOfNight).toBe('G5')
  })
})

describe('calculate — 2026-05-15', () => {
  const result = calculate(2026, 5, 15)

  test('Long Count 13.0.13.10.13', () => {
    expect(result.longCount.display).toBe('13.0.13.10.13')
    expect(result.longCount.baktun).toBe(13)
    expect(result.longCount.katun).toBe(0)
    expect(result.longCount.tun).toBe(13)
    expect(result.longCount.uinal).toBe(10)
    expect(result.longCount.kin).toBe(13)
  })

  test("Tzolk'in 9 Ben", () => {
    expect(result.tzolkin.coefficient).toBe(9)
    expect(result.tzolkin.name).toBe('Ben')
    expect(result.tzolkin.daySignNumber).toBe(13)
  })

  test('Haab 6 Sip', () => {
    expect(result.haab.monthName).toBe('Sip')
    expect(result.haab.day).toBe(6)
  })

  test('Lord of Night G6', () => {
    expect(result.lordOfNight).toBe('G6')
  })
})

describe('calculate — output shape', () => {
  test('all required fields are present', () => {
    const result = calculate(1988, 12, 7)
    expect(result).toHaveProperty('tzolkin')
    expect(result).toHaveProperty('haab')
    expect(result).toHaveProperty('longCount')
    expect(result).toHaveProperty('lordOfNight')
    expect(result.tzolkin).toHaveProperty('coefficient')
    expect(result.tzolkin).toHaveProperty('name')
    expect(result.tzolkin).toHaveProperty('daySignNumber')
    expect(result.longCount).toHaveProperty('display')
  })
})
