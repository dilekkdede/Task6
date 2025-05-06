import {Component, OnInit} from '@angular/core';
import {CategoryService} from '../services/category.service';
import {MessageService} from 'primeng/api';
import {TaskService} from '../services/task.service';
import {ConfirmationService} from 'primeng/api';


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


  showDialog() {
    this.isEditButton = false;
    this.visible = true;
    this.name = null;
    this.status = null;
    this.categoryId = null;
  }


  constructor(private categoryService: CategoryService, private messageService: MessageService, private taskService: TaskService, private confirmationService: ConfirmationService) {
  }

  confirmDelete(event: Event, categoryId: any) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Kategoriyi silmek istediğinize emin misiniz!',
      header: 'Danger Zone',
      icon: 'pi pi-info-circle',
      rejectLabel: 'Cancel',
      rejectButtonProps: {
        label: 'İptal',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Evet',
        severity: 'danger',
      },

      accept: () => {
        this.deleteCategory(categoryId);
      },
      reject: () => {
      },
    });
  }

  ngOnInit(): void {
    this.getData();
  }

  getData() {
    this.categoryService.findAll().then(response => {

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


  }

  showTasksDialog: boolean = false;
  showDialogButtons: boolean = false;
  tasks: any[] = [];


  tasksByCategoryId(categoryId: number) {


    this.categoryService.getTasksByCategoryId(categoryId).then(response => {
      this.tasks = response;
      this.showTasksDialog = true;
    }).catch(error => {
      console.log('Error:', error);
    });
  }

  deleteCategory(categoryId: any) {
    this.categoryService.delete(categoryId).then(response => {
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


}
