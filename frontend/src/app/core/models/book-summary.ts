import { Difficulty } from './difficulty';

export interface BookSummary {
  id: string;
  title: string;
  author: string;
  difficulty: Difficulty;
  type: string;
}
