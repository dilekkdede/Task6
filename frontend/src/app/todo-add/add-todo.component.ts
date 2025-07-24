import {AfterViewInit, Component, Input, OnDestroy, OnInit, Output, EventEmitter} from '@angular/core';
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
export class AddTodoComponent implements OnInit, OnDestroy, AfterViewInit {
  @Input({required: true}) todoData!: any;
  @Input({required: true}) todoVisible!: any;
  @Output() todoBackEvent: EventEmitter<any> = new EventEmitter<any>();


  visible: boolean = false;
  title: any = null;
  categories: any[] = [];
  categoryId: any = null;
  dueDate: any = null;
  isEditButton: boolean = false;
  taskId: any = null;


  constructor(private taskService: TaskService, private messageService: MessageService, private categoryService: CategoryService, private datePipe: DatePipe, private confirmationService: ConfirmationService) {

  }


  ngOnInit(): void {

    console.log(this.todoData)
    this.getCategories();
    this.visible = this.todoVisible;
    if (this.todoData === undefined || this.todoData === null) {
      this.isEditButton = false;
    } else {
      this.isEditButton = true;
      this.visible = true;
      this.title = this.todoData.title;
      this.dueDate = this.datePipe.transform(this.todoData.dueDate, 'MM/dd/YYYY');
      this.categoryId = this.todoData.category?.id;
      this.taskId = this.todoData.id;
    }


  }


  cancel() {
    this.todoBackEvent.emit(true);
    this.visible = false;
  }


  saveTask() {
    this.visible = false;

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
        this.todoBackEvent.emit(true);

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
      "id": this.taskId,
      "title": this.title,
      "dueDate": new Date(this.dueDate),
      "category": {
        "id": this.categoryId,
      }

    }


    this.taskService.update(this.taskId, task).then(response => {
      if (response.status === 200) {
        this.messageService.add({
          severity: 'success',
          summary: 'Başarılı',
          detail: response.message
        })
        this.visible = false;
        this.todoBackEvent.emit(true);

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

  ngAfterViewInit(): void {
    console.log('ngAfterViewInit');
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy');
  }


}
