import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassType } from 'app/shared/model/class-type.model';
import { ClassTypeService } from './class-type.service';
import { ClassTypeDeleteDialogComponent } from './class-type-delete-dialog.component';

@Component({
  selector: 'jhi-class-type',
  templateUrl: './class-type.component.html'
})
export class ClassTypeComponent implements OnInit, OnDestroy {
  classTypes?: IClassType[];
  eventSubscriber?: Subscription;

  constructor(protected classTypeService: ClassTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.classTypeService.query().subscribe((res: HttpResponse<IClassType[]>) => (this.classTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClassTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IClassType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInClassTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('classTypeListModification', () => this.loadAll());
  }

  delete(classType: IClassType): void {
    const modalRef = this.modalService.open(ClassTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.classType = classType;
  }
}
