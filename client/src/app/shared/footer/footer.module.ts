import { FooterComponent } from './footer.component';
import { MaterialModule } from './../material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

const routes: Routes = [
    { path: '', component: FooterComponent },
];

@NgModule({
	imports: [
		CommonModule,
		RouterModule.forChild(routes),
		MaterialModule,
		HttpClientModule

	],
	declarations: [
		FooterComponent,
	],
	exports: [
		FooterComponent
	]
})
export class FooterModule { }
