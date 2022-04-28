import { HomeComponent } from './home.component';
import { MaterialModule } from './../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
    { path: 'index', component: HomeComponent },
    { path: '', component: HomeComponent },
];

@NgModule({
	imports: [
		CommonModule,
        RouterModule.forChild(routes),
        MaterialModule
	],
	declarations: [
		HomeComponent,
	],
	exports: [
	]
})
export class HomeModule { }
