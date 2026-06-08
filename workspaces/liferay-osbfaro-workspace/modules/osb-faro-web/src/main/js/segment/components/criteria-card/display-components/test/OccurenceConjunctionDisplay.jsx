/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {RelationalOperators} from '~/segment/segment-editor/dynamic/utils/constants';

import OccurenceConjunctionDisplay from '../OccurenceConjunctionDisplay';

jest.unmock('react-dom');

describe('OccurenceConjunctionDisplay', () => {
	it('renders', () => {
		const {container} = render(
			<OccurenceConjunctionDisplay
				operatorName={RelationalOperators.GT}
				value={13}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
