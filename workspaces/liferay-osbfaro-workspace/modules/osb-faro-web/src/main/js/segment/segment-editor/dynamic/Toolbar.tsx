import * as API from 'shared/api';
import BaseEditPage from 'shared/components/base-edit-page';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPopover from '@clayui/popover';
import React from 'react';
import {ACCOUNT_NAME, createOrderIOMap, NAME} from 'shared/util/pagination';
import {ACCOUNTS, INDIVIDUALS} from 'shared/util/router';
import {
	accountsListColumns,
	individualsListColumns,
} from 'shared/util/table-columns';
import {ClayToggle} from '@clayui/form';
import {close, modalTypes, open} from 'shared/actions/modals';
import {Criteria} from './utils/types';
import {Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {SegmentCategories, SegmentTypes} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toLocale} from 'shared/util/numbers';
import {useDispatch} from 'react-redux';
import {useField} from 'formik';
import {useRequest} from 'shared/hooks/useRequest';
import {validateSegmentInputs} from './utils/utils';

type MembersParams = {
	channelId: string;
	criteriaString: string;
	delta?: number;
	groupId: string;
	includeAnonymousUsers: boolean;
	orderIOMap?: any;
	page?: number;
	query?: string;
	segmentCategory: SegmentCategories;
};

export const fetchMembers = ({
	channelId,
	criteriaString,
	delta,
	groupId,
	includeAnonymousUsers,
	orderIOMap,
	page,
	query,
	segmentCategory,
}: MembersParams): Promise<{items: any[]; total: number}> => {
	if (segmentCategory === SegmentCategories.Account) {
		return API.accounts
			.searchByFilter({
				channelId,
				filter: criteriaString,
				groupId,
				includeAnonymousUsers,
				orderIOMap,
				page,
				pageSize: delta,
				query,
			})
			.then(
				({
					items,
					totalCount,
				}: {
					items: Array<Record<string, any>>;
					totalCount: number;
				}) => ({items, total: totalCount})
			);
	}

	return API.individuals.search({
		channelId,
		delta,
		filter: criteriaString,
		groupId,
		includeAnonymousUsers,
		orderIOMap,
		page,
		query,
	});
};

const IncludeAnonymousToggle: React.FC = () => {
	const [{value}, , {setValue}] = useField<boolean>('includeAnonymousUsers');

	return (
		<ClayToggle
			id="includeAnonymousUsers"
			label={Liferay.Language.get('include-anonymous')}
			onToggle={setValue}
			toggled={!!value}
		/>
	);
};

interface IToolbarProps {
	channelId: string;
	criteria: Criteria;
	criteriaString: string;
	groupId: string;
	id: string;
	includeAnonymousUsers: boolean;
	onDeleteSegment?: () => void;
	segmentCategory: SegmentCategories;
	segmentType: SegmentTypes;
	valid: boolean;
}

