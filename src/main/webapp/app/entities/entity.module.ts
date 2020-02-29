import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'identity',
        loadChildren: () => import('./identity/identity.module').then(m => m.AssistantCollaboratorIdentityModule)
      },
      {
        path: 'discipline',
        loadChildren: () => import('./discipline/discipline.module').then(m => m.AssistantCollaboratorDisciplineModule)
      },
      {
        path: 'lead-record',
        loadChildren: () => import('./lead-record/lead-record.module').then(m => m.AssistantCollaboratorLeadRecordModule)
      },
      {
        path: 'direction',
        loadChildren: () => import('./direction/direction.module').then(m => m.AssistantCollaboratorDirectionModule)
      },
      {
        path: 'discipline-type',
        loadChildren: () => import('./discipline-type/discipline-type.module').then(m => m.AssistantCollaboratorDisciplineTypeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AssistantCollaboratorEntityModule {}
