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

import {animate, state, style, transition, trigger} from '@angular/animations';
import {
	AfterViewInit,
	Component,
	Input,
	OnInit,
	ViewChild,
} from '@angular/core';
import {NgxNotifierService} from 'ngx-notifier';
import {io} from 'socket.io-client';

import {ScrollToBottomDirective} from '../../../directives/scroll-to-bottom.directive';
import {WindowService} from '../../../services/chat/window.service';
import notificationSoundBase64 from './notificationSoundBase64';

declare const Liferay: any;

@Component({
	animations: [
		trigger('slideInOut', [
			state(
				'in',
				style({
					height: '*',
					overflow: 'hidden',
				})
			),
			state(
				'out',
				style({
					height: '0px',
					opacity: '0',
					overflow: 'hidden',
				})
			),
			transition('in => out', animate('400ms ease-in-out')),
			transition('out => in', animate('400ms ease-in-out')),
		]),
	],
	selector: 'liferay-chat-window',
	styleUrls: ['./window.component.css'],
	templateUrl: './window.component.html',
})
export class WindowComponent implements OnInit, AfterViewInit {
	connectedClients = [];
	connectedClientsHash: any = {};
	contacts: any;
	hasContactsListingPermissions: boolean = false;
	helpMenuOpen: string | any = 'out';
	isConnected: boolean = false;
	isLoading = false;
	message: any;
	selectedClient: any = null;
	selectedClientChatData = {
		TotalNumberOfMessages: 0,
		numberOfPages: 0,
		pageIndex: 0,
	};
	selectedClientMessages: any;
	selectedTabIndex = 0;
	socket: any;

	@Input('attachmentFolderId')
	attachmentFolderId: any = '45993';

	@Input('socketServerAddress')
	socketServerAddress: any = this.suggestSocketServerUrl();

	@ViewChild(ScrollToBottomDirective) // @ts-ignore
	scroll: ScrollToBottomDirective;

	constructor(
		private window: WindowService,
		private ngxNotifierService: NgxNotifierService
	) {}

	public get SelfId() {
		return Liferay.ThemeDisplay.getUserId();
	}

	toggleHelpMenu(): void {
		if (!this.contacts) {
			this.initializeConnection();
		}
		this.helpMenuOpen = this.helpMenuOpen === 'out' ? 'in' : 'out';
	}

	async ngOnInit() {}

	connect() {
		this.socket = io(this.socketServerAddress, {
			query: {
				userId: Liferay.ThemeDisplay.getUserId(),
				userName: Liferay.ThemeDisplay.getUserName(),
			},
		});
		this.socket.on('connect', () => {
			this.isConnected = true;
			this.socket.emit('who-is-on');
		});
		this.socket.on('who-is-on', (data: []) => {
			this.updateActiveUsers(data);
		});
		this.socket.on('disconnect', () => {
			this.isConnected = false;
		});
		this.socket.on('message', (message: any) => {
			this.playNotification();
			this.receiveMessageEvent(message[1], message[0]);
		});
	}

	updateActiveUsers(usersList: []) {
		this.connectedClientsHash = {};
		usersList.forEach((client) => {
			this.connectedClientsHash[client['id']] = client['name'];
		});
		this.connectedClients = usersList;
		if (!this.hasContactsListingPermissions) {
			const contacts = {};
			for (let index = 0; index < usersList.length; index++) {

				// @ts-ignore

				contacts[usersList[index]['id']] = {
					id: usersList[index]['id'],
					name: usersList[index]['name'],
				};
			}

			this.contacts = contacts;
		}
	}

	getClientName(id: any) {

		// @ts-ignore

		return this.connectedClientsHash[id];
	}

	async receiveMessageEvent(messageId: string, client: any) {
		if (this.selectedClient && this.selectedClient.id === client) {
			const newMessage = await this.window.getMessageById(messageId);

			// @ts-ignore

			this.selectedClientMessages.push(newMessage);
		}
		else {
			if (this.contacts[client]['unread']) {
				this.contacts[client]['unread'] =
					Number(this.contacts[client]['unread']) + 1;
			}
			else {
				this.contacts[client]['unread'] = 1;
			}
		}
	}

	sendMessage(messageId: any, client: any) {
		this.socket.emit('message', [client, messageId]);
	}

	public getInitials(name: string) {
		const nameParts = name.split(' ');

		return nameParts.length > 1
			? nameParts[0][0] + nameParts[1][0]
			: name[0] + name[name.length - 1];
	}

