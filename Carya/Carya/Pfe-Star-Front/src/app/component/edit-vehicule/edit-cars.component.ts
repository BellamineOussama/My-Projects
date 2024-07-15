import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarsService } from '../../service/cars.service';

@Component({
  selector: 'app-edit-car',
  templateUrl: './edit-cars.component.html', // Adjusted path
})
export class EditCarComponent implements OnInit {
  car: any = {};

  constructor(private route: ActivatedRoute, private carService: CarsService, private router: Router) {}

  ngOnInit(): void {
    const carId = this.route.snapshot.paramMap.get('id');

    if (carId !== null) {
      const id = +carId; // Conversion de string en number
      this.carService.getVehiculeById(id).subscribe((data: any) => {
        this.car = data;
        console.log(data);
      });
    }
  }

  onUpdate() {
    this.carService.updateCar(this.car.id, this.car).subscribe(
      response => {
        this.router.navigate(['/cars']);
      },
      error => {
        console.error('Error updating car', error);
      }
    );
  }
}
