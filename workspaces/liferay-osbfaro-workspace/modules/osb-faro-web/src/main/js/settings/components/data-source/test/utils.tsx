/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {disconnect} from '~/shared/api/data-source';

import {useDisconnectDataSource} from '../utils';

jest.unmock('react-dom');

jest.mock('~/shared/api/data-source', () => ({
	disconnect: jest.fn(() => Promise.resolve({})),
}));

interface RunFlowOptions {
	beforeSubmit?: () => Promise<any> | any;
	onSubmit?: () => Promise<any> | any;
}

const runDisconnectFlow = async ({
	beforeSubmit,
	onSubmit = () => Promise.resolve(),
}: RunFlowOptions = {}) => {
	const open = jest.fn();
	const close = jest.fn();
	const addAlert = jest.fn();

	let handleDisconnect: () => void = () => {};

	const Capture: React.FC = () => {
		const hook = useDisconnectDataSource({
			addAlert,
			beforeSubmit,
			close,
			groupId: '23',
			id: 'ds-1',
			onSubmit,
			open,
		});

		handleDisconnect = hook.handleDisconnect;

		return null;
	};

	render(<Capture />);

	handleDisconnect();

	const modalConfig = open.mock.calls[0][1];

	await modalConfig.onSubmit();

	return {addAlert, close, open};
};

describe('useDisconnectDataSource', () => {
	beforeEach(() => {
		(disconnect as jest.Mock).mockClear();
		(disconnect as jest.Mock).mockResolvedValue({});
	});

	it('runs beforeSubmit before the disconnect API call', async () => {
		const callOrder: string[] = [];

		const beforeSubmit = jest.fn(() => {
			callOrder.push('beforeSubmit');

			return Promise.resolve();
		});

		(disconnect as jest.Mock).mockImplementation(() => {
			callOrder.push('disconnect');

			return Promise.resolve({});
		});

		await runDisconnectFlow({beforeSubmit});

		expect(callOrder).toEqual(['beforeSubmit', 'disconnect']);
	});

	it('runs the consumer onSubmit (refetch) after the disconnect API call', async () => {
		const callOrder: string[] = [];

		(disconnect as jest.Mock).mockImplementation(() => {
			callOrder.push('disconnect');

			return Promise.resolve({});
		});

		const onSubmit = jest.fn(() => {
			callOrder.push('onSubmit');

			return Promise.resolve();
		});

		await runDisconnectFlow({onSubmit});

		expect(callOrder).toEqual(['disconnect', 'onSubmit']);
	});

	it('skips beforeSubmit gracefully when the consumer does not provide one', async () => {
		const onSubmit = jest.fn(() => Promise.resolve());

		await runDisconnectFlow({onSubmit});

		expect(disconnect).toHaveBeenCalledWith({groupId: '23', id: 'ds-1'});
		expect(onSubmit).toHaveBeenCalled();
	});

	it('does not call disconnect when beforeSubmit throws', async () => {
		const beforeSubmit = jest.fn(() =>
			Promise.reject(new Error('revoke failed'))
		);
		const onSubmit = jest.fn(() => Promise.resolve());

		await runDisconnectFlow({beforeSubmit, onSubmit});

		expect(disconnect).not.toHaveBeenCalled();
		expect(onSubmit).not.toHaveBeenCalled();
	});
});
