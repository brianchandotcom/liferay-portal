import {
	AfterViewInit,
	Component,
	ElementRef,
	EventEmitter,
	OnInit,
	Output,
	Renderer2,
	ViewChild,
} from '@angular/core';
import {NgAudioRecorderService, OutputFormat} from 'ng-audio-recorder';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';

@Component({
	selector: 'chat-voice-note',
	templateUrl: './voice-note.component.html',
	styleUrls: ['./voice-note.component.css'],
})
export class VoiceNoteComponent implements AfterViewInit {
	private outputFormat: OutputFormat = OutputFormat.WEBM_BLOB;
	private attachment: any;
	public rawAudioObject: any;
	public isRecording: boolean = false;
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
		let prom = new Promise((resolve, reject) => {
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
		let file = await this.stop();
		console.log('called');
		this.rawAudioObject =
			'data:audio/webm;base64,' + (await this.blobToBase64(file as Blob));
		this.attachment = file;
	}
	getAudioToFile(theBlob: Blob, fileName: string): File {
		let file: any = theBlob;
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
	public timeOut: any;
	public timer = 0;
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
