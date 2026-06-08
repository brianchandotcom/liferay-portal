/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Segment} from '~/shared/util/records';
import {renderWithStore} from '~/test/mock-store';

import withSegment from '../WithSegment';

jest.unmock('react-dom');

jest.mock(
	'~/shared/hoc/WithAction',
	() => () => (wrappedComponent) => wrappedComponent
);

describe('WithSegment', () => {
	it('passes the segment to the WrappedComponent', () => {
		let result = null;

		const MockComponent = (props) => {
			result = props;

			return null;
		};

		const WrappedComponent = withSegment(MockComponent);

		renderWithStore(WrappedComponent, {
			id: 'test',
			segment: new Segment(),
		});

		expect(result.segment).toBeInstanceOf(Segment);
	});
});
