import { Liferay } from './liferay';
import { create } from 'pkce';

class WebClient {
    constructor({
            clientId,
            authorizeURL = Liferay.OAuth.getAuthorizeURL(),
            tokenURL = Liferay.OAuth.getTokenURL(),
            redirectURL = Liferay.OAuth.getBuiltInRedirectURL()
        }) {

        this.clientId = clientId;
        this.authorizeURL = authorizeURL;
        this.tokenURL = tokenURL;
        this.redirectURL = redirectURL;
        this.encodedRedirectURL = encodeURIComponent(this.redirectURL);
    }

    async fetch(url, options = {}) {
        return this._fetch(url, options).then(
            (response) => {
                if (response.ok) {
                    return response.json();
                }
                return Promise.reject(response);
            }
        );
    }

    token() {
        var webclient = this;
        let waitUntil = sessionStorage.getItem(`${webclient.clientId}-wait-until`);
        let promise = new Promise(function(resolve, reject) {
            let seconds = webclient._time() / 1000;
            let response = JSON.parse(sessionStorage.getItem(`${webclient.clientId}-token`));
            let expiration = sessionStorage.getItem(`${webclient.clientId}-expiration`);

            if(response != null && seconds < expiration) {
                resolve(response);
            }
            else {
                if(waitUntil == null) {
                    resolve(webclient._requestNewToken(webclient.clientId));
                }
                else if(webclient._time() < waitUntil) {
                    let interval = setInterval(function() {
                        let seconds = webclient._time() / 1000;
                        let response = sessionStorage.getItem(`${webclient.clientId}-token`);
                        let expiration = sessionStorage.getItem(`${webclient.clientId}-expiration`);

                        if(response != null && seconds < expiration) {
                            resolve(response);
                            clearInterval(interval);
                            return;
                        }

                        if(webclient._time() > waitUntil) {
                            clearInterval(interval);
                            resolve(webclient._requestNewToken(webclient.clientId));
                        }
                    }, 200);
                } else {
                    resolve(webclient._requestNewToken(webclient.clientId));
                }
            }
        });

        return promise;
    }

    _createIframe(id) {
        var ifrm = document.createElement('iframe');
        ifrm.id = id;
        ifrm.style.display = 'none';
        document.body.appendChild(ifrm);
        return ifrm;
    }

    _fetch(url, options = {}) {
        if (!url.includes("//")) {
            return fetch(window.location.origin + "/" + url, {
                headers: {
                    "Content-Type": "application/json",
                    "x-csrf-token": Liferay.authToken,
                },
                ...options
            });
        }
        else if (url.startsWith(window.location.origin)) {
            return fetch(url, {
                headers: {
                    "Content-Type": "application/json",
                    "x-csrf-token": Liferay.authToken,
                },
                ...options
            });
        }
        else {
            return this.token().then(
                (response) => {
                    return fetch(url, {
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": `Bearer ${response.access_token}`,
                        },
                        ...options
                    });
                }
            )
        }
    }

    _requestNewToken() {
        var webclient = this;
        sessionStorage.setItem(`${webclient.clientId}-wait-until`, webclient._time() + 3000);

        let codePair = create();
        let codeChallenge = codePair.codeChallenge;
        let ifrm = webclient._createIframe(webclient.clientId);

        var promise = new Promise(function(resolve, reject) {
            ifrm.contentWindow.addEventListener('message', function abc(event) {
                try {
                    if (event.data.client_id === webclient.clientId) {
                        if (event.data.interaction_required) {
                            reject();
                        }
                        else if (event.data.data !== null) {
                            var tokenResponse = webclient._requestToken(codePair, event.data.code);
                            resolve(tokenResponse);
                            tokenResponse.then(response => webclient._storeToken(response));
                        }
                    }
                }
                finally {
                    ifrm.parentNode.removeChild(ifrm);
                }
            });
        });

        ifrm.src = `${webclient.authorizeURL}?response_type=code&client_id=${webclient.clientId}&state=${webclient.clientId}&redirect_uri=${webclient.encodedRedirectURL}&prompt=none&code_challenge=${codeChallenge}&code_challenge_method=S256`;

        return promise;
    }

    _requestToken(codePair, code) {
        var webclient = this;
        return fetch(webclient.tokenURL, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'grant_type': 'authorization_code',
                'code': code,
                'redirect_uri': webclient.redirectURL,
                'client_id': webclient.clientId,
                'code_verifier': codePair.codeVerifier
            })
        }).then((response) => {
            if (response.ok) {
                return response.json();
            }
            return Promise.reject(response);
        });
    }

    _storeToken(tokenData) {
        var webclient = this;
        sessionStorage.setItem(`${webclient.clientId}-token`, JSON.stringify(tokenData));
        let seconds = webclient._time() / 1000;
        let expiration = seconds + tokenData.expires_in / 2;
        sessionStorage.setItem(`${webclient.clientId}-expiration`, expiration);
    }

    _time() {
        return (new Date()).getTime();
    }

}

export default WebClient;