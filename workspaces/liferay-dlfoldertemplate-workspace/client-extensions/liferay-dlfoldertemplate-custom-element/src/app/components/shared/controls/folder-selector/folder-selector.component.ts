/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {
	DynamicFlatNode,
	FolderServiceService,
} from '../../../../services/dynamic/folder-service.service';

declare const Liferay: any;

@Component({
	selector: 'app-folder-selector',
	styleUrls: ['./folder-selector.component.css'],
	templateUrl: './folder-selector.component.html',
})
export class FolderSelectorComponent implements OnInit {
	Folders: any;
	options: any = {};

	constructor(
		@Inject(MAT_DIALOG_DATA) public data: any | null = null,
		public dialogRef: MatDialogRef<FolderSelectorComponent> | null,
		private cdr: ChangeDetectorRef,
		private folderServiceService: FolderServiceService
	) {}
	get isSignedIn() {
		return Liferay.ThemeDisplay.isSignedIn();
	}
	ngOnInit(): void {
		this.loadFirstLevel();
	}

	async traverseFolders(folder: DynamicFlatNode) {
		if (!folder.expandable) {
			return;
		}
		if (folder.children && !!folder.children.length) {
			try {
				for (let index = 0; index < folder.children.length; index++) {
					await this.traverseFolders(folder.children[index]);
				}
			}
			catch (exp: any) {
				Liferay.Util.openToast({title: exp.message, type: 'danger'});
			}
		}
	}
	select(node: any) {
		this.dialogRef?.close({data: node, selected: true});
	}
	async loadFirstLevel() {
		const folders = await this.folderServiceService.loadRootLevel();
		this.Folders = folders as Array<DynamicFlatNode>;
		this.cdr.detectChanges();
	}

	closeDialog() {
		this.dialogRef?.close({selected: false});
	}
}
