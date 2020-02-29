import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdentity } from 'app/shared/model/identity.model';
import { IdentityService } from './identity.service';
import { IdentityDeleteDialogComponent } from './identity-delete-dialog.component';

@Component({
  selector: 'jhi-identity',
  templateUrl: './identity.component.html'
})
export class IdentityComponent implements OnInit, OnDestroy {
  identities?: IIdentity[];
  eventSubscriber?: Subscription;

  constructor(protected identityService: IdentityService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.identityService.query().subscribe((res: HttpResponse<IIdentity[]>) => (this.identities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIdentities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdentity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIdentities(): void {
    this.eventSubscriber = this.eventManager.subscribe('identityListModification', () => this.loadAll());
  }

  delete(identity: IIdentity): void {
    const modalRef = this.modalService.open(IdentityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.identity = identity;
  }
}
