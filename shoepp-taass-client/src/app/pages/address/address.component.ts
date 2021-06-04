/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {User} from '../../models/User';
import {UserService} from '../../services/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import * as Feather from 'feather-icons';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit, AfterViewInit {

  public user: User;
  currentDate = new Date();
  successMsg = false;
  errorMsg = false;
  addressFormGroup: FormGroup;

  constructor(private router: Router,
              private userService: UserService) { }

  ngOnInit(): void {
    this.getUser();
    this.initAddressForm();
  }

  public getUser(): void {
    this.userService.get().subscribe(
      (response: User) => {
        console.log(response?.address.city);
        this.user = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  updateAddress(): void {
    if (this.addressFormGroup.valid) {
      this.userService.updateAddress(this.addressFormGroup.value).subscribe(result => {
          this.errorMsg = false;
          this.successMsg = true;
        },
        (error: HttpErrorResponse) => {
          this.successMsg = false;
          this.errorMsg = true;
        });
    } else {
      this.successMsg = false;
      this.errorMsg = true;
    }
  }

  initAddressForm(): void {
    this.addressFormGroup = new FormGroup({
      streetAddress: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      country: new FormControl('', [Validators.required]),
      zipCode: new FormControl('', [Validators.required]),
    });
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/home']);
  }

  closeMsg(): void {
    this.successMsg = false;
    this.errorMsg = false;
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
