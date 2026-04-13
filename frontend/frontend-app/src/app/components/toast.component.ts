import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="toast-container" *ngIf="visible">
      <div class="toast-card" [ngClass]="type">
        <i
          class="bi me-2"
          [ngClass]="{
            'bi-check-circle': type === 'success',
            'bi-x-circle': type === 'error',
            'bi-info-circle': type === 'info'
          }"
        ></i>
        <span>{{ message }}</span>
      </div>
    </div>
  `,
  styleUrls: ['./toast.component.css']
})
export class ToastComponent {
  @Input() message = '';
  @Input() type: 'success' | 'error' | 'info' = 'info';
  @Input() visible = false;
}