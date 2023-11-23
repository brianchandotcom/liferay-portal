/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {foldertemplateinformationsService} from '../../../services/liferay/folder-template-informations';

@Component({
	selector: 'app-new-template',
	styleUrls: ['./new-template.component.css'],
	templateUrl: './new-template.component.html',
})
export class NewTemplateComponent {
	templateForm: FormGroup;

	constructor(
		private foldertemplateinformationsService: foldertemplateinformationsService,
		private fb: FormBuilder,
		@Inject(MAT_DIALOG_DATA) public data: any | null = null,
		public dialogRef: MatDialogRef<NewTemplateComponent> | null
	) {
		this.templateForm = this.fb.group({
			description: [''],
			templateName: ['', Validators.required],
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
			const result = await this.foldertemplateinformationsService.postFolderTemplateInformation(
				this.getFormData()
			);
			if (result) {
				this.closeDialog('created');
			}
		}
		catch (exp) {

			// /todo alert error

		}
	}

	closeDialog(result: string) {
		if (this.dialogRef) {
			this.dialogRef.close({event: result});
		}
	}
}
