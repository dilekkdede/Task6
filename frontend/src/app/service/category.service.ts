import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  baseUrl: any = 'http://localhost:8080/rest/api';


  constructor() { }
}
