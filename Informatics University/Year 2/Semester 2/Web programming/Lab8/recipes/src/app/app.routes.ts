import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { BrowseComponent } from './browse/browse.component';
import { IndexComponent } from './index/index.component';
import { AddComponent } from './add/add.component';

export const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'browse', component: BrowseComponent},
    {path: 'add', component: AddComponent}
];