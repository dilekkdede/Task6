import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  baseUrl: any = 'http://localhost:8080/rest/api';


  constructor() {
  }

  async findAll(status: any, categoryId: any) {
    const response = await axios.get(this.baseUrl + '/task/find-all?status=' + status + '&categoryId=' + categoryId).then(function (response) {
      return response.data.data;
    })
    return response;

  }

  async save(city: any) {
    const response = await axios.post(this.baseUrl + '/task/save', city).then(function (response) {
      return response.data;
    })
    return response;
  }

  async delete(id: number) {
    const response = await axios.delete(this.baseUrl + '/task/delete/' + id).then(function (response) {
      return response.data;
    })
    return response;
  }

  async update(id: number, category: any) {
    const response = await axios.put(this.baseUrl + '/task/update/' + id, category).then(function (response) {
      return response.data;
    })
    return response;
  }

  async completed(id: number) {
    const response = await axios.get(this.baseUrl + '/task/completed/' + id).then(function (response) {
      return response.data;
    });
    return response;
  }

}
