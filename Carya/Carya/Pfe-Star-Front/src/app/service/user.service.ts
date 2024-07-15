import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl='http://localhost:8082/api/utilisateurs';

  constructor(private http: HttpClient) { }

  getUserByID(id:number): Observable<any>{
    return this.http.get<any>(`${this.baseUrl}/${id}`)
  }

  getAllUser():Observable<any[]>{
    return this.http.get<any[]>(this.baseUrl);
  }

  deleteuser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getUsersRoles(Role:any){
    return this.http.get<any>(`${this.baseUrl}/role/${Role}`)
  }
}
