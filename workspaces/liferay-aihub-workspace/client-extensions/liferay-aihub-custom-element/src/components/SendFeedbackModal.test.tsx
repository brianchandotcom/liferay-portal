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
		agentDefinitionExternalReferenceCodes: ['agent-1'],
		chatbotExternalReferenceCode: 'chatbot-1',
		onClose: vi.fn(),
		onSubmitted: vi.fn(),
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
			screen.getByText('Exposure of Personal or Sensitive Data (PII)')
		).toBeInTheDocument();
		expect(
			screen.getByText('Agent Error or Malfunction')
		).toBeInTheDocument();
	});

	it('labels the dialog with its title for assistive technology', () => {
		renderModal();

		const dialog = screen.getByRole('dialog');

		expect(dialog).toHaveAttribute(
			'aria-labelledby',
			'aihub-feedback-modal-title'
		);
		expect(screen.getByText('Send Feedback')).toHaveAttribute(
			'id',
			'aihub-feedback-modal-title'
		);
	});

	it('closes the modal when Escape is pressed', () => {
		const {onClose} = renderModal();

		fireEvent.keyDown(window, {key: 'Escape'});

		expect(onClose).toHaveBeenCalledTimes(1);
	});

	it('keeps Send disabled until a reason is chosen', () => {
		renderModal();

		const send = screen.getByRole('button', {name: 'Send'});

		expect(send).toBeDisabled();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'other'},
		});

		expect(send).toBeEnabled();
	});

	it('submits and calls onSubmitted on success', async () => {
		mockedPost.mockResolvedValue({id: 'mock-1'});

		const {onSubmitted} = renderModal();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'incorrect'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'Send'}));

		await waitFor(() => expect(onSubmitted).toHaveBeenCalledTimes(1));

		expect(mockedPost).toHaveBeenCalledWith(
			expect.objectContaining({
				reason: 'incorrect',
				surface: 'clickToChat',
			})
		);
	});

	it('shows an inline error and keeps the modal open on failure', async () => {
		mockedPost.mockRejectedValue(new Error('nope'));

		const {onSubmitted} = renderModal();

		fireEvent.change(screen.getByLabelText(/Reason/), {
			target: {value: 'other'},
		});
		fireEvent.click(screen.getByRole('button', {name: 'Send'}));

		await waitFor(() =>
			expect(screen.getByText('nope')).toBeInTheDocument()
		);

		expect(onSubmitted).not.toHaveBeenCalled();
	});
});
