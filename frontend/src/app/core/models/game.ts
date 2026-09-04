import { GameStatus } from './game-status';
import { Section } from './section';

export interface Game {
  gameId: string;
  bookTitle: string;
  health: number;
  status: GameStatus;
  section: Section;
}
