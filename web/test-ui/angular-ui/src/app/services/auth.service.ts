import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  /* TODO environment */
  private API_URL: string = '';
  private TOKEN: string = '';

  constructor(private _http: HttpClient,
              private _router: Router) { }


}
