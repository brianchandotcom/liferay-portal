import React from 'react';
import {compose} from 'redux';
import * as data from '~/test/data';
import {withChannelProvider} from '~/test/mock-channel-context';
import {withStaticRouter} from '~/test/mock-router';
import {renderWithStore} from '~/test/mock-store';

import withBaseEdit from '../WithBaseEdit';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('~/shared/components/DocumentTitle');

jest.unmock('react-dom');

class TestComponent extends React.Component {
	render() {
		return <div>foobar</div>;
	}
}

describe('WithBaseEdit', () => {
	it('renders the wrapped component', () => {
		const WrappedComponent = compose(
			withChannelProvider,
			withStaticRouter,
			withBaseEdit
		)(TestComponent);

		const {getByText} = renderWithStore(WrappedComponent, {
			groupId: '23',
			id: '123',
			segment: data.mockSegment(),
		});

		expect(getByText('foobar')).toBeInTheDocument();
	});
});
