/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ChangeDetectorRef,
	Component,
	Inject,
	Input,
	OnInit,
} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {
	MAT_DIALOG_DATA,
	MatDialog,
	MatDialogRef,
} from '@angular/material/dialog';
import OrgChart from '@balkangraph/orgchart.js';

import {FolderTemplatesService} from '../../../../services/dynamic/folder-templates';
import {addIcon, editIcon, removeIcon} from '../../../../services/icons';

@Component({
	selector: 'app-create',
	styleUrls: ['./edit-form/edit-form.component.css'],
	templateUrl: './edit-form/edit-form.component.html',
})
export class EditFormEmbeddedComponent implements OnInit {
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
		public dialogRef: MatDialogRef<EditFormEmbeddedComponent>,
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
@Component({
	selector: 'app-create',
	styleUrls: ['./create.component.css'],
	templateUrl: './create.component.html',
})
export class TemplateDiagramCreateComponent implements OnInit {
	template: any = 0;

	openDialog(data: any) {
		const dialogRef = this.dialog.open(EditFormEmbeddedComponent, {
			data,
		});
		dialogRef.afterClosed().subscribe((result: any) => {
			switch (result.event) {
				case 'edit': {
					if (result.status === 'success') {
						this.chart.update(result.data);
						this.chart.draw(OrgChart.action.init);
					}
					break;
				}
				default:
					break;
			}
		});
	}

	constructor(
		private dialog: MatDialog,
		private foldertemplatesService: FolderTemplatesService,
		public dialogRef: MatDialogRef<TemplateDiagramCreateComponent>,
		@Inject(MAT_DIALOG_DATA) public templateData: any
	) {}

	chart: OrgChart | any;
	@Input('data')
	data: any[] | any;

	ngOnInit() {
		this.template = this.templateData.template.id;
		this.loadTemplateNodes();
	}

