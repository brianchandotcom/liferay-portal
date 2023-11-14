import fetch from 'node-fetch';
import config from '../util/configTreePath.js';
import axios from 'axios';

const lxcDXPMainDomain = config['com.liferay.lxc.dxp.mainDomain'] ||
    config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol = config['com.liferay.lxc.dxp.server.protocol'] ||
    config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export function headless_admin_userService(_token) {
    const token = _token;
    function deleteAccountGroup(accountGroupId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `Bearer ${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/${accountGroupId}`, requestOptions)
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


    function getAccountGroup(accountGroupId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/${accountGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchAccountGroup(accountGroupId, AccountGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/${accountGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccountGroup(accountGroupId, AccountGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/${accountGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountGroupByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getAccountGroupByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchAccountGroupByExternalReferenceCode(externalReferenceCode, AccountGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccountGroupByExternalReferenceCode(externalReferenceCode, AccountGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountGroupsPage(filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountGroup(AccountGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountAccountGroupsPage(accountId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-groups?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${accountExternalReferenceCode}/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function postAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/by-external-reference-code/${accountExternalReferenceCode}/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(accountExternalReferenceCode, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/account-groups?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountGroupsPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountAccountGroupsPageExportBatch(accountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-groups/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/batch?callbackURL=${callbackURL}`, requestOptions)
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


    function postAccountGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccountGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/account-groups/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountsPage(filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccount(Account) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Account),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccount(accountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}`, requestOptions)
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


    function getAccount(accountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchAccount(accountId, Account) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Account),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccount(accountId, Account) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Account),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganizationAccounts(organizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts`, requestOptions)
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


    function getOrganizationAccountsPage(organizationId, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationAccounts(organizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getAccountByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchAccountByExternalReferenceCode(externalReferenceCode, Account) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Account),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccountByExternalReferenceCode(externalReferenceCode, Account) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Account),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchOrganizationMoveAccounts(sourceOrganizationId, targetOrganizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/move-accounts/${sourceOrganizationId}/${targetOrganizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchOrganizationMoveAccountsByExternalReferenceCode(sourceOrganizationId, targetOrganizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/move-accounts/${sourceOrganizationId}/${targetOrganizationId}/by-external-reference-code`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganizationAccountsByExternalReferenceCode(organizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts/by-external-reference-code`, requestOptions)
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


    function postOrganizationAccountsByExternalReferenceCode(organizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts/by-external-reference-code`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/batch?callbackURL=${callbackURL}`, requestOptions)
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


    function postAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountsPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationAccountsPageExportBatch(organizationId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(externalReferenceCode, accountRoleId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/account-roles/${accountRoleId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
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


    function postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(externalReferenceCode, accountRoleId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/account-roles/${accountRoleId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(externalReferenceCode, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts/by-email-address/${emailAddress}/account-roles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountAccountRolesPage(accountId, keywords = null, filter = null, page = null, pageSize = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles?keywords=${keywords}&filter=${filter}&page=${page}&pageSize=${pageSize}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountAccountRole(accountId, AccountRole) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountRole),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountAccountRoleUserAccountAssociation(accountId, accountRoleId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${accountRoleId}/user-accounts/${userAccountId}`, requestOptions)
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


    function postAccountAccountRoleUserAccountAssociation(accountId, accountRoleId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${accountRoleId}/user-accounts/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountAccountRolesByExternalReferenceCodePage(externalReferenceCode, keywords = null, filter = null, page = null, pageSize = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/account-roles?keywords=${keywords}&filter=${filter}&page=${page}&pageSize=${pageSize}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountAccountRoleByExternalReferenceCode(externalReferenceCode, AccountRole) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(AccountRole),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/account-roles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(accountExternalReferenceCode, accountRoleId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/account-roles/${accountRoleId}/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(accountExternalReferenceCode, accountRoleId, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/account-roles/${accountRoleId}/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-external-reference-code/${externalReferenceCode}/account-roles`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountAccountRolesPageExportBatch(accountId, keywords = null, filter = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/export-batch?keywords=${keywords}&filter=${filter}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountAccountRoleBatch(accountId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getEmailAddress(emailAddressId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/email-addresses/${emailAddressId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationEmailAddressesPage(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/email-addresses`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountEmailAddressesPage(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/email-addresses`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationEmailAddressesPageExportBatch(organizationId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/email-addresses/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountEmailAddressesPageExportBatch(userAccountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/email-addresses/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOpenAPI(type) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/openapi.${type}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganization(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}`, requestOptions)
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


    function getOrganization(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchOrganization(organizationId, Organization) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Organization),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putOrganization(organizationId, Organization) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Organization),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganizationByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getOrganizationByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchOrganizationByExternalReferenceCode(externalReferenceCode, Organization) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Organization),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putOrganizationByExternalReferenceCode(externalReferenceCode, Organization) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Organization),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationsPage(flatten = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations?flatten=${flatten}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganization(Organization) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(Organization),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountOrganization(accountId, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/organizations/${organizationId}`, requestOptions)
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


    function getAccountOrganization(accountId, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountOrganization(accountId, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountOrganizationsPage(accountId, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/organizations?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationChildOrganizationsPage(organizationId, flatten = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/child-organizations?flatten=${flatten}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserAccountsByEmailAddress(organizationId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts/by-email-address`, requestOptions)
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


    function postUserAccountsByEmailAddress(organizationId, organizationRoleIds = null, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts/by-email-address?organizationRoleIds=${organizationRoleIds}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserAccountByEmailAddress(organizationId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
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


    function postUserAccountByEmailAddress(organizationId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationOrganizationsPage(parentOrganizationId, flatten = null, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${parentOrganizationId}/organizations?flatten=${flatten}&filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountByExternalReferenceCodeOrganizationsPage(externalReferenceCode, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/organizations?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountByExternalReferenceCodeOrganization(externalReferenceCode, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/organizations/${organizationId}`, requestOptions)
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


    function getAccountByExternalReferenceCodeOrganization(externalReferenceCode, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountByExternalReferenceCodeOrganization(externalReferenceCode, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/organizations/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganizationBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/batch?callbackURL=${callbackURL}`, requestOptions)
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


    function postOrganizationBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putOrganizationBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountOrganizationsPageExportBatch(accountId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/organizations/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationsPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getPhone(phoneId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/phones/${phoneId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationPhonesPage(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/phones`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountPhonesPage(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/phones`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationPhonesPageExportBatch(organizationId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/phones/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountPhonesPageExportBatch(userAccountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/phones/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountPostalAddressesPage(accountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/postal-addresses`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationPostalAddressesPage(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/postal-addresses`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountPostalAddressesPage(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/postal-addresses`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getPostalAddress(postalAddressId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/postal-addresses/${postalAddressId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountPostalAddressesPageExportBatch(accountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/postal-addresses/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationPostalAddressesPageExportBatch(organizationId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/postal-addresses/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountPostalAddressesPageExportBatch(userAccountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/postal-addresses/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteOrganizationRoleUserAccountAssociation(roleId, userAccountId, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}/organization/${organizationId}`, requestOptions)
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


    function postOrganizationRoleUserAccountAssociation(roleId, userAccountId, organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}/organization/${organizationId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getRole(roleId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteRoleUserAccountAssociation(roleId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}`, requestOptions)
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


    function postRoleUserAccountAssociation(roleId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteSiteRoleUserAccountAssociation(roleId, userAccountId, siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}/site/${siteId}`, requestOptions)
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


    function postSiteRoleUserAccountAssociation(roleId, userAccountId, siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/${roleId}/association/user-account/${userAccountId}/site/${siteId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getRolesPage(types = null, page = null, pageSize = null, search = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles?types=${types}&page=${page}&pageSize=${pageSize}&search=${search}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postRolesPageExportBatch(types = null, search = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/roles/export-batch?types=${types}&search=${search}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteUserAccountSegmentsPage(siteId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}/user-accounts/${userAccountId}/segments`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteSegmentsPage(siteId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}/segments?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteSegmentsPageExportBatch(siteId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}/segments/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSegmentUserAccountsPage(segmentId, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/segments/${segmentId}/user-accounts?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSite(siteId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMyUserAccountSitesPage(page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/my-user-account/sites?page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getSiteByFriendlyUrlPath(friendlyUrlPath) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/by-friendly-url-path/${friendlyUrlPath}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteMyUserAccountSubscription(subscriptionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/my-user-account/subscriptions/${subscriptionId}`, requestOptions)
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


    function getMyUserAccountSubscription(subscriptionId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/my-user-account/subscriptions/${subscriptionId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMyUserAccountSubscriptionsPage(contentType = null, page = null, pageSize = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/my-user-account/subscriptions?contentType=${contentType}&page=${page}&pageSize=${pageSize}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountEmailVerificationTicket(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/email-verification-ticket`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountPasswordResetTicket(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/password-reset-ticket`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(accountExternalReferenceCode, externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress(externalReferenceCode, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts/by-email-address`, requestOptions)
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


    function postAccountUserAccountsByExternalReferenceCodeByEmailAddress(externalReferenceCode, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts/by-email-address`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(externalReferenceCode, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
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


    function postAccountUserAccountByExternalReferenceCodeByEmailAddress(externalReferenceCode, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountUserAccountsPage(accountId, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountUserAccount(accountId, UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountsPage(filter = "", page = 0, pageSize = 100, search = "", sort = "") {
        var prom = new Promise(async (resolve, reject) => {
            filter = filter === ""?"":`&${filter}`;
            let config = {
                method: 'get',
                maxBodyLength: Infinity,
                url: `${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts?page=${page}${filter}`,
                headers: {
                    'Authorization': `${token}`
                }
            };
            axios.request(config)
                .then((response) => {
                    resolve(response.data);
                })
                .catch((error) => {
                    console.log(error.message);
                    reject(error.message);
                });
        })
        return prom;
    }


    function postUserAccount(UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountUserAccount(accountId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/${userAccountId}`, requestOptions)
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


    function getAccountUserAccount(accountId, userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getMyUserAccount() {
        var prom = new Promise(async (resolve, reject) => {
            let config = {
                method: 'get',
                maxBodyLength: Infinity,
                url: `${oauth2JWKSURI}/o/headless-admin-user/v1.0/my-user-account`,
                headers: {
                    'Authorization': `${token}`
                }
            };
            axios.request(config)
                .then((response) => {
                    resolve(response.data);
                })
                .catch((error) => {
                    console.log(error.message);
                    reject(error.message);
                });
        })
        return prom;
    }


    function getSiteUserAccountsPage(siteId, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}/user-accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserAccount(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}`, requestOptions)
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


    function getUserAccount(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchUserAccount(userAccountId, UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserAccount(userAccountId, UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountImage(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/image`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationUserAccountsPage(organizationId, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserAccountByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getUserAccountByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserAccountByExternalReferenceCode(externalReferenceCode, UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountUserAccountByEmailAddress(accountId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
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


    function postAccountUserAccountByEmailAddress(accountId, emailAddress) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address/${emailAddress}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteAccountUserAccountsByEmailAddress(accountId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address`, requestOptions)
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


    function postAccountUserAccountsByEmailAddress(accountId, accountRoleIds = null, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address?accountRoleIds=${accountRoleIds}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getAccountUserAccountsByExternalReferenceCodePage(externalReferenceCode, filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountUserAccountByExternalReferenceCode(externalReferenceCode, UserAccount) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserAccount),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}/user-accounts`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/batch?callbackURL=${callbackURL}`, requestOptions)
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


    function postUserAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserAccountBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountsPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postSiteUserAccountsPageExportBatch(siteId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/sites/${siteId}/user-accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountUserAccountBatch(accountId, callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postAccountUserAccountsPageExportBatch(accountId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationUserAccountsPageExportBatch(organizationId, filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/user-accounts/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserGroupUsers(userGroupId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}/user-group-users`, requestOptions)
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


    function postUserGroupUsers(userGroupId, requestBodyarray) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyarray),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}/user-group-users`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserGroup(userGroupId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}`, requestOptions)
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


    function getUserGroup(userGroupId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchUserGroup(userGroupId, UserGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserGroup(userGroupId, UserGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/${userGroupId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserUserGroups(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/user-groups`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserGroupByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
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


    function getUserGroupByExternalReferenceCode(externalReferenceCode) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function patchUserGroupByExternalReferenceCode(externalReferenceCode, UserGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PATCH',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserGroupByExternalReferenceCode(externalReferenceCode, UserGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/by-external-reference-code/${externalReferenceCode}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserGroupsPage(filter = null, page = null, pageSize = null, search = null, sort = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups?filter=${filter}&page=${page}&pageSize=${pageSize}&search=${search}&sort=${sort}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserGroup(UserGroup) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(UserGroup),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserGroupsPageExportBatch(filter = null, search = null, sort = null, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/export-batch?filter=${filter}&search=${search}&sort=${sort}&callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function deleteUserGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'DELETE',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/batch?callbackURL=${callbackURL}`, requestOptions)
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


    function postUserGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function putUserGroupBatch(callbackURL = null, requestBodyobject) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'PUT',
                headers: requestHeaders,
                redirect: 'follow'
                , body: JSON.stringify(requestBodyobject),
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-groups/batch?callbackURL=${callbackURL}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getOrganizationWebUrlsPage(organizationId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/web-urls`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getUserAccountWebUrlsPage(userAccountId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/web-urls`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function getWebUrl(webUrlId) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            var requestOptions = {
                method: 'GET',
                headers: requestHeaders,
                redirect: 'follow'
            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/web-urls/${webUrlId}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postOrganizationWebUrlsPageExportBatch(organizationId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/organizations/${organizationId}/web-urls/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    function postUserAccountWebUrlsPageExportBatch(userAccountId, callbackURL = null, contentType = null, fieldNames = null) {
        var prom = new Promise((resolve, reject) => {
            var requestHeaders = new Headers();
            requestHeaders.append('Authorization', `${token}`);
            requestHeaders.append("Content-Type", "application/json");
            var requestOptions = {
                method: 'POST',
                headers: requestHeaders,
                redirect: 'follow'

            };
            fetch(`${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts/${userAccountId}/web-urls/export-batch?callbackURL=${callbackURL}&contentType=${contentType}&fieldNames=${fieldNames}`, requestOptions)
                .then(response => response.json())
                .then(result => resolve(result))
                .catch(error => reject(error));
        })
        return prom;
    }


    return Object.freeze({
        deleteAccountGroup,
        getAccountGroup,
        patchAccountGroup,
        putAccountGroup,
        deleteAccountGroupByExternalReferenceCode,
        getAccountGroupByExternalReferenceCode,
        patchAccountGroupByExternalReferenceCode,
        putAccountGroupByExternalReferenceCode,
        getAccountGroupsPage,
        postAccountGroup,
        getAccountAccountGroupsPage,
        deleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode,
        postAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode,
        getAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage,
        postAccountGroupsPageExportBatch,
        postAccountAccountGroupsPageExportBatch,
        deleteAccountGroupBatch,
        postAccountGroupBatch,
        putAccountGroupBatch,
        getAccountsPage,
        postAccount,
        deleteAccount,
        getAccount,
        patchAccount,
        putAccount,
        deleteOrganizationAccounts,
        getOrganizationAccountsPage,
        postOrganizationAccounts,
        deleteAccountByExternalReferenceCode,
        getAccountByExternalReferenceCode,
        patchAccountByExternalReferenceCode,
        putAccountByExternalReferenceCode,
        patchOrganizationMoveAccounts,
        patchOrganizationMoveAccountsByExternalReferenceCode,
        deleteOrganizationAccountsByExternalReferenceCode,
        postOrganizationAccountsByExternalReferenceCode,
        deleteAccountBatch,
        postAccountBatch,
        putAccountBatch,
        postAccountsPageExportBatch,
        postOrganizationAccountsPageExportBatch,
        deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress,
        postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress,
        getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage,
        getAccountAccountRolesPage,
        postAccountAccountRole,
        deleteAccountAccountRoleUserAccountAssociation,
        postAccountAccountRoleUserAccountAssociation,
        getAccountAccountRolesByExternalReferenceCodePage,
        postAccountAccountRoleByExternalReferenceCode,
        deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode,
        postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode,
        getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage,
        postAccountAccountRolesPageExportBatch,
        postAccountAccountRoleBatch,
        getEmailAddress,
        getOrganizationEmailAddressesPage,
        getUserAccountEmailAddressesPage,
        postOrganizationEmailAddressesPageExportBatch,
        postUserAccountEmailAddressesPageExportBatch,
        getOpenAPI,
        deleteOrganization,
        getOrganization,
        patchOrganization,
        putOrganization,
        deleteOrganizationByExternalReferenceCode,
        getOrganizationByExternalReferenceCode,
        patchOrganizationByExternalReferenceCode,
        putOrganizationByExternalReferenceCode,
        getOrganizationsPage,
        postOrganization,
        deleteAccountOrganization,
        getAccountOrganization,
        postAccountOrganization,
        getAccountOrganizationsPage,
        getOrganizationChildOrganizationsPage,
        deleteUserAccountsByEmailAddress,
        postUserAccountsByEmailAddress,
        deleteUserAccountByEmailAddress,
        postUserAccountByEmailAddress,
        getOrganizationOrganizationsPage,
        getAccountByExternalReferenceCodeOrganizationsPage,
        deleteAccountByExternalReferenceCodeOrganization,
        getAccountByExternalReferenceCodeOrganization,
        postAccountByExternalReferenceCodeOrganization,
        deleteOrganizationBatch,
        postOrganizationBatch,
        putOrganizationBatch,
        postAccountOrganizationsPageExportBatch,
        postOrganizationsPageExportBatch,
        getPhone,
        getOrganizationPhonesPage,
        getUserAccountPhonesPage,
        postOrganizationPhonesPageExportBatch,
        postUserAccountPhonesPageExportBatch,
        getAccountPostalAddressesPage,
        getOrganizationPostalAddressesPage,
        getUserAccountPostalAddressesPage,
        getPostalAddress,
        postAccountPostalAddressesPageExportBatch,
        postOrganizationPostalAddressesPageExportBatch,
        postUserAccountPostalAddressesPageExportBatch,
        deleteOrganizationRoleUserAccountAssociation,
        postOrganizationRoleUserAccountAssociation,
        getRole,
        deleteRoleUserAccountAssociation,
        postRoleUserAccountAssociation,
        deleteSiteRoleUserAccountAssociation,
        postSiteRoleUserAccountAssociation,
        getRolesPage,
        postRolesPageExportBatch,
        getSiteUserAccountSegmentsPage,
        getSiteSegmentsPage,
        postSiteSegmentsPageExportBatch,
        getSegmentUserAccountsPage,
        getSite,
        getMyUserAccountSitesPage,
        getSiteByFriendlyUrlPath,
        deleteMyUserAccountSubscription,
        getMyUserAccountSubscription,
        getMyUserAccountSubscriptionsPage,
        getUserAccountEmailVerificationTicket,
        getUserAccountPasswordResetTicket,
        deleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode,
        getAccountByExternalReferenceCodeUserAccountByExternalReferenceCode,
        postAccountByExternalReferenceCodeUserAccountByExternalReferenceCode,
        deleteAccountUserAccountsByExternalReferenceCodeByEmailAddress,
        postAccountUserAccountsByExternalReferenceCodeByEmailAddress,
        deleteAccountUserAccountByExternalReferenceCodeByEmailAddress,
        postAccountUserAccountByExternalReferenceCodeByEmailAddress,
        getAccountUserAccountsPage,
        postAccountUserAccount,
        getUserAccountsPage,
        postUserAccount,
        deleteAccountUserAccount,
        getAccountUserAccount,
        getMyUserAccount,
        getSiteUserAccountsPage,
        deleteUserAccount,
        getUserAccount,
        patchUserAccount,
        putUserAccount,
        postUserAccountImage,
        getOrganizationUserAccountsPage,
        deleteUserAccountByExternalReferenceCode,
        getUserAccountByExternalReferenceCode,
        putUserAccountByExternalReferenceCode,
        deleteAccountUserAccountByEmailAddress,
        postAccountUserAccountByEmailAddress,
        deleteAccountUserAccountsByEmailAddress,
        postAccountUserAccountsByEmailAddress,
        getAccountUserAccountsByExternalReferenceCodePage,
        postAccountUserAccountByExternalReferenceCode,
        deleteUserAccountBatch,
        postUserAccountBatch,
        putUserAccountBatch,
        postUserAccountsPageExportBatch,
        postSiteUserAccountsPageExportBatch,
        postAccountUserAccountBatch,
        postAccountUserAccountsPageExportBatch,
        postOrganizationUserAccountsPageExportBatch,
        deleteUserGroupUsers,
        postUserGroupUsers,
        deleteUserGroup,
        getUserGroup,
        patchUserGroup,
        putUserGroup,
        getUserUserGroups,
        deleteUserGroupByExternalReferenceCode,
        getUserGroupByExternalReferenceCode,
        patchUserGroupByExternalReferenceCode,
        putUserGroupByExternalReferenceCode,
        getUserGroupsPage,
        postUserGroup,
        postUserGroupsPageExportBatch,
        deleteUserGroupBatch,
        postUserGroupBatch,
        putUserGroupBatch,
        getOrganizationWebUrlsPage,
        getUserAccountWebUrlsPage,
        getWebUrl,
        postOrganizationWebUrlsPageExportBatch,
        postUserAccountWebUrlsPageExportBatch
    });
}
