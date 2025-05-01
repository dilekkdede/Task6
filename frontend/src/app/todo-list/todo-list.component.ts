import {Component, OnInit} from '@angular/core';
import {TaskService} from '../services/task.service';
import {MessageService} from 'primeng/api';
import {CategoryService} from '../services/category.service';


interface StatusParam {
  name: string;
  code: string;
}

@Component({
  selector: 'app-todo-list',
  standalone: false,
  templateUrl: './todo-list.component.html',
  styleUrl: './todo-list.component.css'
})
export class TodoListComponent implements OnInit {

  visible: boolean = false;
  tasks: any = [];
  taskId: any = null;
  title: any = null;
  status: any = null;
  dueDate: any = null;
  category: any = null;
  categoryId: any = null;
  isEditButton: boolean = false;
  categories: any[] = [];


  statusList: StatusParam[] | undefined;

  selectedStatus: StatusParam | undefined;


  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.taskId = null;
    this.title = null;
    this.status = null;
    this.dueDate = null;
    this.category = null;
  }

  constructor(private taskService: TaskService, private messageService: MessageService, private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.getData();
    this.getCategories();


    this.statusList = [
      {name: 'Tamamlanmış', code: '0'},
      {name: 'Tamamlanmamış', code: '1'},

    ];

  }

  getData() {
    this.taskService.findAll('').then(response => {
      this.tasks = response;

    });
  }

  getCategories() {
    this.categoryService.findAll().then(response => {
      this.categories = response;
    }).catch(error => {
      console.log('Kategori verileri alınırken hata:', error);
    });
  }

  cancel() {
    this.visible = false;
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
    });
  }


  editTask(task: any) {
    this.isEditButton = true;
    this.visible = true;
    this.title = task.title;
    this.dueDate = task.dueDate;
    this.categoryId = task.category?.id;
    this.taskId = task.id;
  }

  updateTask() {
    this.visible = true;

    const task = {
      "id": this.taskId,
      "title": this.title,
      "dueDate": this.dueDate,
      "category": {
        "id": this.categoryId,
      }

    }

    this.taskService.update(this.taskId, task).then(response => {
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
    console.log(task);
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

  sorgula() {
    console.log(this.selectedStatus?.code);

    const status = this.selectedStatus?.code === undefined ? '' : this.selectedStatus?.code;

    this.taskService.findAll(status).then(response => {
      this.tasks = response;

    });
  }

  // @ts-ignore
  getStatusColor(status: number) {
    switch (status) {
      case 0:
        return 'success';
      case 1:
        return 'danger';
    }
  }

}
