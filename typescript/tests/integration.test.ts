/**
 * Integration tests for calculate() — full Gregorian date → MayanChart pipeline.
 */
import { describe, expect, test } from 'vitest'
import { calculate } from '../src/calculator'

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

  test('Lord of Night G5', () => {
    expect(result.lordOfNight).toBe('G5')
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

  test('Lord of Night G1', () => {
    expect(result.lordOfNight).toBe('G1')
  })
})

describe('calculate — 2012-12-21 (Maya calendar turnover)', () => {
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
