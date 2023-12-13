/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {headless_deliveryService} from '../headless-wrapper/headless-delivery.js';
import config from '../util/configTreePath.js';
import {getServerToken} from './silent-authorization.js';

const viewByAnyone = 'Anyone';
const viewOption = viewByAnyone;

const chat_attachments_folder_external_reference_code =
	config['jobs.attachments.folder.external.reference.code'];

export async function getOrCreateMainFolder(globalSiteId, userId) {
	const token = await getServerToken(userId);
	const delivery_srv = new headless_deliveryService(token);
	const folder = await delivery_srv.getSiteDocumentsFolderByExternalReferenceCode(
		globalSiteId,
		chat_attachments_folder_external_reference_code
	);
	if (folder && folder.id) {
		return folder.id;
	}
	else {
		const folderObject = {
			description: 'Liferay Chat Attachments Folder',
			externalReferenceCode: chat_attachments_folder_external_reference_code,
			name: chat_attachments_folder_external_reference_code,
			parentDocumentFolderId: 0,
			viewableBy: viewOption,
		};
		const folder = await delivery_srv.postSiteDocumentFolder(
			globalSiteId,
			folderObject
		);

		return folder.id;
	}
}
export async function getOrCreateUserFolder(globalSiteId, uploaderUserId) {
	const folderKey = `user_id_${uploaderUserId}`;
	const token = await getServerToken(uploaderUserId);
	const delivery_srv = new headless_deliveryService(token);
	const folder = await delivery_srv.getSiteDocumentsFolderByExternalReferenceCode(
		globalSiteId,
		folderKey
	);
	if (folder && folder.id) {
		return folder.id;
	}
	else {
		const mainFolderId = await getOrCreateMainFolder(
			globalSiteId,
			uploaderUserId
		);
		const folderObject = {
			description:
				'Liferay Chat Attachments Folder Uploaded by User ' +
				uploaderUserId,
			externalReferenceCode: folderKey,
			name: `User_${uploaderUserId}_Attachments`,
			viewableBy: viewOption,
		};
		const folder = await delivery_srv.postDocumentFolderDocumentFolder(
			mainFolderId,
			folderObject
		);

		return folder.id;
	}
}
