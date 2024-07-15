import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './monotoring/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { UnauthorizedComponent } from './component/unauthorized/unauthorized.component';
import { ArticleComponent } from './component/article/article.component';
import { BlogComponent } from './component/blog/blog.component';
import { Blog2Component } from './component/blog2/blog2.component';
import { CarComponent } from './component/car/car.component';
import { CarsComponent } from './component/cars/cars.component';
import { ContactComponent } from './component/contact/contact.component';
import { FaqComponent } from './component/faq/faq.component';
import { ForgotComponent } from './component/forgot/forgot.component';
import { IndexComponent } from './component/index/index.component';
import { Index2Component } from './component/index2/index2.component';
import { PricingComponent } from './component/agence/pricing.component';
import { PrivacyComponent } from './component/privacy/privacy.component';
import { SigninComponent } from './component/signin/signin.component';
import { SignupComponent } from './component/signup/signup.component';
import { AddCarsComponent } from './component/add-cars/add-cars.component';
import { ListeAgenceComponent } from './component/liste-agence/liste-agence.component';
import { AdminComponent } from './component/admin/admin.component';
import { AgenceTableComponent } from './component/agence-table/agence-table.component';
import { AllCarsComponent } from './component/all-cars/all-cars.component';
import { AllLocationComponent } from './component/all-location/all-location.component';
import { ClientTableComponent } from './component/client-table/client-table.component';
import { DriverTableComponent } from './component/driver-table/driver-table.component';
import {EditCarComponent} from "./component/edit-vehicule/edit-cars.component";

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8081',
        realm: 'Pfe-Star',
        clientId: 'Teams-Pfe'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UnauthorizedComponent,
    ArticleComponent,
    BlogComponent,
    Blog2Component,
    CarComponent,
    CarsComponent,
    EditCarComponent,
    ContactComponent,
    FaqComponent,
    ForgotComponent,
    IndexComponent,
    Index2Component,
    PricingComponent,
    PrivacyComponent,
    SigninComponent,
    SignupComponent,
    AddCarsComponent,
    ListeAgenceComponent,
    AdminComponent,
    AgenceTableComponent,
    AllCarsComponent,
    AllLocationComponent,
    ClientTableComponent,
    DriverTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
