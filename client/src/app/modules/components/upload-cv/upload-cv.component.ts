import { CvService } from './../../service/cv.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-upload-cv',
  templateUrl: './upload-cv.component.html',
  styleUrls: ['./upload-cv.component.css'],
})
export class UploadCvComponent implements OnInit {
  form!: FormGroup;
  selectedFile!: File;
  selectedClFile!: File;
  files!: any[];
  filesData!: any[];
  uploadFile!: FormData;

  lat: number = 45.285301692674715;
  lon: number = 19.73419729587938;

  markerLat: number = 45.285301692674715;
  markerLon: number = 19.73419729587938;

  addressNotValid: boolean = false;
  uploaded: boolean = false;

  constructor(private service: CvService) {}

  ngOnInit(): void {
    this.initForm();
    this.uploadFile = new FormData();
    this.files = [];
    this.filesData = [];
    // this.requestForGeoCoordinates();
    // this.getLocationFromAddress();
  }

  initForm() {
    this.form = new FormGroup({
      applicantName: new FormControl('', Validators.required),
      applicantSurname: new FormControl('', Validators.required),
      qualificationLevel: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required)
      // cvFile: new FormControl(''),
      // coverLetterFile: new FormControl(''),
    });
  }

  onAddFile(event: any) {
    this.selectedFile = event?.target.files[0];
  }

  onAddClFile(event: any) {
    this.selectedClFile = event?.target.files[0];
  }

  onClickUploadFiles() {
    if(this.addressNotValid) {
      return;
    }
    var dto = Object.assign({}, this.form.value);

    dto['id'] = Math.random().toString();
    dto['lat'] = this.markerLat;
    dto['lon'] = this.markerLon;
    dto['cvFileName'] = this.selectedFile.name;
    dto['coverLetterFileName'] = this.selectedClFile.name;

    this.uploadFile.append('cvFile', this.selectedFile, this.selectedFile.name);
    this.uploadFile.append('coverLetterFile', this.selectedClFile, this.selectedClFile.name);
    this.service.uploadCv(this.uploadFile, JSON.stringify(dto)).subscribe((res: any) => {
        console.log(res);
        this.uploaded = true;
      }, error => {
        this.uploaded = false;
      });
  }

  updateCoordinates(event: any) {
    this.markerLat = event.coords.lat;
    this.markerLon = event.coords.lng;
    console.log(event);
  }
  // requestForGeoCoordinates() {
  //   this.service.requestForGeoCoordinates().subscribe((data: any) => {

  //   })
  // }

  getLocationFromAddress() {
    let adresatemp = this.form.get('address')!.value;
    if(adresatemp == '') {
      return;
    }
    this.service.getLocationFromAddress(adresatemp).subscribe((res: any) => {
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

}
