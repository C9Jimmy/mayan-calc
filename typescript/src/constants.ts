/** GMT correlation constant (Goodman-Martinez-Thompson archaeological consensus). */
export const GMT_CORRELATION = 584283;

export const TZOLKIN_COEFF_ORIGIN = 4;
export const TZOLKIN_NAME_ORIGIN_IDX = 19;
export const TZOLKIN_CYCLE = 260;
export const TZOLKIN_COEFF_COUNT = 13;
export const TZOLKIN_SIGN_COUNT = 20;

export const HAAB_CYCLE = 365;
export const HAAB_DAYS_PER_MONTH = 20;
export const HAAB_CORRELATION_OFFSET = 348; // aligns creation date with 8 Kumk'u (17*20+8)

export const LC_BAKTUN = 144000;
export const LC_KATUN = 7200;
export const LC_TUN = 360;
export const LC_UINAL = 20;

export const LORD_OF_NIGHT_CYCLE = 9;

export const MEEUS_YEAR_FACTOR = 365.25;
export const MEEUS_MONTH_FACTOR = 30.6001;
export const MEEUS_EPOCH_A = 4716;
export const MEEUS_EPOCH_B = 1524;

export const TZOLKIN_DAY_SIGNS = [
  "Imix", "Ik'", "Ak'bal", "K'an", "Chikchan",
  "Kimi", "Manik'", "Lamat", "Muluk", "Ok",
  "Chuwen", "Eb", "Ben", "Hix", "Men",
  "Kib", "Kaban", "Etz'nab", "Kawak", "Ajaw",
] as const;

export const HAAB_MONTHS = [
  "Pop", "Wo", "Sip", "Sotz'", "Sek",
  "Xul", "Yaxk'in", "Mol", "Ch'en", "Yax",
  "Sak", "Keh", "Mak", "K'ank'in", "Muwan",
  "Pax", "K'ayab", "Kumk'u", "Wayeb",
] as const;
