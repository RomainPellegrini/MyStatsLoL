import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PlayerService {
  private baseUrl = 'http://localhost:8080'; // Ton backend Spring Boot

  constructor(private http: HttpClient) {}

  getPlayerByPuuid(value : string): Observable<string> {
    return this.http.get(`${this.baseUrl}/players/playerByPuuid/${value}`, { responseType: 'text' });
  }
}
