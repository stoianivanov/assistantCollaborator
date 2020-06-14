import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { DisciplineRecordComponent } from './discipline-record.component';
import { DisciplineRecordDetailComponent } from './discipline-record-detail.component';
import { DisciplineRecordUpdateComponent } from './discipline-record-update.component';
import { DisciplineRecordDeleteDialogComponent } from './discipline-record-delete-dialog.component';
import { disciplineRecordRoute } from './discipline-record.route';

@NgModule({
  imports: [AssistantCollaboratorSharedModule, RouterModule.forChild(disciplineRecordRoute)],
  declarations: [
    DisciplineRecordComponent,
    DisciplineRecordDetailComponent,
    DisciplineRecordUpdateComponent,
    DisciplineRecordDeleteDialogComponent
  ],
  entryComponents: [DisciplineRecordDeleteDialogComponent]
})
export class AssistantCollaboratorDisciplineRecordModule {}