export const Toolbar: React.FC<IToolbarProps> = ({
	channelId,
	criteria,
	criteriaString,
	groupId,
	id,
	includeAnonymousUsers,
	onDeleteSegment,
	segmentCategory,
	segmentType,
	valid,
}) => {
	const dispatch = useDispatch();

	const criteriaValid = !!criteria && validateSegmentInputs(criteria);

	const isAccountSegment = segmentCategory === SegmentCategories.Account;
	const isBatch = segmentType === SegmentTypes.Batch;

	const baseParams = {
		channelId,
		criteriaString,
		groupId,
		includeAnonymousUsers,
		segmentCategory,
	};

	const {data, loading} = useRequest<MembersParams, {total: number}>({
		dataSourceFn: fetchMembers,
		debounceDelay: 400,
		initialState: {data: null, error: false, loading: false},
		resetStateIfSkipingRequest: true,
		skipRequest: !criteriaValid,
		variables: {...baseParams, delta: 0},
	});

	const membersCount = criteriaValid ? data?.total ?? 0 : 0;

	const viewLabel = isAccountSegment
		? Liferay.Language.get('view-accounts')
		: Liferay.Language.get('view-members');

	const previewTooltipProps = criteriaValid
		? {title: viewLabel}
		: {
				'data-tooltip': true,
				'data-tooltip-align': 'bottom',
				title: Liferay.Language.get(
					'some-of-your-criteria-are-incomplete-or-invalid'
				),
			};

	const handlePreviewClick = () =>
		dispatch(
			open(modalTypes.SEARCHABLE_ENTITIES_TABLE_MODAL, {
				...(isAccountSegment && {initialDelta: 20}),
				columns: isAccountSegment
					? [accountsListColumns.getAccountName({channelId, groupId})]
					: [
							{
								...individualsListColumns.name,
								className: 'w-50',
							},
							{
								...individualsListColumns.accountName,
								className: 'w-50',
								label: Liferay.Language.get('account-name'),
							},
						],
				dataSourceFn: (params: Partial<MembersParams>) =>
					fetchMembers({...baseParams, ...params}),
				entityLabel: isAccountSegment
					? Liferay.Language.get('accounts')
					: Liferay.Language.get('individuals'),
				entityType: isAccountSegment ? ACCOUNTS : INDIVIDUALS,
				initialOrderIOMap: createOrderIOMap(
					isAccountSegment ? ACCOUNT_NAME : NAME
				),
				onClose: () => dispatch(close()),
				rowIdentifier: 'id',
				size: 'lg',
				title: isAccountSegment
					? Liferay.Language.get('segment-accounts')
					: Liferay.Language.get('segment-membership'),
			})
		);

	const cancelURL = id
		? toRoute(Routes.CONTACTS_SEGMENT, {channelId, groupId, id})
		: toRoute(Routes.CONTACTS_LIST_SEGMENT, {
				channelId,
				groupId,
				type: SEGMENTS,
			});

	return (
		<BaseEditPage.Toolbar
			backURL={cancelURL}
			title={
				id
					? Liferay.Language.get('edit-segment')
					: Liferay.Language.get('new-segment')
			}
		>
			{isBatch && (
				<>
					<BaseEditPage.Toolbar.Item>
						<IncludeAnonymousToggle />
					</BaseEditPage.Toolbar.Item>

					<BaseEditPage.Toolbar.Item className="pl-0">
						<ClayPopover
							alignPosition="bottom"
							closeOnClickOutside
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('help')}
									borderless
									className="rounded-lg"
									displayType="secondary"
									monospaced
									size="xs"
									symbol="question-circle-full"
								/>
							}
						>
							{Liferay.Language.get(
								'criteria-containing-individual-or-account-attributes-excludes-anonymous-individuals'
							)}
						</ClayPopover>
					</BaseEditPage.Toolbar.Item>

					<BaseEditPage.Toolbar.Divider />

					<BaseEditPage.Toolbar.Item>
						<Text size={3} weight="semi-bold">
							{sub(
								isAccountSegment
									? Liferay.Language.get('total-accounts-x')
									: Liferay.Language.get('total-members-x'),
								[
									loading ? (
										<ClayLoadingIndicator
											className="d-inline-block"
											key="LOADING"
											size="sm"
										/>
									) : (
										<span key="TOTAL_MEMBERS_COUNT">
											{toLocale(membersCount)}
										</span>
									),
								],
								false
							)}
						</Text>
					</BaseEditPage.Toolbar.Item>
				</>
			)}

			<BaseEditPage.Toolbar.Item className={isBatch ? 'pl-0' : undefined}>
				<ClayButtonWithIcon
					{...previewTooltipProps}
					aria-label={viewLabel}
					borderless
					className="rounded-lg"
					data-testid="preview-criteria-button"
					disabled={!criteriaValid || !membersCount}
					displayType="secondary"
					monospaced
					onClick={handlePreviewClick}
					size="sm"
					symbol="view"
				/>
			</BaseEditPage.Toolbar.Item>

			{id && onDeleteSegment && (
				<BaseEditPage.Toolbar.Item>
					<ClayButton
						borderless
						className="rounded-lg"
						displayType="secondary"
						onClick={onDeleteSegment}
						size="sm"
						type="button"
					>
						{Liferay.Language.get('delete-segment')}
					</ClayButton>
				</BaseEditPage.Toolbar.Item>
			)}

			<BaseEditPage.Toolbar.Divider />

			<BaseEditPage.Toolbar.Cancel href={cancelURL} />

			<BaseEditPage.Toolbar.Save
				disabled={!valid}
				label={Liferay.Language.get('save-segment')}
				type="submit"
			/>
		</BaseEditPage.Toolbar>
	);
};

export default Toolbar;
