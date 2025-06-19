/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';
import HeadlessDelivery from '../rest/HeadlessDelivery';
import HeadlessPublisherAssetses from '../rest/HeadlessPublisherAsset';

const DOCUMENTS_ROOT_FOLDER = 0;
const PICK_LIST_ASSET_TYPE = 'package';
const PUBLISHER_ASSETS_FOLDER = 'publisher_assets';

export default class PublisherAsset {
	private file: any;
	private appFolderName: string;
	private product: Product;
	private versions: string;

	constructor(file: any, product: Product, versions: string) {
		this.file = file;
		this.product = product;
		this.versions = versions;
		this.appFolderName = `app_${this.product.productId}`;
	}

	private async createPublisherAssetsFolderId(): Promise<number> {
		const response = await HeadlessDelivery.createDocumentFolder(
			PUBLISHER_ASSETS_FOLDER,
			DOCUMENTS_ROOT_FOLDER
		);

		return response.id;
	}

	private async getAppFolderId(publisherFolderId: number): Promise<number> {
		const {items: appFolders} =
			await HeadlessDelivery.getDocumentFolderDocuments(
				publisherFolderId
			);

		const appFolder = appFolders.find(
			(document: any) => document.name === this.appFolderName
		);

		let appFolderId = appFolder?.id;

		if (!appFolderId) {
			const packageFolder = await HeadlessDelivery.createDocumentFolder(
				this.appFolderName,
				publisherFolderId
			);

			appFolderId = packageFolder.id;
		}

		return appFolderId;
	}

	private async getPublisherAssetDocumentId(
		appFolderId: number
	): Promise<number> {
		const {items} =
			await HeadlessDelivery.getDocumentFolderDocuments(appFolderId);

		const appDocument = items.find(
			(document: any) => document.name === this.file.fileName
		);

		let appDocumentId = appDocument?.id;

		if (!appDocumentId) {
			const formData = new FormData();
			const blob = new Blob([this.file.file]);

			formData.append('file', blob, this.file.fileName);
			const sourceDocument =
				await HeadlessDelivery.createDocumentFolderDocument(
					appFolderId,
					formData
				);

			appDocumentId = sourceDocument.id;
		}

		return appDocumentId;
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
			publisherFolderId = this.createPublisherAssetsFolderId();
		}

		return publisherFolderId;
	}

	static async processPublisherAsset(
		file: any,
		product: Product,
		versions: string
	) {
		try {
			const publisherAsset = new PublisherAsset(file, product, versions);

			const publisherFolderId =
				await publisherAsset.getPublisherFolderId();

			const appFolderId =
				await publisherAsset.getAppFolderId(publisherFolderId);

			const appDocumentId =
				await publisherAsset.getPublisherAssetDocumentId(appFolderId);

			const accountId = Liferay.CommerceContext.account?.accountId;

			await HeadlessPublisherAssetses.createPublisherAsset({
				name: publisherAsset.product.name.en_US,
				publisherAssetType: PICK_LIST_ASSET_TYPE,
				r_accountEntryToPublisherAssets_accountEntryId: accountId,
				r_productEntryToPublisherAssets_CPDefinitionId: publisherAsset
					.product.id as unknown as string,
				sourceCode: appDocumentId,
				version: publisherAsset.versions,
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
