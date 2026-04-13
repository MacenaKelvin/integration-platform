import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Purchase {
  id: number;
  purchaseId: string;
  customerId: string;
  totalAmount: number;
  date: string;
  status: 'PENDING' | 'SUCCESS' | 'ERROR';
  attempts: number;
}

export interface CreatePurchasePayload {
  purchaseId: string;
  customerId: string;
  totalAmount: number;
}

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  private http = inject(HttpClient);
  private api = 'http://localhost:8080/purchases';

  getAll(): Observable<Purchase[]> {
    return this.http.get<Purchase[]>(this.api);
  }

  create(payload: CreatePurchasePayload): Observable<Purchase> {
    return this.http.post<Purchase>(this.api, payload);
  }

  reprocess(id: number): Observable<Purchase> {
    return this.http.post<Purchase>(`${this.api}/${id}/reprocess`, {});
  }
}