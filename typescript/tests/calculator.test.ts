/**
 * Unit tests for individual calculator functions.
 * Anchor dates (Python-verified):
 *   Creation date (-3113/8/11): JDN=584283, 0.0.0.0.0, 4 Ajaw, 8 Kumk'u, G1
 *   1988-12-07:                 JDN=2447503, 12.18.15.11.0, 12 Ajaw, 3 Mak, G5
 *   2000-01-01:                 JDN=2451545, 12.19.6.15.2, 11 Ik', 10 K'ank'in, G6
 *   2012-12-21:                 JDN=2456283, 13.0.0.0.0, 4 Ajaw, 3 K'ank'in, G1
 */
import { describe, expect, test } from 'vitest'
import {
  dateToJdn,
  jdnToHaab,
  jdnToLongCount,
  jdnToLordOfNight,
  jdnToTzolkin,
} from '../src/calculator'

const CREATION_JDN = 584283
const FRANKIE_JDN = 2447503  // 1988-12-07

// ── dateToJdn ────────────────────────────────────────────────────────────────

describe('dateToJdn', () => {
  test('1988-12-07 → 2447503', () => {
    expect(dateToJdn(1988, 12, 7)).toBe(2447503)
  })

  test('2000-01-01 → 2451545 (J2000.0 epoch)', () => {
    expect(dateToJdn(2000, 1, 1)).toBe(2451545)
  })

  test('2012-12-21 → 2456283 (Maya 13.0.0.0.0)', () => {
    expect(dateToJdn(2012, 12, 21)).toBe(2456283)
  })

  test('2000-02-28: month ≤ 2 branch', () => {
    expect(dateToJdn(2000, 2, 28)).toBe(2451603)
  })

  test('2000-02-29: leap day', () => {
    expect(dateToJdn(2000, 2, 29)).toBe(2451604)
  })

  test('creation date -3113/8/11 → 584283 (GMT anchor)', () => {
    expect(dateToJdn(-3113, 8, 11)).toBe(584283)
  })
})

// ── jdnToLongCount ───────────────────────────────────────────────────────────

describe('jdnToLongCount', () => {
  test('creation date → 0.0.0.0.0', () => {
    const lc = jdnToLongCount(CREATION_JDN)
    expect(lc.baktun).toBe(0)
    expect(lc.katun).toBe(0)
    expect(lc.tun).toBe(0)
    expect(lc.uinal).toBe(0)
    expect(lc.kin).toBe(0)
    expect(lc.display).toBe('0.0.0.0.0')
  })

  test('1988-12-07 → 12.18.15.11.0', () => {
    const lc = jdnToLongCount(FRANKIE_JDN)
    expect(lc.baktun).toBe(12)
    expect(lc.katun).toBe(18)
    expect(lc.tun).toBe(15)
    expect(lc.uinal).toBe(11)
    expect(lc.kin).toBe(0)
    expect(lc.display).toBe('12.18.15.11.0')
  })

  test('2012-12-21 → 13.0.0.0.0', () => {
    expect(jdnToLongCount(2456283).display).toBe('13.0.0.0.0')
  })

  test('2000-01-01 → 12.19.6.15.2', () => {
    expect(jdnToLongCount(2451545).display).toBe('12.19.6.15.2')
  })

  test('display has exactly 5 dot-separated parts', () => {
    const parts = jdnToLongCount(FRANKIE_JDN).display.split('.')
    expect(parts).toHaveLength(5)
    expect(parts.every(p => /^\d+$/.test(p))).toBe(true)
  })
})

// ── jdnToTzolkin ─────────────────────────────────────────────────────────────

