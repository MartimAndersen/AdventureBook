import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

export type SaveGameDialogResult = 'save' | 'discard';

@Component({
  selector: 'app-save-game-dialog',
  standalone: true,
  imports: [MatButtonModule, MatDialogModule],
  template: `
    <h2 mat-dialog-title>Leave this game?</h2>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-button [mat-dialog-close]="'discard'">Leave without saving</button>
      <button mat-raised-button color="primary" [mat-dialog-close]="'save'">
        Save and leave
      </button>
    </mat-dialog-actions>
  `,
})
export class SaveGameDialog {}

