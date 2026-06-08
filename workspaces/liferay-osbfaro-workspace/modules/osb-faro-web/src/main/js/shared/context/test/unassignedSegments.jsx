/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {mockSegment} from '~/test/data';

import {
	ActionType,
	UnassignedSegmentsProvider,
	unassignedSegmentsReducer,
	useUnassignedSegmentsContext,
	withUnassignedSegmentsProvider,
} from '../unassignedSegments';

jest.unmock('react-dom');

const initialState = {
	unassignedSegments: [],
};

describe('unassignedSegmentsReducer', () => {
	it('returns unassignedSegments state with added items', () => {
		const segment = mockSegment();

		const {unassignedSegments} = unassignedSegmentsReducer(initialState, {
			payload: [segment],
			type: ActionType.setSegments,
		});

		expect(unassignedSegments).toContain(segment);
	});
});

describe('UnassignedSegmentsProvider', () => {
	afterEach(cleanup);

	it('allows an initial context value to be set through the unassignedSegments prop', () => {
		const successMsg = 'has unassignedSegments intialized in context!';

		const ChildComponent = () => {
			const {unassignedSegments} = useUnassignedSegmentsContext();

			return unassignedSegments.length && successMsg;
		};

		const {container} = render(
			<UnassignedSegmentsProvider unassignedSegments={[mockSegment()]}>
				<ChildComponent />
			</UnassignedSegmentsProvider>
		);

		expect(container).toHaveTextContent(successMsg);
	});
});

describe('useUnassignedSegmentsContext', () => {
	afterEach(cleanup);

	it('returns context', () => {
		const successMsg = 'has context!';

		const ChildComponent = () => {
			const {unassignedSegments, unassignedSegmentsDispatch} =
				useUnassignedSegmentsContext();

			return (
				unassignedSegments && unassignedSegmentsDispatch && successMsg
			);
		};

		const {container} = render(
			<UnassignedSegmentsProvider>
				<ChildComponent />
			</UnassignedSegmentsProvider>
		);

		expect(container).toHaveTextContent(successMsg);
	});
});

describe('withUnassignedSegmentsProvider', () => {
	afterEach(cleanup);

	it('passes the WrappedComponent', () => {
		const WrappedComponent = withUnassignedSegmentsProvider(() => (
			<div>foo</div>
		));
		const {container} = render(<WrappedComponent />);
		expect(container).toHaveTextContent('foo');
	});
});
