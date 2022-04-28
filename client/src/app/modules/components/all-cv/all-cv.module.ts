import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './../../../shared/material/material.module';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AllCvComponent } from './all-cv.component';

const routes: Routes = [
	{ path: '', component: AllCvComponent }
];

@NgModule({
  imports: [
		CommonModule,
		RouterModule.forChild(routes),
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [AllCvComponent]
})
export class AllCvModule { }
