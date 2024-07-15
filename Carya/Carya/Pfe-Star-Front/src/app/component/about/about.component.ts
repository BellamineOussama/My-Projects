import {Component, OnInit} from '@angular/core';
import {CarsService} from "../../service/cars.service";
import {LocationService} from "../../service/location.service";
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  standalone: true,
  styleUrl: './about.component.css'
})
export class AboutComponent implements OnInit{
  constructor(public shar : CarsService, private reservationService : LocationService, public kc : KeycloakService) {
  }
  cars :any =[]
  avis:any = {
    client:{
      username:''
    }
  }

  onlogOut(){
    this.kc.logout(window.location.origin);
  }

  async login(){
    await this.kc.login({
      redirectUri: window.location.origin
    });
  }

  carId: any;
  startDate: any;
  endDate: any;
  isAvailable: any;
  car1:any;

  idCar:any;
  ngOnInit(): void {
    this.shar.getAllVehicules().subscribe(
      res => {
        this.cars = res;
        this.car1 = res;
        console.log(this.cars)
      },
      err=>{
        console.log(err)
      }
    );

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
