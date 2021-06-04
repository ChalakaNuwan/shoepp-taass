/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogService} from '../../../../services/catalog.service';
import {Product} from '../../../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private catalogService: CatalogService,
              private router: Router) { }

  productId: number;
  public product: Product = {brands: [], categories: [], description: '', id: 0, picture: '', price: 0, sizes: [], stock: 0, title: ''};
  public categories: string[];
  public brands: string[];
  public sizes: string[];

  public error = false;

  public productCategories: string[] = [];
  public productBrands: string[] = [];
  public productSizes: string[] = [];

  ngOnInit(): void {
    this.getBrands();
    this.getSizes();
    this.getCategories();
  }

  addProduct(): void {
    const body = {
      title: this.product.title,
      description: this.product.description,
      picture: this.product.picture,
      price: this.product.price,
      stock: this.product.stock,
      sizes: this.productSizes,
      brands: this.productBrands,
      categories: this.productCategories
    };

    console.log(this.productBrands);

    this.catalogService.addProduct(body).subscribe(result => {
        this.router.navigate(['/admin']);
      },
      (error: HttpErrorResponse) => {
        this.error = true;
      });
  }

  getBrands(): void {
    this.catalogService.getBrands().subscribe(
      (response: string[]) => {
        this.brands = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  getSizes(): void {
    this.catalogService.getSizs().subscribe(
      (response: string[]) => {
        this.sizes = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  getCategories(): void {
    this.catalogService.getCategories().subscribe(
      (response: string[]) => {
        this.categories = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  onCategoryRemove(tag): void {
    this.productCategories = this.productCategories.filter(item => item !== tag);
    console.log(this.product.categories);
  }

  onBrandRemove(tag): void {
    this.productBrands = this.productBrands.filter(item => item !== tag);
  }

  onSizeRemove(tag): void {
    this.productSizes = this.productSizes.filter(item => item !== tag);
  }

  addBrand(brand: string): void {
    console.log(brand);
    console.log(this.productBrands);
    this.productBrands.indexOf(brand) === -1 ? this.productBrands.push(brand) : noop();
    console.log(this.productBrands);
  }

  addSize(size: string): void {
    this.productSizes.indexOf(size) === -1 ? this.productSizes.push(size) : noop();
  }

  addCategory(category: string): void {
    this.productCategories.indexOf(category) === -1 ? this.productCategories.push(category) : noop();
  }

}
