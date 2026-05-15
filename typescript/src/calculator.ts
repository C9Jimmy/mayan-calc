import { GMT_CORRELATION, HAAB_MONTHS, TZOLKIN_DAY_SIGNS } from './constants'
import { HaabDate, LongCount, MayanChart, TzolkinDate } from './models'

/** Gregorian date → Julian Day Number (Meeus algorithm). */
export function dateToJdn(year: number, month: number, day: number): number {
  const [y, m] = month <= 2 ? [year - 1, month + 12] : [year, month]
  const a = Math.floor(y / 100)
  const b = 2 - a + Math.floor(a / 4)
  return (
    Math.floor(365.25 * (y + 4716)) +
    Math.floor(30.6001 * (m + 1)) +
    day + b - 1524
  )
}

/**
 * JDN → Tzolk'in date.
 *
 * Offsets align with archaeological consensus: creation date 0.0.0.0.0 = 4 Ajaw.
 * +3 on number: total=0 → 4 (not 1).
 * +19 on sign:  total=0 → Ajaw at index 19 (not Imix at index 0).
 * Double-modulo guards against pre-epoch dates where total < 0.
 */
export function jdnToTzolkin(jdn: number): TzolkinDate {
  const total = jdn - GMT_CORRELATION
  const number = (((total + 3) % 13) + 13) % 13 + 1
  const signIdx = (((total + 19) % 20) + 20) % 20
  return {
    number,
    daySign: TZOLKIN_DAY_SIGNS[signIdx],
    daySignNumber: signIdx + 1,
  }
}

/**
 * JDN → Haab date.
 * +348 aligns with creation date 8 Kumk'u: position 17×20+8 = 348.
 */
export function jdnToHaab(jdn: number): HaabDate {
  const total = jdn - GMT_CORRELATION
  const haabPos = (((total + 348) % 365) + 365) % 365
  const monthIdx = Math.floor(haabPos / 20)
  return {
    month: HAAB_MONTHS[monthIdx],
    day: haabPos % 20,
  }
}

/** JDN → Long Count (baktun.katun.tun.uinal.kin). */
export function jdnToLongCount(jdn: number): LongCount {
  const total = jdn - GMT_CORRELATION
  const baktun = Math.floor(total / 144000)
  const katun = Math.floor((total % 144000) / 7200)
  const tun = Math.floor((total % 7200) / 360)
  const uinal = Math.floor((total % 360) / 20)
  const kin = total % 20
  return {
    baktun,
    katun,
    tun,
    uinal,
    kin,
    display: `${baktun}.${katun}.${tun}.${uinal}.${kin}`,
  }
}

/** JDN → Lord of Night G1–G9 (9-day cycle). */
export function jdnToLordOfNight(jdn: number): number {
  const total = jdn - GMT_CORRELATION
  return ((total % 9) + 9) % 9 + 1
}

/** Gregorian date → complete Maya calendar output. */
export function calculate(year: number, month: number, day: number): MayanChart {
  const jdn = dateToJdn(year, month, day)
  return {
    tzolkin: jdnToTzolkin(jdn),
    haab: jdnToHaab(jdn),
    longCount: jdnToLongCount(jdn),
    lordOfNight: jdnToLordOfNight(jdn),
  }
}
