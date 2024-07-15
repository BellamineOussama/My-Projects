import { Component, OnInit } from '@angular/core';
import { LocationService } from "../service/location.service";
import { KeycloakService } from "keycloak-angular";
import { UserService } from "../service/user.service";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-driver-profile',
  templateUrl: './driver-profile.component.html',
  styleUrls: ['./driver-profile.component.css'] // Corrected 'styleUrl' to 'styleUrls'
})
export class DriverProfileComponent implements OnInit {
  users: any = [];
  user: any; // Declare user
  user1: any; // Declare user1

  constructor(
    private route: ActivatedRoute,
    public shar: UserService,
    private reservationService: LocationService,
    public kc: KeycloakService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.getUserByID(id);
      } else {
        console.log('No ID provided');
      }
    });

    // Check if the user is logged in
    // this.isLoggedIn = this.kc.isLoggedIn(); // Uncomment if needed and add isLoggedIn property
  }

  getUserByID(id: number): void {
    this.shar.getUserByID(id).subscribe(
      res => {
        this.user = res;
        this.user1 = res;
        console.log(this.user); // Corrected from 'console.log(this.car);' to 'console.log(this.user);'
      },
      err => {
        console.error('Error retrieving user:', err); // Corrected error message
      }
    );
  }
}
