import { Injectable } from '@angular/core';
import {KeycloakService} from "keycloak-angular";

@Injectable({
  providedIn: 'root'
})
export class SharService {

  constructor(private kc : KeycloakService) { }

  isRole(Role: string[]): boolean {
    const isLoggedIn = this.kc.isLoggedIn();
    if (isLoggedIn) {
      const userRoles = this.kc.getUserRoles();
      for (const role of Role) {
        if (userRoles.includes(role)) {
          return true;
        }
      }
    }
    return false;
  }
}
