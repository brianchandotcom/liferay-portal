import {Component, OnInit} from '@angular/core';
import {NewTemplateComponent} from "../new-template/new-template.component";
import {PageEvent} from "@angular/material/paginator";
import {MatDialog} from "@angular/material/dialog";
import {foldertemplatesService} from "../../../services/liferay/foldertemplates";
import {foldertemplateinformationsService} from "../../../services/liferay/foldertemplateinformations";

@Component({
  selector: 'app-list-templates',
  templateUrl: './list-templates.component.html',
  styleUrls: ['./list-templates.component.css'],
})
export class ListTemplatesComponent implements OnInit{
  public data: any[] = [];
  public Headers: any[] = [
    "id", "templateName", "createDate"
  ];
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];
  showPageSizeOptions = true;
  pageEvent: PageEvent | any;
  dataPage: Array<any> = [];

  constructor(private foldertemplateinformationsService: foldertemplateinformationsService, private dialog: MatDialog) {

  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadPage();
  }

  isLoading = true;

  async loadPage() {
    this.isLoading = true;
    let data = (await this.foldertemplateinformationsService.getFolderTemplateInformationsPage(null,
        null, null, null, null,
        null, this.pageIndex + 1, this.pageSize, null, null));
    // @ts-ignore
    this.data = data.items as Array<any>;
    // @ts-ignore
    this.length = data.totalCount;
    this.isLoading = false;
  }

  reload() {
    this.pageIndex = 0;
    this.loadPage();
  }

  isCreate: boolean = false;

  create() {
    this.openDialog();
  }

  openDialog() {
    const dialogRef = this.dialog.open(NewTemplateComponent, {
      data: {isDialog: true}
    });
    dialogRef.afterClosed().subscribe(result => {
      switch (result.event) {
        case 'cancel':
          console.log('No record has been created!')
          break;
        case 'created':
          console.log('A record has been created, Reloading data!');
          this.reload();
          break;
      }
    });
  }

  ngOnInit(): void {
    this.loadPage();
  }


}
