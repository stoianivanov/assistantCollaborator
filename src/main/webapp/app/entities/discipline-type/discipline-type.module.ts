import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { DisciplineTypeComponent } from './discipline-type.component';
import { DisciplineTypeDetailComponent } from './discipline-type-detail.component';
import { DisciplineTypeUpdateComponent } from './discipline-type-update.component';
import { DisciplineTypeDeleteDialogComponent } from './discipline-type-delete-dialog.component';
import { disciplineTypeRoute } from './discipline-type.route';

@NgModule({
  imports: [AssistantCollaboratorSharedModule, RouterModule.forChild(disciplineTypeRoute)],
  declarations: [
    DisciplineTypeComponent,
    DisciplineTypeDetailComponent,
    DisciplineTypeUpdateComponent,
    DisciplineTypeDeleteDialogComponent
  ],
  entryComponents: [DisciplineTypeDeleteDialogComponent]
})
export class AssistantCollaboratorDisciplineTypeModule {}
