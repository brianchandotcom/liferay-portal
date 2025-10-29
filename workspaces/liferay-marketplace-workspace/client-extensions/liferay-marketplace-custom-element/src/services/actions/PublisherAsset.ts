/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../core/SearchBuilder';
import {DOCUMENT_FOLDER_PERMISSIONS} from '../../enums/File';
import {Liferay} from '../../liferay/liferay';
import {MarketplaceProperties} from '../../utils/attributes';
import HeadlessDelivery from '../rest/HeadlessDelivery';
import HeadlessPublisherAssetses from '../rest/HeadlessPublisherAsset';

const DOCUMENTS_ROOT_FOLDER = 0;
const PICK_LIST_ASSET_TYPE = 'package';
const PUBLISHER_ASSETS_FOLDER = 'publisher_assets';

export default class PublisherAsset {
	constructor(
		protected file: any,
		protected product: Product,
		protected properties: MarketplaceProperties,
		protected versions: string
	) {}

	private async createPublisherAssetsFolderId(): Promise<number> {
		const response = await HeadlessDelivery.createDocumentFolder(
			PUBLISHER_ASSETS_FOLDER,
			DOCUMENTS_ROOT_FOLDER
		);

		return response.id;
	}

	private async getAppFolderId(publisherFolderId: number): Promise<number> {
		const folderName = `app_${this.product.productId}`;

		const {items: appFolders} =
			await HeadlessDelivery.getDocumentFolderDocuments(
				publisherFolderId,
				new URLSearchParams({
					filter: SearchBuilder.contains('name', folderName),
				})
			);

		const appFolder = appFolders.find(
			(document: any) => document.name === folderName
		);

		let appFolderId = appFolder?.id;

		if (!appFolderId) {
			const packageFolder = await HeadlessDelivery.createDocumentFolder(
				folderName,
				publisherFolderId,
				DOCUMENT_FOLDER_PERMISSIONS.SITE_MEMBERS
			);

			appFolderId = packageFolder.id;
		}

		return appFolderId;
	}

	private async getPublisherAssetDocumentId(
		appFolderId: number
	): Promise<number> {
		const formData = new FormData();
		const blob = new Blob([this.file.file]);

		formData.append('file', blob, this.file.fileName);
		const sourceDocument =
			await HeadlessDelivery.createDocumentFolderDocument(
				appFolderId,
				formData
			);

		return sourceDocument.id;
	}

	private async getPublisherFolderId(): Promise<number> {
		let publisherFolderId;

		const publisherAssetsFolder = await HeadlessDelivery.getDocumentFolders(
			Liferay.ThemeDisplay.getScopeGroupId(),
			new URLSearchParams({
				filter: SearchBuilder.contains('name', PUBLISHER_ASSETS_FOLDER),
			})
		);

		if (publisherAssetsFolder.items.length) {
			publisherFolderId = publisherAssetsFolder.items[0].id;
		}

		if (!publisherFolderId) {
			publisherFolderId = await this.createPublisherAssetsFolderId();
		}

		return publisherFolderId;
	}

	public async process() {
		try {
			const publisherFolderId = await this.getPublisherFolderId();

			const appFolderId = await this.getAppFolderId(publisherFolderId);

			const productRelationshipName =
				this.properties.featurePreview?.includes(
					'product-versioning-new-primary-key'
				)
					? 'r_productEntryToPublisherAssets_CProductId'
					: 'r_productEntryToPublisherAssets_CPDefinitionId';

			await HeadlessPublisherAssetses.createPublisherAsset({
				name: this.product.name.en_US,
				[productRelationshipName]: this.product.id as unknown as string,
				publisherAssetType: PICK_LIST_ASSET_TYPE,
				r_accountEntryToPublisherAssets_accountEntryId:
					Liferay.CommerceContext.account?.accountId,
				sourceCode: await this.getPublisherAssetDocumentId(appFolderId),
				version: this.versions,
			});
		}
		catch {
			Liferay.Util.openToast({
				message:
					'Something went wrong when trying to upload a new package',
				type: 'danger',
			});
		}
	}
}
