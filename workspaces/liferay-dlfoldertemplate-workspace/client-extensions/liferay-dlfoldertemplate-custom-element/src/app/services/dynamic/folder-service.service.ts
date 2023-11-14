import {headless_deliveryService} from "../liferay/headless-delivery";

declare const Liferay: any;
import {Injectable} from '@angular/core';


export class DynamicFlatNode {
    public children: DynamicFlatNode[] | any;

    constructor(
        public name: string,
        public id: number,
        public level = 1,
        public expandable = false,
        public isLoading = false,
        private deliveryService: headless_deliveryService
    ) {
        this.loadChilds();
    }

    async loadChilds() {
        this.children = await this.getChildren();
    }

    getSubFolders() {
        let subfolders = this
    }

    public async getChildren() {
        if (this.expandable) {
            let sub: any = await this.deliveryService.getDocumentFolderDocumentFoldersPage(this.id);
            return sub.items.map((folder: any) => new DynamicFlatNode(
                folder.name,
                folder.id, 1, folder.numberOfDocumentFolders > 0, false, this.deliveryService));
        }
        return [];
    }
}

@Injectable({
    providedIn: 'root'
})
export class FolderServiceService {
    constructor(private deliveryService: headless_deliveryService) {
    }

    public async loadRootLevel() {
        let folders: any = (await this.deliveryService.getSiteDocumentFoldersPage(Liferay.ThemeDisplay.getScopeGroupId()));
        return folders.items.map((folder: any) => new DynamicFlatNode(
            folder.name,
            folder.id, 1, folder.numberOfDocumentFolders > 0, false, this.deliveryService));
    }

    /** Initial data from database */
    async initialData(): Promise<DynamicFlatNode[]> {
        return await this.loadRootLevel();
    }

}

@Injectable({
    providedIn: 'root',
})
export class TreeService {
    buildTree(jsonData: any): TreeNode {
        return this.buildNode(jsonData);
    }

    private buildNode(nodeData: any): TreeNode {
        const node: TreeNode = {name: nodeData.name, children: []};

        if (nodeData.children && nodeData.children.length > 0) {
            nodeData.children.forEach((childData: any) => {
                const childNode = this.buildNode(childData);
                node.children.push(childNode);
            });
        }

        return node;
    }
}

export interface TreeNode {
    name: string;
    children: TreeNode[];
}
