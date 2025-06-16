/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../core/SearchBuilder';
import fetcher from '../fetcher';
import {Liferay} from '../../liferay/liferay';
import {axios} from '../../utils/axios';

const DOCUMENTS_ROOT_FOLDER = 0;
const PICK_LIST_ASSET_TYPE = 'package';
const PUBLISHER_ASSETS_FOLDER = 'publisher_assets';

export default class PublisherAssetses {
	static getProductPublisherAssetsByProductId(productId: number | string) {
		const searchParams = new URLSearchParams({
			filter: SearchBuilder.eq(
				'r_productEntryToPublisherAssets_CPDefinitionId',
				productId
			),
		});

		return fetcher<APIResponse>(
			`o/c/publisherassetses?${searchParams.toString()}`
		);
	}

	static async createDocumentFolder(
		name: string,
		parentDocumentFolderId: number
	) {
		const url =
			parentDocumentFolderId !== 0
				? `o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`
				: `o/headless-delivery/v1.0/sites/$${Liferay.ThemeDisplay.getScopeGroupId()}/document-folders`;

		return fetcher.post(url, {
			name,
			parentDocumentFolderId,
			viewableBy: 'Anyone',
		});
	}

	static async createDocumentFolderDocument(
		documentFolderId: string,
		body: any
	) {
		const response = await axios.post(
			`o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents`,
			body
		);

		return response.data;
	}

	static async createPublisherAsset(body: any) {
		const response = await axios.post('o/c/publisherassetses', body);
		return response.data;
	}

	static async getDocumentFolders(
		siteId: number | string,
		searchParams: URLSearchParams = new URLSearchParams()
	) {
		return fetcher(
			`o/headless-delivery/v1.0/sites/${siteId}/document-folders?${searchParams.toString()}`
		);
	}

	static async getDocumentFolderDocuments(folderId: number | string) {
		return fetcher(
			`o/headless-delivery/v1.0/document-folders/${folderId}/documents`
		);
	}

	static getProductPublisherAssets(productId: number | string) {
		const searchParams = new URLSearchParams({
			filter: SearchBuilder.eq(
				'r_productEntryToPublisherAssets_CPDefinitionId',
				productId
			),
		});
		return fetcher(`o/c/publisherassetses?${searchParams.toString()}`);
	}

	static async processLiferayPackage(
		file: any,
		product: Product,
		versions: string
	) {
		const folderName = file.fileName
			.replaceAll('-', '')
			.replaceAll('.', '')
			.replace('zip', '');

		const siteId = Liferay.ThemeDisplay.getScopeGroupId();

		let publisherFolderId;

		const publisherAssetsFolder = await this.getDocumentFolders(
			siteId,
			new URLSearchParams({
				filter: SearchBuilder.contains('name', PUBLISHER_ASSETS_FOLDER),
			})
		);

		if (publisherAssetsFolder.items.length > 0) {
			publisherFolderId = publisherAssetsFolder.items[0].id;
		}

		if (!publisherFolderId) {
			const createFolderResponse = await this.createDocumentFolder(
				PUBLISHER_ASSETS_FOLDER,
				DOCUMENTS_ROOT_FOLDER
			);
			publisherFolderId = createFolderResponse?.id;
		}

		const {items: packageFolders} =
			await this.getDocumentFolderDocuments(publisherFolderId);

		const packageFolder = packageFolders.find(
			(document: any) => document.fileName === file.fileName
		);

		let packageFolderId = packageFolder?.id;

		if (!packageFolderId) {
			const packageFolder = await this.createDocumentFolder(
				folderName,
				publisherFolderId
			);

			packageFolderId = packageFolder.id;
		}

		const {items} = await this.getDocumentFolderDocuments(packageFolderId);

		const document = items.find(
			(document: any) => document.fileName === file.fileName
		);

		let documentId = document?.id;

		if (!documentId) {
			const formData = new FormData();
			const blob = new Blob([file.file]);

			formData.append('file', blob, file.fileName);
			const sourceDocument = await this.createDocumentFolderDocument(
				packageFolderId,
				formData
			);

			documentId = sourceDocument.id;
		}

		const accountId = Liferay.CommerceContext.account?.accountId;

		await this.createPublisherAsset({
			name: product.name.en_US,
			publisherAssetType: PICK_LIST_ASSET_TYPE,
			r_accountEntryToPublisherAssets_accountEntryId: accountId,
			r_productEntryToPublisherAssets_CPDefinitionId:
				product.id as unknown as string,
			sourceCode: documentId,
			version: versions,
		})
			.then(() =>
				console.log(`Publisher Asset ${product.name.en_US} created`)
			)
			.catch((error: Error) =>
				console.log(
					`Error to process Publisher Asset - ${
						product.name.en_US
					} / ${(error as Error).message}`
				)
			);
	}
}
