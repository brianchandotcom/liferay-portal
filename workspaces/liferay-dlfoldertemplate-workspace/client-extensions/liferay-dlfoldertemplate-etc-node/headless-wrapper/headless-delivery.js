import fetch from 'node-fetch';
import config from '../util/configTreePath.js';


const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export function headless_deliveryService(_token) {
    const token = _token;

    function getSiteBlogPostingImagesPage(siteId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteBlogPostingImage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteBlogPostingImage(blogPostingImageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-posting-images/${blogPostingImageId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getBlogPostingImage(blogPostingImageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-posting-images/${blogPostingImageId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteBlogPostingImageBatch(siteId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteBlogPostingImageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-posting-images/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function postSiteBlogPostingImagesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-posting-images/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getBlogPostingRenderedContentByDisplayPageDisplayPageKey(blogPostingId, displayPageKey, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/rendered-content-by-display-page/${displayPageKey}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteBlogPostingByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteBlogPostingByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteBlogPostingByExternalReferenceCode(siteId, externalReferenceCode, BlogPosting) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(BlogPosting),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteBlogPostingSubscribe(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteBlogPostingUnsubscribe(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteBlogPosting(blogPostingId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getBlogPosting(blogPostingId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchBlogPosting(blogPostingId, BlogPosting) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(BlogPosting),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putBlogPosting(blogPostingId, BlogPosting) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(BlogPosting),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteBlogPostingMyRating(blogPostingId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getBlogPostingMyRating(blogPostingId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postBlogPostingMyRating(blogPostingId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putBlogPostingMyRating(blogPostingId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteBlogPostingsPage(siteId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteBlogPosting(siteId, BlogPosting) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(BlogPosting),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteBlogPostingPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteBlogPostingPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getBlogPostingPermissionsPage(blogPostingId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putBlogPostingPermissionsPage(blogPostingId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteBlogPostingsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteBlogPostingBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteBlogPostingBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putBlogPostingBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteComment(commentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/comments/${commentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getComment(commentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/comments/${commentId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putComment(commentId, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/comments/${commentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentCommentsPage(structuredContentId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentComment(structuredContentId, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId, structuredContentExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId, structuredContentExternalReferenceCode, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode(siteId, structuredContentExternalReferenceCode, externalReferenceCode, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${structuredContentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getBlogPostingCommentsPage(blogPostingId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postBlogPostingComment(blogPostingId, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getCommentCommentsPage(parentCommentId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/comments/${parentCommentId}/comments?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postCommentComment(parentCommentId, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/comments/${parentCommentId}/comments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentCommentsPage(documentId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/comments?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentComment(documentId, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/comments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId, blogPostingExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId, blogPostingExternalReferenceCode, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode(siteId, blogPostingExternalReferenceCode, externalReferenceCode, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/blog-postings/by-external-reference-code/${blogPostingExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId, parentCommentExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId, parentCommentExternalReferenceCode, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode(siteId, parentCommentExternalReferenceCode, externalReferenceCode, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/comments/by-external-reference-code/${parentCommentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId, documentExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId, documentExternalReferenceCode, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode(siteId, documentExternalReferenceCode, externalReferenceCode, Comment) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Comment),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${documentExternalReferenceCode}/comments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postBlogPostingCommentBatch(blogPostingId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentCommentBatch(structuredContentId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/comments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postBlogPostingCommentsPageExportBatch(blogPostingId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/blog-postings/${blogPostingId}/comments/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentCommentsPageExportBatch(documentId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/comments/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }



    function getAssetLibraryContentElementsPage(assetLibraryId, aggregationTerms = null, fields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-elements?aggregationTerms=${aggregationTerms}&fields=${fields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteContentElementsPage(siteId, aggregationTerms = null, fields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-elements?aggregationTerms=${aggregationTerms}&fields=${fields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteContentElementsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-elements/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryContentElementsPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-elements/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getContentSetContentSetElementsPage(contentSetId, fields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-sets/${contentSetId}/content-set-elements?fields=${fields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryContentSetByKeyContentSetElementsPage(assetLibraryId, key, fields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-sets/by-key/${key}/content-set-elements?fields=${fields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryContentSetByUuidContentSetElementsPage(assetLibraryId, uuid, fields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-sets/by-uuid/${uuid}/content-set-elements?fields=${fields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteContentSetByKeyContentSetElementsPage(siteId, key, fields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-sets/by-key/${key}/content-set-elements?fields=${fields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteContentSetByUuidContentSetElementsPage(siteId, uuid, fields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-sets/by-uuid/${uuid}/content-set-elements?fields=${fields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryContentStructuresPage(assetLibraryId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteContentStructuresPage(siteId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-structures?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getContentStructure(contentStructureId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-structures/${contentStructureId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getContentStructurePermissionsPage(contentStructureId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-structures/${contentStructureId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putContentStructurePermissionsPage(contentStructureId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-structures/${contentStructureId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteContentStructuresPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-structures/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteContentStructurePermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-structures/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteContentStructurePermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-structures/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryContentStructuresPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryContentStructurePermissionsPage(assetLibraryId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryContentStructurePermissionsPage(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-structures/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryContentTemplatesPage(assetLibraryId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-templates?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }

    function getSiteContentTemplate(siteId, contentTemplateId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-templates/${contentTemplateId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteContentTemplatesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/content-templates/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryContentTemplatesPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/content-templates/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocumentFolder(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getDocumentFolder(documentFolderId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchDocumentFolder(documentFolderId, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentFolder(documentFolderId, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocumentFolderMyRating(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getDocumentFolderMyRating(documentFolderId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentFolderMyRating(documentFolderId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentFolderMyRating(documentFolderId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentFolderSubscribe(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentFoldersPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocumentFolder(siteId, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentFolderDocumentFoldersPage(parentDocumentFolderId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentFolderDocumentFolder(parentDocumentFolderId, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${parentDocumentFolderId}/document-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentFoldersRatedByMePage(siteId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders/rated-by-me?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentFolderUnsubscribe(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentFoldersPage(assetLibraryId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocumentFolder(assetLibraryId, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteDocumentsFolderByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteDocumentsFolderByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteDocumentsFolderByExternalReferenceCode(siteId, externalReferenceCode, DocumentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(DocumentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents-folder/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentFoldersRatedByMePage(assetLibraryId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/rated-by-me?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocumentFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putDocumentFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocumentFoldersPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocumentFolderBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentFolderPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteDocumentFolderPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/document-folders/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentFolderPermissionsPage(documentFolderId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentFolderPermissionsPage(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocumentFolderBatch(assetLibraryId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentFolderPermissionsPage(assetLibraryId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryDocumentFolderPermissionsPage(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocumentFoldersPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/document-folders/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocument(documentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getDocument(documentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            console.log(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}`)
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchDocument(documentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocument(documentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentsRatedByMePage(siteId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/rated-by-me?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteDocumentByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteDocumentByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteDocumentByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentsRatedByMePage(assetLibraryId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/rated-by-me?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentFolderDocumentsPage(documentFolderId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentFolderDocument(documentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentsPage(assetLibraryId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocument(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentsPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocument(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocumentMyRating(documentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getDocumentMyRating(documentId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentMyRating(documentId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentMyRating(documentId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAssetLibraryDocumentByExternalReferenceCode(assetLibraryId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getAssetLibraryDocumentByExternalReferenceCode(assetLibraryId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryDocumentByExternalReferenceCode(assetLibraryId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentRenderedContentByDisplayPageDisplayPageKey(documentId, displayPageKey, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/rendered-content-by-display-page/${displayPageKey}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteDocumentPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteDocumentPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentFolderDocumentBatch(documentFolderId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocumentsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postDocumentFolderDocumentsPageExportBatch(documentFolderId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/document-folders/${documentFolderId}/documents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocumentBatch(assetLibraryId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryDocumentsPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryDocumentPermissionsPage(assetLibraryId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryDocumentPermissionsPage(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/documents/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteDocumentBatch(siteId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/documents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getDocumentPermissionsPage(documentId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putDocumentPermissionsPage(documentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/${documentId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteDocumentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putDocumentBatch(callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/documents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseArticle(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getKnowledgeBaseArticle(knowledgeBaseArticleId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchKnowledgeBaseArticle(knowledgeBaseArticleId, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseArticle(knowledgeBaseArticleId, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseArticleMyRating(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getKnowledgeBaseArticleMyRating(knowledgeBaseArticleId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseArticleMyRating(knowledgeBaseArticleId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseArticleMyRating(knowledgeBaseArticleId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseArticleSubscribe(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseArticleUnsubscribe(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteKnowledgeBaseArticlesPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseArticle(siteId, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseArticleSubscribe(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseArticleUnsubscribe(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseFolderKnowledgeBaseArticlesPage(knowledgeBaseFolderId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseFolderKnowledgeBaseArticle(knowledgeBaseFolderId, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteKnowledgeBaseArticleByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteKnowledgeBaseArticleByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseArticleByExternalReferenceCode(siteId, externalReferenceCode, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseArticleKnowledgeBaseArticlesPage(parentKnowledgeBaseArticleId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${parentKnowledgeBaseArticleId}/knowledge-base-articles?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseArticleKnowledgeBaseArticle(parentKnowledgeBaseArticleId, KnowledgeBaseArticle) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseArticle),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${parentKnowledgeBaseArticleId}/knowledge-base-articles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseFolderKnowledgeBaseArticlesPageExportBatch(knowledgeBaseFolderId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseArticleBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putKnowledgeBaseArticleBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseArticlePermissionsPage(knowledgeBaseArticleId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseArticlePermissionsPage(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseArticleBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteKnowledgeBaseArticlePermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseArticlePermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseArticlesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseFolderKnowledgeBaseArticleBatch(knowledgeBaseFolderId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/knowledge-base-articles/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseAttachment(knowledgeBaseAttachmentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-attachments/${knowledgeBaseAttachmentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getKnowledgeBaseAttachment(knowledgeBaseAttachmentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-attachments/${knowledgeBaseAttachmentId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(siteId, knowledgeBaseArticleExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode(siteId, knowledgeBaseArticleExternalReferenceCode, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-articles/by-external-reference-code/${knowledgeBaseArticleExternalReferenceCode}/knowledge-base-attachments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(knowledgeBaseArticleId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseArticleKnowledgeBaseAttachment(knowledgeBaseArticleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseAttachmentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function postKnowledgeBaseArticleKnowledgeBaseAttachmentsPageExportBatch(knowledgeBaseArticleId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch(knowledgeBaseArticleId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-articles/${knowledgeBaseArticleId}/knowledge-base-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseFolder(knowledgeBaseFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getKnowledgeBaseFolder(knowledgeBaseFolderId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchKnowledgeBaseFolder(knowledgeBaseFolderId, KnowledgeBaseFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseFolder(knowledgeBaseFolderId, KnowledgeBaseFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteKnowledgeBaseFoldersPage(siteId, fields = null, nestedFields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseFolder(siteId, KnowledgeBaseFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseFolderKnowledgeBaseFoldersPage(parentKnowledgeBaseFolderId, fields = null, nestedFields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${parentKnowledgeBaseFolderId}/knowledge-base-folders?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postKnowledgeBaseFolderKnowledgeBaseFolder(parentKnowledgeBaseFolderId, KnowledgeBaseFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${parentKnowledgeBaseFolderId}/knowledge-base-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteKnowledgeBaseFolderByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteKnowledgeBaseFolderByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseFolderByExternalReferenceCode(siteId, externalReferenceCode, KnowledgeBaseFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(KnowledgeBaseFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteKnowledgeBaseFolderPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteKnowledgeBaseFolderPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseFolderBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getKnowledgeBaseFolderPermissionsPage(knowledgeBaseFolderId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putKnowledgeBaseFolderPermissionsPage(knowledgeBaseFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/${knowledgeBaseFolderId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteKnowledgeBaseFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putKnowledgeBaseFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/knowledge-base-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteKnowledgeBaseFoldersPageExportBatch(siteId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/knowledge-base-folders/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryLanguagesPage(assetLibraryId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/languages?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteLanguagesPage(siteId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/languages?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryLanguagesPageExportBatch(assetLibraryId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/languages/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteLanguagesPageExportBatch(siteId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/languages/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardAttachment(messageBoardAttachmentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-attachments/${messageBoardAttachmentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardAttachment(messageBoardAttachmentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-attachments/${messageBoardAttachmentId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(siteId, messageBoardMessageExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode(siteId, messageBoardMessageExternalReferenceCode, externalReferenceCode, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${messageBoardMessageExternalReferenceCode}/message-board-attachments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardMessageMessageBoardAttachmentsPage(messageBoardMessageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardMessageMessageBoardAttachment(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardThreadMessageBoardAttachmentsPage(messageBoardThreadId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardAttachment(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardAttachmentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function postMessageBoardMessageMessageBoardAttachmentsPageExportBatch(messageBoardMessageId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardAttachmentsPageExportBatch(messageBoardThreadId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardAttachmentBatch(messageBoardThreadId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardMessageMessageBoardAttachmentBatch(messageBoardMessageId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/message-board-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardMessageMyRating(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardMessageMyRating(messageBoardMessageId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardMessageMyRating(messageBoardMessageId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardMessageMyRating(messageBoardMessageId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardMessagesPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardMessageMessageBoardMessagesPage(parentMessageBoardMessageId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${parentMessageBoardMessageId}/message-board-messages?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardMessageMessageBoardMessage(parentMessageBoardMessageId, MessageBoardMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardMessage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${parentMessageBoardMessageId}/message-board-messages`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardMessageSubscribe(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardMessageUnsubscribe(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardThreadMessageBoardMessagesPage(messageBoardThreadId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardMessage(messageBoardThreadId, MessageBoardMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardMessage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardMessage(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardMessage(messageBoardMessageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchMessageBoardMessage(messageBoardMessageId, MessageBoardMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardMessage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardMessage(messageBoardMessageId, MessageBoardMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardMessage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteMessageBoardMessageByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteMessageBoardMessageByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteMessageBoardMessageByExternalReferenceCode(siteId, externalReferenceCode, MessageBoardMessage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardMessage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardMessageByFriendlyUrlPath(siteId, friendlyUrlPath, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/by-friendly-url-path/${friendlyUrlPath}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteUserMessageBoardMessagesActivityPage(siteId, userId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/${userId}/message-board-messages/activity?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardMessageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putMessageBoardMessageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardMessagePermissionsPage(messageBoardMessageId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardMessagePermissionsPage(messageBoardMessageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-messages/${messageBoardMessageId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardMessagePermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteMessageBoardMessagePermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardMessageBatch(messageBoardThreadId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMessageBoardMessagesPageExportBatch(messageBoardThreadId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/message-board-messages/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardMessagesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-messages/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardSectionsPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardSection(siteId, MessageBoardSection) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardSection),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardSectionMessageBoardSectionsPage(parentMessageBoardSectionId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${parentMessageBoardSectionId}/message-board-sections?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardSectionMessageBoardSection(parentMessageBoardSectionId, MessageBoardSection) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardSection),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${parentMessageBoardSectionId}/message-board-sections`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardSectionSubscribe(messageBoardSectionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardSectionUnsubscribe(messageBoardSectionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardSection(messageBoardSectionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardSection(messageBoardSectionId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchMessageBoardSection(messageBoardSectionId, MessageBoardSection) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardSection),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardSection(messageBoardSectionId, MessageBoardSection) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardSection),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardSectionByFriendlyUrlPath(siteId, friendlyUrlPath) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-section/by-friendly-url-path/${friendlyUrlPath}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardSectionBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardSectionBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putMessageBoardSectionBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardSectionPermissionsPage(messageBoardSectionId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardSectionPermissionsPage(messageBoardSectionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardSectionPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteMessageBoardSectionPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardSectionsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-sections/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardThreadsPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardThread(siteId, MessageBoardThread) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardThread),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardThreadMyRating(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardThreadMyRating(messageBoardThreadId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardThreadMyRating(messageBoardThreadId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardThreadMyRating(messageBoardThreadId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardThreadsRankedPage(dateCreated = null, dateModified = null, fields = null, messageBoardSectionId = null, nestedFields = null, restrictFields = null, page = null, pageSize = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/ranked?dateCreated=${dateCreated}&dateModified=${dateModified}&fields=${fields}&messageBoardSectionId=${messageBoardSectionId}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardThreadByFriendlyUrlPath(siteId, friendlyUrlPath, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/by-friendly-url-path/${friendlyUrlPath}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardSectionMessageBoardThreadsPage(messageBoardSectionId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardSectionMessageBoardThread(messageBoardSectionId, MessageBoardThread) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardThread),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardThreadSubscribe(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardThreadUnsubscribe(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardThread(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getMessageBoardThread(messageBoardThreadId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchMessageBoardThread(messageBoardThreadId, MessageBoardThread) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardThread),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardThread(messageBoardThreadId, MessageBoardThread) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MessageBoardThread),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardThreadBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMessageBoardThreadBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putMessageBoardThreadBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMessageBoardThreadPermissionsPage(messageBoardThreadId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putMessageBoardThreadPermissionsPage(messageBoardThreadId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-threads/${messageBoardThreadId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteMessageBoardThreadsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteMessageBoardThreadPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteMessageBoardThreadPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/message-board-threads/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardSectionMessageBoardThreadBatch(messageBoardSectionId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postMessageBoardSectionMessageBoardThreadsPageExportBatch(messageBoardSectionId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/message-board-sections/${messageBoardSectionId}/message-board-threads/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteNavigationMenu(navigationMenuId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getNavigationMenu(navigationMenuId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putNavigationMenu(navigationMenuId, NavigationMenu) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(NavigationMenu),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteNavigationMenusPage(siteId, fields = null, nestedFields = null, restrictFields = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteNavigationMenu(siteId, NavigationMenu) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(NavigationMenu),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteNavigationMenuBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getNavigationMenuPermissionsPage(navigationMenuId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putNavigationMenuPermissionsPage(navigationMenuId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/${navigationMenuId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteNavigationMenusPageExportBatch(siteId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteNavigationMenuPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteNavigationMenuPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/navigation-menus/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteNavigationMenuBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putNavigationMenuBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/navigation-menus/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOpenAPI(type) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/openapi.${type}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePageExperienceExperienceKey(siteId, friendlyUrlPath, experienceKey, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences/${experienceKey}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePageRenderedPage(siteId, friendlyUrlPath, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/rendered-page?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePagesExperiencesPage(siteId, friendlyUrlPath, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePage(siteId, friendlyUrlPath, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePagesPage(siteId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteSitePage(siteId, SitePage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(SitePage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSitePageExperienceExperienceKeyRenderedPage(siteId, friendlyUrlPath, experienceKey, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/${friendlyUrlPath}/experiences/${experienceKey}/rendered-page?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteSitePagesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteSitePageBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/site-pages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryStructuredContentFolderByExternalReferenceCode(assetLibraryId, externalReferenceCode, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteStructuredContentFolder(structuredContentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getStructuredContentFolder(structuredContentFolderId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchStructuredContentFolder(structuredContentFolderId, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentFolder(structuredContentFolderId, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentFolderSubscribe(structuredContentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentFolderUnsubscribe(structuredContentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentFoldersPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContentFolder(siteId, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryStructuredContentFoldersPage(assetLibraryId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContentFolder(assetLibraryId, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentFolderStructuredContentFoldersPage(parentStructuredContentFolderId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${parentStructuredContentFolderId}/structured-content-folders?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentFolderStructuredContentFolder(parentStructuredContentFolderId, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${parentStructuredContentFolderId}/structured-content-folders`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteStructuredContentFolderByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteStructuredContentFolderByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteStructuredContentFolderByExternalReferenceCode(siteId, externalReferenceCode, StructuredContentFolder) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContentFolder),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteStructuredContentFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putStructuredContentFolderBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentFolderPermissionsPage(structuredContentFolderId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folder/${structuredContentFolderId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentFolderPermissionsPage(structuredContentFolderId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folder/${structuredContentFolderId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContentFolderBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContentFoldersPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentFolderPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteStructuredContentFolderPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryStructuredContentFolderPermissionsPage(assetLibraryId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryStructuredContentFolderPermissionsPage(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContentFolderBatch(assetLibraryId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-content-folders/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContentFoldersPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-content-folders/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentRenderedContentContentTemplate(structuredContentId, contentTemplateId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/rendered-content/${contentTemplateId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentsPage(siteId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContent(siteId, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteStructuredContentMyRating(structuredContentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getStructuredContentMyRating(structuredContentId, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentMyRating(structuredContentId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentMyRating(structuredContentId, Rating) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Rating),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/my-rating`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryStructuredContentsPage(assetLibraryId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContent(assetLibraryId, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getContentStructureStructuredContentsPage(contentStructureId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-structures/${contentStructureId}/structured-contents?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentByKey(siteId, key, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-key/${key}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentByUuid(siteId, uuid, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-uuid/${uuid}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentSubscribe(structuredContentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentUnsubscribe(structuredContentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentRenderedContentByDisplayPageDisplayPageKey(structuredContentId, displayPageKey, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/rendered-content-by-display-page/${displayPageKey}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteStructuredContent(structuredContentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getStructuredContent(structuredContentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchStructuredContent(structuredContentId, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContent(structuredContentId, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryStructuredContentByExternalReferenceCode(assetLibraryId, externalReferenceCode, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteStructuredContentByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteStructuredContentByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteStructuredContentByExternalReferenceCode(siteId, externalReferenceCode, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentFolderStructuredContentsPage(structuredContentFolderId, aggregationTerms = null, fields = null, flatten = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents?aggregationTerms=${aggregationTerms}&fields=${fields}&flatten=${flatten}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentFolderStructuredContent(structuredContentFolderId, StructuredContent) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(StructuredContent),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContentBatch(assetLibraryId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteStructuredContentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putStructuredContentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContentBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteStructuredContentsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteStructuredContentPermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteStructuredContentPermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/structured-contents/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getStructuredContentPermissionsPage(structuredContentId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putStructuredContentPermissionsPage(structuredContentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-contents/${structuredContentId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentFolderStructuredContentsPageExportBatch(structuredContentFolderId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postStructuredContentFolderStructuredContentBatch(structuredContentFolderId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/structured-content-folders/${structuredContentFolderId}/structured-contents/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAssetLibraryStructuredContentsPageExportBatch(assetLibraryId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAssetLibraryStructuredContentPermissionsPage(assetLibraryId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAssetLibraryStructuredContentPermissionsPage(assetLibraryId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/asset-libraries/${assetLibraryId}/structured-contents/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postContentStructureStructuredContentsPageExportBatch(contentStructureId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/content-structures/${contentStructureId}/structured-contents/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiNode(wikiNodeId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getWikiNode(wikiNodeId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiNode(wikiNodeId, WikiNode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiNode),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteWikiNodeByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteWikiNodeByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteWikiNodeByExternalReferenceCode(siteId, externalReferenceCode, WikiNode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiNode),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteWikiNodesPage(siteId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteWikiNode(siteId, WikiNode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiNode),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiNodeSubscribe(wikiNodeId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiNodeUnsubscribe(wikiNodeId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteWikiNodesPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteWikiNodePermissionsPage(siteId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteWikiNodePermissionsPage(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteWikiNodeBatch(siteId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-nodes/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiNodeBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putWikiNodeBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWikiNodePermissionsPage(wikiNodeId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiNodePermissionsPage(wikiNodeId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWikiPageWikiPageAttachmentsPage(wikiPageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiPageWikiPageAttachment(wikiPageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(siteId, wikiPageExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode(siteId, wikiPageExternalReferenceCode, externalReferenceCode, fields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${wikiPageExternalReferenceCode}/wiki-page-attachments/by-external-reference-code/${externalReferenceCode}?fields=${fields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiPageAttachment(wikiPageAttachmentId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-page-attachments/${wikiPageAttachmentId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getWikiPageAttachment(wikiPageAttachmentId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-page-attachments/${wikiPageAttachmentId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiPageWikiPageAttachmentBatch(wikiPageId, callbackURL = null, MultipartBody) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(MultipartBody),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiPageAttachmentBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-page-attachments/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function postWikiPageWikiPageAttachmentsPageExportBatch(wikiPageId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/wiki-page-attachments/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiPage(wikiPageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getWikiPage(wikiPageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiPage(wikiPageId, WikiPage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiPage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteWikiPageByExternalReferenceCode(siteId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function getSiteWikiPageByExternalReferenceCode(siteId, externalReferenceCode, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putSiteWikiPageByExternalReferenceCode(siteId, externalReferenceCode, WikiPage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiPage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/sites/${siteId}/wiki-pages/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWikiNodeWikiPagesPage(wikiNodeId, aggregationTerms = null, fields = null, nestedFields = null, restrictFields = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages?aggregationTerms=${aggregationTerms}&fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiNodeWikiPage(wikiNodeId, WikiPage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiPage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWikiPageWikiPagesPage(parentWikiPageId, fields = null, nestedFields = null, restrictFields = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${parentWikiPageId}/wiki-pages?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiPageWikiPage(parentWikiPageId, WikiPage) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(WikiPage),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${parentWikiPageId}/wiki-pages`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiPageSubscribe(wikiPageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/subscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiPageUnsubscribe(wikiPageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/unsubscribe`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiNodeWikiPagesPageExportBatch(wikiNodeId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postWikiNodeWikiPageBatch(wikiNodeId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-nodes/${wikiNodeId}/wiki-pages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteWikiPageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => {
                    if (response.status == 204) {
                        resolve(response.text());
                    } else {
                        reject();
                    }
                }, error => {
                    reject(error)
                }).catch(error => {
                reject(error)
            });
        })
        return prom;
    }


    function putWikiPageBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWikiPagePermissionsPage(wikiPageId, fields = null, nestedFields = null, restrictFields = null, roleNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/permissions?fields=${fields}&nestedFields=${nestedFields}&restrictFields=${restrictFields}&roleNames=${roleNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putWikiPagePermissionsPage(wikiPageId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-delivery/v1.0/wiki-pages/${wikiPageId}/permissions`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    return Object.freeze({
        getSiteBlogPostingImagesPage,
        postSiteBlogPostingImage,
        deleteBlogPostingImage,
        getBlogPostingImage,
        postSiteBlogPostingImageBatch,
        deleteBlogPostingImageBatch,
        postSiteBlogPostingImagesPageExportBatch,
        getBlogPostingRenderedContentByDisplayPageDisplayPageKey,
        deleteSiteBlogPostingByExternalReferenceCode,
        getSiteBlogPostingByExternalReferenceCode,
        putSiteBlogPostingByExternalReferenceCode,
        putSiteBlogPostingSubscribe,
        putSiteBlogPostingUnsubscribe,
        deleteBlogPosting,
        getBlogPosting,
        patchBlogPosting,
        putBlogPosting,
        deleteBlogPostingMyRating,
        getBlogPostingMyRating,
        postBlogPostingMyRating,
        putBlogPostingMyRating,
        getSiteBlogPostingsPage,
        postSiteBlogPosting,
        getSiteBlogPostingPermissionsPage,
        putSiteBlogPostingPermissionsPage,
        getBlogPostingPermissionsPage,
        putBlogPostingPermissionsPage,
        postSiteBlogPostingsPageExportBatch,
        postSiteBlogPostingBatch,
        deleteBlogPostingBatch,
        putBlogPostingBatch,
        deleteComment,
        getComment,
        putComment,
        getStructuredContentCommentsPage,
        postStructuredContentComment,
        deleteSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode,
        getSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode,
        putSiteStructuredContentByExternalReferenceCodeStructuredContentExternalReferenceCodeCommentByExternalReferenceCode,
        getBlogPostingCommentsPage,
        postBlogPostingComment,
        getCommentCommentsPage,
        postCommentComment,
        getDocumentCommentsPage,
        postDocumentComment,
        deleteSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode,
        getSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode,
        putSiteBlogPostingByExternalReferenceCodeBlogPostingExternalReferenceCodeCommentByExternalReferenceCode,
        deleteSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode,
        getSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode,
        putSiteCommentByExternalReferenceCodeParentCommentExternalReferenceCodeCommentByExternalReferenceCode,
        deleteSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode,
        getSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode,
        putSiteDocumentByExternalReferenceCodeDocumentExternalReferenceCodeCommentByExternalReferenceCode,
        postBlogPostingCommentBatch,
        postStructuredContentCommentBatch,
        postBlogPostingCommentsPageExportBatch,
        postDocumentCommentsPageExportBatch,
        getAssetLibraryContentElementsPage,
        getSiteContentElementsPage,
        postSiteContentElementsPageExportBatch,
        postAssetLibraryContentElementsPageExportBatch,
        getContentSetContentSetElementsPage,
        getAssetLibraryContentSetByKeyContentSetElementsPage,
        getAssetLibraryContentSetByUuidContentSetElementsPage,
        getSiteContentSetByKeyContentSetElementsPage,
        getSiteContentSetByUuidContentSetElementsPage,
        getAssetLibraryContentStructuresPage,
        getSiteContentStructuresPage,
        getContentStructure,
        getContentStructurePermissionsPage,
        putContentStructurePermissionsPage,
        postSiteContentStructuresPageExportBatch,
        getSiteContentStructurePermissionsPage,
        putSiteContentStructurePermissionsPage,
        postAssetLibraryContentStructuresPageExportBatch,
        getAssetLibraryContentStructurePermissionsPage,
        putAssetLibraryContentStructurePermissionsPage,
        getAssetLibraryContentTemplatesPage,
        getSiteContentTemplate,
        postSiteContentTemplatesPageExportBatch,
        postAssetLibraryContentTemplatesPageExportBatch,
        deleteDocumentFolder,
        getDocumentFolder,
        patchDocumentFolder,
        putDocumentFolder,
        deleteDocumentFolderMyRating,
        getDocumentFolderMyRating,
        postDocumentFolderMyRating,
        putDocumentFolderMyRating,
        putDocumentFolderSubscribe,
        getSiteDocumentFoldersPage,
        postSiteDocumentFolder,
        getDocumentFolderDocumentFoldersPage,
        postDocumentFolderDocumentFolder,
        getSiteDocumentFoldersRatedByMePage,
        putDocumentFolderUnsubscribe,
        getAssetLibraryDocumentFoldersPage,
        postAssetLibraryDocumentFolder,
        deleteSiteDocumentsFolderByExternalReferenceCode,
        getSiteDocumentsFolderByExternalReferenceCode,
        putSiteDocumentsFolderByExternalReferenceCode,
        getAssetLibraryDocumentFoldersRatedByMePage,
        deleteDocumentFolderBatch,
        putDocumentFolderBatch,
        postSiteDocumentFoldersPageExportBatch,
        postSiteDocumentFolderBatch,
        getSiteDocumentFolderPermissionsPage,
        putSiteDocumentFolderPermissionsPage,
        getDocumentFolderPermissionsPage,
        putDocumentFolderPermissionsPage,
        postAssetLibraryDocumentFolderBatch,
        getAssetLibraryDocumentFolderPermissionsPage,
        putAssetLibraryDocumentFolderPermissionsPage,
        postAssetLibraryDocumentFoldersPageExportBatch,
        deleteDocument,
        getDocument,
        patchDocument,
        putDocument,
        getSiteDocumentsRatedByMePage,
        deleteSiteDocumentByExternalReferenceCode,
        getSiteDocumentByExternalReferenceCode,
        putSiteDocumentByExternalReferenceCode,
        getAssetLibraryDocumentsRatedByMePage,
        getDocumentFolderDocumentsPage,
        postDocumentFolderDocument,
        getAssetLibraryDocumentsPage,
        postAssetLibraryDocument,
        getSiteDocumentsPage,
        postSiteDocument,
        deleteDocumentMyRating,
        getDocumentMyRating,
        postDocumentMyRating,
        putDocumentMyRating,
        deleteAssetLibraryDocumentByExternalReferenceCode,
        getAssetLibraryDocumentByExternalReferenceCode,
        putAssetLibraryDocumentByExternalReferenceCode,
        getDocumentRenderedContentByDisplayPageDisplayPageKey,
        getSiteDocumentPermissionsPage,
        putSiteDocumentPermissionsPage,
        postDocumentFolderDocumentBatch,
        postSiteDocumentsPageExportBatch,
        postDocumentFolderDocumentsPageExportBatch,
        postAssetLibraryDocumentBatch,
        postAssetLibraryDocumentsPageExportBatch,
        getAssetLibraryDocumentPermissionsPage,
        putAssetLibraryDocumentPermissionsPage,
        postSiteDocumentBatch,
        getDocumentPermissionsPage,
        putDocumentPermissionsPage,
        deleteDocumentBatch,
        putDocumentBatch,
        deleteKnowledgeBaseArticle,
        getKnowledgeBaseArticle,
        patchKnowledgeBaseArticle,
        putKnowledgeBaseArticle,
        deleteKnowledgeBaseArticleMyRating,
        getKnowledgeBaseArticleMyRating,
        postKnowledgeBaseArticleMyRating,
        putKnowledgeBaseArticleMyRating,
        putKnowledgeBaseArticleSubscribe,
        putKnowledgeBaseArticleUnsubscribe,
        getSiteKnowledgeBaseArticlesPage,
        postSiteKnowledgeBaseArticle,
        putSiteKnowledgeBaseArticleSubscribe,
        putSiteKnowledgeBaseArticleUnsubscribe,
        getKnowledgeBaseFolderKnowledgeBaseArticlesPage,
        postKnowledgeBaseFolderKnowledgeBaseArticle,
        deleteSiteKnowledgeBaseArticleByExternalReferenceCode,
        getSiteKnowledgeBaseArticleByExternalReferenceCode,
        putSiteKnowledgeBaseArticleByExternalReferenceCode,
        getKnowledgeBaseArticleKnowledgeBaseArticlesPage,
        postKnowledgeBaseArticleKnowledgeBaseArticle,
        postKnowledgeBaseFolderKnowledgeBaseArticlesPageExportBatch,
        deleteKnowledgeBaseArticleBatch,
        putKnowledgeBaseArticleBatch,
        getKnowledgeBaseArticlePermissionsPage,
        putKnowledgeBaseArticlePermissionsPage,
        postSiteKnowledgeBaseArticleBatch,
        getSiteKnowledgeBaseArticlePermissionsPage,
        putSiteKnowledgeBaseArticlePermissionsPage,
        postSiteKnowledgeBaseArticlesPageExportBatch,
        postKnowledgeBaseFolderKnowledgeBaseArticleBatch,
        deleteKnowledgeBaseAttachment,
        getKnowledgeBaseAttachment,
        deleteSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode,
        getSiteKnowledgeBaseArticleByExternalReferenceCodeKnowledgeBaseArticleExternalReferenceCodeKnowledgeBaseAttachmentByExternalReferenceCode,
        getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage,
        postKnowledgeBaseArticleKnowledgeBaseAttachment,
        deleteKnowledgeBaseAttachmentBatch,
        postKnowledgeBaseArticleKnowledgeBaseAttachmentsPageExportBatch,
        postKnowledgeBaseArticleKnowledgeBaseAttachmentBatch,
        deleteKnowledgeBaseFolder,
        getKnowledgeBaseFolder,
        patchKnowledgeBaseFolder,
        putKnowledgeBaseFolder,
        getSiteKnowledgeBaseFoldersPage,
        postSiteKnowledgeBaseFolder,
        getKnowledgeBaseFolderKnowledgeBaseFoldersPage,
        postKnowledgeBaseFolderKnowledgeBaseFolder,
        deleteSiteKnowledgeBaseFolderByExternalReferenceCode,
        getSiteKnowledgeBaseFolderByExternalReferenceCode,
        putSiteKnowledgeBaseFolderByExternalReferenceCode,
        getSiteKnowledgeBaseFolderPermissionsPage,
        putSiteKnowledgeBaseFolderPermissionsPage,
        postSiteKnowledgeBaseFolderBatch,
        getKnowledgeBaseFolderPermissionsPage,
        putKnowledgeBaseFolderPermissionsPage,
        deleteKnowledgeBaseFolderBatch,
        putKnowledgeBaseFolderBatch,
        postSiteKnowledgeBaseFoldersPageExportBatch,
        getAssetLibraryLanguagesPage,
        getSiteLanguagesPage,
        postAssetLibraryLanguagesPageExportBatch,
        postSiteLanguagesPageExportBatch,
        deleteMessageBoardAttachment,
        getMessageBoardAttachment,
        deleteSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode,
        getSiteMessageBoardMessageByExternalReferenceCodeMessageBoardMessageExternalReferenceCodeMessageBoardAttachmentByExternalReferenceCode,
        getMessageBoardMessageMessageBoardAttachmentsPage,
        postMessageBoardMessageMessageBoardAttachment,
        getMessageBoardThreadMessageBoardAttachmentsPage,
        postMessageBoardThreadMessageBoardAttachment,
        deleteMessageBoardAttachmentBatch,
        postMessageBoardMessageMessageBoardAttachmentsPageExportBatch,
        postMessageBoardThreadMessageBoardAttachmentsPageExportBatch,
        postMessageBoardThreadMessageBoardAttachmentBatch,
        postMessageBoardMessageMessageBoardAttachmentBatch,
        deleteMessageBoardMessageMyRating,
        getMessageBoardMessageMyRating,
        postMessageBoardMessageMyRating,
        putMessageBoardMessageMyRating,
        getSiteMessageBoardMessagesPage,
        getMessageBoardMessageMessageBoardMessagesPage,
        postMessageBoardMessageMessageBoardMessage,
        putMessageBoardMessageSubscribe,
        putMessageBoardMessageUnsubscribe,
        getMessageBoardThreadMessageBoardMessagesPage,
        postMessageBoardThreadMessageBoardMessage,
        deleteMessageBoardMessage,
        getMessageBoardMessage,
        patchMessageBoardMessage,
        putMessageBoardMessage,
        deleteSiteMessageBoardMessageByExternalReferenceCode,
        getSiteMessageBoardMessageByExternalReferenceCode,
        putSiteMessageBoardMessageByExternalReferenceCode,
        getSiteMessageBoardMessageByFriendlyUrlPath,
        getSiteUserMessageBoardMessagesActivityPage,
        deleteMessageBoardMessageBatch,
        putMessageBoardMessageBatch,
        getMessageBoardMessagePermissionsPage,
        putMessageBoardMessagePermissionsPage,
        getSiteMessageBoardMessagePermissionsPage,
        putSiteMessageBoardMessagePermissionsPage,
        postMessageBoardThreadMessageBoardMessageBatch,
        postMessageBoardThreadMessageBoardMessagesPageExportBatch,
        postSiteMessageBoardMessagesPageExportBatch,
        getSiteMessageBoardSectionsPage,
        postSiteMessageBoardSection,
        getMessageBoardSectionMessageBoardSectionsPage,
        postMessageBoardSectionMessageBoardSection,
        putMessageBoardSectionSubscribe,
        putMessageBoardSectionUnsubscribe,
        deleteMessageBoardSection,
        getMessageBoardSection,
        patchMessageBoardSection,
        putMessageBoardSection,
        getSiteMessageBoardSectionByFriendlyUrlPath,
        postSiteMessageBoardSectionBatch,
        deleteMessageBoardSectionBatch,
        putMessageBoardSectionBatch,
        getMessageBoardSectionPermissionsPage,
        putMessageBoardSectionPermissionsPage,
        getSiteMessageBoardSectionPermissionsPage,
        putSiteMessageBoardSectionPermissionsPage,
        postSiteMessageBoardSectionsPageExportBatch,
        getSiteMessageBoardThreadsPage,
        postSiteMessageBoardThread,
        deleteMessageBoardThreadMyRating,
        getMessageBoardThreadMyRating,
        postMessageBoardThreadMyRating,
        putMessageBoardThreadMyRating,
        getMessageBoardThreadsRankedPage,
        getSiteMessageBoardThreadByFriendlyUrlPath,
        getMessageBoardSectionMessageBoardThreadsPage,
        postMessageBoardSectionMessageBoardThread,
        putMessageBoardThreadSubscribe,
        putMessageBoardThreadUnsubscribe,
        deleteMessageBoardThread,
        getMessageBoardThread,
        patchMessageBoardThread,
        putMessageBoardThread,
        postSiteMessageBoardThreadBatch,
        deleteMessageBoardThreadBatch,
        putMessageBoardThreadBatch,
        getMessageBoardThreadPermissionsPage,
        putMessageBoardThreadPermissionsPage,
        postSiteMessageBoardThreadsPageExportBatch,
        getSiteMessageBoardThreadPermissionsPage,
        putSiteMessageBoardThreadPermissionsPage,
        postMessageBoardSectionMessageBoardThreadBatch,
        postMessageBoardSectionMessageBoardThreadsPageExportBatch,
        deleteNavigationMenu,
        getNavigationMenu,
        putNavigationMenu,
        getSiteNavigationMenusPage,
        postSiteNavigationMenu,
        postSiteNavigationMenuBatch,
        getNavigationMenuPermissionsPage,
        putNavigationMenuPermissionsPage,
        postSiteNavigationMenusPageExportBatch,
        getSiteNavigationMenuPermissionsPage,
        putSiteNavigationMenuPermissionsPage,
        deleteNavigationMenuBatch,
        putNavigationMenuBatch,
        getOpenAPI,
        getSiteSitePageExperienceExperienceKey,
        getSiteSitePageRenderedPage,
        getSiteSitePagesExperiencesPage,
        getSiteSitePage,
        getSiteSitePagesPage,
        postSiteSitePage,
        getSiteSitePageExperienceExperienceKeyRenderedPage,
        postSiteSitePagesPageExportBatch,
        postSiteSitePageBatch,
        deleteAssetLibraryStructuredContentFolderByExternalReferenceCode,
        getAssetLibraryStructuredContentFolderByExternalReferenceCode,
        putAssetLibraryStructuredContentFolderByExternalReferenceCode,
        deleteStructuredContentFolder,
        getStructuredContentFolder,
        patchStructuredContentFolder,
        putStructuredContentFolder,
        putStructuredContentFolderSubscribe,
        putStructuredContentFolderUnsubscribe,
        getSiteStructuredContentFoldersPage,
        postSiteStructuredContentFolder,
        getAssetLibraryStructuredContentFoldersPage,
        postAssetLibraryStructuredContentFolder,
        getStructuredContentFolderStructuredContentFoldersPage,
        postStructuredContentFolderStructuredContentFolder,
        deleteSiteStructuredContentFolderByExternalReferenceCode,
        getSiteStructuredContentFolderByExternalReferenceCode,
        putSiteStructuredContentFolderByExternalReferenceCode,
        deleteStructuredContentFolderBatch,
        putStructuredContentFolderBatch,
        getStructuredContentFolderPermissionsPage,
        putStructuredContentFolderPermissionsPage,
        postSiteStructuredContentFolderBatch,
        postAssetLibraryStructuredContentFoldersPageExportBatch,
        getSiteStructuredContentFolderPermissionsPage,
        putSiteStructuredContentFolderPermissionsPage,
        getAssetLibraryStructuredContentFolderPermissionsPage,
        putAssetLibraryStructuredContentFolderPermissionsPage,
        postAssetLibraryStructuredContentFolderBatch,
        postSiteStructuredContentFoldersPageExportBatch,
        getStructuredContentRenderedContentContentTemplate,
        getSiteStructuredContentsPage,
        postSiteStructuredContent,
        deleteStructuredContentMyRating,
        getStructuredContentMyRating,
        postStructuredContentMyRating,
        putStructuredContentMyRating,
        getAssetLibraryStructuredContentsPage,
        postAssetLibraryStructuredContent,
        getContentStructureStructuredContentsPage,
        getSiteStructuredContentByKey,
        getSiteStructuredContentByUuid,
        putStructuredContentSubscribe,
        putStructuredContentUnsubscribe,
        getStructuredContentRenderedContentByDisplayPageDisplayPageKey,
        deleteStructuredContent,
        getStructuredContent,
        patchStructuredContent,
        putStructuredContent,
        deleteAssetLibraryStructuredContentByExternalReferenceCode,
        getAssetLibraryStructuredContentByExternalReferenceCode,
        putAssetLibraryStructuredContentByExternalReferenceCode,
        deleteSiteStructuredContentByExternalReferenceCode,
        getSiteStructuredContentByExternalReferenceCode,
        putSiteStructuredContentByExternalReferenceCode,
        getStructuredContentFolderStructuredContentsPage,
        postStructuredContentFolderStructuredContent,
        postAssetLibraryStructuredContentBatch,
        deleteStructuredContentBatch,
        putStructuredContentBatch,
        postSiteStructuredContentBatch,
        postSiteStructuredContentsPageExportBatch,
        getSiteStructuredContentPermissionsPage,
        putSiteStructuredContentPermissionsPage,
        getStructuredContentPermissionsPage,
        putStructuredContentPermissionsPage,
        postStructuredContentFolderStructuredContentsPageExportBatch,
        postStructuredContentFolderStructuredContentBatch,
        postAssetLibraryStructuredContentsPageExportBatch,
        getAssetLibraryStructuredContentPermissionsPage,
        putAssetLibraryStructuredContentPermissionsPage,
        postContentStructureStructuredContentsPageExportBatch,
        deleteWikiNode,
        getWikiNode,
        putWikiNode,
        deleteSiteWikiNodeByExternalReferenceCode,
        getSiteWikiNodeByExternalReferenceCode,
        putSiteWikiNodeByExternalReferenceCode,
        getSiteWikiNodesPage,
        postSiteWikiNode,
        putWikiNodeSubscribe,
        putWikiNodeUnsubscribe,
        postSiteWikiNodesPageExportBatch,
        getSiteWikiNodePermissionsPage,
        putSiteWikiNodePermissionsPage,
        postSiteWikiNodeBatch,
        deleteWikiNodeBatch,
        putWikiNodeBatch,
        getWikiNodePermissionsPage,
        putWikiNodePermissionsPage,
        getWikiPageWikiPageAttachmentsPage,
        postWikiPageWikiPageAttachment,
        deleteSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode,
        getSiteWikiPageByExternalReferenceCodeWikiPageExternalReferenceCodeWikiPageAttachmentByExternalReferenceCode,
        deleteWikiPageAttachment,
        getWikiPageAttachment,
        postWikiPageWikiPageAttachmentBatch,
        deleteWikiPageAttachmentBatch,
        postWikiPageWikiPageAttachmentsPageExportBatch,
        deleteWikiPage,
        getWikiPage,
        putWikiPage,
        deleteSiteWikiPageByExternalReferenceCode,
        getSiteWikiPageByExternalReferenceCode,
        putSiteWikiPageByExternalReferenceCode,
        getWikiNodeWikiPagesPage,
        postWikiNodeWikiPage,
        getWikiPageWikiPagesPage,
        postWikiPageWikiPage,
        putWikiPageSubscribe,
        putWikiPageUnsubscribe,
        postWikiNodeWikiPagesPageExportBatch,
        postWikiNodeWikiPageBatch,
        deleteWikiPageBatch,
        putWikiPageBatch,
        getWikiPagePermissionsPage,
        putWikiPagePermissionsPage
    });
}
