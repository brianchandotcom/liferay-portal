/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {
	EntityType,
	ReferencedObjectsProvider,
} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';

import ReferencedEntityDisplay from '../ReferencedEntityDisplay';

jest.unmock('react-dom');

const defaultProps = {
	id: '123',
	label: 'Page',
	type: EntityType.Assets,
};

const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
	referencedObjects: {
		assets: {
			123: {id: '123', name: 'Foo Page'},
		},
	},
});

const DefaultComponent = (props) => (
	<ReferencedObjectsProvider segment={mockSegment}>
		<ReferencedEntityDisplay {...defaultProps} {...props} />
	</ReferencedObjectsProvider>
);

describe('ReferencedEntityDisplay', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with an undefined entity display', () => {
		const {container} = render(<DefaultComponent id="456" />);

		expect(container).toMatchSnapshot();
	});
});
