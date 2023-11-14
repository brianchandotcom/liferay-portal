import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpParams, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

declare const Liferay: any;

@Injectable({
    providedIn: 'root'
})
export class foldertemplateinformationsService {
    constructor(private http: HttpClient) {
    }

    public deleteByExternalReferenceCode(externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/c/foldertemplateinformations/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }
    public getByExternalReferenceCode(externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/c/foldertemplateinformations/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchByExternalReferenceCode(externalReferenceCode: any, FolderTemplateInformation: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/c/foldertemplateinformations/by-external-reference-code/${externalReferenceCode}`, FolderTemplateInformation, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putByExternalReferenceCode(externalReferenceCode: any, FolderTemplateInformation: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/c/foldertemplateinformations/by-external-reference-code/${externalReferenceCode}`, FolderTemplateInformation, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postFolderTemplateInformationsPageExportBatch(filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
        if (filter)
            queryParams = queryParams.append('filter', filter);

        if (search)
            queryParams = queryParams.append('search', search);

        if (sort)
            queryParams = queryParams.append('sort', sort);

        if (callbackURL)
            queryParams = queryParams.append('callbackURL', callbackURL);

        if (contentType)
            queryParams = queryParams.append('contentType', contentType);

        if (fieldNames)
            queryParams = queryParams.append('fieldNames', fieldNames);

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/c/foldertemplateinformations/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getFolderTemplateInformationsPage(aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (flatten)
            queryParams = queryParams.append('flatten', flatten);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (filter)
            queryParams = queryParams.append('filter', filter);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);

        if (search)
            queryParams = queryParams.append('search', search);

        if (sort)
            queryParams = queryParams.append('sort', sort);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/c/foldertemplateinformations/`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postFolderTemplateInformation(FolderTemplateInformation: any) {

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })


        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/c/foldertemplateinformations/`, FolderTemplateInformation, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteFolderTemplateInformationBatch(callbackURL: any = null, requestBodyobject: any) {
        let queryParams = new HttpParams();
        if (callbackURL)
            queryParams = queryParams.append('callbackURL', callbackURL);

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
            , data: requestBodyobject

        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/c/foldertemplateinformations/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postFolderTemplateInformationBatch(callbackURL: any = null, requestBodyobject: any) {
        let queryParams = new HttpParams();
        if (callbackURL)
            queryParams = queryParams.append('callbackURL', callbackURL);

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/c/foldertemplateinformations/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putFolderTemplateInformationBatch(callbackURL: any = null, requestBodyobject: any) {
        let queryParams = new HttpParams();
        if (callbackURL)
            queryParams = queryParams.append('callbackURL', callbackURL);

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/c/foldertemplateinformations/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getOpenAPI(type: any) {

        let queryParams = new HttpParams();

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/c/foldertemplateinformations/openapi.${type}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteFolderTemplateInformation(folderTemplateInformationId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/c/foldertemplateinformations/${folderTemplateInformationId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getFolderTemplateInformation(folderTemplateInformationId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/c/foldertemplateinformations/${folderTemplateInformationId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchFolderTemplateInformation(folderTemplateInformationId: any, FolderTemplateInformation: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/c/foldertemplateinformations/${folderTemplateInformationId}`, FolderTemplateInformation, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putFolderTemplateInformation(folderTemplateInformationId: any, FolderTemplateInformation: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/c/foldertemplateinformations/${folderTemplateInformationId}`, FolderTemplateInformation, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getFolderTemplateInformationPermissionsPage(folderTemplateInformationId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (roleNames)
            queryParams = queryParams.append('roleNames', roleNames);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/c/foldertemplateinformations/${folderTemplateInformationId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putFolderTemplateInformationPermissionsPage(folderTemplateInformationId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/c/foldertemplateinformations/${folderTemplateInformationId}/permissions`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }

}
