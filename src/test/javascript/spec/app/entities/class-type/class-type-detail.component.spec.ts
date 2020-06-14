import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssistantCollaboratorTestModule } from '../../../test.module';
import { ClassTypeDetailComponent } from 'app/entities/class-type/class-type-detail.component';
import { ClassType } from 'app/shared/model/class-type.model';

describe('Component Tests', () => {
  describe('ClassType Management Detail Component', () => {
    let comp: ClassTypeDetailComponent;
    let fixture: ComponentFixture<ClassTypeDetailComponent>;
    const route = ({ data: of({ classType: new ClassType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AssistantCollaboratorTestModule],
        declarations: [ClassTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClassTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
