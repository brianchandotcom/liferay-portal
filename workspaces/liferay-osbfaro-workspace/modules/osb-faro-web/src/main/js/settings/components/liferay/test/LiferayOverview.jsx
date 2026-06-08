/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import LiferayOverview from '../LiferayOverview';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({
		isAdmin: () => true,
	}),
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: () => ({
		data: {
			channelsCount: 5,
			groupsCount: 10,
			individualsCount: 100,
		},
		loading: false,
	}),
}));

const defaultProps = {
	dataSource: new DataSource(data.mockLiferayDataSource()),
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/data-source/test']}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id">
				<MockedProvider>
					<LiferayOverview {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('LiferayOverview', () => {
	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
