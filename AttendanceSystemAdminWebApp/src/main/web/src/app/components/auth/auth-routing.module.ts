import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
// import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
// import { ChangePasswordComponent } from '../auth/change-password/change-password.component';
// import { WelcomepageComponent } from '../layout/pages/Welcomepage/welcomepage/welcomepage.component';

const routes: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: '',
        component: LoginComponent
      },
      // {
      //   path: 'forgot-password',
      //   component: ForgotPasswordComponent
      // }
      // ,
      // {
      //   path: 'change-password',
      //   component: ChangePasswordComponent
      // },
      // {
      //   path: 'welcome',
      //   component: WelcomepageComponent,
      // }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
