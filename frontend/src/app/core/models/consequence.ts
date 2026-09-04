export interface Consequence {
  type: 'LOSE_HEALTH' | 'GAIN_HEALTH';
  value: number;
  text: string;
}
