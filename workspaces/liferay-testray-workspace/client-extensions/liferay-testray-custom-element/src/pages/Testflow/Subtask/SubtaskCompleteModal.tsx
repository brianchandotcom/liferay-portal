/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {getUniqueList} from '~/util';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import Modal from '../../../components/Modal';
import SearchBuilder from '../../../core/SearchBuilder';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {useFetch} from '../../../hooks/useFetch';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {Liferay} from '../../../services/liferay';
import {
	APIResponse,
	TestrayCaseResult,
	TestraySubTask,
	liferayMessageBoardImpl,
	testrayCaseResultImpl,
	testraySubTaskImpl,
} from '../../../services/rest';
import {CaseResultStatuses} from '../../../util/statuses';

type SubtaskForm = typeof yupSchema.subtask.__outputType;

type SubTaskCompleteModalProps = {
	modal: FormModalOptions;
	revalidateSubtask: () => void;
	setForceRefetch: React.Dispatch<React.SetStateAction<number>>;
	subtask: TestraySubTask;
};

const SubtaskCompleteModal: React.FC<SubTaskCompleteModalProps> = ({
	modal: {observer, onClose, onError, onSave},
	revalidateSubtask,
	setForceRefetch,
	subtask,
}) => {
	const {data: mbMessage} = useFetch(
		liferayMessageBoardImpl.getMessagesIdURL(subtask.mbMessageId)
	);

	const caseResultsStatusFilter = useMemo(
		() =>
			new SearchBuilder()
				.eq('r_subtaskToCaseResults_c_subtaskId', subtask.id)
				.and()
				.in('dueStatus', ['BLOCKED', 'FAILED', 'PASSED', 'TESTFIX'])
				.and()
				.ne('issues', '')
				.build(),
		[subtask.id]
	);

	const {data: caseResults} = useFetch<APIResponse<TestrayCaseResult>>(
		testrayCaseResultImpl.resource,
		{
			params: {
				aggregationTerms: 'dueStatus',
				fields: 'id',
				filter: caseResultsStatusFilter,
				nestedFields: 'subtaskToCaseResults',
				pageSize: 4,
			},
		}
	);

	const subtaskIssues = subtask.issues
		? subtask.issues
				.split(',')
				.map((name) => name.trim())
				.filter(Boolean)
		: [];

	const issues = getUniqueList([
		...subtaskIssues,
		...subtask.caseResultIssues,
	]).join(', ');

	const statusMode = useMemo(() => {
		const statuses = caseResults?.facets[0].facetValues;

		if (!statuses) {
			return CaseResultStatuses.FAILED;
		}

		const status = statuses.reduce(
			(prevValue, status) => {
				if (
					status.numberOfOccurrences > prevValue.numberOfOccurrences
				) {
					return status;
				}

				return prevValue;
			},
			{numberOfOccurrences: 0, term: ''}
		);

		return status.term;
	}, [caseResults]);

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
	} = useForm<SubtaskForm>({
		resolver: yupResolver(yupSchema.subtask),
	});

	const _onSubmit = async ({
		comment,
		dueStatus,
		issues = '',
	}: SubtaskForm) => {
		const _issues = issues
			.split(',')
			.map((name) => name.trim())
			.filter(Boolean);

		const commentSubtask = {
			comment,
			mbMessageId: subtask.mbMessageId,
			mbThreadId: subtask.mbThreadId,
			userId: Number(Liferay.ThemeDisplay.getUserId()),
		};

		try {
			await testraySubTaskImpl.complete(
				dueStatus as string,
				_issues,
				commentSubtask,
				subtask?.id
			);

			revalidateSubtask();

			onSave();
			setForceRefetch(new Date().getTime());
		}
		catch (error) {
			onError(error);
		}
	};

	useEffect(() => {
		setValue('dueStatus', statusMode);
		setValue('comment', mbMessage?.articleBody);
		setValue('issues', issues);
	}, [issues, statusMode, mbMessage, setValue]);

	const inputProps = {
		errors,
		register,
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.sub('edit-x', 'status')}
			visible
		>
			<Container>
				<Form.Select
					className="container-fluid-max-md"
					defaultOption={false}
					label={i18n.translate('case-results-status')}
					name="dueStatus"
					options={[
						{
							label: i18n.translate('blocked'),
							value: CaseResultStatuses.BLOCKED,
						},
						{
							label: i18n.translate('failed'),
							value: CaseResultStatuses.FAILED,
						},
						{
							label: i18n.translate('passed'),
							value: CaseResultStatuses.PASSED,
						},
						{
							label: i18n.translate('test-fix'),
							value: CaseResultStatuses.TEST_FIX,
						},
					]}
					register={register}
				/>

				<Form.Input
					{...inputProps}
					className="container-fluid-max-md"
					label={i18n.translate('issues')}
					name="issues"
					register={register}
				/>

				<Form.Input
					{...inputProps}
					className="container-fluid-max-md"
					label={i18n.translate('comment')}
					name="comment"
					register={register}
					type="textarea"
				/>
			</Container>
		</Modal>
	);
};

export default withVisibleContent(SubtaskCompleteModal);
