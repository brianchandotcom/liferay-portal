/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIRequestContext, expect} from '@playwright/test';

export interface APIOptions {
	baseURL?: string;
	basicAuth?: {password: string; user: string};
	oAuth2?: {clientId: string; clientSecret: string};
}

export type HeadlessPage<T> = {
	items: T[];
	page: number;
	pageSize: number;
	totalCount: number;
};

export class APIHelpers {
	readonly baseURL: string;
	readonly options: APIOptions;
	readonly request: APIRequestContext;

	private token: string | undefined;
	private tokenExpiry = 0;

	constructor(request: APIRequestContext, options: APIOptions = {}) {
		this.baseURL =
			options.baseURL ?? process.env.BASE_URL ?? 'http://localhost:8080';
		this.options = options;
		this.request = request;
	}

	async delete<T = unknown>(path: string): Promise<T | undefined> {
		const response = await this.request.delete(`${this.baseURL}${path}`, {
			headers: await this.headers(),
		});

		expect(response.ok(), await response.text()).toBeTruthy();

		if (response.status() === 204) {
			return undefined;
		}

		return (await response.json()) as T;
	}

	async get<T = unknown>(path: string): Promise<T> {
		const response = await this.request.get(`${this.baseURL}${path}`, {
			headers: await this.headers(),
		});

		expect(response.ok(), await response.text()).toBeTruthy();

		return (await response.json()) as T;
	}

	async post<T = unknown>(path: string, body: unknown): Promise<T> {
		const response = await this.request.post(`${this.baseURL}${path}`, {
			data: body,
			headers: await this.headers(),
		});

		expect(response.ok(), await response.text()).toBeTruthy();

		return (await response.json()) as T;
	}

	private async authHeader(): Promise<Record<string, string>> {
		if (this.options.oAuth2) {
			if (!this.token || Date.now() >= this.tokenExpiry) {
				this.token = await this.fetchOAuthToken();
			}

			return {Authorization: `Bearer ${this.token}`};
		}

		if (!this.options.basicAuth) {
			throw new Error(
				'APIHelpers: either oAuth2 or basicAuth must be configured'
			);
		}

		const {password, user} = this.options.basicAuth;
		const encoded = Buffer.from(`${user}:${password}`).toString('base64');

		return {Authorization: `Basic ${encoded}`};
	}

	private async fetchOAuthToken(): Promise<string> {
		const {clientId, clientSecret} = this.options.oAuth2!;

		const response = await this.request.post(
			`${this.baseURL}/o/oauth2/token`,
			{
				form: {
					client_id: clientId,
					client_secret: clientSecret,
					grant_type: 'client_credentials',
				},
			}
		);

		expect(response.ok(), await response.text()).toBeTruthy();

		const body = (await response.json()) as {
			access_token: string;
			expires_in?: number;
		};

		this.tokenExpiry = Date.now() + ((body.expires_in ?? 3600) - 60) * 1000;

		return body.access_token;
	}

	private async headers(): Promise<Record<string, string>> {
		return {
			...(await this.authHeader()),
			'Content-Type': 'application/json',
		};
	}
}
