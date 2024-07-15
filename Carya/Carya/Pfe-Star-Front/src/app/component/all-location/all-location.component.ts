import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../service/location.service';
import { CarsService } from '../../service/cars.service';
import { SharService } from '../../shar.service';

@Component({
  selector: 'app-all-location',
  templateUrl: './all-location.component.html',
  styleUrls: ['./all-location.component.css'] // Fix typo: 'styleUrl' should be 'styleUrls'
})
export class AllLocationComponent implements OnInit {
  cars: any[] = [];
  locations: any[] = [];

  constructor(
    public locationService: LocationService,
    public roleService: SharService,
    public carsService: CarsService
  ) {}

  ngOnInit(): void {
    const agenceName = sessionStorage.getItem('username');
    const isAgence = this.roleService.isRole(['agence']);

    if (isAgence && agenceName) {
      this.carsService.getVehiculeByAgence(agenceName).subscribe(
        res => {
          this.cars = res;
          console.log(this.cars);
          this.loadLocationsByVehicules();
        },
        err => {
          console.log(err);
        }
      );
    } else {
      this.locationService.getAllLocatin().subscribe(
        res => {
          this.locations = res;
          console.log(this.locations);
        },
        err => {
          console.log(err);
        }
      );
    }
  }

  loadLocationsByVehicules(): void {
    this.locations = [];
    for (let car of this.cars) {
      this.locationService.getLocationByVehicule(car.id).subscribe(
        res => {
          this.locations.push(...res);
          console.log(this.locations);
        },
        err => {
          console.log(err);
        }
      );
    }
  }
  toggleDetails(id: number): void {
    const agency = this.locations.find((a: { id: number }) => a.id === id);
    if (agency) {
      agency.showDetails = !agency.showDetails;
    }
  }
  delete(id:any){
    this.locationService.deleteLocation(id).subscribe(
      res=>{
        window.location.reload();
      },
      err=>{
        console.log(err)
      }
    );
  }
}
