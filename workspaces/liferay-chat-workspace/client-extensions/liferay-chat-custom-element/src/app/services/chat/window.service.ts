import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {v4 as uuidv4} from 'uuid';
import {map} from 'rxjs/operators';

declare const Liferay: any;

@Injectable({
	providedIn: 'root',
})
export class WindowService {
	private message_object_serviceUrl = '/o/c/chatmessages/';
	constructor(private http: HttpClient) {}
	public folderId = '';
	public async postMessage(
		_toClientId: any,
		_message: any,
		file: File | any = null
	) {
		let fileId: string | any = null;
		if (file) {

			// @ts-ignore

			fileId = (await this.uploadFile(file, this.folderId))['id'];
		}
		let messageId1 = await this.postMessageToLiferay(
			_toClientId,
			_message,
			true,
			fileId
		);
		let updatedMessage = await this.getMessageById(messageId1);

		// @ts-ignore

		let ids = {

			// @ts-ignore

			clientMessageId: updatedMessage['clientMessageID'],
			fromMessageId: messageId1,
		};

		// @ts-ignore

		return ids;
	}
	private postMessageToLiferay(
		_toClientId: any,
		_message: any,
		updateOwner: boolean,
		fileId: string | any = null
	) {
		const httpOptions = {
			headers: new HttpHeaders({
				'Content-Type': 'application/json',
			}),
		};
		let prom = new Promise((resolve, reject) => {
			let body: any = {
				fromClientId: Liferay.ThemeDisplay.getUserId(),
				message: _message,
				toClientId: _toClientId,
				updateOwner: updateOwner,
			};
			if (fileId) {
				body['attachment'] = fileId;
			}
			this.http
				.post(
					`${this.message_object_serviceUrl}?p_auth=${Liferay.authToken}`,
					JSON.stringify(body),
					httpOptions
				)
				.subscribe(
					(result) => {

						// @ts-ignore

						resolve(result['id']);
					},
					(error) => {
						reject(error);
					}
				);
		});
		return prom;
	}
	public getMessages(toClientId: any, pageIndex = 1) {
		let userId = Liferay.ThemeDisplay.getUserId();
		let prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`${this.message_object_serviceUrl}?filter=%28creatorId%20eq%20${userId}%20and%20toClientId%20eq%20%27${toClientId}%27%20and%20fromClientId%20eq%20%27${userId}%27%29%20or%20%28creatorId%20eq%20${userId}%20and%20toClientId%20eq%20%27${userId}%27%20and%20fromClientId%20eq%20%27${toClientId}%27%29&sort=dateCreated%3Adesc&page=${pageIndex}&p_auth=${Liferay.authToken}`
				)
				.subscribe((result) => {

					// @ts-ignore

					result['items'] = result['items'].reverse();
					resolve(result);
				});
		});
		return prom;
	}
	public async getContacts() {
		let prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`/o/headless-admin-user/v1.0/user-accounts?page=0&p_auth=${Liferay.authToken}`
				)
				.pipe(
					map((res: any) => res.items),
					map((contacts: any[]) => {
						let hashMap: any = {};
						contacts.forEach((contact: any) => {
							if (
								Liferay.ThemeDisplay.getUserId() !=
								contact['id']
							) {
								hashMap[contact['id']] = {
									id: contact['id'],
									name: contact['name'],
								};
							}
						});
						return hashMap;
					})
				)
				.subscribe(
					(result) => {
						resolve(result);
					},
					(error) => {
						reject('Unable to get users list!');
					}
				);
		});
		return prom;
	}
	public getMessageById(messageId: any) {
		let userId = Liferay.ThemeDisplay.getUserId();
		let prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`${this.message_object_serviceUrl}/${messageId}?p_auth=${Liferay.authToken}`
				)
				.subscribe((result) => {
					resolve(result);
				});
		});
		return prom;
	}
	public async uploadFile(file: File, folderId: string) {
		const myGuid = uuidv4();
		const formData = new FormData();
		const extension = file.name.split('.').pop();
		formData.append('file', file, `${myGuid}.${extension}`);
		let prom = new Promise((resolve, reject) => {
			this.http
				.post(
					`/o/headless-delivery/v1.0/document-folders/${folderId}/documents?p_auth=${Liferay.authToken}`,
					formData
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
	public async getAttachment(documentId: any) {
		let prom = new Promise((resolve, reject) => {
			this.http
				.get(
					`/o/headless-delivery/v1.0/documents/${documentId}?p_auth=${Liferay.authToken}`
				)
				.subscribe((result) => {
					resolve(result);
				});
		});
		return prom;
	}
}
