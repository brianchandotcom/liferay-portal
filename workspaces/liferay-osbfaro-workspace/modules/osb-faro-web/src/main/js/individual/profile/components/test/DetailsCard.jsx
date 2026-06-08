/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {StaticRouter} from 'react-router';
import {Individual} from '~/shared/util/records';
import {mockIndividual} from '~/test/data';

import IndividualDetailsCard from '../DetailsCard';

jest.unmock('react-dom');

describe('IndividualDetailsCard', () => {
	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<IndividualDetailsCard
					entity={new Individual(fromJS(mockIndividual()))}
					groupId="23"
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
