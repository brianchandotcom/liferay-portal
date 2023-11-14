import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {Injector, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {createCustomElement} from "@angular/elements";
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatSelectModule} from "@angular/material/select";
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatDialogModule} from "@angular/material/dialog";
import { TemplateDiagramCreateComponent } from './components/template/diagram/create/create.component';
import { EditFormComponent } from './components/template/diagram/create/edit-form/edit-form.component';
import {NewTemplateComponent} from "./components/template/new-template/new-template.component";
import { ListTemplatesComponent } from './components/template/list-templates/list-templates.component';
import { TemplateListItemComponent } from './components/template/controls/template-list-item/template-list-item.component';
import { EmptyDataSetComponent } from './components/shared/controls/empty-data-set/empty-data-set.component';
import { TemplateListHeaderComponent } from './components/template/controls/template-list-header/template-list-header.component';
import {MatBadgeModule} from "@angular/material/badge";
import { CreateFoldersComponent } from './components/create-folders/create-folders.component';
import { FolderSelectorComponent } from './components/shared/controls/folder-selector/folder-selector.component';
import {MatTreeModule} from "@angular/material/tree";
import { FolderItemComponent } from './components/shared/controls/folder-selector/folder-item/folder-item.component';
import {MatExpansionModule} from "@angular/material/expansion";
@NgModule({
    declarations: [
        AppComponent,
        TemplateDiagramCreateComponent,
        EditFormComponent,
        NewTemplateComponent,
        ListTemplatesComponent,
        TemplateListItemComponent,
        EmptyDataSetComponent,
        TemplateListHeaderComponent,
        CreateFoldersComponent,
        FolderSelectorComponent,
        FolderItemComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatPaginatorModule,
        MatTableModule,
        MatInputModule,
        MatIconModule,
        MatSelectModule,
        MatCardModule,
        MatButtonModule,
        MatToolbarModule,
        ReactiveFormsModule,
        MatTooltipModule,
        MatDialogModule,
        MatProgressSpinnerModule,
        MatBadgeModule,
        MatTreeModule,
        MatExpansionModule
    ],
    providers: [],
    bootstrap: []
})
export class AppModule {
    ngDoBootstrap() {
    }
    constructor(private injector: Injector) {
        const ListTemplates = createCustomElement(ListTemplatesComponent, {
            injector: this.injector,
        });
        customElements.define('liferay-folder-templates', ListTemplates);

        const folderList = createCustomElement(FolderSelectorComponent, {
            injector: this.injector,
        });
        customElements.define('liferay-controls-folder-list', folderList);
    }
}
