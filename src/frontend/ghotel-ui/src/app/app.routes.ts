import {Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login.component/login.component';
import {RegisterComponent} from './components/auth/register.component/register.component';
import {LogoutComponent} from './components/auth/logout.component/logout.component';
import {HomeComponent} from './components/common/home.component/home.component';
import {RoomListComponent} from './components/room/room-list.component/room-list.component';
import {RoomDetailsComponent} from './components/room/room-details.component/room-details.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
  {path:'rooms',component:RoomListComponent},
  {path:'rooms/:id',component:RoomDetailsComponent}
];
