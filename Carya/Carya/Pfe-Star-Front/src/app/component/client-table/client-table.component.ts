import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-client-table',
  templateUrl: './client-table.component.html',
  styleUrl: './client-table.component.css'
})
export class ClientTableComponent implements OnInit {
  constructor(public shar : UserService){
  }
  users:any=[];
  ngOnInit(): void {
    this.shar.getUsersRoles('user').subscribe(
      res => {
        this.users = res;
        console.log(this.users)
      },
      err=>{
        console.log(err)
      }
    );
  }
  toggleDetails(id: number): void {
    const agency = this.users.find((a: { id: number }) => a.id === id);
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
