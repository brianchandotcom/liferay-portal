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

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgAudioRecorderService, OutputFormat} from 'ng-audio-recorder';

import {WindowService} from '../../../services/chat/window.service';

@Component({
	selector: 'app-direct-message-window',
	styleUrls: ['./direct-message-window.component.css'],
	templateUrl: './direct-message-window.component.html',
})
export class DirectMessageWindowComponent {
	public autoScroll = true;
	public isRecording: boolean = false;
	public message = '';

	private outputFormat: OutputFormat = OutputFormat.WEBM_BLOB;

	@Input('socket')
	socket: any;

	// @ts-ignore

	@Input('Attachment')
	attachment: File | any;

	@Input('ChatInfo')
	public chatInfo = {
		TotalNumberOfMessages: 0,
		numberOfPages: 0,
		pageIndex: 0,
	};

	@Input('FromClientId')
	public fromClientId: any;

	@Input('ToClientId')
	toClientId: any;

	@Input()
	public isLoading: boolean = false;

	@Input('Messages')
	public messages: any;

	@Output() sendEmitter = new EventEmitter<string>();
	@Output() loadOlderEmitter = new EventEmitter<string>();
	public messageDirection(message: any) {
		return message['fromClientId'] === this.fromClientId ? 'right' : 'left';
	}

	send() {
		if (this.message.length <= 0) {
			return;
		}
		this.autoScroll = true;
		this.isLoading = true;

		this.sendEmitter.emit(this.message);

		this.isLoading = false;
		this.message = '';
	}

	@Output('SendFile') fileAttached = new EventEmitter<File>();
	async onFileSelected(event: any) {
		this.autoScroll = false;
		this.isLoading = true;
		this.attachment = event.target.files[0];

		await this.fileAttached.emit(this.attachment);

		this.attachment = null;
		this.isLoading = false;
		this.autoScroll = true;
	}

	loadOlder() {
		this.autoScroll = false;
		this.loadOlderEmitter.emit();
	}

	constructor(
		private window: WindowService,
		private audioRecorderService: NgAudioRecorderService
	) {}

	async cancelRecording() {
		this.isLoading = false;
		this.autoScroll = true;
		this.isRecording = false;
	}

	async sendRecording(file: File) {
		this.autoScroll = false;
		this.isLoading = true;

		await this.fileAttached.emit(file);

		this.isLoading = false;
		this.autoScroll = true;
		this.isRecording = false;
	}

	startRecording() {
		this.isRecording = true;
	}
}
