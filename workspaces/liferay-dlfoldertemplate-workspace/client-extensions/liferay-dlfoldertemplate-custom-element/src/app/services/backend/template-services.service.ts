/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {ConfigService} from '../config.service';

declare const Liferay: any;

@Injectable({
	providedIn: 'root',
})
export class TemplateServicesService {
	agentOauthAppId = 'liferay-dlfoldertemplate-oauth-application-user-agent';

	constructor(
		private http: HttpClient,
		private configService: ConfigService
	) {}

	suggestServerUrl() {
		const OAuthApp = Liferay.OAuth2Client.FromUserAgentApplication(
			this.agentOauthAppId
		).homePageURL;

		return OAuthApp;
	}

	public async createFolder(
		templateId: any,
		containerId: any,
		rootName: any
	) {
		const accessToken = (
			await Liferay.OAuth2Client.FromUserAgentApplication(
				this.agentOauthAppId
			)._getOrRequestToken()
		).access_token;
		const prom = new Promise((resolve, reject) => {
			const requestOptions = {
				headers: new HttpHeaders({
					Authorization: `Bearer ${accessToken}`,
				}),
			};
			try {
				this.http
					.post(
						`${this.suggestServerUrl()}${
							this.configService.configObject[
								'folder.generate.service.url'
							]
						}/${templateId}/${containerId}/${rootName}`,
						null,
						requestOptions
					)
					.subscribe(
						(result: any) => {
							resolve(result);
						},
						(error: any) => {
							reject(error);
						}
					);
			}
			catch (exp: any) {
				reject(exp);
			}
		});

		return prom;
	}
}
