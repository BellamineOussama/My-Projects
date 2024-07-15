import {Component, OnInit} from '@angular/core';
import {CarsService} from "../../service/cars.service";
import {LocationService} from "../../service/location.service";
import {KeycloakService} from "keycloak-angular";
import {SharService} from "../../shar.service";

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrl: './faq.component.css'
})
export class FaqComponent implements OnInit{
  constructor(public shar : CarsService, private reservationService : LocationService ,public kc : KeycloakService , public role : SharService) {
  }

  ngOnInit(): void {
        throw new Error('Method not implemented.');
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
}
