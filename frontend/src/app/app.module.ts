import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {providePrimeNG} from 'primeng/config';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {ListboxModule} from 'primeng/listbox';
import {CalendarModule} from 'primeng/calendar';
import {CardModule} from 'primeng/card';

import Aura from '@primeng/themes/aura';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

// PrimeNG modules
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {InputTextModule} from 'primeng/inputtext';
import {ToastModule} from 'primeng/toast';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {FloatLabelModule} from 'primeng/floatlabel';
import {InputNumberModule} from 'primeng/inputnumber';
import {CheckboxModule} from 'primeng/checkbox';
import {InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {TableModule} from 'primeng/table';
import {TabsModule} from 'primeng/tabs';
import {ImageModule} from 'primeng/image';

import {MessageService} from 'primeng/api';
import {RippleModule} from 'primeng/ripple';
import {ChartModule} from 'primeng/chart';
import {InputMaskModule} from 'primeng/inputmask';
import {FileUploadModule} from 'primeng/fileupload';
import {TaskComponent} from './task/task.component';
import {CategoryComponent} from './category/category.component';
import {DropdownModule} from "primeng/dropdown";
import {HomeComponent} from './home/home.component';
import {Select} from "primeng/select";
import {ScrollPanelModule} from 'primeng/scrollpanel';

@NgModule({
  declarations: [
    AppComponent,
    TaskComponent,
    CategoryComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    ToastModule,
    MessagesModule,
    MessageModule,
    FloatLabelModule,
    InputNumberModule,
    CheckboxModule,
    InputGroupAddonModule,
    ListboxModule,
    CalendarModule,
    CardModule,
    TableModule,
    TabsModule,
    ChartModule,
    InputMaskModule,
    FileUploadModule,
    RippleModule,
    ImageModule,
    DropdownModule,
    Select,
    ScrollPanelModule
  ],
  providers: [
    MessageService,
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
