import {Component, OnInit} from '@angular/core';
import {CarsService} from "../../service/cars.service";
import {LocationService} from "../../service/location.service";
import {NgForOf, NgIf} from "@angular/common";
import {KeycloakService} from "keycloak-angular";
import {SharService} from "../../shar.service";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    RouterOutlet
  ],
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit{
  constructor(public shar : CarsService, private reservationService : LocationService ,public kc : KeycloakService,public role : SharService) {
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
}
  }
  filterType:any;


  onlogOut(){
    this.kc.logout(window.location.origin);
  }

  async login(){
    await this.kc.login({
      redirectUri: window.location.origin
    });
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

  isDropdownOpen = false;
  isLoggedIn = false; // Replace with your actual login status check

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  signOut() {
    // Implement sign out logic here
    console.log('Sign out clicked');
  }
}
