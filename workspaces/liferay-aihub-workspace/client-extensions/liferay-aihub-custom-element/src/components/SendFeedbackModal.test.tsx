/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';
import {beforeEach, describe, expect, it, vi} from 'vitest';

import {postAIIssueReport} from '../api';
import SendFeedbackModal from './SendFeedbackModal';

vi.mock('../api', () => ({postAIIssueReport: vi.fn()}));

const mockedPost = vi.mocked(postAIIssueReport);

function renderModal(
	overrides: Partial<{onClose: () => void; onSubmitted: () => void}> = {}
) {
	const props = {
		agentId: 'agent-1',
		onClose: vi.fn(),
		onSubmitted: vi.fn(),
		traceId: 'trace-1',
		...overrides,
	};

	render(<SendFeedbackModal {...props} />);

	return props;
}

describe('SendFeedbackModal', () => {
	beforeEach(() => {
		mockedPost.mockReset();
	});

	it('renders the title and every reason option', () => {
		renderModal();

		expect(screen.getByText('Send Feedback')).toBeInTheDocument();
		expect(
			screen.getByText('Incorrect or Inaccurate Response')
		).toBeInTheDocument();
		expect(
			screen.getByText('Exposure of personal / Sensitive Data (PII)')
		).toBeInTheDocument();
		expect(
			screen.getByText('Agent Error / Malfunction')
		).toBeInTheDocument();
	});

	it('keeps Send disabled until a reason is chosen', () => {
		renderModal();

		const send = screen.getByRole('button', {name: 'Send'});

		expect(send).toBeDisabled();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'OTHER'},
		});

		expect(send).toBeEnabled();
	});

	it('submits and calls onSubmitted on success', async () => {
		mockedPost.mockResolvedValue({id: 'mock-1'});

		const {onSubmitted} = renderModal();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'INCORRECT_OR_INACCURATE_RESPONSE'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'Send'}));

		await waitFor(() => expect(onSubmitted).toHaveBeenCalledTimes(1));

		expect(mockedPost).toHaveBeenCalledWith(
			expect.objectContaining({
				reason: 'INCORRECT_OR_INACCURATE_RESPONSE',
				surface: 'CLICK_TO_CHAT',
			})
		);
	});

	it('shows an inline error and keeps the modal open on failure', async () => {
		mockedPost.mockRejectedValue(new Error('nope'));

		const {onSubmitted} = renderModal();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'OTHER'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'Send'}));

		await waitFor(() =>
			expect(screen.getByText('nope')).toBeInTheDocument()
		);

		expect(onSubmitted).not.toHaveBeenCalled();
	});
});