	async openChat(client: any) {
		this.contacts[client.id]['unread'] = '';
		const data = await this.window.getMessages(client.id);

		// @ts-ignore

		this.selectedClientChatData.numberOfPages = data['lastPage'];

		// @ts-ignore

		this.selectedClientChatData.pageIndex = data['page'];

		// @ts-ignore

		this.selectedClientChatData.TotalNumberOfMessages = data['totalCount'];

		// @ts-ignore

		this.selectedClientMessages = data['items'];
		this.selectedClient = client;
		this.selectedTabIndex = 1;
	}

	async loadContacts() {
		try {
			this.isLoading = true;
			this.contacts = await this.window.getContacts();
			if (Object.keys(this.contacts).length <= 0) {
				this.hasContactsListingPermissions = false;
			}
			else {
				this.hasContactsListingPermissions = true;
			}
		}
		catch (exp) {
			this.hasContactsListingPermissions = false;
		}
		finally {
			this.isLoading = false;
			this.connect();
		}
	}

	async loadOlderMessages() {
		this.isLoading = true;
		this.selectedClientChatData.pageIndex++;
		const data = await this.window.getMessages(
			this.selectedClient.id,
			this.selectedClientChatData.pageIndex
		);

		// @ts-ignore

		this.selectedClientChatData.numberOfPages = data['lastPage'];

		// @ts-ignore

		this.selectedClientChatData.pageIndex = data['page'];

		// @ts-ignore

		this.selectedClientChatData.TotalNumberOfMessages = data['totalCount'];

		Array.prototype.unshift.apply(
			this.selectedClientMessages,

			// @ts-ignore

			data['items']
		);
		this.isLoading = false;
	}

	getMessageClass(message: any) {
		return message['fromClientId'] === Liferay.ThemeDisplay.getUserId()
			? 'me'
			: 'you';
	}

	async handleChildSend(eventData: any) {
		this.isLoading = true;
		const messageId = await this.window.postMessage(
			this.selectedClient.id,
			eventData
		);
		await this.updateSelf(messageId.fromMessageId);
		this.sendMessage(messageId.clientMessageId, this.selectedClient.id);
		this.isLoading = false;
	}

	async send() {
		const ids = await this.window.postMessage(
			this.selectedClient.id,
			this.message
		);
		await this.updateSelf(ids.fromMessageId);
		this.sendMessage(ids.clientMessageId, this.selectedClient.id);
	}

	async updateSelf(messageId: any) {
		const newMessage = await this.window.getMessageById(messageId);

		// @ts-ignore

		this.selectedClientMessages.push(newMessage);
	}

	initializeConnection() {
		if (Liferay.ThemeDisplay.isSignedIn()) {
			this.window.folderId = this.attachmentFolderId;
			this.loadContacts();
		}
	}

	ngAfterViewInit(): void {
		this.initializeConnection();
	}

	back() {
		this.selectedTabIndex = 0;
		this.selectedClient = null;
	}

	public getKeys(object: any) {
		const keys = Object.keys(object);

		return keys;
	}

	getClientStatus(client: string) {
		return this.connectedClientsHash[client]
			? 'online-contact'
			: 'offline-contact';
	}
	playNotification() {
		const audio = new Audio();

		audio.src = notificationSoundBase64;

		audio.play();
	}

	async handleChildFileSend($event: File) {
		try {
			this.isLoading = true;
			const ids = await this.window.postMessage(
				this.selectedClient.id,
				'File Attachment',
				$event
			);
			await this.updateSelf(ids.fromMessageId);
			this.sendMessage(ids.clientMessageId, this.selectedClient.id);
		}
		catch (exp) {

			// @ts-ignore

			this.ngxNotifierService.createToast(exp.error.title, '', 150000);
		}
		finally {
			this.isLoading = false;
		}
	}

	suggestSocketServerUrl() {
		let home = Liferay.ThemeDisplay.getURLHome();

		if (home.indexOf('webserver') > -1) {
			const domain = new URL(home);

			return domain.origin.replace('webserver', 'chatsocketserver');
		}
		else {
			const domain = new URL(home);
			home = domain.origin;
			const server = home.substring(
				home.indexOf('/') + 2,
				home.indexOf('-')
			);
			const serverUrl =
				server.toLowerCase() === 'uat'
					? '-extuat.lfr.cloud'
					: '-extprd.lfr.cloud';
			const socketServer = home
				.substring(0, home.indexOf('.'))
				.replace(server, 'chatsocketserver');

			return socketServer + serverUrl;
		}
	}
}
