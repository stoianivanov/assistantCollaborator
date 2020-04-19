import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { AssistantCollaboratorCoreModule } from 'app/core/core.module';
import { AssistantCollaboratorAppRoutingModule } from './app-routing.module';
import { AssistantCollaboratorHomeModule } from './home/home.module';
import { AssistantCollaboratorEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HonoredLectorComponent } from './honored-lector/honored-lector.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  imports: [
    BrowserModule,
    AssistantCollaboratorSharedModule,
    AssistantCollaboratorCoreModule,
    AssistantCollaboratorHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AssistantCollaboratorEntityModule,
    AssistantCollaboratorAppRoutingModule,
    NgbModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class AssistantCollaboratorAppModule {}
