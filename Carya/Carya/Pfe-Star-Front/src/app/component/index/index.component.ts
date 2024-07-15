import {Component, OnInit} from '@angular/core';
import {CarsService} from "../../service/cars.service";
import {LocationService} from "../../service/location.service";
import { KeycloakService } from 'keycloak-angular';
import {SharService} from "../../shar.service";
import {Router} from "@angular/router";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent implements OnInit{
  constructor(private router: Router, public shar : CarsService, private reservationService : LocationService ,public kc : KeycloakService, public role : SharService) {
  }
  agencies = [
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/europcar_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/sixt_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/centauro_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/sicily_by_car_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/dollar_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/windy_car_rental_logo_lrg.gif' },
    { logo: 'https://cdn2.rcstatic.com/images/supplier_logos/greenmotion_logo_lrg.gif' },
    // Add more agencies as needed
  ];

  cars :any =[]
  filtredCars :any = []
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
  search1:any;
  search2:any;
  avvvv:any;

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
          this.filtredCars=res;
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

  filterCars() {
    this.filtredCars=[]
    for (let i=0 ; i<this.cars.length;i++){
      this.reservationService.checkAvailability(this.cars[i].id , this.search1, this.search2).subscribe(available => {
        console.log(available)
        if(available){
          this.filtredCars.push(this.cars[i])
        }

      });


    }

  }

  checkAvailability() {
    this.reservationService.checkAvailability(this.idCar, this.startDate, this.endDate).subscribe(available => {
      this.isAvailable = available;
      console.log(available)
    });
  }

  createReservation() {
    const reservation = {
      userId: sessionStorage.getItem('username'),
      carId: this.idCar,
      startDate: this.startDate,
      endDate: this.endDate
    };
    this.reservationService.createReservation(reservation).subscribe(
      res=>{
        console.log(res)
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

