
import { Component, OnInit } from '@angular/core';
import { AgenceService } from '../../service/agence.service';
import {UserService} from "../../service/user.service";
@Component({
  selector: 'app-agence-table',
  templateUrl: './agence-table.component.html',
  styleUrl: './agence-table.component.css'
})
export class AgenceTableComponent implements OnInit {
  constructor(public shar : UserService) {
  }
  agences:any=[];
  ngOnInit(): void {
    this.shar.getUsersRoles('agence').subscribe(
      res => {
        this.agences = res;
        console.log(this.agences)
      },
      err=>{
        console.log(err)
      }
    );

  }
  toggleDetails(id: number): void {
    const agency = this.agences.find((a: { id: number }) => a.id === id);
    if (agency) {
      agency.showDetails = !agency.showDetails;
    }
  }


  // Handle deleting an agency
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
  

