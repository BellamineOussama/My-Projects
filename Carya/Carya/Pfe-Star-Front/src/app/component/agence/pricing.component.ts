import { Component, OnInit } from '@angular/core';
import { CarsService } from '../../service/cars.service';
import { LocationService } from '../../service/location.service';
import { KeycloakService } from 'keycloak-angular';
import { SharService } from '../../shar.service';

@Component({
  selector: 'app-pricing',
  templateUrl: './pricing.component.html',
  styleUrls: ['./pricing.component.css']
})
export class PricingComponent implements OnInit {
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

  constructor(
    public shar: CarsService,
    private reservationService: LocationService,
    public kc: KeycloakService,
    public role: SharService
  ) {}

  ngOnInit(): void {}

  cars: any = [];

  onLogOut(): void {
    this.kc.logout(window.location.origin);
  }

  async login(): Promise<void> {
    await this.kc.login({ redirectUri: window.location.origin });
  }

  delete(id: any): void {
    this.shar.deleteVehicule(id).subscribe(
      () => {
        window.location.reload();
      },
      (err) => {
        console.error('Error deleting vehicle:', err);
      }
    );
  }


  isDropdownOpen = false;
  isLoggedIn = false; // Replace with your actual login status check

  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  signOut(): void {
    console.log('Sign out clicked');
  }
}
