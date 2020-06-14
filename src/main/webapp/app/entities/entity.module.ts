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
        path: 'direction',
        loadChildren: () => import('./direction/direction.module').then(m => m.AssistantCollaboratorDirectionModule)
      },
      {
        path: 'discipline-type',
        loadChildren: () => import('./discipline-type/discipline-type.module').then(m => m.AssistantCollaboratorDisciplineTypeModule)
      },
      {
        path: 'discipline-record',
        loadChildren: () => import('./discipline-record/discipline-record.module').then(m => m.AssistantCollaboratorDisciplineRecordModule)
      },
      {
        path: 'class-type',
        loadChildren: () => import('./class-type/class-type.module').then(m => m.AssistantCollaboratorClassTypeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AssistantCollaboratorEntityModule {}
