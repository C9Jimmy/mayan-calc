export interface BirthInput {
  readonly year: number;
  readonly month: number;
  readonly day: number;
}

export interface TzolkinDate {
  readonly coefficient: number;   // 1–13
  readonly name: string;          // e.g. "Ajaw"
  readonly daySignNumber: number; // 1–20, position in the 20-sign cycle
}

export interface HaabDate {
  readonly day: number;       // 0–19
  readonly monthName: string; // e.g. "Mak"
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
  readonly lordOfNight: string; // "G1"–"G9"
}
