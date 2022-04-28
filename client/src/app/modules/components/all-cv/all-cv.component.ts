import { Subscription } from 'rxjs';
import { CvService } from './../../service/cv.service';
import { CV } from './../../model/Cv';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-all-cv',
  templateUrl: './all-cv.component.html',
  styleUrls: ['./all-cv.component.css']
})
export class AllCvComponent implements OnInit {

  allCv: CV[] = [];

  getAllCvSubscriber!: Subscription;

  constructor(private cvService: CvService) { }

  ngOnInit(): void {
    this.getAllCv();
  }

  ngOnDestroy() {
    this.getAllCvSubscriber ? this.getAllCvSubscriber.unsubscribe() : null;
  }

  getAllCv() {
    this.getAllCvSubscriber = this.cvService.getAllCv().subscribe((data: CV[]) => {
      this.allCv = data;
    })
  }

  deleteCv(id: string) {

  }

}
