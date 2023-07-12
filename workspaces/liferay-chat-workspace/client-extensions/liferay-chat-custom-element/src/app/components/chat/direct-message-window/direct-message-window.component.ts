import {Component, EventEmitter, Input, Output} from '@angular/core';
import {WindowService} from '../../../services/chat/window.service';
import {NgAudioRecorderService, OutputFormat} from 'ng-audio-recorder';

declare const Liferay: any;
@Component({
	selector: 'app-direct-message-window',
	templateUrl: './direct-message-window.component.html',
	styleUrls: ['./direct-message-window.component.css'],
})
export class DirectMessageWindowComponent {
	@Input('socket')
	socket: any;

	// @ts-ignore

	@Input('Attachment')
	attachment: File | any;
	public autoScroll = true;
	@Input('ChatInfo')
	public chatInfo = {
		pageIndex: 0,
		numberOfPages: 0,
		TotalNumberOfMessages: 0,
	};
	@Input('FromClientId')
	public fromClientId: any;
	@Input('ToClientId')
	toClientId: any;
	@Input()
	public isLoading: boolean = false;
	@Input('Messages')
	public messages: any;
	public message = '';
	@Output() sendEmitter = new EventEmitter<string>();
	@Output() loadOlderEmitter = new EventEmitter<string>();
	public messageDirection(message: any) {
		return message['fromClientId'] == this.fromClientId ? 'right' : 'left';
	}
	send() {
		if (this.message.length <= 0) return;
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
	isRecording: boolean = false;
	private outputFormat: OutputFormat = OutputFormat.WEBM_BLOB;
	async cancelRecording() {
		console.log('cancel recording');
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
