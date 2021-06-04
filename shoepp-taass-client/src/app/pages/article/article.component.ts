/* tslint:disable:no-trailing-whitespace */
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {CatalogService} from '../../services/catalog.service';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {Product} from '../../models/product';
import {CartService} from '../../services/cart.service';
import {CartItem} from '../../models/cart';
import * as Feather from 'feather-icons';


@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit, AfterViewInit {

  productId: number;
  product: Product;
  quantity: number;
  size: number;

  errMsg = false;
  succMsg = false;

  constructor(private route: ActivatedRoute,
              private catalogService: CatalogService,
              private cartService: CartService) { }

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('id'));
    this.getArticle();
  }

  private getArticle(): void {
    this.catalogService.getProduct(this.productId).subscribe(
      (response: Product) => {
        this.product = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  public addCart(): void {
    if (this.quantity > this.product.stock) {
      this.succMsg = false;
      this.errMsg = true;
    } else {
      const item: CartItem =  {
        id: 0,
        productId: this.productId,
        quantity: Number(this.quantity),
        size: Number(this.size),
        unitPrice: this.product.price
      };

      this.cartService.addProduct(item).subscribe(result => {
          this.errMsg = false;
          this.succMsg = true;
          const value = Number(localStorage.getItem('cartItems')) + 1;
          localStorage.setItem('cartItems', value.toString());
        },
        (error: HttpErrorResponse) => {
          this.succMsg = false;
          this.errMsg = true;
        });
    }
  }

  ngAfterViewInit(): void {
    Feather.replace();
  }
}
