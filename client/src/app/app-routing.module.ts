import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '' , loadChildren:() => import('./shared/home/home.module').then(m => m.HomeModule)},
  { path: 'app' , loadChildren:() => import('./modules/modules.module').then(m => m.MainModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
