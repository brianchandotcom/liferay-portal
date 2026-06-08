/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {emitAuthCode, emitError, emitToken} from '~/shared/util/oauth';

import OAuthReceive from '../OAuthReceive';

jest.mock('~/shared/util/oauth', () => ({
	emitAuthCode: jest.fn(),
	emitError: jest.fn(),
	emitToken: jest.fn(),
}));

jest.unmock('react-dom');

describe('OAuthReceive', () => {
	afterEach(() => {
		emitToken.mockReset();
	});

	it('renders', () => {
		const {container} = render(<OAuthReceive />);

		expect(container).toMatchSnapshot();
	});

	it('calls emitToken if one was passed', () => {
		expect(emitToken).not.toBeCalled();
		const token = 'foobarbazbiz';
		const verifier = 'bizfizzbuzz';

		render(<OAuthReceive oauth_token={token} oauth_verifier={verifier} />);
		expect(emitToken).toBeCalledWith({token, verifier});
	});

	it('calls emitAuthCode if a code was passed', () => {
		expect(emitAuthCode).not.toBeCalled();
		const code = 'foobarbazbiz';

		render(<OAuthReceive code={code} />);
		expect(emitAuthCode).toBeCalledWith({code});
	});

	it('does not call emitToken if not all credentials are passed', () => {
		expect(emitToken).not.toBeCalled();
		const token = 'foobarbazbiz';

		render(<OAuthReceive oauth_token={token} />);
		expect(emitToken).not.toBeCalled();
	});

	it('calls emitError if an error was passed', () => {
		expect(emitError).not.toBeCalled();
		const error = 'foobar';
		const errorDescription = 'bazbiz';
		const errorMessage = `${error}: ${errorDescription}`;

		render(
			<OAuthReceive error={error} error_description={errorDescription} />
		);
		expect(emitError).toBeCalledWith({message: errorMessage});
	});
});
