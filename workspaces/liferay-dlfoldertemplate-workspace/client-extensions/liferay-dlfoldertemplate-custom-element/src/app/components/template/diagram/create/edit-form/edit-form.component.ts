/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
@Component({
	selector: 'app-edit-form',
	styleUrls: ['./edit-form.component.css'],
	templateUrl: './edit-form.component.html',
})
export class EditFormComponent implements OnInit {
	folder: FormGroup | any;
	event: string = '';
	parent: number = 0;
	node: any;
	currentFolderId: any = 0;
	templateID: any = 0;
	foldertemplatesService: any;
	constructor(
		private cdr: ChangeDetectorRef,
		private fb: FormBuilder,
		public dialogRef: MatDialogRef<EditFormComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any
	) {}

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
			const formData: any = this.folder.value;
			formData['templateID'] = this.templateID;
			const updatedNode = {
				description: formData.description,
				name: formData.name,
				parentID: this.parent,
				templateID: this.templateID,
			};
			this.node.name = formData.name;
			this.node.description = formData.description;
			await this.foldertemplatesService.putFolderTemplate(
				this.node.id,
				updatedNode
			);
			this.dialogRef.close({
				data: this.node,
				event: this.event,
				status: 'success',
			});
		}
	}

	ngOnInit(): void {
		if (this.data) {
			this.event = this.data.event;
			this.templateID = this.data.node.templateID;
			this.foldertemplatesService = this.data.foldertemplatesService;
			this.node = this.data.node;
			if (this.data.node.pid) {
				this.parent = this.data.node.pid;
				this.currentFolderId = this.data.node.id;
			}
			this.folder = this.fb.group({
				description: [this.node.description],
				name: [this.node.name, Validators.required],
			});
			this.cdr.detectChanges();
		}
		else {
			this.folder = this.fb.group({
				description: [''],
				name: ['', Validators.required],
			});
		}
	}
}
