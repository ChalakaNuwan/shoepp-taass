/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import * as Feather from 'feather-icons';
import {CatalogService} from '../../services/catalog.service';
import {Product} from '../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {newArray} from '@angular/compiler/src/util';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit, AfterViewInit {

  constructor(private catalogService: CatalogService) { }

  public products: Product[];
  public categories: string[];
  public brands: string[];
  public sizes: string[];

  public categoriesFilter: string[] = [];
  public brandsFilter: string[] = [];
  public sizesFilter: string[] = [];
  public pricelowFilter: string;
  public pricehighFilter: string;
  public searchFilter: string;
  public orderFilter: string;

  ngOnInit(): void {
    this.getBrands();
    this.getSizes();
    this.getCategories();
    this.loadProducts();
  }

  ngAfterViewInit(): void {
    Feather.replace();
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

  public loadProducts(): void {
    this.catalogService.getFilterProducts({}).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  filterProducts(): void {
    console.log(this.pricehighFilter);

    const filters = {
      size: this.sizesFilter,
      category: this.categoriesFilter,
      brand: this.brandsFilter,
      pricelow: this.pricelowFilter,
      pricehigh: this.pricehighFilter,
      sort: this.orderFilter,
      //page: 0,
      search: this.searchFilter
    };

    this.catalogService.getFilterProducts(filters).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );

  }

  categoryChange(category, event): void {
    if (event.currentTarget.checked) {
      this.categoriesFilter.push(category);
    } else {
      this.categoriesFilter = this.categoriesFilter.filter(item => item !== category);
    }
  }

  sizeChange(size, event): void {
    if (event.currentTarget.checked) {
      this.sizesFilter.push(size);
    } else {
      this.sizesFilter = this.sizesFilter.filter(item => item !== size);
    }
  }

  brandChange(brand, event): void {
    if (event.currentTarget.checked) {
      this.brandsFilter.push(brand);
    } else {
      this.brandsFilter = this.brandsFilter.filter(item => item !== brand);
    }
  }

  orderChange(s: string): void {
    this.orderFilter = s;
    this.filterProducts();
  }
}
