/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {v4 as uuidv4} from 'uuid';

declare const Liferay: any;

@Injectable({
    providedIn: 'root',
})
export class WindowService {
    public folderId = '';
    private message_object_serviceUrl = '/o/c/chatmessages/';

    constructor(private http: HttpClient) {
    }

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
        const messageId1 = await this.postMessageToLiferay(
            _toClientId,
            _message,
            true,
            fileId
        );
        const updatedMessage = await this.getMessageById(messageId1);

        // @ts-ignore
        const ids = {

            // @ts-ignore

            clientMessageId: updatedMessage['id'],
            fromMessageId: messageId1,
        };

        // @ts-ignore

        return ids;
    }

    public getMessages(toClientId: any, pageIndex = 1) {
        const userId = Liferay.ThemeDisplay.getUserId();
        const prom = new Promise((resolve) => {
            Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
                .fetch(`/chat/messages?clientID=${toClientId}&page=${pageIndex}`)
                .then(
                    (result: any) => {
                        result['items'] = result['items'].reverse();
                        resolve(result);
                    });
        });
        return prom;
    }

    public async getContacts() {
        let prom = new Promise((resolve, reject) => {
            Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
                .fetch('/chat/contacts').then((result: any) => {
                let contacts = result.items;
                const hashMap: any = {};
                contacts.forEach((contact: any) => {
                    if (
                        Liferay.ThemeDisplay.getUserId().toString() !==
                        contact['id'].toString()
                    ) {
                        hashMap[contact['id']] = {
                            id: contact['id'],
                            name: contact['name'],
                        };
                    }
                });
                resolve(hashMap);
            })
        });
        return prom;
    }

    public getMessageById(messageId: any) {
        const prom = new Promise((resolve) => {
            Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
                .fetch(`/chat/messages/${messageId}`)
                .then(
                    (result: any) => {
                        resolve(result);
                    });
        });
        return prom;
    }

    public async uploadFile(file: File, folderId: string) {
        const myGuid = uuidv4();
        let accessToken = (await Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')._getOrRequestToken()).access_token;

        const requestOptions = {
            headers: new HttpHeaders({
                'Authorization': `Bearer ${accessToken}`
            })
        };
        const globalsiteId = Liferay.ThemeDisplay.getCompanyGroupId();
        const formData = new FormData();
        const extension = file.name.split('.').pop();
        formData.append('file', file, `${myGuid}.${extension}`);
        const prom = new Promise((resolve, reject) => {
            this.http
                .post(
                    `${this.suggestServerUrl()}/chat/messages/attach/${globalsiteId}`,
                    formData,
                    requestOptions
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
        const prom = new Promise((resolve) => {
            Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
                .fetch(`/chat/messages/attachment/${documentId}`)
                .then(
                    (result: any) => {
                        resolve(result);
                    });
        });
        return prom;
    }

    private async postMessageToLiferay(
        _toClientId: any,
        _message: any,
        updateOwner: boolean,
        fileId: string | any = null
    ) {
        let accessToken = (await Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')._getOrRequestToken()).access_token;

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            }),
        };
        const prom = new Promise((resolve, reject) => {
            const body: any = {
                fromClientId: Liferay.ThemeDisplay.getUserId(),
                message: _message,
                toClientId: _toClientId,
                updateOwner,
            };
            if (fileId) {
                body['attachment'] = fileId;
            }
            Liferay.OAuth2Client.FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
                .fetch('/chat/messages', {
                    method: 'POST',
                    body: JSON.stringify(body),
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${accessToken}`
                    },
                }).then((result: any) => {
                resolve(result['id']);
            }, (error: any) => {
                reject(error);
            });
        });
        return prom;
    }
    suggestServerUrl() {
        let OAuthApp = Liferay
            .OAuth2Client
            .FromUserAgentApplication('liferay-chat-etc-node-oauth-application-user-agent')
            .homePageURL;
        return OAuthApp;
    }
}
