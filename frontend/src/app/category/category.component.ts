import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../services/category.service';
import {MessageService} from 'primeng/api';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-category',
  standalone: false,
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {

  visible: boolean = false;
  categories: any = [];
  categoryId: any = null;
  name: any = null;
  status: any = null;
  isEditButton: boolean = false;

  chechkBoxValue: any = null;




  onCheckboxChange(task: any) {
    console.log(`Task ${task.id} completed status: ${task.isChecked}`);
  }

  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.name = null;
    this.status = null;
    this.categoryId = null;
  }


  constructor(private categoryService: CategoryService, private messageService: MessageService, private taskService: TaskService) {
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.categoryService.findAll().then(response => {
      console.log('Categories:', response);

      this.categories = response;

    })
  }

  cancel() {
    this.visible = false;
  }

  saveCategory() {
    const category = {
      "id": null,
      "name": this.name,

    }
    this.categoryService.save(category).then(response => {
      if (response.status === 201) {
        this.visible = false;
        this.getData();
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
    })
    console.log(category);
  }

  updateCategory() {
    this.visible = true;

    const category = {
      "id": this.categoryId,
      "name": this.name,
    }
    this.categoryService.update(this.categoryId, category).then(response => {

      if (response.status === 200) {
        this.visible = false;
        this.getData();
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

    })

    console.log(category);


  }

  showTasksDialog: boolean = false;
  showDialogButtons: boolean = false;
  tasks: any[] = [];

  filterActiveTasks() {

  }

  filterCompletedTasks() {

  }


  tasksByCategoryId(categoryId: number) {


    this.categoryService.getTasksByCategoryId(categoryId).then(response => {
      console.log('Tasks:', response);
      this.tasks = response;
      this.showTasksDialog = true;
    }).catch(error => {
      console.log('Error:', error);
    });
  }

  deleteCategory(category: any) {
    this.categoryService.delete(category.id).then(response => {
      if (response.status === 200) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Category silindi'
        })

      }
    }).catch(error => {
      console.log(error);
    })
  }

  editCategory(category: any) {

    this.isEditButton = true;
    this.visible = true;
    this.name = category.name;
    this.categoryId = category.id;
  }

  deleteTask(task: any) {
    this.taskService.delete(task.id).then(response => {
      if (response.status === 200) {
        this.visible = false;
        this.getData();
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: 'Task silindi'

        })
      }
    }).catch(error => {
      console.log(error);
    })

  }


}
