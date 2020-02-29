import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { IdentityDetailComponent } from 'app/entities/identity/identity-detail.component';
import { Identity } from 'app/shared/model/identity.model';

describe('Component Tests', () => {
  describe('Identity Management Detail Component', () => {
    let comp: IdentityDetailComponent;
    let fixture: ComponentFixture<IdentityDetailComponent>;
    const route = ({ data: of({ identity: new Identity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [IdentityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IdentityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdentityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load identity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.identity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
