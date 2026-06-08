/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {compose} from 'redux';
import {withStaticRouter} from '~/test/mock-router';
import {renderWithStore} from '~/test/mock-store';

import {RemoteData} from '../../util/records';
import withAction from '../WithAction';

jest.unmock('react-dom');

describe('withAction', () => {
	const action = () => ({type: 'NO_OP'});
	const mapStateToRemoteData = () => new RemoteData({loading: false});

	it('returns a new Component', () => {
		const WrappedComponent = withAction(
			action,
			mapStateToRemoteData
		)(jest.fn());

		expect(WrappedComponent).toBeInstanceOf(Object);
	});

	it('renders the wrapped component', () => {
		const WrappedComponent = withAction(
			action,
			mapStateToRemoteData
		)(() => <div>foo</div>);

		const {container} = renderWithStore(WrappedComponent);

		expect(container).toMatchSnapshot();
	});

	it('renders loading if the RemoteData is loading and data is null', () => {
		const WrappedComponent = withAction(
			action,
			() => new RemoteData()
		)(jest.fn());

		const {container} = renderWithStore(WrappedComponent);

		expect(container).toMatchSnapshot();
	});

	it('renders error if the RemoteData has error', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(action, () => new RemoteData({error: true}))
		)(jest.fn());

		const {container} = renderWithStore(WrappedComponent);

		expect(container).toMatchSnapshot();
	});

	it('renders a custom error message', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(action, () => new RemoteData({error: true}), {
				errorPageProps: {
					message: 'my fancy message, oh so fancy',
				},
			})
		)(jest.fn());

		const {container} = renderWithStore(WrappedComponent);

		expect(container).toMatchSnapshot();
	});

	it('renders the wrapped component if bypassErrorPage is true even if the RemoteData has an error', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withAction(
				action,
				() => new RemoteData({data: {test: 'test'}, error: true}),
				{bypassErrorPage: true}
			)
		)(() => <div>foo</div>);

		const {container} = renderWithStore(WrappedComponent);

		expect(container).toMatchSnapshot();
	});
});
