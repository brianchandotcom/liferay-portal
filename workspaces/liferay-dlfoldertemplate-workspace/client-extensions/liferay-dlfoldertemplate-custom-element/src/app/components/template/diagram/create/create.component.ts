import {Component, Inject, Input, OnInit} from '@angular/core';
import OrgChart from "@balkangraph/orgchart.js";
import {detailsIcon, editIcon, addIcon, removeIcon} from '../../../../services/icons'
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EditFormComponent} from "./edit-form/edit-form.component";
import {foldertemplatesService} from "../../../../services/liferay/foldertemplates";
import {timeout} from "rxjs/operators";

@Component({
    selector: 'app-create',
    templateUrl: './create.component.html',
    styleUrls: ['./create.component.css']
})
export class TemplateDiagramCreateComponent implements OnInit {

    template: any = 0;

    openDialog(data: any) {
        const dialogRef = this.dialog.open(EditFormComponent, {
            data: data
        });
        dialogRef.afterClosed().subscribe((result:any) => {
            switch (result.event) {
                case 'edit':
                {
                    if (result.status == "success")
                    {
                        this.chart.update(result.data);
                        this.chart.draw(OrgChart.action.init);
                    }
                    break;
                }
                default:
                    console.log(result)
            }
        });
    }

    constructor(private dialog: MatDialog, private foldertemplatesService: foldertemplatesService,
                public dialogRef: MatDialogRef<TemplateDiagramCreateComponent>,@Inject(MAT_DIALOG_DATA) public templateData: any) {

    }

    chart: OrgChart | any;
    @Input('data')
    data: any[] | any;

    ngOnInit() {
        console.log(this.templateData.template.id);
        this.template = this.templateData.template.id;
        this.loadTemplateNodes();
    }

