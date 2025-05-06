import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: any = 'http://localhost:8080/rest/api';


  constructor() {
  }

  async findAll() {
    const response = await axios.get(this.baseUrl + '/category/find-all').then(function (response) {
      return response.data.data;
    })
    return response;

  }

  async save(city: any) {
    const response = await axios.post(this.baseUrl + '/category/save', city).then(function (response) {
      return response.data;
    })
    return response;
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/category/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }

  async update(id: number, category: any) {
    const response = await axios.put(this.baseUrl + '/category/update/' + id, category).then(function (response) {
      return response.data;
    })
    return response;
  }

  async getTasksByCategoryId(id: number) {
    const response = await axios.get(this.baseUrl + '/category/get-tasks-by-category-id/' + id)
      .then(function (response) {
        return response.data.data;
      });
    return response;
  }


}
