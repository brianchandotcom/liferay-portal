/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

declare const Liferay: any;

@Injectable({
	providedIn: 'root',
})
export class headless_deliveryService {
	constructor(private http: HttpClient) {}

	public getSiteDocumentFoldersPage(
		siteId: any,
		aggregationTerms: any = null,
		fields: any = null,
		flatten: any = null,
		nestedFields: any = null,
		restrictFields: any = null,
		filter: any = null,
		page: any = null,
		pageSize: any = null,
		search: any = null,
		sort: any = null
	) {
		let queryParams = new HttpParams();
		if (aggregationTerms) {
			queryParams = queryParams.append(
				'aggregationTerms',
				aggregationTerms
			);
		}

		if (fields) {
			queryParams = queryParams.append('fields', fields);
		}

		if (flatten) {
			queryParams = queryParams.append('flatten', flatten);
		}

		if (nestedFields) {
			queryParams = queryParams.append('nestedFields', nestedFields);
		}

		if (restrictFields) {
			queryParams = queryParams.append('restrictFields', restrictFields);
		}

		if (filter) {
			queryParams = queryParams.append('filter', filter);
		}

		if (page) {
			queryParams = queryParams.append('page', page);
		}

		if (pageSize) {
			queryParams = queryParams.append('pageSize', pageSize);
		}

		if (search) {
			queryParams = queryParams.append('search', search);
		}

		if (sort) {
			queryParams = queryParams.append('sort', sort);
		}

		const httpOptions = {
			headers: new HttpHeaders({
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			}),
			params: queryParams,
		};

		const prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`/o/headless-delivery/v1.0/sites/${siteId}/document-folders`,
					httpOptions
				)
				.subscribe(
					(result) => {
						resolve(result);
					},
					(error) => {
						reject(error);
					}
				);
		});

		return prom;
	}
	public getDocumentFolderDocumentFoldersPage(
		parentDocumentFolderId: any,
		aggregationTerms: any = null,
		fields: any = null,
		flatten: any = null,
		nestedFields: any = null,
		restrictFields: any = null,
		filter: any = null,
		page: any = null,
		pageSize: any = null,
		search: any = null,
		sort: any = null
	) {
		let queryParams = new HttpParams();
		if (aggregationTerms) {
			queryParams = queryParams.append(
				'aggregationTerms',
				aggregationTerms
			);
		}

		if (fields) {
			queryParams = queryParams.append('fields', fields);
		}

		if (flatten) {
			queryParams = queryParams.append('flatten', flatten);
		}

		if (nestedFields) {
			queryParams = queryParams.append('nestedFields', nestedFields);
		}

		if (restrictFields) {
			queryParams = queryParams.append('restrictFields', restrictFields);
		}

		if (filter) {
			queryParams = queryParams.append('filter', filter);
		}

		if (page) {
			queryParams = queryParams.append('page', page);
		}

		if (pageSize) {
			queryParams = queryParams.append('pageSize', pageSize);
		}

		if (search) {
			queryParams = queryParams.append('search', search);
		}

		if (sort) {
			queryParams = queryParams.append('sort', sort);
		}

		const httpOptions = {
			headers: new HttpHeaders({
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			}),
			params: queryParams,
		};

		const prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`,
					httpOptions
				)
				.subscribe(
					(result) => {
						resolve(result);
					},
					(error) => {
						reject(error);
					}
				);
		});

		return prom;
	}
}
