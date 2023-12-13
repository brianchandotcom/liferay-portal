/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {foldertemplatesService} from '../headless-wrapper/foldertemplates.js';
import {headless_deliveryService} from '../headless-wrapper/headless-delivery.js';
import config from '../util/configTreePath.js';
import {getServerToken} from './silent-authorization.js';
const viewByAnyone = 'Anyone';
const viewOption = viewByAnyone;
const employee_folder_external_reference_code =
	config['employee.folder.external.reference.code'];
const employee_folder_site_id = config['employee.folder.site.id'];

async function getOrCreateMainFolder() {
	const token = await getServerToken(0);
	const delivery_srv = new headless_deliveryService(token);
	const folder = await delivery_srv.getSiteDocumentsFolderByExternalReferenceCode(
		employee_folder_site_id,
		employee_folder_external_reference_code
	);
	if (folder && folder.id) {
		return folder.id;
	}
	else {
		const folderObject = {
			description: 'Liferay Employees Folder',
			externalReferenceCode: employee_folder_external_reference_code,
			name: employee_folder_external_reference_code,
			parentDocumentFolderId: 0,
			viewableBy: viewOption,
		};
		const folder = await delivery_srv.postSiteDocumentFolder(
			employee_folder_site_id,
			folderObject
		);

		return folder.id;
	}
}
async function getOrCreateMainFolderById(folderId) {
	const token = await getServerToken(0);
	const delivery_srv = new headless_deliveryService(token);
	const folder = await delivery_srv.getDocumentFolder(folderId);
	if (folder && folder.id) {
		return folder.id;
	}
	else {
		const folderObject = {
			description: 'Liferay Employees Folder',
			externalReferenceCode: employee_folder_external_reference_code,
			name: employee_folder_external_reference_code,
			parentDocumentFolderId: 0,
			viewableBy: viewOption,
		};
		const folder = await delivery_srv.postSiteDocumentFolder(
			employee_folder_site_id,
			folderObject
		);

		return folder.id;
	}
}
async function getOrCreateUserFolder(
	parentFolderId,
	folderName,
	folderDescription
) {
	const token = await getServerToken(0);
	const delivery_srv = new headless_deliveryService(token);
	const folderObject = {
		description: folderDescription,
		name: `${folderName.trim()}`,
		viewableBy: viewOption,
	};
	const folder = await delivery_srv.postDocumentFolderDocumentFolder(
		parentFolderId,
		folderObject
	);

	return folder.id;
}
async function getTemplate(templateId) {
	const token = await getServerToken(0);
	const foldertemplateSrv = new foldertemplatesService(token);
	const dynamicTemplateData = await foldertemplateSrv.getFolderTemplatesPage(
		templateId
	);
	const template = dynamicTemplateData.items;

	return template;
}

export async function start(userId, templateId) {
	const template = await getTemplate(templateId);
	const root = template.filter((folder) => folder.root)[0];
	root.name = userId;
	const mainFolderId = await getOrCreateMainFolder();
	await traverseFolders(template, root.id, mainFolderId);
}

export async function startCustom(
	rootFolderName,
	templateId,
	containerFolderId
) {
	const template = await getTemplate(templateId);
	const root = template.filter((folder) => folder.root)[0];
	root.name = rootFolderName;
	const mainFolderId = await getOrCreateMainFolderById(containerFolderId);
	await traverseFolders(template, root.id, mainFolderId);
}

async function traverseFolders(folders, rootFolderId, actualParentFolderId) {
	const rootFolder = folders.filter(
		(folder) => folder.id.toString() === rootFolderId.toString()
	)[0];
	const actualFolderId = await getOrCreateUserFolder(
		actualParentFolderId,
		rootFolder.name,
		rootFolder.description
	);
	const subFolders = folders.filter(
		(folder) => folder.parentID.toString() === rootFolderId.toString()
	);
	if (subFolders.length) {
		await subFolders.forEach(async (folder) => {
			await traverseFolders(folders, folder.id, actualFolderId);
		});
	}
}
