import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { ClassTypeComponent } from './class-type.component';
import { ClassTypeDetailComponent } from './class-type-detail.component';
import { ClassTypeUpdateComponent } from './class-type-update.component';
import { ClassTypeDeleteDialogComponent } from './class-type-delete-dialog.component';
import { classTypeRoute } from './class-type.route';

@NgModule({
  imports: [AssistantCollaboratorSharedModule, RouterModule.forChild(classTypeRoute)],
  declarations: [ClassTypeComponent, ClassTypeDetailComponent, ClassTypeUpdateComponent, ClassTypeDeleteDialogComponent],
  entryComponents: [ClassTypeDeleteDialogComponent]
})
export class AssistantCollaboratorClassTypeModule {}
