/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpClientModule} from '@angular/common/http';
import {Injector, NgModule} from '@angular/core';
import {createCustomElement} from '@angular/elements';
import {ReactiveFormsModule} from '@angular/forms';
import {MatBadgeModule} from '@angular/material/badge';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSelectModule} from '@angular/material/select';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {CreateFoldersComponent} from './components/create-folders/create-folders.component';
import {EmptyDataSetComponent} from './components/shared/controls/empty-data-set/empty-data-set.component';
import {FolderItemComponent} from './components/shared/controls/folder-selector/folder-item/folder-item.component';
import {FolderSelectorComponent} from './components/shared/controls/folder-selector/folder-selector.component';
import {TemplateListHeaderComponent} from './components/template/controls/template-list-header/template-list-header.component';
import {TemplateListItemComponent} from './components/template/controls/template-list-item/template-list-item.component';
import {
	EditFormEmbeddedComponent,
	TemplateDiagramCreateComponent,
} from './components/template/diagram/create/create.component';
import {EditFormComponent} from './components/template/diagram/create/edit-form/edit-form.component';
import {ListTemplatesComponent} from './components/template/list-templates/list-templates.component';
import {NewTemplateComponent} from './components/template/new-template/new-template.component';
@NgModule({
	declarations: [
		TemplateDiagramCreateComponent,
		EditFormComponent,
		NewTemplateComponent,
		ListTemplatesComponent,
		TemplateListItemComponent,
		EmptyDataSetComponent,
		TemplateListHeaderComponent,
		CreateFoldersComponent,
		FolderSelectorComponent,
		FolderItemComponent,
		EditFormEmbeddedComponent,
	],
	imports: [
		BrowserAnimationsModule,
		BrowserModule,
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
		MatExpansionModule,
	],
	providers: [],
})
export class AppModule {
	ngDoBootstrap() {}
	constructor(private injector: Injector) {
		const ListTemplates = createCustomElement(ListTemplatesComponent, {
			injector: this.injector,
		});
		customElements.define('liferay-folder-templates', ListTemplates);
	}
}
