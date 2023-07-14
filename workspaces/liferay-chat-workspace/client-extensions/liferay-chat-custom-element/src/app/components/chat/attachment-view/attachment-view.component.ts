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

import {AfterViewInit, Component, Input, OnInit} from '@angular/core';

import {WindowService} from '../../../services/chat/window.service';

declare const Liferay: any;

@Component({
	selector: 'attachment-viewer',
	styleUrls: ['./attachment-view.component.css'],
	templateUrl: './attachment-view.component.html',
})
export class AttachmentViewComponent implements OnInit, AfterViewInit {
	@Input('documentId')
	public documentId: any = '';

	public attachmentObject: any;
	public contentUrl: any;
	public encodingFormat: any;
	public fileExtension: any;
	public fileType: string = '';
	public isDocumentPreviewReady: boolean = true;
	public isLoading: boolean = false;
	public timeOut: any;

	public async getPreview() {
		this.isLoading = true;

		this.attachmentObject = await this.window.getAttachment(
			this.documentId
		);

		this.encodingFormat = this.attachmentObject['encodingFormat'];
		this.fileExtension = this.attachmentObject['fileExtension'];
		this.contentUrl = this.attachmentObject['contentUrl'];

		this.setFileType();

		this.isLoading = false;
	}

	public get liferay() {
		return Liferay;
	}

	constructor(private window: WindowService) {}

	setFileType() {
		if (this.encodingFormat.indexOf('pdf') !== -1) {
			this.fileType = 'pdf';
		}
		if (this.encodingFormat.indexOf('image') !== -1) {
			this.fileType = 'image';
		}
		if (this.encodingFormat.indexOf('audio') !== -1) {
			this.fileType = 'audio';
		}
	}

	ngAfterViewInit(): void {
		this.getPreview();
	}

	ngOnInit(): void {}

	handleImagePreviewError() {
		this.isDocumentPreviewReady = false;
		this.startTimer();
	}

	startTimer() {
		this.timeOut = setInterval(() => {
			this.isDocumentPreviewReady = true;
		}, 3000);
	}

	stopTimer() {
		clearInterval(this.timeOut);
	}
}
