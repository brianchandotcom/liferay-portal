/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CompositionCard from '../CompositionCard';

jest.unmock('react-dom');

describe('CompositionCard', () => {
	it('renders', () => {
		const {container} = render(
			<CompositionCard
				activeIndividualCount={20}
				individualCount={100}
				knownIndividualCount={50}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
