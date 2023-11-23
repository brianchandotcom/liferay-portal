/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {animate, state, style, transition, trigger} from '@angular/animations';
import {
	ChangeDetectorRef,
	Component,
	EventEmitter,
	Input,
	OnInit,
	Output,
} from '@angular/core';

import {DynamicFlatNode} from '../../../../../services/dynamic/folder-service.service';
declare const Liferay: any;
@Component({
	animations: [
		trigger('showHide', [
			state('show', style({opacity: 1})),
			state('hide', style({display: 'none', opacity: 0})),
			transition('show => hide', animate('300ms ease-out')),
			transition('hide => show', animate('300ms ease-in')),
		]),
	],
	selector: 'app-folder-item',
	styleUrls: ['./folder-item.component.css'],
	templateUrl: './folder-item.component.html',
})
export class FolderItemComponent implements OnInit {
	@Input('node')
	public node: DynamicFlatNode | any;
	@Input('ref')
	dialogRef: any;
	@Output('select') functionEmitter: EventEmitter<any> = new EventEmitter<
		any
	>();
	isListVisible = false;

	toggleListVisibility() {
		this.isListVisible = !this.isListVisible;
	}
	constructor(private cdr: ChangeDetectorRef) {}
	ngOnInit(): void {
		try {
			this.hasChildren = this.node.expandable;
			if (this.hasChildren) {
				this.loadChilds();
			}
		}
		catch (exp: any) {
			Liferay.Util.openToast({title: exp.message, type: 'danger'});
		}
	}
	children: Array<DynamicFlatNode> = [];
	hasChildren: boolean = false;
	async loadChilds() {
		if (this.node.expandable) {
			this.node.children = await this.node.getChildren();
			this.children = this.node.children;
			this.cdr.detectChanges();
		}
	}
	select(node: any) {
		this.dialogRef?.close({data: node, selected: true});
	}

	select1(node: any) {
		if (node) {
			this.functionEmitter.emit(node);
		}
		else {
			this.functionEmitter.emit(this.node);
		}
	}
}
