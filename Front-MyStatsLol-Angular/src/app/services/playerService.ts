import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PlayerService {
  private baseUrl = environment.apiUrl;


  constructor(private http: HttpClient) {}

  getPlayerByPuuid(value : string): Observable<string> {
    return this.http.get(`${this.baseUrl}/players/playerByPuuid/${value}`, { responseType: 'text' });
  }
}
