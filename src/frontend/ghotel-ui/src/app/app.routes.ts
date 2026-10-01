import {Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login.component/login.component';
import {RegisterComponent} from './components/auth/register.component/register.component';
import {LogoutComponent} from './components/auth/logout.component/logout.component';
import {HomeComponent} from './components/common/home.component/home.component';
import {RoomListComponent} from './components/room/room-list.component/room-list.component';
import {RoomDetailsComponent} from './components/room/room-details.component/room-details.component';
import {CustomerFormComponent} from './components/customer/customer-form.component/customer-form.component';
import {ReservationFormComponent} from './components/reservation/reservation-form.component/reservation-form.component';
import {ReservationListComponent} from './components/reservation/reservation-list.component/reservation-list.component';
import {
  ReservationDetailsComponent
} from './components/reservation/reservation-details.component/reservation-details.component';
import {RoomFormComponent} from './components/room/room-form.component/room-form.component';
import {SuccessComponent} from './components/common/success.component/success.component';
import {UnauthorizedComponent} from './components/common/unauthorized.component/unauthorized.component';
import {UserListComponent} from './components/admin/user-list.component/user-list.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'success', component: SuccessComponent},
  // {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
  {path:'rooms',component:RoomListComponent},
  {path: 'rooms/:id', component: RoomDetailsComponent},
  {path: 'reservation/new/customer', component: CustomerFormComponent},
  {path: 'reservation/new/details', component: ReservationFormComponent},
  {path: 'reservation/new/rooms', component: RoomFormComponent},
  {path:'reservations',component:ReservationListComponent},
  {path: 'reservations/:id', component: ReservationDetailsComponent},
  {path: 'unauthorized', component: UnauthorizedComponent},
  {path: 'admin', component: UserListComponent}
];
