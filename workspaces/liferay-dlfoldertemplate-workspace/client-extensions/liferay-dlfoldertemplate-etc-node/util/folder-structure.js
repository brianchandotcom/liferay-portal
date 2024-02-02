/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getFolderTemplateNodesPage,
	postDocumentFolder,
} from '../headless-wrapper/liferayServices.js';

async function createFolder(parentFolderId, folderName, folderDescription) {
	const folderObject = {
		description: folderDescription,
		name: folderName.toString().trim(),
		viewableBy: 'Anyone',
	};

	const folder = await postDocumentFolder(parentFolderId, folderObject);

	return folder.id;
}

export async function createFolders(
	rootFolderName,
	templateId,
	containerFolderId
) {
	try {
		const templateNodes = await getFolderTemplateNodesPage(templateId);

		if (templateNodes.length <= 0) {
			throw new Error(
				'The template is empty. Please add at least one node to the template.'
			);
		}

		const rootNode = templateNodes.find((node) => node.root);

		await traverseTemplateNodes(
			templateNodes,
			rootNode.id,
			containerFolderId,
			rootFolderName
		);
	}
	catch (error) {
		throw new Error(error.message);
	}
}

async function traverseTemplateNodes(
	templateNodes,
	rootFolderId,
	parentFolderId,
	rootFolderName
) {
	const currentNode = templateNodes.find(
		(node) => node.id.toString() === rootFolderId.toString()
	);

	const folderId = await createFolder(
		parentFolderId,
		currentNode.root ? rootFolderName : currentNode.name,
		currentNode.description
	);

	const childNodes = templateNodes.filter(
		(node) => node.parentId.toString() === currentNode.id.toString()
	);

	if (childNodes.length) {
		await childNodes.forEach(async (node) => {
			await traverseTemplateNodes(
				templateNodes,
				node.id,
				folderId,
				rootFolderName
			);
		});
	}
}
