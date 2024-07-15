import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddCarsComponent } from './add-cars.component';
import { CarsService } from '../../service/cars.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('AddCarsComponent', () => {
  let component: AddCarsComponent;
  let fixture: ComponentFixture<AddCarsComponent>;
  let mockCarsService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockCarsService = {
      createVehicule: jasmine.createSpy('createVehicule').and.returnValue(of({}))
    };
    mockRouter = {
      navigate: jasmine.createSpy('navigate')
    };

    await TestBed.configureTestingModule({
      declarations: [AddCarsComponent],
      imports: [FormsModule, HttpClientTestingModule, RouterTestingModule],
      providers: [
        { provide: CarsService, useValue: mockCarsService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call createVehicule on submit', () => {
    component.car = { modele: 'Test Model', year: 2020 };
    component.selectedFile = new File([''], 'filename');
    component.onSubmit();

    expect(mockCarsService.createVehicule).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/cars']);
  });

  it('should handle file input change', () => {
    const event = {
      target: {
        files: [new File([''], 'testfile.jpg')]
      }
    };
    component.onFileSelected(event);
    expect(component.selectedFile).toBe(event.target.files[0]);
  });
});
