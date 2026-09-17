import {Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login.component/login.component';
import {RegisterComponent} from './components/auth/register.component/register.component';
import {LogoutComponent} from './components/auth/logout.component/logout.component';

export const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'logout', component: LogoutComponent}
];