describe('jdnToTzolkin', () => {
  test('creation date → 4 Ajaw (archaeological standard)', () => {
    const tz = jdnToTzolkin(CREATION_JDN)
    expect(tz.coefficient).toBe(4)
    expect(tz.name).toBe('Ajaw')
    expect(tz.daySignNumber).toBe(20)
  })

  test('1988-12-07 → 12 Ajaw', () => {
    const tz = jdnToTzolkin(FRANKIE_JDN)
    expect(tz.coefficient).toBe(12)
    expect(tz.name).toBe('Ajaw')
    expect(tz.daySignNumber).toBe(20)
  })

  test("2000-01-01 → 11 Ik'", () => {
    const tz = jdnToTzolkin(2451545)
    expect(tz.coefficient).toBe(11)
    expect(tz.name).toBe("Ik'")
  })

  test('260-day cycle is idempotent', () => {
    const base = jdnToTzolkin(FRANKIE_JDN)
    const cycled = jdnToTzolkin(FRANKIE_JDN + 260)
    expect(cycled.coefficient).toBe(base.coefficient)
    expect(cycled.name).toBe(base.name)
  })

  test('coefficient is always 1–13 over a full 260-day cycle', () => {
    for (let offset = 0; offset < 260; offset++) {
      const { coefficient } = jdnToTzolkin(FRANKIE_JDN + offset)
      expect(coefficient).toBeGreaterThanOrEqual(1)
      expect(coefficient).toBeLessThanOrEqual(13)
    }
  })

  test('daySignNumber is always 1–20 over a full 260-day cycle', () => {
    for (let offset = 0; offset < 260; offset++) {
      const { daySignNumber } = jdnToTzolkin(FRANKIE_JDN + offset)
      expect(daySignNumber).toBeGreaterThanOrEqual(1)
      expect(daySignNumber).toBeLessThanOrEqual(20)
    }
  })
})

// ── jdnToHaab ────────────────────────────────────────────────────────────────

describe('jdnToHaab', () => {
  test("creation date → 8 Kumk'u (archaeological standard)", () => {
    const h = jdnToHaab(CREATION_JDN)
    expect(h.monthName).toBe("Kumk'u")
    expect(h.day).toBe(8)
  })

  test('1988-12-07 → 3 Mak', () => {
    const h = jdnToHaab(FRANKIE_JDN)
    expect(h.monthName).toBe('Mak')
    expect(h.day).toBe(3)
  })

  test("2000-01-01 → 10 K'ank'in", () => {
    const h = jdnToHaab(2451545)
    expect(h.monthName).toBe("K'ank'in")
    expect(h.day).toBe(10)
  })

  test('365-day cycle is idempotent', () => {
    const base = jdnToHaab(FRANKIE_JDN)
    const cycled = jdnToHaab(FRANKIE_JDN + 365)
    expect(cycled.monthName).toBe(base.monthName)
    expect(cycled.day).toBe(base.day)
  })

  test('day is always 0–19 over a full 365-day cycle', () => {
    for (let offset = 0; offset < 365; offset++) {
      const { day } = jdnToHaab(FRANKIE_JDN + offset)
      expect(day).toBeGreaterThanOrEqual(0)
      expect(day).toBeLessThanOrEqual(19)
    }
  })
})

// ── jdnToLordOfNight ─────────────────────────────────────────────────────────

describe('jdnToLordOfNight', () => {
  test('creation date → G1', () => {
    expect(jdnToLordOfNight(CREATION_JDN)).toBe('G1')
  })

  test('1988-12-07 → G5', () => {
    expect(jdnToLordOfNight(FRANKIE_JDN)).toBe('G5')
  })

  test('2012-12-21 → G1', () => {
    expect(jdnToLordOfNight(2456283)).toBe('G1')
  })

  test('9-day cycle returns to G1', () => {
    for (let i = 0; i < 9; i++) {
      expect(jdnToLordOfNight(CREATION_JDN + i)).toBe(`G${i + 1}`)
    }
  })

  test('value is always G1–G9', () => {
    const valid = new Set(Array.from({ length: 9 }, (_, i) => `G${i + 1}`))
    for (let offset = 0; offset < 9; offset++) {
      expect(valid.has(jdnToLordOfNight(FRANKIE_JDN + offset))).toBe(true)
    }
  })
})
