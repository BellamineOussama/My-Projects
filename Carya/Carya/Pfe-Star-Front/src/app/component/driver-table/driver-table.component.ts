import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import {SharService} from "../../shar.service";

@Component({
  selector: 'app-driver-table',
  templateUrl: './driver-table.component.html',
  styleUrl: './driver-table.component.css'
})
export class DriverTableComponent {
  constructor(public shar : UserService ){
  }
  drivers:any=[];
  ngOnInit(): void {
    this.shar.getUsersRoles('chauffeur').subscribe(
      res =>{
        this.drivers=res;
        console.log(this.drivers)
      },
      err=>{
        console.log(err)
      }
    );
  }
  toggleDetails(id: number): void {
    const agency = this.drivers.find((a: { id: number }) => a.id === id);
    if (agency) {
      agency.showDetails = !agency.showDetails;
    }
  }

  delete(id:any){
    this.shar.deleteuser(id).subscribe(
      res=>{
        window.location.reload();
      },
      err=>{
        console.log(err)
      }
    );
  }
}
