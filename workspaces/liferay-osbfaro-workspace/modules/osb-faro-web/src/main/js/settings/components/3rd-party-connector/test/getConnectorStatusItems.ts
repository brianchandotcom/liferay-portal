/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getInitialLogEntries} from '../getConnectorStatusItems';
import {ConnectorStatus} from '../types';

describe('getInitialLogEntries', () => {
	it('seeds a single Token generated entry for Inactive with no data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Inactive, 0);

		expect(entries).toHaveLength(1);
		expect(entries[0].title).toBe('Token Generated');
	});

	it('seeds Listening and Token generated for Active with no data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Active, 0);

		expect(entries).toHaveLength(2);
		expect(entries[0].title).toBe('Listening...');
		expect(entries[1].title).toBe('Token Generated');
	});

	it('seeds Data flow, Listening, Token generated for Active with data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Active, 5);

		expect(entries).toHaveLength(3);
		expect(entries[0].title).toBe('Data Flow Established');
		expect(entries[0].iconDisplayType).toBe('success');
		expect(entries[1].title).toBe('Listening...');
		expect(entries[2].title).toBe('Token Generated');
	});

	it('seeds Inactive data flow and prior history for Inactive with data', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Inactive, 10);

		expect(entries).toHaveLength(4);
		expect(entries[0].title).toBe('Inactive Data Flow');
		expect(entries[1].title).toBe('Data Flow Established');
		expect(entries[2].title).toBe('Listening...');
		expect(entries[3].title).toBe('Token Generated');
	});

	it('seeds disconnected history for Disconnected status', () => {
		const entries = getInitialLogEntries(ConnectorStatus.Disconnected, 0);

		expect(entries).toHaveLength(4);
		expect(entries[0].title).toBe('Data Source Disconnected');
		expect(entries[1].title).toBe('Inactive Data Flow');
		expect(entries[2].title).toBe('Data Flow Established');
		expect(entries[3].title).toBe('Listening...');
	});
});

describe('item shape', () => {
	it('every entry from getInitialLogEntries has all required fields', () => {
		const allSeeds = [
			getInitialLogEntries(ConnectorStatus.Inactive, 0),
			getInitialLogEntries(ConnectorStatus.Active, 0),
			getInitialLogEntries(ConnectorStatus.Active, 1),
			getInitialLogEntries(ConnectorStatus.Inactive, 1),
			getInitialLogEntries(ConnectorStatus.Disconnected, 0),
		];

		allSeeds.forEach((entries) => {
			entries.forEach((entry) => {
				expect(typeof entry.bold).toBe('boolean');
				expect(typeof entry.icon).toBe('string');
				expect(['secondary', 'success']).toContain(
					entry.iconDisplayType
				);
				expect(typeof entry.title).toBe('string');
				expect(typeof entry.secondaryText).toBe('string');
			});
		});
	});
});
