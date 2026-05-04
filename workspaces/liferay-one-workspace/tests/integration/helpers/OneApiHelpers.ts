/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIRequestContext, expect} from '@playwright/test';

export interface OneApiOptions {
	baseURL?: string;
	basicAuth?: {password: string; user: string};
}

export type HeadlessPage<T> = {
	items: T[];
	page: number;
	pageSize: number;
	totalCount: number;
};

export class OneApiHelpers {
	readonly baseURL: string;
	readonly options: OneApiOptions;
	readonly request: APIRequestContext;

	constructor(request: APIRequestContext, options: OneApiOptions = {}) {
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

	private authHeader(): Record<string, string> {
		if (!this.options.basicAuth) {
			throw new Error('OneApiHelpers: basicAuth must be configured');
		}

		const {password, user} = this.options.basicAuth;
		const encoded = Buffer.from(`${user}:${password}`).toString('base64');

		return {Authorization: `Basic ${encoded}`};
	}

	private async headers(): Promise<Record<string, string>> {
		return {
			...this.authHeader(),
			'Content-Type': 'application/json',
		};
	}
}
