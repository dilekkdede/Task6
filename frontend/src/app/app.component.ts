import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {PrimeNG} from 'primeng/config';
import {TaskService} from './services/task.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css',
  providers: [MessageService]
})
export class AppComponent implements OnInit {
  constructor(private primeng: PrimeNG, private taskService: TaskService) {
  }

  ngOnInit() {
    this.primeng.ripple.set(true);
  }


}

