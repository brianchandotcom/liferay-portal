import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {headless_deliveryService} from "../../../../services/liferay/headless-delivery";
import {DynamicFlatNode, FolderServiceService} from "../../../../services/dynamic/folder-service.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

declare const Liferay: any;

@Component({
    selector: 'app-folder-selector',
    templateUrl: './folder-selector.component.html',
    styleUrls: ['./folder-selector.component.css']
})
export class FolderSelectorComponent implements OnInit {
    Folders: any;
    options: any = {};

    constructor(@Inject(MAT_DIALOG_DATA) public data: any | null = null, public dialogRef: MatDialogRef<FolderSelectorComponent> | null,private cdr: ChangeDetectorRef, private deliveryService: headless_deliveryService, private folderServiceService: FolderServiceService) {

    }
    get isSignedIn()
    {
        return Liferay.ThemeDisplay.isSignedIn();
    }
    ngOnInit(): void {
        this.loadFirstLevel();
    }

    async traverseFolders(folder: DynamicFlatNode) {
        if (!folder.expandable) {
            return;
        }
        await folder.getSubFolders();
        if (folder.children && folder.children.length > 0) {
            try {
                for (let index = 0; index < folder.children.length; index++) {
                    await this.traverseFolders(folder.children[index]);
                }
            } catch (exp) {
                console.log(folder);
            }
        }
    }
    select(node:any)
    {
        this.dialogRef?.close({selected:true,data:node});
    }
    async loadFirstLevel() {
        let folders = await this.folderServiceService.loadRootLevel();
        this.Folders = folders as Array<DynamicFlatNode>;
        this.cdr.detectChanges();
    }

    closeDialog(cancel: string) {
        this.dialogRef?.close({selected:false});
    }
}
