import { Component } from '@angular/core';
import { CarsService } from '../../service/cars.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-cars',
  templateUrl: './add-cars.component.html',
  styleUrls: ['./add-cars.component.css']
})
export class AddCarsComponent {

  car: any = {};
  selectedFile: File | null = null;

  constructor(private carsService: CarsService, private router: Router) {}

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onSubmit1(): void {
    if (!this.car || !this.selectedFile) {
      console.error('Car details or image file is missing');
      return;
    }

    const formData = new FormData();
    formData.append('vehicule', JSON.stringify(this.car));
    formData.append('image', this.selectedFile, this.selectedFile.name);

    console.log('FormData content:', formData.get('vehicule'), formData.get('image'));

    this.carsService.createVehicule1(formData).subscribe(
      response => {
        console.log('Car added successfully', response);
        this.car = {};
        this.selectedFile = null;
        this.router.navigate(['/cars']);
      },
      error => {
        console.error('Error adding car', error);
      }
    );
  }
  typecar: string = 'Voiture'; // Default value

  onSelectChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.typecar = selectElement.value;
    console.log(this.typecar)
  }

    onSubmit(): void {


      this.car.agenceName = sessionStorage.getItem('username');
      this.car.typeVehicule = this.typecar;

      this.carsService.createVehicule(this.car).subscribe(
        response => {
          console.log('Car added successfully', response);
          this.car = {};
          this.selectedFile = null;
          this.router.navigate(['/cars']);
        },
        error => {
          console.error('Error adding car', error);
        }
      );
    }
}
