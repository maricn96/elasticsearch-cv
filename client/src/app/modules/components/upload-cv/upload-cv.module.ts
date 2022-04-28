import { AgmCoreModule } from '@agm/core';
import { MaterialModule } from './../../../shared/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UploadCvComponent } from './upload-cv.component';

const routes: Routes = [
	{ path: '', component: UploadCvComponent }
];

@NgModule({
  imports: [
		CommonModule,
		RouterModule.forChild(routes),
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBWZfdjAVke_iptKpnO1jZclgjJIS8znwA'
    })
  ],
  declarations: [UploadCvComponent]
})
export class UploadCvModule { }
