import { Component, OnInit } from '@angular/core';
import { CarsService } from "../../service/cars.service";
import { LocationService } from "../../service/location.service";
import { KeycloakService } from "keycloak-angular";
import { ActivatedRoute } from "@angular/router";
import {SharService} from "../../shar.service";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css'] // Fixed the incorrect `styleUrl` to `styleUrls`
})
export class CarComponent implements OnInit {

  car: any = [];
  avis: any = {
    client: {
      username: ''
    }
  };

  carId: any;
  startDate: any;
  endDate: any;
  isAvailable: any;
  car1: any;
  idCar: any;
  isDropdownOpen = false;
  isLoggedIn = false; // Replace with your actual login status check

  constructor(
    public shar: CarsService,
    private reservationService: LocationService,
    public kc: KeycloakService,
    private route: ActivatedRoute, public role : SharService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.getVehiculeById(id);
      } else {
        console.log('No ID provided');
      }
    });

    // Check if the user is logged in
    this.isLoggedIn = this.kc.isLoggedIn();
  }

  getVehiculeById(id: number): void {
    this.shar.getVehiculeById(id).subscribe(
      res => {
        this.car = res;
        this.car1 = res;
        console.log(this.car);
      },
      err => {
        console.error('Error retrieving vehicle:', err);
      }
    );
  }

  onlogOut() {
    this.kc.logout(window.location.origin);
  }

  async login() {
    await this.kc.login({
      redirectUri: window.location.origin
    });
  }

  delete(id: any) {
    this.shar.deleteVehicule(id).subscribe(
      res => {
        window.location.reload();
      },
      err => {
        console.log(err);
      }
    );
  }

  saveId(id: any) {
    this.idCar = id;
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  signOut() {
    this.kc.logout(window.location.origin);
    this.isLoggedIn = false;
  }

  isUserInAdminOrAgenceRole(): boolean {
    return this.kc.isUserInRole('ADMIN') || this.kc.isUserInRole('AGENCE');
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



  withChauffeur: boolean = false;
}
