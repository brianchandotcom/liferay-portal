/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import FieldPreviewModal from '../FieldPreviewModal';

jest.unmock('react-dom');

const DefaultComponent = (
	props: Partial<React.ComponentProps<typeof FieldPreviewModal>> = {}
) => (
	<FieldPreviewModal
		dataSourceFn={() => Promise.resolve()}
		onClose={noop}
		sourceName="foo"
		{...props}
	/>
);

describe('FieldPreviewModal', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with fieldname', () => {
		const fieldName = 'bar';

		const {getByText} = render(<DefaultComponent fieldName={fieldName} />);

		expect(getByText(fieldName)).toBeTruthy();
	});
});
