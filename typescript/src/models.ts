export interface BirthInput {
  readonly year: number;
  readonly month: number;
  readonly day: number;
}

export interface TzolkinDate {
  readonly number: number;        // 1–13
  readonly daySign: string;       // e.g. "Ajaw"
  readonly daySignNumber: number; // 1–20, position in the 20-sign cycle
}

export interface HaabDate {
  readonly month: string; // e.g. "Mak"
  readonly day: number;   // 0–19
}

export interface LongCount {
  readonly baktun: number;
  readonly katun: number;
  readonly tun: number;
  readonly uinal: number;
  readonly kin: number;
  readonly display: string; // e.g. "12.18.15.11.0"
}

export interface MayanChart {
  readonly tzolkin: TzolkinDate;
  readonly haab: HaabDate;
  readonly longCount: LongCount;
  readonly lordOfNight: number; // 1–9 (G1–G9)
}
