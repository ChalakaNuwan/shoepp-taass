/* tslint:disable:no-trailing-whitespace */
import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {ProfileComponent} from './pages/profile/profile.component';
import {AuthGuard} from './auth/auth.guard';
import {OrdersComponent} from './pages/orders/orders.component';
import {AddressComponent} from './pages/address/address.component';
import {OrderComponent} from './pages/order/order.component';
import {ArticleComponent} from './pages/article/article.component';
import {CartComponent} from './pages/cart/cart.component';
import {CheckoutComponent} from './pages/checkout/checkout.component';
import {OrderSubmittedComponent} from './pages/order-submitted/order-submitted.component';
import {StoreComponent} from './pages/store/store.component';
import {AdminComponent} from './pages/admin/admin.component';
import {EditComponent} from './pages/admin/edit/edit.component';
import {AddComponent} from './pages/admin/add/add/add.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'article/:id', component: ArticleComponent},
  {path: 'store', component: StoreComponent},
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'orders',
    component: OrdersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'address',
    component: AddressComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'order/:id',
    component: OrderComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'cart',
    component: CartComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'orderSubmitted',
    component: OrderSubmittedComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admin/edit/:id',
    component: EditComponent,
    canActivate:  [AuthGuard]
  },
  {
    path: 'admin/add',
    component: AddComponent,
    canActivate:  [AuthGuard]
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
