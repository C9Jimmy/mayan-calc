import { describe, expect, test } from 'vitest'
import { calculate } from '../src'

describe('calculate', () => {
  test('2012-12-21 returns canonical Maya date', () => {
    const result = calculate(2012, 12, 21)

    expect(result.tzolkin).toEqual({
      coefficient: 4,
      name: 'Ajaw',
      daySignNumber: 20,
    })
    expect(result.haab).toEqual({
      day: 3,
      monthName: "K'ank'in",
    })
    expect(result.longCount).toEqual({
      baktun: 13,
      katun: 0,
      tun: 0,
      uinal: 0,
      kin: 0,
      display: '13.0.0.0.0',
    })
    expect(result.lordOfNight).toBe('G9')
  })

  test('2000-01-01 returns canonical Maya date', () => {
    const result = calculate(2000, 1, 1)

    expect(result.tzolkin).toEqual({
      coefficient: 11,
      name: "Ik'",
      daySignNumber: 2,
    })
    expect(result.haab).toEqual({
      day: 10,
      monthName: "K'ank'in",
    })
    expect(result.longCount.display).toBe('12.19.6.15.2')
    expect(result.lordOfNight).toBe('G5')
  })

  test('1988-12-07 returns canonical Maya date', () => {
    const result = calculate(1988, 12, 7)

    expect(result.tzolkin).toEqual({
      coefficient: 12,
      name: 'Ajaw',
      daySignNumber: 20,
    })
    expect(result.haab).toEqual({
      day: 3,
      monthName: 'Mak',
    })
    expect(result.longCount.display).toBe('12.18.15.11.0')
    expect(result.lordOfNight).toBe('G4')
  })

  test('2026-05-15 returns canonical Maya date', () => {
    const result = calculate(2026, 5, 15)

    expect(result.tzolkin).toEqual({
      coefficient: 9,
      name: 'Ben',
      daySignNumber: 13,
    })
    expect(result.haab).toEqual({
      day: 6,
      monthName: 'Sip',
    })
    expect(result.longCount.display).toBe('13.0.13.10.13')
    expect(result.lordOfNight).toBe('G6')
  })

  test('accepts valid leap day', () => {
    expect(() => calculate(2024, 2, 29)).not.toThrow()
  })

  test('rejects invalid Gregorian dates', () => {
    const invalidDates = [
      [2023, 2, 29],
      [2024, 13, 1],
      [2024, 0, 10],
      [2024, 4, 31],
      [2024, 1, 0],
    ] as const

    for (const [year, month, day] of invalidDates) {
      expect(() => calculate(year, month, day)).toThrow(RangeError)
    }
  })
})
