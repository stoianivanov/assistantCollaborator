import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { HonoredLectorComponent } from 'app/honored-lector/honored-lector.component';
import { LoginComponent } from 'app/login/login.component';

@NgModule({
  imports: [AssistantCollaboratorSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, HonoredLectorComponent, LoginComponent]
})
export class AssistantCollaboratorHomeModule {}