    loadChart() {
        OrgChart.templates['white'] = Object.assign({}, OrgChart.templates['ana']);
        OrgChart.templates['white'].size = [300, 100];
        OrgChart.templates['white'].node = '<rect x="0" y="0" height="80" width="300" fill="#039BE5" stroke-width="1" stroke="#ffca2761" rx="0" ry="0"></rect>'
            + `<svg width="64px" height="64px" x="20" y="10" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M853.333333 256H469.333333l-85.333333-85.333333H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v170.666667h853.333334v-85.333334c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFA000" />
            <path d="M853.333333 256H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v426.666667c0 46.933333 38.4 85.333333 85.333334 85.333333h682.666666c46.933333 0 85.333333-38.4 85.333334-85.333333V341.333333c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFCA28" />
            </svg>`;
        OrgChart.templates["white"].ripple = {
            radius: 0,
            color: "#0890D3",
            // @ts-ignore
            rect: {x: 0, y: 0, width: 300, height: 80, rx: 0, ry: 0}
        };
        OrgChart.templates['white'].nodeMenuButton = '<g style="cursor:pointer;" transform="matrix(1,0,0,1,285,33)" data-ctrl-n-menu-id="{id}"><rect x="-4" y="-10" fill="#000000" fill-opacity="0" width="22" height="22"></rect><circle cx="0" cy="0" r="2" fill="black"></circle><circle cx="0" cy="7" r="2" fill="black"></circle><circle cx="0" cy="14" r="2" fill="black"></circle></g>';
        ;
        OrgChart.templates['white']['field_0'] = `<svg x="100" width="200" height="60" xmlns="http://www.w3.org/2000/svg">
  <text x="10" y="20" font-family="Arial" font-size="14">
    <tspan x="10" dy="2.2em">{val}</tspan>
  </text>
</svg>`;
        const tree = document.getElementById('tree');
        if (tree) {
            var chart = new OrgChart(tree, {
                template: "white",
                enableDragDrop: true,
                nodeMouseClick: OrgChart.action.expand,
                toolbar: {
                    layout: false,
                    zoom: true,
                    fit: true,
                    expandAll: true
                },
                nodeMenu: {
                    edit: {
                        text: "Edit",
                        icon: editIcon,
                        onClick: (node: any) => {
                            let selectedNode = this.chart.get(node);
                            selectedNode.templateID = this.template;
                            let data = {
                                node: selectedNode,
                                event: 'edit'
                            }
                            console.log(data);
                            this.openDialog(data);
                            return true;
                        }
                    },
                    addCustom: {
                        text: "Add",
                        icon: addIcon,
                        onClick: async (node: any) => {
                            let newNode = await this.addNode(node);
                            return false;
                        }
                    },
                    deleteNode: {
                        text: "Remove",
                        icon: removeIcon,
                        onClick: async (nodeId: any) => {
                            console.log('Deleting node...')
                            if (nodeId == this.chart.roots[0].id) {
                                return false;
                            } else {
                                console.log(this.chart.getNode(nodeId));
                                let result = await this.deleteNodeAndChilds(nodeId);
                            }
                            setTimeout(()=>{
                                this.chart.draw(OrgChart.action.init);
                                this.chart.load(this.data);
                            },120)
                            return true;
                        }
                    }
                },
                enableSearch: false,
                nodeBinding: {
                    field_0: "name"
                }
            });
            this.chart = chart;
            chart.on('drop', (sender: any, draggedNodeId: any, droppedNodeId: any) => {
                if (draggedNodeId == 1) {
                    return false;
                }
                let node = this.chart.get(draggedNodeId);
                let newParentNode = this.chart.get(droppedNodeId);
                console.log(node);
                console.log(newParentNode);
                this.updateNode(node.id,newParentNode.id,node.name,node.templateID);
                return false;
            });
            chart.load(this.data);
        }
    }
    async updateNode(nodeId:any,pid:any,name:any,templateID:any)
    {
        try {
            let updatedNode = {
                "name": name,
                "parentID": pid,
                "templateID": templateID
            }
            await this.foldertemplatesService.patchFolderTemplate(nodeId,updatedNode);
            let currentNode = this.chart.get(nodeId);
            currentNode.pid = pid;
            this.chart.update(currentNode);
            this.chart.load(this.data);
            this.chart.draw(OrgChart.action.init);
        }catch (exp)
        {
            return false;
        }
       return true;
    }
    async addNode(parentNode: any,root:boolean=false,name:string="Unnamed") {
        try {
            let result: any = await this.foldertemplatesService.postFolderTemplate({
                "description": "",
                "name": name,
                "parentID": parentNode,
                "templateID": this.template,
                "root":root
            });
            let node: any = {
                id: result.id,
                pid: result.parentID,
                name: result.name,
                templateID:result.templateID,
                description: result.description
            }
            this.data.push(node);
            this.chart.draw(OrgChart.action.init);
        } catch (exp) {
            ///todo
            //show alert
        }
    }

    async removeNode(nodeId: number) {
        try {
            let result = await this.foldertemplatesService.deleteFolderTemplate(nodeId);


        } catch (exp) {
            return false;
        }
        return true;
    }

    async loadTemplateNodes() {
        let result: any = await this.foldertemplatesService.getFolderTemplatesPage(null, null, null,
            null, null, `templateID eq ${this.template}`, 0);
        let nodes = result.items.map((node: any) => {
            return {
                id: node.id,
                pid: node.parentID,
                name: node.name,
                description: node.description,
                templateID:node.templateID
            };
        });
        this.data = nodes;
        if (nodes.length <= 0) {
            this.addNode(0,true,"Root Folder");
        }
        this.loadChart();


    }

    async deleteNodeAndChilds(nodeId: any) {
        let node = this.chart.getNode(nodeId)
        if (node.childrenIds.length == 0) {
            let result = await this.removeNode(nodeId);
            this.chart.remove(nodeId);
            return true;
        }
        for (let index = 0; index < node.childrenIds.length; index++) {
            await this.deleteNodeAndChilds(node.childrenIds[index]);
        }
        setTimeout(async () => {
            let result = await this.removeNode(nodeId);
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
