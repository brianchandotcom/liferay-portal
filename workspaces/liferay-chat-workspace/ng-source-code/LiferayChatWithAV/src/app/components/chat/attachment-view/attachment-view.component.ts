import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {WindowService} from "../../../services/chat/window.service";

declare const Liferay: any;

@Component({
    selector: 'attachment-viewer',
    templateUrl: './attachment-view.component.html',
    styleUrls: ['./attachment-view.component.css']
})
export class AttachmentViewComponent implements OnInit, AfterViewInit {
    @Input('documentId')
    public documentId: any = "";
    public isLoading: boolean = false;
    public attachmentObject: any;
    public encodingFormat: any;
    public fileExtension: any;
    public contentUrl: any;
    public fileType: string = "";
    constructor(private window: WindowService) {
    }
    public get liferay() {
        return Liferay;
    }
    public async getPreview() {
        this.isLoading = true;
        this.attachmentObject = await this.window.getAttachment(this.documentId);
        this.encodingFormat = this.attachmentObject["encodingFormat"];
        this.fileExtension = this.attachmentObject["fileExtension"];
        this.contentUrl = this.attachmentObject["contentUrl"];
        this.setFileType();
        this.isLoading = false;
    }
    setFileType() {
        if (this.encodingFormat.indexOf('pdf') != -1) {
            this.fileType = "pdf";
        }
        if (this.encodingFormat.indexOf('image') != -1) {
            this.fileType = "image";
        }
        if (this.encodingFormat.indexOf('audio') != -1) {
            this.fileType = "audio";
        }
    }
    ngAfterViewInit(): void {
        this.getPreview()
    }
    ngOnInit(): void {
    }
    isDocumentPreviewReady : boolean = true;
    handleImagePreviewError() {
        this.isDocumentPreviewReady = false;
        this.startTimer();
    }
    public timeOut : any;
    startTimer()
    {
        this.timeOut = setInterval(()=>{
            this.isDocumentPreviewReady = true;
        },3000);
    }
    stopTimer()
    {
        clearInterval(this.timeOut);
    }
}
