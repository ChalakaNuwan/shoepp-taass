/* tslint:disable:no-trailing-whitespace */
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CatalogService} from '../../../services/catalog.service';
import {Brand, Product} from '../../../models/product';
import {HttpErrorResponse} from '@angular/common/http';
import {noop} from 'rxjs';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private catalogService: CatalogService,
              private router: Router) { }

  productId: number;
  product: Product;
  public categories: string[];
  public brands: string[];
  public sizes: string[];

  public error = false;

  public productCategories: string[] = [];
  public productBrands: string[] = [];
  public productSizes: string[] = [];

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.getArticle();
    this.getBrands();
    this.getSizes();
    this.getCategories();
  }

  private getArticle(): void {
    this.catalogService.getProduct(this.productId).subscribe(
      (response: Product) => {
        this.product = response;
        this.product.categories.forEach(category => {
          this.productCategories.push(category.name);
        });
        this.product.brands.forEach(brand => {
          this.productBrands.push(brand.name);
        });
        this.product.sizes.forEach(size => {
          this.productSizes.push(size.value);
        });
        console.log(this.productBrands);
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  editProduct(): void {
    console.log(this.product.title);
    const body = {
      id: this.product.id,
      title: this.product.title,
      description: this.product.description,
      picture: this.product.picture,
      price: this.product.price,
      stock: this.product.stock,
      sizes: this.productSizes,
      brands: this.productBrands,
      categories: this.productCategories
    };

    this.catalogService.editProduct(body).subscribe(result => {
        this.router.navigate(['/admin']);
      },
      (error: HttpErrorResponse) => {
        this.error = true;
        console.log('errore add');
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
    this.productBrands.indexOf(brand) === -1 ? this.productBrands.push(brand) : noop();
  }

  addSize(size: string): void {
    this.productSizes.indexOf(size) === -1 ? this.productSizes.push(size) : noop();
  }

  addCategory(category: string): void {
    this.productCategories.indexOf(category) === -1 ? this.productCategories.push(category) : noop();
  }
}
