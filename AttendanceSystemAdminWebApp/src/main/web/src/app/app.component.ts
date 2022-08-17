import { Component } from '@angular/core';
import { Router, Event, NavigationStart } from '@angular/router';
import { NgxPermissionsService, NgxRolesService } from 'ngx-permissions';
// import { Role } from './models/role';
import { CommonService } from './services/common.service';
import { CustomLoaderComponent } from '../../src/app/components/shared/custom-loader/custom-loader.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  // providers: [Role]
})
export class AppComponent {
  title = 'PartnerPortal';
  public CustomLoader = CustomLoaderComponent;
  route: string = '';
  userRole: string[] = [];
  constructor(private roleService: NgxRolesService,
    private permissionService: NgxPermissionsService,
    private router: Router,
    private dataService: CommonService,
    // private role: Role,
    ) {
    router.events.subscribe((event: Event) => {
      if (event instanceof NavigationStart) {

        this.route = event.url.replace('/', '').replace('#', '').split('?')[0];
        if (this.route !== '' && this.route !== '/' && this.route !== 'forgot-password' && this.route.split('/')[0] !== 'forgot-password' && this.route.split('/')[0] !== 'change-password' && this.route.split('/')[0] !== 'welcome' && this.route.split('/')[0] !== 'maintenancemode') {
          let user = localStorage.getItem('token');
          if (!user) {
            this.router.navigateByUrl('');
          }
          // this.userRole = this.dataService.decryptData(user);
          // if (this.userRole) {

          //   if (this.userRole.toString() == this.role.admin) {
          //     this.permissionService.loadPermissions([this.role.admin])
          //     this.roleService.addRole('ADMIN', [this.role.admin]);
          //   }
          //   if (this.userRole.toString() == this.role.user) {
          //     this.permissionService.loadPermissions([this.role.user])
          //     this.roleService.addRole('USER', [this.role.user]);
          //   }
          // }
        }
      }
    });
    // setTimeout(() => { this.router.navigateByUrl('') }, 60000);
  }
}
