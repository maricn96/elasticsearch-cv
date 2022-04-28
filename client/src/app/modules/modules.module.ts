import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = [
  { path: 'allCv' , loadChildren:() => import('./components/all-cv/all-cv.module').then(m => m.AllCvModule)},
  { path: 'uploadCv' , loadChildren:() => import('./components/upload-cv/upload-cv.module').then(m => m.UploadCvModule)},
  { path: 'searchCv' , loadChildren:() => import('./components/search-cv/search-cv.module').then(m => m.SearchCvModule)}
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ]
})
export class MainModule { }
