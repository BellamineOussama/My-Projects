import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvisService {

  private baseUrl = 'http://localhost:8080/api/avis';

  constructor(private http: HttpClient) { }

  getAllAvis():Observable<any[]>{
    return this.http.get<any[]>(this.baseUrl);
  }

  getAvisByClientId(Clientid:number):Observable<any[]>{
    return this.http.get<any[]>(`${this.baseUrl}/client/${Clientid}`);
  }

  deleteAvis(id:number):Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
