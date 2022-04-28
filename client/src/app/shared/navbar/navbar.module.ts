import { MaterialModule } from './../material/material.module';
import { NavbarComponent } from './navbar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

const routes: Routes = [
    { path: 'navbar', component: NavbarComponent },
];

@NgModule({
	imports: [
		CommonModule,
		RouterModule.forChild(routes),
		MaterialModule,
		HttpClientModule

	],
	declarations: [
		NavbarComponent,
	],
	exports: [
		NavbarComponent
	]
})
export class NavbarModule { }
