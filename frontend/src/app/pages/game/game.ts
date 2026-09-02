import { Component, OnInit } from '@angular/core';

import { GamesService } from '../../core/services/games.service';
import { Game as GameModel } from '../../core/models/game';

@Component({
  selector: 'app-game',
  imports: [],
  templateUrl: './game.html',
  styleUrl: './game.scss',
})
export class Game implements OnInit {
  game?: GameModel;

  constructor(private gamesService: GamesService) {}

  ngOnInit(): void {
    this.game = this.gamesService.currentGame;

    console.log(this.game);
  }
}
