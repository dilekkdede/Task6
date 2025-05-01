import {Injectable} from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  baseUrl: any = 'http://localhost:8080/rest/api';


  constructor() {
  }

  //Forntend tarafında parametlere servise böyle geçilir
  async findAll(status: any) {
    const response = await axios.get(this.baseUrl + '/task/find-all?status=' + status).then(function (response) {
      return response.data[0].data;
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

}
