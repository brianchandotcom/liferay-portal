/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {useRequest} from '~/shared/hooks/useRequest';

import SelectChannelsModal from '../SelectChannelsModal';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('~/shared/hoc/CrossPageSelect', () => ({
	__esModule: true,
	default: () => <div />,
}));

const mockChannels = [
	{groupsCount: 2, id: 'channel-1', name: 'Channel With Sites'},
	{groupsCount: 0, id: 'channel-2', name: 'Channel Without Sites'},
	{groupsCount: 1, id: 'channel-3', name: 'Another Channel With Sites'},
];

const defaultProps = {
	groupId: '23',
	initialItems: [],
	onClose: jest.fn(),
	onSelect: jest.fn(),
};

const submitButton = () =>
	document.querySelector('.modal-footer button[type="submit"]') as Element;

describe('SelectChannelsModal', () => {
	beforeEach(() => {
		(useRequest as jest.Mock).mockReturnValue({
			data: {items: mockChannels, total: 3},
			error: false,
			loading: false,
		});
	});

	it('auto-selects channels with synced sites on first data load', () => {
		const onSelect = jest.fn();

		render(<SelectChannelsModal {...defaultProps} onSelect={onSelect} />);

		fireEvent.click(submitButton());

		expect(onSelect).toHaveBeenCalledWith(
			expect.arrayContaining(['channel-1', 'channel-3'])
		);
	});

	it('does not auto-select channels without synced sites', () => {
		const onSelect = jest.fn();

		render(<SelectChannelsModal {...defaultProps} onSelect={onSelect} />);

		fireEvent.click(submitButton());

		expect(onSelect.mock.calls[0][0]).not.toContain('channel-2');
	});

	it('does not duplicate channels already present in initialItems', () => {
		const onSelect = jest.fn();

		render(
			<SelectChannelsModal
				{...defaultProps}
				initialItems={['channel-1']}
				onSelect={onSelect}
			/>
		);

		fireEvent.click(submitButton());

		const selectedIds: string[] = onSelect.mock.calls[0][0];

		expect(selectedIds.filter((id) => id === 'channel-1')).toHaveLength(1);
	});
});
