import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { finalize } from 'rxjs';
import { Purchase, PurchaseService } from './services/purchase.service';
import { ToastComponent } from './components/toast.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, ToastComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  private purchaseService = inject(PurchaseService);
  private cdr = inject(ChangeDetectorRef);

  purchases: Purchase[] = [];
  isSubmitting = false;
  showForm = false;

  toastMessage = '';
  toastType: 'success' | 'error' | 'info' = 'info';
  toastVisible = false;

  form = {
    purchaseId: '',
    customerId: '',
    totalAmount: 0
  };

  ngOnInit(): void {
    this.loadData();
  }

  showToast(message: string, type: 'success' | 'error' | 'info') {
    this.toastMessage = message;
    this.toastType = type;
    this.toastVisible = true;

    setTimeout(() => {
      this.toastVisible = false;
      this.cdr.detectChanges();
    }, 3000);
  }

  loadData(): void {
    this.purchaseService.getAll().subscribe({
      next: (data) => {
        this.purchases = [...data];
        this.cdr.detectChanges();
      },
      error: () => {
        this.showToast('Erro ao carregar compras', 'error');
      }
    });
  }

  openForm(): void {
    this.showForm = true;
    this.cdr.detectChanges();
  }

  closeForm(): void {
    this.showForm = false;
    this.cdr.detectChanges();
  }

  resetForm(): void {
    this.form = {
      purchaseId: '',
      customerId: '',
      totalAmount: 0
    };
  }

  createPurchase(): void {
    if (!this.form.purchaseId || !this.form.customerId || !this.form.totalAmount) {
      this.showToast('Preencha todos os campos', 'error');
      return;
    }

    this.isSubmitting = true;
    this.cdr.detectChanges();

    this.purchaseService.create(this.form)
      .pipe(
        finalize(() => {
          this.isSubmitting = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          this.purchases = [response, ...this.purchases];
          this.resetForm();

          this.showForm = false;
          this.showToast('Compra criada com sucesso!', 'success');

          this.loadData();

          setTimeout(() => {
            this.cdr.detectChanges();
          }, 0);
        },
        error: () => {
          this.showToast('Erro ao criar compra', 'error');
        }
      });
  }

  reprocess(id: number): void {
    this.purchaseService.reprocess(id).subscribe({
      next: () => {
        this.showToast('Reprocessamento realizado!', 'info');
        this.loadData();
      },
      error: () => {
        this.showToast('Erro no reprocessamento', 'error');
      }
    });
  }

  get successCount(): number {
    return this.purchases.filter(p => p.status === 'SUCCESS').length;
  }

  get errorCount(): number {
    return this.purchases.filter(p => p.status === 'ERROR').length;
  }
}