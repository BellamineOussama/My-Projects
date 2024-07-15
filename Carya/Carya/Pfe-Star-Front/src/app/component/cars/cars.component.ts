import { Component, OnInit } from '@angular/core';
import { CarsService } from '../../service/cars.service'
import { LocationService } from '../../service/location.service';
import {KeycloakService} from "keycloak-angular";
import {SharService} from "../../shar.service";
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrl: './cars.component.css'
})
export class CarsComponent implements OnInit{
  constructor( private router: Router,public shar : CarsService, private reservationService : LocationService ,public kc : KeycloakService, public role : SharService) {
  }
  cars :any =[]
  avis:any = {
    client:{
      username:''
    }
  }

  carId: any;
  startDate: any;
  endDate: any;
  isAvailable: any;
  car1:any;

  idCar:any;
  ngOnInit(): void {
    const isAgence = this.role.isRole(['agence']);
    if (isAgence) {
      // Assume agenceName is available in session or context
      const agenceName = sessionStorage.getItem('username');
      if (agenceName) {
        this.shar.getVehiculeByAgence(agenceName).subscribe(
          res => {
            this.cars = res;
            this.car1 = res;
            console.log(this.cars);
          },
          err => {
            console.log(err);
          }
        );
      } else {
        console.log('No agence name found in session storage');
      }
    } else {
      this.shar.getAllVehicules().subscribe(
        res => {
          this.cars = res;
          this.car1 = res;
          console.log(this.cars);
        },
        err => {
          console.log(err);
        }
      );
    }

  }
  filterType:any;


  filterr(test :any){

    this.filterType=test;
    this.cars= this.car1;
    if(test ==='All'){
      return
    }
    this.cars = this.cars.filter((car: {typeVehicule: any; type: any; }) => car.typeVehicule === this.filterType);
  }

  delete(id:any){
    this.shar.deleteVehicule(id).subscribe(
      res=>{
        window.location.reload();
      },
      err=>{
        console.log(err)
      }
    );
  }

  saveId(id:any){
    this.idCar=id;
  }

  addAvis(){
    this.avis.client.username=sessionStorage.getItem('username')
    this.shar.addAvis(this.avis).subscribe(
      res=>{
        this.shar.addAvisVoiture(this.idCar,res).subscribe(
          res1=>{
           window.location.reload();
          },
          err=>{
            console.log(err);
          }
        );
      },
      err=>{
        console.log(err)
      }
    );
  }



  checkAvailability() {
    this.reservationService.checkAvailability(this.idCar, this.startDate, this.endDate).subscribe(available => {
      this.isAvailable = available;
      console.log(available)
    });
  }

  createReservation() {
    const reservation = {
      userId: '52e73823-9012-4434-9698-9e33bd8d06a7',
      carId: this.idCar,
      startDate: this.startDate,
      endDate: this.endDate,
      withChouffer: this.withChauffeur
    };
    this.reservationService.createReservation(reservation).subscribe(
      res=>{
        console.log(res)
        window.location.reload();
      },err=>{
        alert("Chauffeur not found");
        window.location.reload();
      }
    );
  }

  isDropdownOpen = false;
  isLoggedIn = false; // Replace with your actual login status check

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  signOut() {
    // Implement sign out logic here
    console.log('Sign out clicked');
  }
  onlogOut(){
    this.kc.logout(window.location.origin);
  }

  async login(){
    await this.kc.login({
      redirectUri: window.location.origin
    });
  }

  withChauffeur: boolean = false;
  onEdit(carid:any): void {
    console.log(carid)
    this.router.navigate(['/cars/edit/', carid]);
  }
}
