/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';
import {beforeEach, describe, expect, it, vi} from 'vitest';

import {postAIIssueReport} from '../api';
import useReportFeedback from './useReportFeedback';

vi.mock('../api', () => ({postAIIssueReport: vi.fn()}));

const mockedPost = vi.mocked(postAIIssueReport);

describe('useReportFeedback', () => {
	beforeEach(() => {
		mockedPost.mockReset();
	});

	it('cannot submit until a reason is selected', () => {
		const {result} = renderHook(() =>
			useReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			traceId: 'trace-1',
		})
		);

		expect(result.current.canSubmit).toBe(false);

		act(() => result.current.setReason('other'));

		expect(result.current.canSubmit).toBe(true);
	});

	it('does not post when no reason is selected', async () => {
		const {result} = renderHook(() =>
			useReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			traceId: 'trace-1',
		})
		);

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(false);
		expect(mockedPost).not.toHaveBeenCalled();
	});

	it('posts the trimmed payload and returns true on success', async () => {
		mockedPost.mockResolvedValue({id: 'mock-1'});

		const {result} = renderHook(() =>
			useReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			traceId: 'trace-1',
		})
		);

		act(() => {
			result.current.setReason('piiExposure');
			result.current.setUserMessage('  sensitive  ');
		});

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(true);
		expect(mockedPost).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			reason: 'piiExposure',
			surface: 'clickToChat',
			traceId: 'trace-1',
			userMessage: 'sensitive',
		});
	});

	it('omits an empty comment from the payload', async () => {
		mockedPost.mockResolvedValue({id: 'mock-1'});

		const {result} = renderHook(() =>
			useReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			traceId: 'trace-1',
		})
		);

		act(() => result.current.setReason('other'));

		await act(async () => {
			await result.current.submit();
		});

		expect(mockedPost).toHaveBeenCalledWith({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			reason: 'other',
			surface: 'clickToChat',
			traceId: 'trace-1',
		});
	});

	it('sets an error and returns false on failure', async () => {
		mockedPost.mockRejectedValue(new Error('boom'));

		const {result} = renderHook(() =>
			useReportFeedback({
			agentDefinitionExternalReferenceCodes: ['agent-1'],
			traceId: 'trace-1',
		})
		);

		act(() => result.current.setReason('other'));

		let outcome: boolean | undefined;

		await act(async () => {
			outcome = await result.current.submit();
		});

		expect(outcome).toBe(false);
		expect(result.current.error).toBe('boom');
		expect(result.current.submitting).toBe(false);
	});
});
