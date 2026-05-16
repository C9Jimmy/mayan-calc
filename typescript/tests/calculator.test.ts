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
    expect(result.lordOfNight).toBe('G1')
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
    expect(result.lordOfNight).toBe('G6')
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
    expect(result.lordOfNight).toBe('G5')
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
    expect(result.lordOfNight).toBe('G7')
  })
})
