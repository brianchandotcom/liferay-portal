import {Component, Input} from '@angular/core';
import {NewTemplateComponent} from "../../new-template/new-template.component";
import {MatDialog} from "@angular/material/dialog";
import {TemplateDiagramCreateComponent} from "../../diagram/create/create.component";
import {CreateFoldersComponent} from "../../../create-folders/create-folders.component";

@Component({
  selector: 'app-template-list-item',
  templateUrl: './template-list-item.component.html',
  styleUrls: ['./template-list-item.component.css']
})
export class TemplateListItemComponent {

  constructor(private dialog: MatDialog) {
  }
  @Input('record')
  record:any;
  isLoading: any;
  openDesigner()
  {
    const dialogRef = this.dialog.open(TemplateDiagramCreateComponent, {
      data: {template: this.record}
    });
  }

    createFolder() {
    console.log(this.record);
      const dialogRef = this.dialog.open(CreateFoldersComponent, {
        data: {template: this.record.id}
      });
    }
}
