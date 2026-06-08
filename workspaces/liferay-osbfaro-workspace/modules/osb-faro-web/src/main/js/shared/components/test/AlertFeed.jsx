/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Alert} from '~/shared/types';
import {sub} from '~/shared/util/lang';

import {AlertFeed} from '../AlertFeed';

jest.unmock('react-dom');

const defaultProps = {
	alertsIMap: fromJS({
		1: {
			alertType: Alert.Types.Default,
			id: 1,
			message: 'foo bar',
		},
	}),
	removeAlert: jest.fn(),
};

describe('AlertFeed', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<AlertFeed {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with modal open', () => {
		const {container} = render(<AlertFeed {...defaultProps} modalActive />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a message comprised of language keys with jsx substitutions', () => {
		const props = {
			alertsIMap: fromJS({
				1: {
					alertType: Alert.Types.Default,
					id: 1,
					message: sub(
						Liferay.Language.get('x-usd'),
						[<b key="TEST">Foo</b>],
						false
					),
				},
			}),
		};

		const {container} = render(<AlertFeed {...defaultProps} {...props} />);
		expect(container).toMatchSnapshot();
	});
});
