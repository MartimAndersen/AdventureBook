import { Option } from './option';

export interface Section {
  id: number;
  text: string;
  options: Option[];
}
