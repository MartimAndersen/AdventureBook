import { Consequence } from './consequence';

export interface Option {
  index: number;
  description: string;
  consequence: Consequence | null;
}
