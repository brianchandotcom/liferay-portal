import {Component, Inject, Input} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {foldertemplateinformationsService} from "../../../services/liferay/foldertemplateinformations";

@Component({
    selector: 'app-new-template',
    templateUrl: './new-template.component.html',
    styleUrls: ['./new-template.component.css']
})
export class NewTemplateComponent {
    templateForm: FormGroup;

    constructor(private foldertemplateinformationsService:foldertemplateinformationsService,private fb: FormBuilder,@Inject(MAT_DIALOG_DATA) public data: any | null = null, public dialogRef: MatDialogRef<NewTemplateComponent> | null,) {
        this.templateForm = this.fb.group({
            templateName: ['', Validators.required],
            description: ['']
        });
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
            let result = await this.foldertemplateinformationsService
                .postFolderTemplateInformation(this.getFormData());
            if (result)
            {
                this.closeDialog('created');
            }
        }catch (exp)
        {
            ///todo alert error
        }

    }

    closeDialog(result: string) {
        if (this.dialogRef) {
            this.dialogRef.close({event: result});
        }
    }
}
