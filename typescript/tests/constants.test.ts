import { describe, expect, test } from 'vitest'
import { GMT_CORRELATION, HAAB_MONTHS, TZOLKIN_DAY_SIGNS } from '../src/constants'

describe('TZOLKIN_DAY_SIGNS', () => {
  test('has 20 signs', () => {
    expect(TZOLKIN_DAY_SIGNS).toHaveLength(20)
  })

  test('starts with Imix', () => {
    expect(TZOLKIN_DAY_SIGNS[0]).toBe('Imix')
  })

  test('ends with Ajaw', () => {
    expect(TZOLKIN_DAY_SIGNS[19]).toBe('Ajaw')
  })

  test('contains Chikchan at index 4', () => {
    expect(TZOLKIN_DAY_SIGNS[4]).toBe('Chikchan')
  })
})

describe('HAAB_MONTHS', () => {
  test('has 19 months (18 regular + Wayeb)', () => {
    expect(HAAB_MONTHS).toHaveLength(19)
  })

  test('ends with Wayeb', () => {
    expect(HAAB_MONTHS[18]).toBe('Wayeb')
  })

  test("second-to-last is Kumk'u", () => {
    expect(HAAB_MONTHS[17]).toBe("Kumk'u")
  })

  test('contains Mak', () => {
    expect(HAAB_MONTHS).toContain('Mak')
  })
})

describe('GMT_CORRELATION', () => {
  test('equals 584283 (Goodman-Martinez-Thompson consensus)', () => {
    expect(GMT_CORRELATION).toBe(584283)
  })
})
