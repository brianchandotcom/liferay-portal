/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as API from '~/shared/api';

import {
	ERROR_TYPES,
	EVENT_TYPES,
	OAUTH_ERROR_CODES,
	emitAuthCode,
	emitError,
	emitToken,
	getTempCredentials,
	isOAuthErrorString,
} from '../oauth';

describe('isOAuthErrorString', () => {
	it('returns true is the error is considered an OAuth error type', () => {
		OAUTH_ERROR_CODES.map((code) => {
			expect(isOAuthErrorString(code)).toBe(true);
		});
	});

	it('returns false if the error is NOT considered to be an OAuth error type', () => {
		expect(isOAuthErrorString(undefined)).toBe(false);

		expect(isOAuthErrorString(null)).toBe(false);

		expect(isOAuthErrorString('not_an_oauth_error')).toBe(false);
	});
});

describe('oauth-token', () => {
	class MockWindow {
		closed = false;
		focus = jest.fn();
		location = {
			href: '',
		};

		close() {
			this.closed = true;
		}
	}

	describe('emitAuthCode', () => {
		beforeAll(() => {
			window.opener = {
				postMessage: jest.fn(),
			};
		});

		afterAll(() => {
			delete window.opener;
		});

		it('calls postMessage on the opener', () => {
			const code = 'foobarbaz';

			emitAuthCode({code});

			expect(window.opener.postMessage).toBeCalledWith(
				expect.objectContaining({code}),
				expect.any(String)
			);
		});
	});

	describe('emitError', () => {
		beforeAll(() => {
			window.opener = {
				postMessage: jest.fn(),
			};
		});

		afterAll(() => {
			delete window.opener;
		});

		it('calls postMessage on the opener', () => {
			const errorMessage = 'foobar: bizbaz';

			emitError({message: errorMessage});

			expect(window.opener.postMessage).toBeCalledWith(
				expect.objectContaining({message: errorMessage}),
				expect.any(String)
			);
		});
	});

	describe('emitToken', () => {
		beforeAll(() => {
			window.opener = {
				postMessage: jest.fn(),
			};
		});

		afterAll(() => {
			delete window.opener;
		});

		it('calls postMessage on the opener', () => {
			const partialCredentials = {
				token: 'foobarbaz',
				verifier: 'bizbaz',
			};

			emitToken(partialCredentials);

			expect(window.opener.postMessage).toBeCalledWith(
				expect.objectContaining(partialCredentials),
				expect.any(String)
			);
		});
	});

	describe('getTempCredentials', () => {
		beforeAll(() => {
			jest.useRealTimers();
		});

		afterAll(() => {
			jest.useFakeTimers();
		});

		afterEach(() => {
			jest.restoreAllMocks();
		});

		it('receives token', () => {
			expect.assertions(2);

			const baseUrl = 'https://foobar.biz';
			const callbackUrl = 'http://testtest.io';
			const consumerKey = 'bar';
			const consumerSecret = 'foo';
			const partialCredentials = {
				token: 'foobarbaz',
				verifier: 'bizbaz',
			};

			const originalAddEventListener = window.addEventListener;

			jest.spyOn(window, 'addEventListener').mockImplementation(
				(...args) => {
					originalAddEventListener.call(window, ...args);

					const event = new Event('message');

					event.origin = location.origin;
					event.data = {
						...partialCredentials,
						type: EVENT_TYPES.AC_RECEIVE_OAUTH_TOKEN,
					};

					dispatchEvent(event);
				}
			);

			const authWindow = new MockWindow();

			return getTempCredentials({
				authWindow,
				baseUrl,
				callbackUrl,
				consumerKey,
				consumerSecret,
			}).then((receivedTempCredentials) => {
				expect(receivedTempCredentials).toEqual({
					oAuthAuthorizationURL: baseUrl,
					oAuthCallbackURL: callbackUrl,
					oAuthCode: undefined,
					oAuthToken: partialCredentials.token,
					oAuthTokenSecret: 'bizzybuzz',
					oAuthVerifier: partialCredentials.verifier,
				});
				expect(authWindow.closed).toBe(true);
			});
		});

		it('timeouts after a specified duration', () =>
			expect(
				getTempCredentials({
					authWindow: new MockWindow(),
					baseUrl: 'http://foobar.biz',
					consumerKey: 'bar',
					consumerSeceret: 'foo',
					timeout: 250,
				})
			).rejects.toThrow(
				expect.objectContaining({
					type: ERROR_TYPES.TIMEOUT,
				})
			));

		it('sets closed to true if fetchOAuthUrl rejects', () => {
			expect.assertions(1);

			API.dataSource.fetchOAuthUrl.mockReturnValueOnce(
				Promise.reject({test: 'reject'})
			);

			const authWindow = new MockWindow();

			authWindow.close = jest.fn();

			return getTempCredentials({
				authWindow,
				baseUrl: 'http://foobar.biz',
				consumerKey: 'bar',
				consumerSeceret: 'foo',
				timeout: 250,
			}).catch(() => {
				expect(authWindow.close).toBeCalled();
			});
		});

		it('rejects if the window is closed', () => {
			const authWindow = new MockWindow();

			authWindow.closed = true;

			return expect(
				getTempCredentials({
					authWindow,
					baseUrl: 'http://foobar.biz',
					consumerKey: 'bar',
					consumerSeceret: 'foo',
					timeout: 250,
				})
			).rejects.toThrow(
				expect.objectContaining({
					type: ERROR_TYPES.WINDOW_CLOSED,
				})
			);
		});
	});
});
