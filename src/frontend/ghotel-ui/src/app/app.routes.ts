import {Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login.component/login.component';
import {RegisterComponent} from './components/auth/register.component/register.component';
import {LogoutComponent} from './components/auth/logout.component/logout.component';
import {HomeComponent} from './components/common/home.component/home.component';
import {RoomListComponent} from './components/rooms/room-list.component/room-list.component';

export const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent},
  {path:'rooms',component:RoomListComponent}
];
