import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarsService {

  private baseUrl = 'http://localhost:8082/api/vehicules';

  constructor(private http: HttpClient) { }

  getVehiculeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  getAllVehicules(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getVehiculesByDisponibilite(disponible: boolean): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/disponible?disponible=${disponible}`);
  }

  createVehicule1(formData: FormData): Observable<any> {
    return this.http.post<any>(this.baseUrl+'/image', formData);
  }

  createVehicule(vehicule: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, vehicule);
  }

  updateVehicule(id: number, vehicule: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.put<any>(`${this.baseUrl}/${id}`, vehicule, { headers });
  }

  deleteVehicule(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  addAvis(Avis:any){
    return this.http.post('http://localhost:8082/api/avis', Avis);
  }

  addAvisVoiture(id:any, avis:any){
    return this.http.put(`${this.baseUrl}/${id}/Avis`,avis)
  }
  getVehiculeByAgence(agenceName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/agence?agenceName=${agenceName}`);
  }
  updateCar(id: number, data: any): Observable<any> {
    console.log(data)
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }
}
