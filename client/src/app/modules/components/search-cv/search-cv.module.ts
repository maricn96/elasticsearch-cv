import { AgmCoreModule } from '@agm/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../shared/material/material.module';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchCvComponent } from './search-cv.component';

const routes: Routes = [
	{ path: '', component: SearchCvComponent }
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
  declarations: [SearchCvComponent],

})
export class SearchCvModule { }
