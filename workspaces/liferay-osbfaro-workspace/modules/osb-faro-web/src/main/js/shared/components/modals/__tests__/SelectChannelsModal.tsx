import React from 'react';
import SelectChannelsModal from '../SelectChannelsModal';
import {fireEvent, render} from '@testing-library/react';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('shared/hoc/CrossPageSelect', () => ({
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

	it('should auto-select channels with synced sites on first data load', () => {
		const onSelect = jest.fn();

		render(<SelectChannelsModal {...defaultProps} onSelect={onSelect} />);

		fireEvent.click(submitButton());

		expect(onSelect).toHaveBeenCalledWith(
			expect.arrayContaining(['channel-1', 'channel-3'])
		);
	});

	it('should not auto-select channels without synced sites', () => {
		const onSelect = jest.fn();

		render(<SelectChannelsModal {...defaultProps} onSelect={onSelect} />);

		fireEvent.click(submitButton());

		expect(onSelect.mock.calls[0][0]).not.toContain('channel-2');
	});

	it('should not duplicate channels already present in initialItems', () => {
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