	loadChart() {
		OrgChart.templates['white'] = {...OrgChart.templates['ana']};
		OrgChart.templates['white'].size = [300, 100];
		OrgChart.templates['white'].node =
			'<rect x="0" y="0" height="80" width="300" fill="#039BE5" stroke-width="1" stroke="#ffca2761" rx="0" ry="0"></rect>' +
			`<svg width="64px" height="64px" x="20" y="10" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M853.333333 256H469.333333l-85.333333-85.333333H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v170.666667h853.333334v-85.333334c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFA000" />
            <path d="M853.333333 256H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v426.666667c0 46.933333 38.4 85.333333 85.333334 85.333333h682.666666c46.933333 0 85.333333-38.4 85.333334-85.333333V341.333333c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFCA28" />
            </svg>`;
		OrgChart.templates['white'].ripple = {
			color: '#0890D3',
			radius: 0,

			// @ts-ignore

			rect: {height: 80, rx: 0, ry: 0, width: 300, x: 0, y: 0},
		};
		OrgChart.templates['white'].nodeMenuButton =
			'<g style="cursor:pointer;" transform="matrix(1,0,0,1,285,33)" data-ctrl-n-menu-id="{id}"><rect x="-4" y="-10" fill="#000000" fill-opacity="0" width="22" height="22"></rect><circle cx="0" cy="0" r="2" fill="black"></circle><circle cx="0" cy="7" r="2" fill="black"></circle><circle cx="0" cy="14" r="2" fill="black"></circle></g>';
		OrgChart.templates['white'][
			'field_0'
		] = `<svg x="100" width="200" height="60" xmlns="http://www.w3.org/2000/svg">
  <text x="10" y="20" font-family="Arial" font-size="14">
    <tspan x="10" dy="2.2em">{val}</tspan>
  </text>
</svg>`;
		const tree = document.getElementById('tree');
		if (tree) {
			const chart = new OrgChart(tree, {
				enableDragDrop: true,
				enableSearch: false,
				nodeBinding: {
					field_0: 'name',
				},
				nodeMenu: {
					addCustom: {
						icon: addIcon,
						onClick: async (node: any) => {
							await this.addNode(node);

							return false;
						},
						text: 'Add',
					},
					deleteNode: {
						icon: removeIcon,
						onClick: async (nodeId: any) => {
							if (
								nodeId.toString() ===
								this.chart.roots[0].id.toString()
							) {
								return false;
							}
							else {
								await this.deleteNodeAndChilds(nodeId);
							}
							setTimeout(() => {
								this.chart.draw(OrgChart.action.init);
								this.chart.load(this.data);
							}, 120);

							return true;
						},
						text: 'Remove',
					},
					edit: {
						icon: editIcon,
						onClick: (node: any) => {
							const selectedNode = this.chart.get(node);
							selectedNode.templateID = this.template;
							const data = {
								event: 'edit',
								foldertemplatesService: this
									.foldertemplatesService,
								node: selectedNode,
							};
							this.openDialog(data);

							return true;
						},
						text: 'Edit',
					},
				},
				nodeMouseClick: OrgChart.action.expand,
				template: 'white',
				toolbar: {
					expandAll: true,
					fit: true,
					layout: false,
					zoom: true,
				},
			});
			this.chart = chart;
			chart.on(
				'drop',
				(sender: any, draggedNodeId: any, droppedNodeId: any) => {
					if (draggedNodeId.toString() === '1') {
						return false;
					}
					const node = this.chart.get(draggedNodeId);
					const newParentNode = this.chart.get(droppedNodeId);
					this.updateNode(
						node.id,
						newParentNode.id,
						node.name,
						node.templateID
					);

					return false;
				}
			);
			chart.load(this.data);
		}
	}
	async updateNode(nodeId: any, pid: any, name: any, templateID: any) {
		try {
			const updatedNode = {
				name,
				parentID: pid,
				templateID,
			};
			await this.foldertemplatesService.patchFolderTemplate(
				nodeId,
				updatedNode
			);
			const currentNode = this.chart.get(nodeId);
			currentNode.pid = pid;
			this.chart.update(currentNode);
			this.chart.load(this.data);
			this.chart.draw(OrgChart.action.init);
		}
		catch (exp) {
			return false;
		}

		return true;
	}
	async addNode(
		parentNode: any,
		root: boolean = false,
		name: string = 'Unnamed'
	) {
		try {
			const result: any = await this.foldertemplatesService.postFolderTemplate(
				{
					description: '',
					name,
					parentID: parentNode,
					root,
					templateID: this.template,
				}
			);
			const node: any = {
				description: result.description,
				id: result.id,
				name: result.name,
				pid: result.parentID,
				templateID: result.templateID,
			};
			this.data.push(node);
			this.chart.draw(OrgChart.action.init);
		}
		catch (exp) {

			// /todo
			// show alert

		}
	}

	async removeNode(nodeId: number) {
		try {
			await this.foldertemplatesService.deleteFolderTemplate(nodeId);
		}
		catch (exp) {
			return false;
		}

		return true;
	}

	async loadTemplateNodes() {
		const result: any = await this.foldertemplatesService.getFolderTemplatesPage(
			null,
			null,
			null,
			null,
			null,
			`templateID eq ${this.template}`,
			0
		);
		const nodes = result.items.map((node: any) => {
			return {
				description: node.description,
				id: node.id,
				name: node.name,
				pid: node.parentID,
				templateID: node.templateID,
			};
		});
		this.data = nodes;
		if (nodes.length <= 0) {
			this.addNode(0, true, 'Root Folder');
		}
		this.loadChart();
	}

	async deleteNodeAndChilds(nodeId: any) {
		const node = this.chart.getNode(nodeId);
		if (node.childrenIds.length.toString() === '0') {
			await this.removeNode(nodeId);
			this.chart.remove(nodeId);

			return true;
		}
		for (let index = 0; index < node.childrenIds.length; index++) {
			await this.deleteNodeAndChilds(node.childrenIds[index]);
		}
		setTimeout(async () => {
			const result = await this.removeNode(nodeId);
			this.chart.remove(nodeId);

			return result;
		}, 20);

		return true;
	}

	closeDialog(result: string) {
		if (this.dialogRef) {
			this.dialogRef.close({event: result});
		}
	}
}
