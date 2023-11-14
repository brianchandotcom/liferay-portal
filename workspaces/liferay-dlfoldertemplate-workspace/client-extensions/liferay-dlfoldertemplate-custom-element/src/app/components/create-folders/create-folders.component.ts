import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {TemplateServicesService} from "../../services/backend/template-services.service";
import {FolderSelectorComponent} from "../shared/controls/folder-selector/folder-selector.component";

@Component({
    selector: 'app-create-folders',
    templateUrl: './create-folders.component.html',
    styleUrls: ['./create-folders.component.css']
})
export class CreateFoldersComponent implements OnInit {
    templateForm: FormGroup;
    templateId: any;
    selectedContainerFolder: any;

    constructor(private templateServices: TemplateServicesService, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any | null = null, public dialogRef: MatDialogRef<CreateFoldersComponent> | null, private dialog: MatDialog) {
        this.templateForm = this.fb.group({
            folderName: ['', Validators.required],
            containerId: ['', Validators.required]
        });
    }

    ngOnInit(): void {
        this.templateId = this.data.template;
    }

    public isValid() {
        return this.templateForm.valid;
    }

    public getFormData() {
        if (this.templateForm.valid) {
            const formData = this.templateForm.value;
            return formData;
        }
        return null;
    }

    isStepContentValid() {
        return this.templateForm.valid;
    }

    async save() {
        try {
            let result = await this.templateServices.createFolder(this.templateId, this.selectedContainerFolder.id, this.templateForm.value.folderName);
            if (result) {
                this.closeDialog('created');
            }
        } catch (exp) {
            ///todo alert error
        }

    }

    closeDialog(result: string) {
        if (this.dialogRef) {
            this.dialogRef.close({event: result});
        }
    }

    select() {
        try {
            const dialogRef = this.dialog.open(FolderSelectorComponent, {
                data: {isDialog: true}
            });
            dialogRef.afterClosed().subscribe(result => {
                let selected = result.selected;
                if (selected) {
                    console.log(result.data);
                    // @ts-ignore
                    this.templateForm.get('containerId').setValue(result.data.name);
                    this.selectedContainerFolder = result.data;
                }
            });
        } catch (exp) {
            console.log(exp);
        }

    }
}
