import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { HonoredLectorService } from 'app/honored-lector/honored-lector.service';
import { ILectorsModel } from 'app/shared/model/lectors.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  headers: string[] = [];
  honoredLectors: ILectorsModel[] = [];

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private honoredLectorService: HonoredLectorService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  // handleFileInput(fileInput: any) {
  //   let reader: FileReader = new FileReader();
  //
  //   reader.readAsText(fileInput.target.files[0]);
  //
  //   reader.onload = e => {
  //     let csv: string = '' + reader.result;
  //     let allTextLines = csv.split(/\r|\n|\r/);
  //     this.headers = allTextLines[0].split(',');
  //
  //     let result: any[] = [];
  //     allTextLines = allTextLines.slice(1);
  //     for (let row of allTextLines) {
  //       let obj = {};
  //       if (row.length > 0) {
  //         let data: string[] = row.split(',');
  //         for (let i = 0; i < this.headers.length; ++i) {
  //           obj[this.headers[i]] = data[i];
  //         }
  //         result.push(obj);
  //       }
  //     }
  //     console.log(result);
  //   };
  // }

  handleHonoredLecotor(fileInput: any): void {
    this.honoredLectors = this.honoredLectorService.readHonoredLectors(fileInput);
  }

  updateDisciplineAndLectos(lectors: ILectorsModel[]) {
    console.log(lectors);
  }
}
