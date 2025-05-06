import {Component, OnInit} from '@angular/core';
import {TaskService} from '../services/task.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {CategoryService} from '../services/category.service';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-todo-add',
  standalone: false,
  templateUrl: './add-todo.component.html',
  styleUrl: './add-todo.component.css'
})
export class AddTodoComponent implements OnInit {


  title: any = null;
  categories: any[] = [];
  categoryId: any = null;
  dueDate: any = null;
  isEditButton: boolean = false;



  constructor(private taskService: TaskService, private messageService: MessageService, private categoryService: CategoryService, private datePipe: DatePipe, private confirmationService: ConfirmationService) {
  }


  ngOnInit(): void {
    this.getCategories();

  }


  cancel() {
  }


  saveTask() {
    const task = {
      title: this.title,
      dueDate: this.dueDate,
      category: {
        id: this.categoryId
      }
    };

    this.taskService.save(task).then(response => {
      if (response.status === 201) {
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Başarılı bir şekilde kayıt yapıldı'
        })
      }
      if (response.status === 400) {
        this.messageService.add({
          severity: 'error',
          summary: 'Başarısız',
          detail: response.message
        })
      }
    }).catch(error => {
      console.log(error);
    });
  }

  updateTask() {

    const task = {
      "id": null,
      "title": this.title,
      "dueDate": new Date(this.dueDate),
      "category": {
        "id": this.categoryId,
      }

    }

    this.taskService.update(0, task).then(response => {
      if (response.status === 200) {
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: response.message
        })
      }
      if (response.status === 400) {
        this.messageService.add({
          severity: 'error',
          summary: 'Başarısız',
          detail: response.message
        })
      }
    }).catch(error => {
      console.log(error);
    });
  }

  getCategories() {
    this.categoryService.findAll().then(response => {
      this.categories = response;
    }).catch(error => {
      console.log('Kategori verileri alınırken hata:', error);
    });
  }

}
