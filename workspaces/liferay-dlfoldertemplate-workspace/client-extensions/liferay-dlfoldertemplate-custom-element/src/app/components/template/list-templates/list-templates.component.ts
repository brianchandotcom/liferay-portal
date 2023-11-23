/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {PageEvent} from '@angular/material/paginator';

import {foldertemplateinformationsService} from '../../../services/liferay/folder-template-informations';
import {NewTemplateComponent} from '../new-template/new-template.component';
declare const Liferay: any;
@Component({
	selector: 'app-list-templates',
	styleUrls: ['./list-templates.component.css'],
	templateUrl: './list-templates.component.html',
})
export class ListTemplatesComponent implements OnInit {
	public data: any[] = [];
	public Headers: any[] = ['id', 'templateName', 'createDate'];
	length = 50;
	pageSize = 10;
	pageIndex = 0;
	pageSizeOptions = [5, 10, 25];
	showPageSizeOptions = true;
	pageEvent: PageEvent | any;
	dataPage: Array<any> = [];

	constructor(
		private foldertemplateinformationsService: foldertemplateinformationsService,
		private dialog: MatDialog
	) {}

	handlePageEvent(page: PageEvent) {
		this.pageEvent = page;
		this.length = page.length;
		this.pageSize = page.pageSize;
		this.pageIndex = page.pageIndex;
		this.loadPage();
	}

	isLoading = true;

	async loadPage() {
		this.isLoading = true;
		const data = await this.foldertemplateinformationsService.getFolderTemplateInformationsPage(
			null,
			null,
			null,
			null,
			null,
			null,
			this.pageIndex + 1,
			this.pageSize,
			null,
			null
		);

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
			data: {isDialog: true},
		});
		dialogRef.afterClosed().subscribe((result) => {
			switch (result.event) {
				case 'cancel':
					Liferay.Util.openToast({
						title: 'No record has been created!',
						type: 'danger',
					});
					break;
				case 'created':
					Liferay.Util.openToast({
						title: 'A record has been created, Reloading data!',
						type: 'success',
					});
					this.reload();
					break;
				default:
					break;
			}
		});
	}

	ngOnInit(): void {
		this.loadPage();
	}
}
