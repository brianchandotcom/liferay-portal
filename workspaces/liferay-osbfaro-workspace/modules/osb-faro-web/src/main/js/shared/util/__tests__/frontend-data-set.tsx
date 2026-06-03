import {columns} from '../frontend-data-set';
import {render, screen} from '@testing-library/react';
import {Routes} from '../router';

jest.unmock('react-dom');

describe('columns.nameAndLinkRenderer', () => {
	it('should generate an href that includes the channelId path segment', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: 'Acme Corp'
			})
		);

		expect(screen.getByRole('link')).toHaveAttribute(
			'href',
			'/workspace/23/123/contacts/accounts/abc'
		);
	});

	it('should use value as the link text when provided', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: 'Acme Corp'
			})
		);

		expect(screen.getByRole('link')).toHaveTextContent('Acme Corp');
	});

	it('should fall back to itemData.id as the link text when value is empty', () => {
		render(
			columns.nameAndLinkRenderer({
				channelId: '123',
				groupId: '23',
				itemData: {id: 'abc'},
				route: Routes.CONTACTS_ACCOUNT,
				value: ''
			})
		);

		expect(screen.getByRole('link')).toHaveTextContent('abc');
	});
});
