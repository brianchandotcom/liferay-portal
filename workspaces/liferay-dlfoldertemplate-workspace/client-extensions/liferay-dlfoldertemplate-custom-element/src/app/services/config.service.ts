import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ConfigService {
    constructor() {
    }
    public configObject = {
        "backend.project.id": "liferay-data-import-export-services",
        "dataimporter.service.end.point": "/o/c/dataimporters/",
        "folder.template.service.end.point": "/o/c/foldertemplates/",
        "jobs.attach.service.url":"/jobs/upload/",
        "jobs.status.service.url":"/jobs/status/",
        "folder.generate.service.url":"/jobs/create/folder/direct"
    }
}
