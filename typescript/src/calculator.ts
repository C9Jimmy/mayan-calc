import {
  GMT_CORRELATION,
  HAAB_CORRELATION_OFFSET,
  HAAB_CYCLE,
  HAAB_DAYS_PER_MONTH,
  HAAB_MONTHS,
  LC_BAKTUN,
  LC_KATUN,
  LC_TUN,
  LC_UINAL,
  LORD_OF_NIGHT_CYCLE,
  LORD_OF_NIGHT_ORIGIN,
  MEEUS_EPOCH_A,
  MEEUS_EPOCH_B,
  MEEUS_MONTH_FACTOR,
  MEEUS_YEAR_FACTOR,
  TZOLKIN_COEFF_COUNT,
  TZOLKIN_COEFF_ORIGIN,
  TZOLKIN_DAY_SIGNS,
  TZOLKIN_NAME_ORIGIN_INDEX,
  TZOLKIN_SIGN_COUNT,
} from './constants'
import { HaabDate, LongCount, MayanChart, TzolkinDate } from './models'

/** Gregorian date → Julian Day Number (Meeus algorithm). */
function dateToJdn(year: number, month: number, day: number): number {
  const [y, m] = month <= 2 ? [year - 1, month + 12] : [year, month]
  const a = Math.floor(y / 100)
  const b = 2 - a + Math.floor(a / 4)
  return (
    Math.floor(MEEUS_YEAR_FACTOR * (y + MEEUS_EPOCH_A)) +
    Math.floor(MEEUS_MONTH_FACTOR * (m + 1)) +
    day + b - MEEUS_EPOCH_B
  )
}

/**
 * JDN → Tzolk'in date.
 *
 * Offsets align with archaeological consensus: creation date 0.0.0.0.0 = 4 Ajaw.
 * TZOLKIN_COEFF_ORIGIN - 1 on number: total=0 → 4 (not 1).
 * TZOLKIN_NAME_ORIGIN_INDEX on sign: total=0 → Ajaw at index 19.
 * Double-modulo guards against pre-epoch dates where total < 0.
 */
function jdnToTzolkin(jdn: number): TzolkinDate {
  const total = jdn - GMT_CORRELATION
  const number = (((total + TZOLKIN_COEFF_ORIGIN - 1) % TZOLKIN_COEFF_COUNT) + TZOLKIN_COEFF_COUNT) % TZOLKIN_COEFF_COUNT + 1
  const signIdx = (((total + TZOLKIN_NAME_ORIGIN_INDEX) % TZOLKIN_SIGN_COUNT) + TZOLKIN_SIGN_COUNT) % TZOLKIN_SIGN_COUNT
  return {
    coefficient: number,
    name: TZOLKIN_DAY_SIGNS[signIdx],
    daySignNumber: signIdx + 1,
  }
}

/**
 * JDN → Haab date.
 * +348 aligns with creation date 8 Kumk'u: position 17×20+8 = 348.
 */
function jdnToHaab(jdn: number): HaabDate {
  const total = jdn - GMT_CORRELATION
  const haabPos = (((total + HAAB_CORRELATION_OFFSET) % HAAB_CYCLE) + HAAB_CYCLE) % HAAB_CYCLE
  const monthIdx = Math.floor(haabPos / HAAB_DAYS_PER_MONTH)
  return {
    day: haabPos % HAAB_DAYS_PER_MONTH,
    monthName: HAAB_MONTHS[monthIdx],
  }
}

/** JDN → Long Count (baktun.katun.tun.uinal.kin). */
function jdnToLongCount(jdn: number): LongCount {
  const total = jdn - GMT_CORRELATION
  const baktun = Math.floor(total / LC_BAKTUN)
  const katun = Math.floor((total % LC_BAKTUN) / LC_KATUN)
  const tun = Math.floor((total % LC_KATUN) / LC_TUN)
  const uinal = Math.floor((total % LC_TUN) / LC_UINAL)
  const kin = total % LC_UINAL
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
function jdnToLordOfNight(jdn: number): string {
  const total = jdn - GMT_CORRELATION
  return `G${((total + LORD_OF_NIGHT_ORIGIN - 1) % LORD_OF_NIGHT_CYCLE + LORD_OF_NIGHT_CYCLE) % LORD_OF_NIGHT_CYCLE + 1}`
}

function isLeapYear(year: number): boolean {
  return year % 4 === 0 && (year % 100 !== 0 || year % 400 === 0)
}

function daysInMonth(year: number, month: number): number {
  const monthLengths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31] as const
  if (month === 2 && isLeapYear(year)) return 29
  return monthLengths[month - 1]
}

function validateDate(year: number, month: number, day: number): void {
  if (!Number.isInteger(year) || !Number.isInteger(month) || !Number.isInteger(day)) {
    throw new RangeError('year, month, and day must be integers')
  }
  if (month < 1 || month > 12) {
    throw new RangeError('month must be in 1..12')
  }
  const maxDay = daysInMonth(year, month)
  if (day < 1 || day > maxDay) {
    throw new RangeError(`day must be in 1..${maxDay} for month ${month}`)
  }
}

/** Gregorian date → complete Classic Maya calendar output. */
export function calculate(year: number, month: number, day: number): MayanChart {
  validateDate(year, month, day)
  const jdn = dateToJdn(year, month, day)
  return {
    tzolkin: jdnToTzolkin(jdn),
    haab: jdnToHaab(jdn),
    longCount: jdnToLongCount(jdn),
    lordOfNight: jdnToLordOfNight(jdn),
  }
}
