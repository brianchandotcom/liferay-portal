import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {foldertemplatesService} from "../../../../../services/liferay/foldertemplates";

@Component({
    selector: 'app-edit-form',
    templateUrl: './edit-form.component.html',
    styleUrls: ['./edit-form.component.css']
})
export class EditFormComponent implements OnInit {

    folder: FormGroup | any;
    event: string = "";
    parent: number = 0;
    node: any;
    currentFolderId: any = 0;
    templateID: any = 0;

    constructor(private cdr: ChangeDetectorRef, private foldertemplatesService: foldertemplatesService, private fb: FormBuilder, public dialogRef: MatDialogRef<EditFormComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {

    }

    public getFormData() {
        if (this.folder.valid) {
            const formData = this.folder.value;
            return formData;
        }
        return null;
    }

    cancel() {
        this.dialogRef.close({event: this.event, status: 'cancel'});
    }

    async save() {
        if (this.folder.valid) {
            let formData: any = this.folder.value;
            formData["templateID"] = this.templateID
            let updatedNode = {
                "description": formData.description,
                "name": formData.name,
                "parentID": this.parent,
                "templateID": this.templateID
            }
            this.node.name = formData.name;
            this.node.description = formData.description;
            await this.foldertemplatesService.putFolderTemplate(this.node.id, updatedNode);
            this.dialogRef.close({event: this.event, status: 'success', data: this.node});
        }
    }

    ngOnInit(): void {
        console.log(this.data);

        if (this.data) {
            this.event = this.data.event;
            this.templateID = this.data.node.templateID;
            this.node = this.data.node;
            if (this.data.node.pid) {
                this.parent = this.data.node.pid;
                this.currentFolderId = this.data.node.id;
            }
            console.log(this.node);
            this.folder = this.fb.group({
                name: [this.node.name, Validators.required],
                description: [this.node.description]
            });
            this.cdr.detectChanges();
        } else {
            this.folder = this.fb.group({
                name: ['', Validators.required],
                description: ['']
            });
        }
    }
}
