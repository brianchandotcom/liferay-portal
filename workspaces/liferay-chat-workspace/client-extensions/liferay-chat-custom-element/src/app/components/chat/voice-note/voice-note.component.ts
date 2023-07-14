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

import {
	AfterViewInit,
	Component,
	ElementRef,
	EventEmitter,
	Output,
	Renderer2,
	ViewChild,
} from '@angular/core';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {NgAudioRecorderService, OutputFormat} from 'ng-audio-recorder';

@Component({
	selector: 'chat-voice-note',
	styleUrls: ['./voice-note.component.css'],
	templateUrl: './voice-note.component.html',
})
export class VoiceNoteComponent implements AfterViewInit {
	private attachment: any;
	private outputFormat: OutputFormat = OutputFormat.WEBM_BLOB;
	public isRecording: boolean = false;
	public rawAudioObject: any;
	public timeOut: any;
	public timer = 0;

	@Output('SendVoiceNote') sendVoiceNote = new EventEmitter<File>();
	@Output('CancelRecording') cancelRecording = new EventEmitter<string>();
	constructor(
		private audioRecorderService: NgAudioRecorderService,
		private renderer: Renderer2,
		private sanitizer: DomSanitizer
	) {}

	// @ts-ignore

	@ViewChild('audioPreview') audioTag: ElementRef;
	ngAfterViewInit(): void {
		this.startRecording();
	}

	stop() {
		const prom = new Promise((resolve, reject) => {
			this.audioRecorderService
				.stopRecording(this.outputFormat)
				.then((output) => {
					resolve(
						this.getAudioToFile(output as Blob, 'audioNote.webm')
					);
				})
				.catch((errorCase) => {
					reject(errorCase);
				});
		});

		return prom;
	}

	async startRecording() {
		this.timeOut = this.startTimer();
		this.isRecording = true;
		this.audioRecorderService.startRecording();
	}

	async stopRecording() {
		this.stopTimer();
		const file = await this.stop();
		this.rawAudioObject =
			'data:audio/webm;base64,' + (await this.blobToBase64(file as Blob));
		this.attachment = file;
	}

	getAudioToFile(theBlob: Blob, fileName: string): File {
		const file: any = theBlob;
		file.lastModifiedDate = new Date();
		file.name = fileName;

		return <File>theBlob;
	}

	async cancel() {
		this.stopTimer();
		this.attachment = null;
		this.cancelRecording.emit();
	}

	async sendRecording() {
		if (this.isRecording) {
			this.stopTimer();
			await this.stopRecording();
		}
		await this.sendVoiceNote.emit(this.attachment);
		this.attachment = null;
	}

	startTimer() {
		this.isRecording = true;
		this.timer = 0;
		this.timeOut = setInterval(() => {
			this.timer += 1;
		}, 1000);
	}

	stopTimer() {
		this.isRecording = false;
		clearInterval(this.timeOut);
	}

	blobToBase64(blob: Blob): Promise<string> {
		return new Promise<string>((resolve, reject) => {
			const reader = new FileReader();
			reader.onloadend = () => {
				const base64Data = reader.result as string;
				const base64Audio = base64Data.split(',')[1]; // Extract only the Base64 audio data
				resolve(base64Audio);
			};
			reader.onerror = reject;
			reader.readAsDataURL(blob);
		});
	}

	sanitizeUrl(url: string): SafeUrl {
		return this.sanitizer.bypassSecurityTrustUrl(url);
	}
}
