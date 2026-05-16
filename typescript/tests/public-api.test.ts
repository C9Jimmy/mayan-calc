import { describe, expect, test } from 'vitest'
import * as api from '../src'

describe('public API', () => {
  test('does not expose calculator helper functions from the barrel', () => {
    expect(api).not.toHaveProperty('dateToJdn')
    expect(api).not.toHaveProperty('jdnToTzolkin')
    expect(api).not.toHaveProperty('jdnToHaab')
    expect(api).not.toHaveProperty('jdnToLongCount')
    expect(api).not.toHaveProperty('jdnToLordOfNight')
  })

  test('exposes calculate and canonical name constants', () => {
    expect(api).toHaveProperty('calculate')
    expect(api).toHaveProperty('TZOLKIN_DAY_SIGNS')
    expect(api).toHaveProperty('HAAB_MONTHS')
  })
})
