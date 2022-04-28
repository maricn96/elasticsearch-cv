import { CV } from './../model/Cv';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CvService {

  serverUrl: string = 'http://localhost:8200/api/cv/';
  // googleApiUrl: string = 'https://maps.googleapis.com/maps/api/geocode/';
  hereGeoApi: string = 'https://geocoder.ls.hereapi.com/6.2/geocode.json?searchtext=';
  hereApiKey: string = environment.hereApiKey;

  constructor(private http: HttpClient) { }

  getAllCv() {
    return this.http.get<any>(this.serverUrl + 'all')
  }

  findCvById() {
    return this.http.get<any>(this.serverUrl + '/0.793227612671138')
  }

  uploadCv(files: any, data: any) {
    // console.log(data);

    const params = new HttpParams().append('data', data);
    return this.http.post<any>(this.serverUrl + 'uploadCv', files, { params: params, responseType: 'text' as 'json' });
  }

  search(dto: any) {
    return this.http.post<any>(this.serverUrl + 'search', dto);
  }

  geolocationSearch(dto: any) {
    return this.http.post<any>(this.serverUrl + 'geolocationSearch', dto);
  }

  getLocationFromAddress(address: string) {
    return this.http.get<any>(this.hereGeoApi + address + "&gen=9&apiKey=" + this.hereApiKey)
    // .pipe(catchError((error: HttpErrorResponse) => {

    // }));
  }

  downloadCv(cvSqlId: number) {
    return this.http.get<any>(this.serverUrl + 'getRawCvDocument/' + cvSqlId, {  responseType  : 'arraybuffer' as 'json' });
  }


  // requestForGeoCoordinates() {
  //   return this.http.get<any>(this.googleApiUrl + JSON.stringify({'address': 'Novi Sad'}));
  // }

}
