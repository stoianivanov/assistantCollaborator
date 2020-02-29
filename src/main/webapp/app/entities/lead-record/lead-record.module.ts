import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AssistantCollaboratorSharedModule } from 'app/shared/shared.module';
import { LeadRecordComponent } from './lead-record.component';
import { LeadRecordDetailComponent } from './lead-record-detail.component';
import { LeadRecordUpdateComponent } from './lead-record-update.component';
import { LeadRecordDeleteDialogComponent } from './lead-record-delete-dialog.component';
import { leadRecordRoute } from './lead-record.route';

@NgModule({
  imports: [AssistantCollaboratorSharedModule, RouterModule.forChild(leadRecordRoute)],
  declarations: [LeadRecordComponent, LeadRecordDetailComponent, LeadRecordUpdateComponent, LeadRecordDeleteDialogComponent],
  entryComponents: [LeadRecordDeleteDialogComponent]
})
export class AssistantCollaboratorLeadRecordModule {}
