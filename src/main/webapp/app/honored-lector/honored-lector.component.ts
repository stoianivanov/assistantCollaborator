import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ILectorsModel } from 'app/shared/model/lectors.model';

@Component({
  selector: 'jhi-honored-lector',
  templateUrl: './honored-lector.component.html',
  styleUrls: ['./honored-lector.component.scss']
})
export class HonoredLectorComponent implements OnInit {
  @Input() public lectors: ILectorsModel[] = [];
  @Output() confirmListChange = new EventEmitter<ILectorsModel[]>();
  constructor() {}

  ngOnInit(): void {}

  confirmLectors() {
    this.confirmListChange.emit(this.lectors);
  }
}
