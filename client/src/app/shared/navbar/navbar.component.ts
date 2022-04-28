import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit, OnDestroy {
  cartItemsNumber: number = 0;
  loggedIn: boolean = false;
  flagReload: any;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnDestroy() {
  }
}
