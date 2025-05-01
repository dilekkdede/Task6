import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {TaskComponent} from './task/task.component';
import {CategoryComponent} from './category/category.component';
import {HomeComponent} from './home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },//default route
  {path: 'app', component: AppComponent},
  {path: 'app/task', component: TaskComponent},
  {path: 'app/category', component: CategoryComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
