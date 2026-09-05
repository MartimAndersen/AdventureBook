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
  styles: [`
    h2[mat-dialog-title] {
      color: #433024;
    }

    mat-dialog-actions {
      gap: 8px;
    }

    mat-dialog-actions button[mat-button],
    mat-dialog-actions button[mat-raised-button] {
      min-height: 40px;
      padding: 0 14px;
      color: #433024;
      background: #f3ebe1;
      border: 1px solid #f3e1b7;
      border-radius: 8px;
      transition: box-shadow 120ms ease;
    }

    mat-dialog-actions button[mat-button]:hover,
    mat-dialog-actions button[mat-raised-button]:hover {
      box-shadow: 0 2px 4px rgba(67, 48, 36, 0.2);
    }
  `],
})
export class SaveGameDialog {}

