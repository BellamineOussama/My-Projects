import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgenceService {

  private baseUrl = 'http://localhost:8082/api/agences';

  constructor(private http: HttpClient) { }

  getanyById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getAllanys(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  createany(any: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<any>(this.baseUrl, any, { headers });
  }

  updateany(id: number, any: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put<any>(`${this.baseUrl}/${id}`, any, { headers });
  }

  deleteany(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
