import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpParams, HttpHeaders} from "@angular/common/http";
import {map} from "rxjs/operators";

declare const Liferay: any;

@Injectable({
    providedIn: 'root'
})
export class headless_deliveryService {

    constructor(private http: HttpClient) {
    }

    public deleteBlogPostingImage(blogPostingImageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/blog-posting-images/${blogPostingImageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPostingImage(blogPostingImageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/blog-posting-images/${blogPostingImageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteBlogPostingImagesPage(siteId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPostingImage(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPostingImageBatch(siteId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteBlogPostingImageBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/blog-posting-images/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPostingImagesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteBlogPostingByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteBlogPostingByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteBlogPostingByExternalReferenceCode(siteId: any, externalReferenceCode: any, BlogPosting: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}`, BlogPosting, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPostingRenderedContentByDisplayPageDisplayPageKey(blogPostingId: any, displayPageKey: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/rendered-content-by-display-page/${displayPageKey}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteBlogPosting(blogPostingId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPosting(blogPostingId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchBlogPosting(blogPostingId: any, BlogPosting: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, BlogPosting, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putBlogPosting(blogPostingId: any, BlogPosting: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, BlogPosting, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteBlogPostingMyRating(blogPostingId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPostingMyRating(blogPostingId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postBlogPostingMyRating(blogPostingId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putBlogPostingMyRating(blogPostingId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteBlogPostingsPage(siteId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPosting(siteId: any, BlogPosting: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings`, BlogPosting, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteBlogPostingSubscribe(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteBlogPostingUnsubscribe(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPostingBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteBlogPostingBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/blog-postings/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putBlogPostingBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/blog-postings/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPostingPermissionsPage(blogPostingId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putBlogPostingPermissionsPage(blogPostingId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteBlogPostingsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteBlogPostingPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteBlogPostingPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteComment(commentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/comments/${commentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getComment(commentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/comments/${commentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putComment(commentId: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/comments/${commentId}`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getBlogPostingCommentsPage(blogPostingId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postBlogPostingComment(blogPostingId: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getCommentCommentsPage(parentCommentId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/comments/${parentCommentId}/comments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postCommentComment(parentCommentId: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/comments/${parentCommentId}/comments`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentCommentsPage(documentId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/documents/${documentId}/comments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentComment(documentId: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/documents/${documentId}/comments`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentCommentsPage(structuredContentId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentComment(structuredContentId: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, blogPostingExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, blogPostingExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, blogPostingExternalReferenceCode: any, externalReferenceCode: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, parentCommentExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, parentCommentExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, parentCommentExternalReferenceCode: any, externalReferenceCode: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, documentExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, documentExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, documentExternalReferenceCode: any, externalReferenceCode: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, structuredContentExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, structuredContentExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId: any, structuredContentExternalReferenceCode: any, externalReferenceCode: any, Comment: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, Comment, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentCommentsPageExportBatch(structuredContentId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteCommentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/comments/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putCommentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/comments/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentCommentBatch(documentId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/documents/${documentId}/comments/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postBlogPostingCommentsPageExportBatch(blogPostingId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentCommentsPageExportBatch(documentId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/documents/${documentId}/comments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postBlogPostingCommentBatch(blogPostingId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentCommentBatch(structuredContentId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentElementsPage(siteId: any, aggregationTerms: any = null, fields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentElementsPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryContentElementsPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-elements/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteContentElementsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/content-elements/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentSetByKeyContentSetElementsPage(assetLibraryId: any, key: any, fields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-sets/by-key/${key}/content-set-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentSetByUuidContentSetElementsPage(assetLibraryId: any, uuid: any, fields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-sets/by-uuid/${uuid}/content-set-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentSetByKeyContentSetElementsPage(siteId: any, key: any, fields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-sets/by-key/${key}/content-set-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentSetByUuidContentSetElementsPage(siteId: any, uuid: any, fields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-sets/by-uuid/${uuid}/content-set-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getContentSetContentSetElementsPage(contentSetId: any, fields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/content-sets/${contentSetId}/content-set-elements`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getContentStructure(contentStructureId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/content-structures/${contentStructureId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentStructuresPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentStructuresPage(siteId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-structures`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryContentStructuresPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentStructurePermissionsPage(assetLibraryId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryContentStructurePermissionsPage(assetLibraryId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getContentStructurePermissionsPage(contentStructureId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/content-structures/${contentStructureId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putContentStructurePermissionsPage(contentStructureId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/content-structures/${contentStructureId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteContentStructuresPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/content-structures/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentStructurePermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-structures/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteContentStructurePermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/content-structures/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteContentTemplate(siteId: any, contentTemplateId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/content-templates/${contentTemplateId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryContentTemplatesPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-templates`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryContentTemplatesPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-templates/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteContentTemplatesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/content-templates/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteDocumentsFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentsFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any) {

        let queryParams = new HttpParams();

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteDocumentsFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentFoldersRatedByMePage(assetLibraryId: any, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/rated-by-me`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocumentFolder(documentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentFolder(documentFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchDocumentFolder(documentFolderId: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolder(documentFolderId: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocumentFolderMyRating(documentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentFolderMyRating(documentFolderId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentFolderMyRating(documentFolderId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolderMyRating(documentFolderId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentFoldersPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocumentFolder(siteId: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolderSubscribe(documentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentFoldersPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocumentFolder(assetLibraryId: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentFolderDocumentFoldersPage(parentDocumentFolderId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentFolderDocumentFolder(parentDocumentFolderId: any, DocumentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`, DocumentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentFoldersRatedByMePage(siteId: any, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders/rated-by-me`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolderUnsubscribe(documentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocumentFoldersPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentFolderPermissionsPage(assetLibraryId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryDocumentFolderPermissionsPage(assetLibraryId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocumentFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/document-folders/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/document-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocumentFolderBatch(assetLibraryId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocumentFolderBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentFolderPermissionsPage(documentFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentFolderPermissionsPage(documentFolderId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocumentFoldersPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentFolderPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteDocumentFolderPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/document-folders/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteAssetLibraryDocumentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryDocumentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentRenderedContentByDisplayPageDisplayPageKey(documentId: any, displayPageKey: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/documents/${documentId}/rendered-content-by-display-page/${displayPageKey}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocumentMyRating(documentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentMyRating(documentId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentMyRating(documentId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentMyRating(documentId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentsPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocument(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/documents`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocument(documentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/documents/${documentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocument(documentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/documents/${documentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchDocument(documentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/documents/${documentId}`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocument(documentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/documents/${documentId}`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentsPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocument(assetLibraryId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentFolderDocumentsPage(documentFolderId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentFolderDocument(documentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteDocumentByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteDocumentByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentsRatedByMePage(assetLibraryId: any, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/rated-by-me`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentsRatedByMePage(siteId: any, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents/rated-by-me`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteDocumentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/documents/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentBatch(callbackURL: any = null, MultipartBody: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/documents/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocumentBatch(siteId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/documents/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getDocumentPermissionsPage(documentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/documents/${documentId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putDocumentPermissionsPage(documentId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/documents/${documentId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocumentBatch(assetLibraryId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentFolderDocumentBatch(documentFolderId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryDocumentsPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryDocumentPermissionsPage(assetLibraryId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryDocumentPermissionsPage(assetLibraryId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postDocumentFolderDocumentsPageExportBatch(documentFolderId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteDocumentsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/documents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteDocumentPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/documents/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteDocumentPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/documents/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteKnowledgeBaseArticleByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseArticleByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseArticleByExternalReferenceCode(siteId: any, externalReferenceCode: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseArticleKnowledgeBaseArticlesPage(parentKnowledgeBaseArticleId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-articles/${parentKnowledgeBaseArticleId}/knowledge-base-articles`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseArticleKnowledgeBaseArticle(parentKnowledgeBaseArticleId: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-articles/${parentKnowledgeBaseArticleId}/knowledge-base-articles`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseFolderKnowledgeBaseArticlesPage(knowledgeBaseFolderId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseFolderKnowledgeBaseArticle(knowledgeBaseFolderId: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseArticle(knowledgeBaseArticleId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseArticle(knowledgeBaseArticleId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchKnowledgeBaseArticle(knowledgeBaseArticleId: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticle(knowledgeBaseArticleId: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseArticleMyRating(knowledgeBaseArticleId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseArticleMyRating(knowledgeBaseArticleId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseArticleMyRating(knowledgeBaseArticleId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticleMyRating(knowledgeBaseArticleId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseArticlesPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseArticle(siteId: any, KnowledgeBaseArticle: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles`, KnowledgeBaseArticle, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticleSubscribe(knowledgeBaseArticleId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticleUnsubscribe(knowledgeBaseArticleId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseArticleSubscribe(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseArticleUnsubscribe(siteId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseFolderKnowledgeBaseArticlesPageExportBatch(knowledgeBaseFolderId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseFolderKnowledgeBaseArticleBatch(knowledgeBaseFolderId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseArticlesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseArticleBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseArticleBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-articles/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticleBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseArticlePermissionsPage(knowledgeBaseArticleId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseArticlePermissionsPage(knowledgeBaseArticleId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseArticlePermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseArticlePermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(siteId: any, knowledgeBaseArticleExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(siteId: any, knowledgeBaseArticleExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(knowledgeBaseArticleId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseArticleKnowledgeBaseAttachment(knowledgeBaseArticleId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseAttachment(knowledgeBaseAttachmentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-attachments/${knowledgeBaseAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseAttachment(knowledgeBaseAttachmentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-attachments/${knowledgeBaseAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseArticleKnowledgeBaseAttachmentsPageExportBatch(knowledgeBaseArticleId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch(knowledgeBaseArticleId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseAttachmentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-attachments/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteKnowledgeBaseFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any, KnowledgeBaseFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}`, KnowledgeBaseFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseFolderKnowledgeBaseFoldersPage(parentKnowledgeBaseFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-folders/${parentKnowledgeBaseFolderId}/knowledge-base-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postKnowledgeBaseFolderKnowledgeBaseFolder(parentKnowledgeBaseFolderId: any, KnowledgeBaseFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/knowledge-base-folders/${parentKnowledgeBaseFolderId}/knowledge-base-folders`, KnowledgeBaseFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseFolder(knowledgeBaseFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseFolder(knowledgeBaseFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchKnowledgeBaseFolder(knowledgeBaseFolderId: any, KnowledgeBaseFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, KnowledgeBaseFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseFolder(knowledgeBaseFolderId: any, KnowledgeBaseFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, KnowledgeBaseFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseFoldersPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseFolder(siteId: any, KnowledgeBaseFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders`, KnowledgeBaseFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseFoldersPageExportBatch(siteId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteKnowledgeBaseFolderBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteKnowledgeBaseFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/knowledge-base-folders/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getKnowledgeBaseFolderPermissionsPage(knowledgeBaseFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putKnowledgeBaseFolderPermissionsPage(knowledgeBaseFolderId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteKnowledgeBaseFolderPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteKnowledgeBaseFolderPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteLanguagesPage(siteId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/languages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryLanguagesPage(assetLibraryId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/languages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryLanguagesPageExportBatch(assetLibraryId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/languages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteLanguagesPageExportBatch(siteId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/languages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(siteId: any, messageBoardMessageExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(siteId: any, messageBoardMessageExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardMessageMessageBoardAttachmentsPage(messageBoardMessageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardMessageMessageBoardAttachment(messageBoardMessageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThreadMessageBoardAttachmentsPage(messageBoardThreadId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardAttachment(messageBoardThreadId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardAttachment(messageBoardAttachmentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-attachments/${messageBoardAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardAttachment(messageBoardAttachmentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-attachments/${messageBoardAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardMessageMessageBoardAttachmentsPageExportBatch(messageBoardMessageId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardAttachmentsPageExportBatch(messageBoardThreadId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardMessageMessageBoardAttachmentBatch(messageBoardMessageId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardAttachmentBatch(messageBoardThreadId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardAttachmentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/message-board-attachments/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteMessageBoardMessageByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardMessageByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteMessageBoardMessageByExternalReferenceCode(siteId: any, externalReferenceCode: any, MessageBoardMessage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}`, MessageBoardMessage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardMessageMessageBoardMessagesPage(parentMessageBoardMessageId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-messages/${parentMessageBoardMessageId}/message-board-messages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardMessageMessageBoardMessage(parentMessageBoardMessageId: any, MessageBoardMessage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-messages/${parentMessageBoardMessageId}/message-board-messages`, MessageBoardMessage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThreadMessageBoardMessagesPage(messageBoardThreadId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardMessage(messageBoardThreadId: any, MessageBoardMessage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages`, MessageBoardMessage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardMessageByFriendlyUrlPath(siteId: any, friendlyUrlPath: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-friendly-url-path/${friendlyUrlPath}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteUserMessageBoardMessagesActivityPage(siteId: any, userId: any, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/${userId}/message-board-messages/activity`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardMessage(messageBoardMessageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardMessage(messageBoardMessageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchMessageBoardMessage(messageBoardMessageId: any, MessageBoardMessage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, MessageBoardMessage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessage(messageBoardMessageId: any, MessageBoardMessage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, MessageBoardMessage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardMessageMyRating(messageBoardMessageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardMessageMyRating(messageBoardMessageId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardMessageMyRating(messageBoardMessageId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessageMyRating(messageBoardMessageId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardMessagesPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessageSubscribe(messageBoardMessageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessageUnsubscribe(messageBoardMessageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardMessageBatch(messageBoardThreadId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMessageBoardMessagesPageExportBatch(messageBoardThreadId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardMessagesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardMessageBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/message-board-messages/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessageBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardMessagePermissionsPage(messageBoardMessageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardMessagePermissionsPage(messageBoardMessageId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardMessagePermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteMessageBoardMessagePermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardSectionMessageBoardSectionsPage(parentMessageBoardSectionId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-sections/${parentMessageBoardSectionId}/message-board-sections`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardSectionMessageBoardSection(parentMessageBoardSectionId: any, MessageBoardSection: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-sections/${parentMessageBoardSectionId}/message-board-sections`, MessageBoardSection, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardSectionByFriendlyUrlPath(siteId: any, friendlyUrlPath: any) {

        let queryParams = new HttpParams();

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-section/by-friendly-url-path/${friendlyUrlPath}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardSection(messageBoardSectionId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardSection(messageBoardSectionId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchMessageBoardSection(messageBoardSectionId: any, MessageBoardSection: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, MessageBoardSection, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardSection(messageBoardSectionId: any, MessageBoardSection: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, MessageBoardSection, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardSectionsPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardSection(siteId: any, MessageBoardSection: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections`, MessageBoardSection, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardSectionSubscribe(messageBoardSectionId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardSectionUnsubscribe(messageBoardSectionId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardSectionsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardSectionBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardSectionBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/message-board-sections/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardSectionBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/message-board-sections/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardSectionPermissionsPage(messageBoardSectionId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardSectionPermissionsPage(messageBoardSectionId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardSectionPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteMessageBoardSectionPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardSectionMessageBoardThreadsPage(messageBoardSectionId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardSectionMessageBoardThread(messageBoardSectionId: any, MessageBoardThread: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads`, MessageBoardThread, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardThread(messageBoardThreadId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThread(messageBoardThreadId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchMessageBoardThread(messageBoardThreadId: any, MessageBoardThread: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, MessageBoardThread, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThread(messageBoardThreadId: any, MessageBoardThread: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, MessageBoardThread, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardThreadsPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardThread(siteId: any, MessageBoardThread: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads`, MessageBoardThread, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardThreadMyRating(messageBoardThreadId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThreadMyRating(messageBoardThreadId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardThreadMyRating(messageBoardThreadId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThreadMyRating(messageBoardThreadId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThreadsRankedPage(dateCreated: any = null, dateModified: any = null, fields: any = null, messageBoardSectionId: any = null, nestedFields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (dateCreated)
            queryParams = queryParams.append('dateCreated', dateCreated);

        if (dateModified)
            queryParams = queryParams.append('dateModified', dateModified);

        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (messageBoardSectionId)
            queryParams = queryParams.append('messageBoardSectionId', messageBoardSectionId);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/ranked`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardThreadByFriendlyUrlPath(siteId: any, friendlyUrlPath: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/by-friendly-url-path/${friendlyUrlPath}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThreadSubscribe(messageBoardThreadId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThreadUnsubscribe(messageBoardThreadId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardSectionMessageBoardThreadBatch(messageBoardSectionId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postMessageBoardSectionMessageBoardThreadsPageExportBatch(messageBoardSectionId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteMessageBoardThreadBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/message-board-threads/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThreadBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardThreadBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getMessageBoardThreadPermissionsPage(messageBoardThreadId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putMessageBoardThreadPermissionsPage(messageBoardThreadId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteMessageBoardThreadsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteMessageBoardThreadPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteMessageBoardThreadPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteNavigationMenu(navigationMenuId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getNavigationMenu(navigationMenuId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putNavigationMenu(navigationMenuId: any, NavigationMenu: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}`, NavigationMenu, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteNavigationMenusPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, page: any = null, pageSize: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

        if (nestedFields)
            queryParams = queryParams.append('nestedFields', nestedFields);

        if (restrictFields)
            queryParams = queryParams.append('restrictFields', restrictFields);

        if (page)
            queryParams = queryParams.append('page', page);

        if (pageSize)
            queryParams = queryParams.append('pageSize', pageSize);


        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };

        let prom = new Promise((resolve, reject) => {
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteNavigationMenu(siteId: any, NavigationMenu: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus`, NavigationMenu, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteNavigationMenuBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/navigation-menus/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putNavigationMenuBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/navigation-menus/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteNavigationMenuBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getNavigationMenuPermissionsPage(navigationMenuId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putNavigationMenuPermissionsPage(navigationMenuId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteNavigationMenusPageExportBatch(siteId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteNavigationMenuPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteNavigationMenuPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
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
            this.http.get(`/o/headless-delivery/v1.0/openapi.${type}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePageExperienceExperienceKeyRenderedPage(siteId: any, friendlyUrlPath: any, experienceKey: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences/${experienceKey}/rendered-page`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePage(siteId: any, friendlyUrlPath: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePagesPage(siteId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteSitePage(siteId: any, SitePage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages`, SitePage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePageExperienceExperienceKey(siteId: any, friendlyUrlPath: any, experienceKey: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences/${experienceKey}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePageRenderedPage(siteId: any, friendlyUrlPath: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/rendered-page`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteSitePagesExperiencesPage(siteId: any, friendlyUrlPath: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteSitePageBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteSitePagesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/site-pages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteStructuredContentFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteStructuredContentFolderByExternalReferenceCode(siteId: any, externalReferenceCode: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentFoldersPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContentFolder(assetLibraryId: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentFolderStructuredContentFoldersPage(parentStructuredContentFolderId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/structured-content-folders/${parentStructuredContentFolderId}/structured-content-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentFolderStructuredContentFolder(parentStructuredContentFolderId: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/structured-content-folders/${parentStructuredContentFolderId}/structured-content-folders`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteStructuredContentFolder(structuredContentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentFolder(structuredContentFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchStructuredContentFolder(structuredContentFolderId: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentFolder(structuredContentFolderId: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentFoldersPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContentFolder(siteId: any, StructuredContentFolder: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders`, StructuredContentFolder, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentFolderSubscribe(structuredContentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentFolderUnsubscribe(structuredContentFolderId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContentFoldersPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentFolderPermissionsPage(assetLibraryId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryStructuredContentFolderPermissionsPage(assetLibraryId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContentFoldersPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentFolderPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteStructuredContentFolderPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContentFolderBatch(assetLibraryId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentFolderPermissionsPage(structuredContentFolderId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-content-folder/${structuredContentFolderId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentFolderPermissionsPage(structuredContentFolderId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-content-folder/${structuredContentFolderId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteStructuredContentFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/structured-content-folders/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentFolderBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/structured-content-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContentFolderBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentRenderedContentByDisplayPageDisplayPageKey(structuredContentId: any, displayPageKey: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/rendered-content-by-display-page/${displayPageKey}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId: any, externalReferenceCode: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteStructuredContentByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteStructuredContentByExternalReferenceCode(siteId: any, externalReferenceCode: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentFolderStructuredContentsPage(structuredContentFolderId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentFolderStructuredContent(structuredContentFolderId: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentRenderedContentContentTemplate(structuredContentId: any, contentTemplateId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/rendered-content/${contentTemplateId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteStructuredContent(structuredContentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContent(structuredContentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public patchStructuredContent(structuredContentId: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.patch(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContent(structuredContentId: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentsPage(siteId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContent(siteId: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteStructuredContentMyRating(structuredContentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentMyRating(structuredContentId: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentMyRating(structuredContentId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentMyRating(structuredContentId: any, Rating: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, Rating, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentsPage(assetLibraryId: any, aggregationTerms: any = null, fields: any = null, flatten: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContent(assetLibraryId: any, StructuredContent: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents`, StructuredContent, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getContentStructureStructuredContentsPage(contentStructureId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/content-structures/${contentStructureId}/structured-contents`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentByKey(siteId: any, key: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-key/${key}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentByUuid(siteId: any, uuid: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-uuid/${uuid}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentSubscribe(structuredContentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentUnsubscribe(structuredContentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentFolderStructuredContentsPageExportBatch(structuredContentFolderId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postStructuredContentFolderStructuredContentBatch(structuredContentFolderId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContentsPageExportBatch(assetLibraryId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getAssetLibraryStructuredContentPermissionsPage(assetLibraryId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putAssetLibraryStructuredContentPermissionsPage(assetLibraryId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postContentStructureStructuredContentsPageExportBatch(contentStructureId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/content-structures/${contentStructureId}/structured-contents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteStructuredContentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/structured-contents/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postAssetLibraryStructuredContentBatch(assetLibraryId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContentBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteStructuredContentsPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteStructuredContentPermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteStructuredContentPermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getStructuredContentPermissionsPage(structuredContentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putStructuredContentPermissionsPage(structuredContentId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiNode(wikiNodeId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiNode(wikiNodeId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiNode(wikiNodeId: any, WikiNode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}`, WikiNode, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteWikiNodesPage(siteId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteWikiNode(siteId: any, WikiNode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes`, WikiNode, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiNodeSubscribe(wikiNodeId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiNodeUnsubscribe(wikiNodeId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteWikiNodeByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteWikiNodeByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteWikiNodeByExternalReferenceCode(siteId: any, externalReferenceCode: any, WikiNode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}`, WikiNode, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteWikiNodeBatch(siteId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiNodeBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/wiki-nodes/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiNodeBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/wiki-nodes/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiNodePermissionsPage(wikiNodeId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiNodePermissionsPage(wikiNodeId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postSiteWikiNodesPageExportBatch(siteId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteWikiNodePermissionsPage(siteId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteWikiNodePermissionsPage(siteId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(siteId: any, wikiPageExternalReferenceCode: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(siteId: any, wikiPageExternalReferenceCode: any, externalReferenceCode: any, fields: any = null, restrictFields: any = null) {

        let queryParams = new HttpParams();
        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiPageAttachment(wikiPageAttachmentId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/wiki-page-attachments/${wikiPageAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiPageAttachment(wikiPageAttachmentId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-page-attachments/${wikiPageAttachmentId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiPageWikiPageAttachmentsPage(wikiPageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiPageWikiPageAttachment(wikiPageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiPageWikiPageAttachmentsPageExportBatch(wikiPageId: any, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
        let queryParams = new HttpParams();
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
            this.http.post(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiPageAttachmentBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/wiki-page-attachments/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiPageWikiPageAttachmentBatch(wikiPageId: any, callbackURL: any = null, MultipartBody: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments/batch`, MultipartBody, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiPage(wikiPageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiPage(wikiPageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiPage(wikiPageId: any, WikiPage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}`, WikiPage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiNodeWikiPagesPage(wikiNodeId: any, aggregationTerms: any = null, fields: any = null, nestedFields: any = null, restrictFields: any = null, filter: any = null, page: any = null, pageSize: any = null, search: any = null, sort: any = null) {

        let queryParams = new HttpParams();
        if (aggregationTerms)
            queryParams = queryParams.append('aggregationTerms', aggregationTerms);

        if (fields)
            queryParams = queryParams.append('fields', fields);

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiNodeWikiPage(wikiNodeId: any, WikiPage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages`, WikiPage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiPageWikiPagesPage(parentWikiPageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-pages/${parentWikiPageId}/wiki-pages`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiPageWikiPage(parentWikiPageId: any, WikiPage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams

        };
        let prom = new Promise((resolve, reject) => {
            this.http.post(`/o/headless-delivery/v1.0/wiki-pages/${parentWikiPageId}/wiki-pages`, WikiPage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiPageSubscribe(wikiPageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/subscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiPageUnsubscribe(wikiPageId: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/unsubscribe`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteSiteWikiPageByExternalReferenceCode(siteId: any, externalReferenceCode: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams


        };
        let prom = new Promise((resolve, reject) => {
            this.http.delete(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getSiteWikiPageByExternalReferenceCode(siteId: any, externalReferenceCode: any, fields: any = null, nestedFields: any = null, restrictFields: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putSiteWikiPageByExternalReferenceCode(siteId: any, externalReferenceCode: any, WikiPage: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}`, WikiPage, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiNodeWikiPageBatch(wikiNodeId: any, callbackURL: any = null, requestBodyobject: any) {
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
            this.http.post(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public deleteWikiPageBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.delete(`/o/headless-delivery/v1.0/wiki-pages/batch`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiPageBatch(callbackURL: any = null, requestBodyobject: any) {
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
            this.http.put(`/o/headless-delivery/v1.0/wiki-pages/batch`, requestBodyobject, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public getWikiPagePermissionsPage(wikiPageId: any, fields: any = null, nestedFields: any = null, restrictFields: any = null, roleNames: any = null) {

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
            this.http.get(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/permissions`, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public putWikiPagePermissionsPage(wikiPageId: any, requestBodyarray: any) {
        let queryParams = new HttpParams();
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
                'x-csrf-token': Liferay.authToken
            })
            , params: queryParams
        };
        let prom = new Promise((resolve, reject) => {
            this.http.put(`/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/permissions`, requestBodyarray, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }


    public postWikiNodeWikiPagesPageExportBatch(wikiNodeId: any, filter: any = null, search: any = null, sort: any = null, callbackURL: any = null, contentType: any = null, fieldNames: any = null) {
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
            this.http.post(`/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages/export-batch`, {}, httpOptions).subscribe((result) => {
                resolve(result);
            }, error => {
                reject(error);
            });
        });
        return prom;
    }

}
