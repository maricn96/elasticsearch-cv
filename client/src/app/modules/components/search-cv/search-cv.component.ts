import { CvService } from './../../service/cv.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver';
import * as FileSaver from 'file-saver';


// const FileSaver = require('file-saver');

@Component({
  selector: 'app-search-cv',
  templateUrl: './search-cv.component.html',
  styleUrls: ['./search-cv.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class SearchCvComponent implements OnInit {

  searchTermName: string = '';
  phraseQueryName: boolean = false;
  // andOrName: 'orName' | 'andName' = 'andName';

  searchTermSurname: string = '';
  phraseQuerySurname: boolean = false;
  // andOrSurname: 'orSurname' | 'andSurname' = 'andSurname';

  searchTermQualificationLevel: string = '';

  searchTermCoverLetter: string = '';
  phraseQueryCoverLetter: boolean = false;

  andOrLogic: 'or' | 'and' = 'and';

  lat: number = 45.285301692674715;
  lon: number = 19.73419729587938;

  address: string = '';
  addressNotValid: boolean = false;
  markerLat: number = 45.285301692674715;
  markerLon: number = 19.73419729587938;
  distance!: number;

  filteredCvs: any[] = [];
  hideCVfragment: boolean = false;

  constructor(private service: CvService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    // this.service.findCvById().subscribe(res => {
    //   console.log(res);
    // }) //ne znam zasto sam ovo pozivao..
    this.onClickSearch();
  }

  onClickSearch() {

    // var obj1: any;
    // var obj2: any;
    // var obj3: any;
    // var obj4: any;

    var fieldName = {
      "field": "applicantName",
      "value": this.searchTermName,
      "phraseQuery": this.phraseQueryName,
    };

    var fieldSurname = {
      "field": "applicantSurname",
      "value": this.searchTermSurname,
      "phraseQuery": this.phraseQuerySurname,
    };

    var fieldQualification = {
      "field": "qualificationLevel",
      "value": this.searchTermQualificationLevel,
      "phraseQuery": ""
    }

    var fieldCoverLetter = {
      "field": "coverLetterContent",
      "value": this.searchTermCoverLetter,
      "phraseQuery": this.phraseQueryCoverLetter
    };

    var fields: any[] = [];
    this.searchTermName != '' ? fields.push(fieldName) : null;
    this.searchTermSurname != '' ? fields.push(fieldSurname) : null;
    this.searchTermQualificationLevel != '' ? fields.push(fieldQualification) : null;
    this.searchTermCoverLetter != '' ? fields.push(fieldCoverLetter) : null;

    var dto = {
      "fields": fields,
      "logic": this.andOrLogic == 'and' ? 'AND' : 'OR',
      "sortBy": "applicantName",
      "order": "ASC"
    }

    //dakle kad je upaljen phrasequery mi mozemo da kucamo fragment teksta
    //i on ce po tome da pretrazuje, a kad je to iskljuceno mi kucamo neke reci
    //ali on to sve posmatra kao zasebne termove
    //AND -> posmatra da li su sva polja zadovoljena
    //OR -> posmatra da li je makar neko polje zadovoljeno (ili vise njih)

    // this.searchTermName != '' ? obj1 = {
    //   "field": "applicantName",
    //   "searchTerm": this.searchTermName,
    //   "phraseQuery": this.phraseQueryName,
    //   "logic": this.andOrName == 'andName' ? 'AND' : 'OR',
    //   "sortBy": "applicantName",
    //   "order": "ASC"
    // } : {};

    // this.searchTermSurname != '' ? obj2 = {
    //   "field": "applicantSurname",
    //   "searchTerm": this.searchTermSurname,
    //   "phraseQuery": this.phraseQuerySurname,
    //   "logic": this.andOrSurname == 'andSurname' ? 'AND' : 'OR',
    //   "sortBy": "applicantName",
    //   "order": "ASC"
    // } : {};

    // this.searchTermQualificationLevel != '' ? obj3 = {
    //   "field": "qualificationLevel",
    //   "searchTerm": this.searchTermName,
    //   "phraseQuery": true,
    //   "logic": 'OR',
    //   "sortBy": "applicantName",
    //   "order": "ASC"
    // } : {};

    // this.searchTermCoverLetter != '' ? obj4 = {
    //   "field": "coverLetter",
    //   "searchTerm": this.searchTermCoverLetter,
    //   "phraseQuery": this.phraseQueryCoverLetter,
    //   "logic": this.andOrCoverLetter == 'andCoverLetter' ? 'AND' : 'OR',
    //   "sortBy": "applicantName",
    //   "order": "ASC"
    // } : {};

    // var dto = [
    //   obj1,
    //   obj2,
    //   obj3,
    //   obj4
    // ]

    this.service.search(dto).subscribe((data: any[]) => {
      this.filteredCvs = [];
      console.log(data);
      data.forEach((cv: any) => {
        console.log(cv);
        if(cv.coverLetterContent != null)
          cv.coverLetterContent = this.sanitizer.bypassSecurityTrustHtml(cv.coverLetterContent.toString().replaceAll('<em', '<em style="background-color: #FFF297;"')); //mora ovako zbog ne prepoznavanja style atributa
        this.filteredCvs.push(cv);
      })
    })
  }

  updateCoordinates(event: any) {
    this.markerLat = event.coords.lat;
    this.markerLon = event.coords.lng;
    console.log(event);
  }

  onClickSearchGeolocation() {
    let dto = {
      "lat": this.markerLat,
      "lon": this.markerLon,
      "distance": this.distance
    }
    this.service.geolocationSearch(dto).subscribe((data: any[]) => {
      console.log(data);
      this.filteredCvs = data;
    })
  }

  getLocationFromAddress() {
    console.log(this.address);
    if(this.address == '') {
      return;
    }
    this.service.getLocationFromAddress(this.address).subscribe((res: any) => {
      if(res.Response.View.length == 0) {
        this.addressNotValid = true;
      } else {
        this.addressNotValid = false;
        this.markerLat = res.Response.View[0].Result[0].Location.DisplayPosition.Latitude;
        this.markerLon = res.Response.View[0].Result[0].Location.DisplayPosition.Longitude;
        console.log("LATITUDE: " + this.markerLat);
        console.log("LONGITUDE: " + this.markerLon);
      }
      }, error => {
        this.addressNotValid = true;
        console.log(error);
      })
  }

  downloadCv(cvSqlId: number, cvFileName: string) {
    this.service.downloadCv(cvSqlId).subscribe((data: any) => {
      console.log(data);
  //     let file = new Blob([data], { type: 'application/pdf' });
  // var fileURL = URL.createObjectURL(file);
  // window.open(fileURL);
    //   const pdfUrl = window.URL.createObjectURL(new Blob(data));
    // // const pdfName = material.name;
    // FileSaver.saveAs(pdfUrl, "random.txt");
    const file = new Blob([data], {type: 'text/plain'});
    FileSaver.saveAs(file, cvFileName);
    })
  }

  deleteCv(id: string) {
    console.log(id);
  }

}
