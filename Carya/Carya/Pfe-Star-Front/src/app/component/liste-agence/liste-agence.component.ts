import { Component, OnInit } from '@angular/core';
import {AgenceService} from '../../service/agence.service';

@Component({
  selector: 'app-liste-agence',
  templateUrl: './liste-agence.component.html',
  styleUrl: './liste-agence.component.css'
})
export class ListeAgenceComponent implements OnInit{
  constructor(private service : AgenceService){}
  agence : any=[]
  ngOnInit(): void {
      this.service.getAllanys().subscribe(
        res=>{
          this.agence=res;
          console.log(this.agence);
        },
        err=>{
          console.log(err);
        }
      );
  }

}
