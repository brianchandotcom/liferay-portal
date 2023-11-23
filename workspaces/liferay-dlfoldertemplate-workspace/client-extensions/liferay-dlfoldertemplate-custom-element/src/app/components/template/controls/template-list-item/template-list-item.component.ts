/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';

import {FolderTemplatesService} from '../../../../services/dynamic/folder-templates';
import {foldertemplateinformationsService} from '../../../../services/liferay/folder-template-informations';
import {CreateFoldersComponent} from '../../../create-folders/create-folders.component';
import {TemplateDiagramCreateComponent} from '../../diagram/create/create.component';
declare const Liferay: any;
@Component({
	selector: 'app-template-list-item',
	styleUrls: ['./template-list-item.component.css'],
	templateUrl: './template-list-item.component.html',
})
export class TemplateListItemComponent {
	constructor(
		private dialog: MatDialog,
		private folderTemplatesService: FolderTemplatesService,
		private _foldertemplateinformationsService: foldertemplateinformationsService
	) {}
	@Output('reload')
	functionEmitter: EventEmitter<any> = new EventEmitter<any>();
	@Input('record')
	record: any;
	isLoading: any;
	isDeleting: boolean = false;

	openDesigner() {
		this.dialog.open(TemplateDiagramCreateComponent, {
			data: {template: this.record},
		});
	}

	createFolder() {
		this.dialog.open(CreateFoldersComponent, {
			data: {template: this.record.id},
		});
	}

	async deleteTemplate() {
		try {
			this.isDeleting = true;
			const data: any = await this.folderTemplatesService.getFolderTemplatesPage(
				null,
				null,
				null,
				null,
				null,
				`templateID eq ${this.record.id}`
			);
			await this.folderTemplatesService.deleteFolderTemplateBatch(
				data.items
			);
			await this._foldertemplateinformationsService.deleteFolderTemplateInformation(
				this.record.id
			);
			Liferay.Util.openToast({title: 'Template Deleted!'});
			this.functionEmitter.emit();
			this.isDeleting = false;
		}
		catch (exp: any) {
			Liferay.Util.openToast({title: exp.message, type: 'danger'});
		}
	}
}
