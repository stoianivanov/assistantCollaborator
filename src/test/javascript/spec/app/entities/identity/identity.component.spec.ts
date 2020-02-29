import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { IdentityComponent } from 'app/entities/identity/identity.component';
import { IdentityService } from 'app/entities/identity/identity.service';
import { Identity } from 'app/shared/model/identity.model';

describe('Component Tests', () => {
  describe('Identity Management Component', () => {
    let comp: IdentityComponent;
    let fixture: ComponentFixture<IdentityComponent>;
    let service: IdentityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [IdentityComponent]
      })
        .overrideTemplate(IdentityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdentityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdentityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Identity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.identities && comp.identities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
