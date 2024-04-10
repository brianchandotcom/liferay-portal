/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTabs from '@clayui/tabs';
import {useMemo, useState} from 'react';
import {CSVLink} from 'react-csv';

import Table from '../../common/components/Table';
import CheckboxFilter from '../../common/components/TableHeader/Filter/components/CheckboxFilter';
import DropDownWithDrillDown from '../../common/components/TableHeader/Filter/components/DropDownWithDrillDown';
import DateFilter from '../../common/components/TableHeader/Filter/components/filters/DateFilter/DateFilter';
import Search from '../../common/components/TableHeader/Search/Search';
import TableHeader from '../../common/components/TableHeader/TableHeader';
import {MDFColumnKey} from '../../common/enums/mdfColumnKey';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useIsChannel from '../../common/hooks/useIsChannel';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import useQueryParams from '../../common/hooks/useQueryParams';
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import {MDFRequestListItem} from '../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../common/interfaces/tableColumn';
import {Liferay} from '../../common/services/liferay';
import {LiferayAPIs} from '../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../common/services/liferay/object/useGet';
import {Filters} from '../../common/utils/constants/filters';
import getDropDownFilterMenus from '../../common/utils/getDropDownFilterMenus';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';
import useFilters from './hooks/useFilters';
import useGetListItemsFromMDFRequests from './hooks/useGetListItemsFromMDFRequests';
import {INITIAL_FILTER} from './utils/constants/initialFilter';
import getMDFListColumns from './utils/getMDFListColumns';

type MDFRequestItem = {
	[key in MDFColumnKey]?: any;
};

const MDFRequestList = () => {
	const {isChannel} = useIsChannel();
	const urlParams = useQueryParams();
	const [openRequestFilter, setOpenRequestFilter] = useState(
		!urlParams.get('tab') || urlParams.get('tab') === 'open' ? true : false
	);

	const {userAccount} = useDynamicFieldEntries();
	const actions = usePermissionActions(ObjectActionName.MDF_REQUEST);

	const {filters, filtersTerm, onFilter, setFilters} = useFilters(
		openRequestFilter,
		urlParams,
		isChannel
	);
	const pagination = usePagination(urlParams);

	const {data, isValidating, mutate} = useGet<LiferayItems<MDFRequestDTO[]>>(
		filtersTerm &&
			`/o/${LiferayAPIs.OBJECT}/mdfrequests?nestedFields=mdfReqToMDFClms&filter=${filtersTerm}&page=${pagination.activePage}&pageSize=${pagination.activeDelta}&sort=dateCreated:desc`
	);

	const {data: dataCSV} = useGet<LiferayItems<MDFRequestDTO[]>>(
		filtersTerm &&
			`/o/${
				LiferayAPIs.OBJECT
			}/mdfrequests?nestedFields=mdfReqToMDFClms&filter=${filtersTerm}&page=${1}&pageSize=${-1}&sort=dateCreated:desc`
	);

	const mdfRequestItemsCSV = dataCSV?.items;
	const mdfRequestListItemsCSV =
		useGetListItemsFromMDFRequests(mdfRequestItemsCSV) || [];

	const mdfRequestItems = data?.items;
	const mdfRequestListItems =
		useGetListItemsFromMDFRequests(mdfRequestItems) || [];

	const companiesEntries:
		| React.OptionHTMLAttributes<HTMLOptionElement>[]
		| undefined = useMemo(
		() =>
			userAccount?.accountBriefs.map((accountBrief) => ({
				label: accountBrief.name,
				value: accountBrief.id,
			})),
		[userAccount?.accountBriefs]
	);

	const siteURL = useLiferayNavigate();
	const columns = getMDFListColumns(
		(index) =>
			userAccount?.accountBriefs.some(
				(accountBrief) =>
					accountBrief.id ===
					mdfRequestItems?.[index].r_accToMDFReqs_accountEntryId
			),
		siteURL,
		urlParams,
		actions,
		mutate,
		isChannel
	);

	const getTable = (
		totalCount?: number,
		items?: MDFRequestItem[],
		columns?: TableColumn<MDFRequestListItem>[]
	) => {
		if (items && columns) {
			if (!totalCount) {
				return (
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-50"
							displayType="info"
							title="Info:"
						>
							No entries were found
						</ClayAlert>
					</div>
				);
			}

			return (
				<div className="mt-3">
					<Table<MDFRequestListItem>
						columns={columns}
						rows={items}
						tableLayoutAuto
					/>

					<ClayPaginationBarWithBasicItems
						{...pagination}
						totalItems={totalCount}
					/>
				</div>
			);
		}
	};
	const getFilters = () => {
		const filterFields = [
			{
				component: (
					<DateFilter
						dateFilters={(dates: {
							endDate: string;
							startDate: string;
						}) => {
							onFilter({
								activityPeriod: {
									dates,
								},
							});
						}}
						filterDescription="Activity Date "
						initialDates={filters.activityPeriod?.dates}
					/>
				),
				name: 'Activity Period',
			},
			{
				component: (
					<CheckboxFilter
						availableItems={
							openRequestFilter
								? Filters.MDF_REQUEST_LISTING.openList
								: Filters.MDF_REQUEST_LISTING.completedList
						}
						clearCheckboxes={!filters.status.value?.length}
						initialCheckedItems={filters.status.value}
						updateFilters={(checkedItems) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								status: {
									...previousFilters.status,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				name: 'Status',
			},
		];

		if (actions?.includes(PermissionActionType.SEE_RESTRICTED_FIELDS)) {
			filterFields.push({
				component: (
					<CheckboxFilter
						availableItems={companiesEntries?.map<string>(
							(company) => company.label as string
						)}
						clearCheckboxes={!filters.partner.value?.length}
						initialCheckedItems={filters.partner.value}
						updateFilters={(checkedItems) =>
							setFilters((previousFilters) => ({
								...previousFilters,
								partner: {
									...previousFilters.status,
									value: checkedItems,
								},
							}))
						}
					/>
				),
				name: 'Partner',
			});
		}

		return filterFields;
	};

	return (
		<div className="border-0 my-4">
			<div className="align-items-center d-md-flex justify-content-between mb-3 mr-4">
				<h1>MDF Requests</h1>
				<ClayTabs className="h-100 nav nav-segment nav-tabs">
					<ClayTabs.Item
						active={openRequestFilter}
						className="nav-item"
						onClick={() => {
							setOpenRequestFilter(true);
							urlParams.set('tab', 'open');
						}}
					>
						Open
					</ClayTabs.Item>
					<ClayTabs.Item
						active={!openRequestFilter}
						className="nav-item"
						onClick={() => {
							setOpenRequestFilter(false);
							urlParams.set('tab', 'completed');
						}}
					>
						Completed
					</ClayTabs.Item>
				</ClayTabs>
			</div>

			<TableHeader>
				<div className="d-flex">
					<div>
						<Search
							initialSearchTerm={filters.searchTerm}
							onSearchSubmit={(searchTerm: string) =>
								onFilter({
									searchTerm,
								})
							}
							urlParams={urlParams}
						/>

						<div className="bd-highlight flex-shrink-2 mt-1">
							{!!filters.searchTerm &&
								!!mdfRequestItems?.length &&
								!isValidating && (
									<div>
										<p className="font-weight-semi-bold m-0 ml-1 mt-3 text-paragraph-sm">
											{mdfRequestItems?.length > 1
												? `${mdfRequestItems?.length} results for ${filters.searchTerm}`
												: `${mdfRequestItems?.length} result for ${filters.searchTerm}`}
										</p>
									</div>
								)}

							{filters.hasValue && (
								<ClayButton
									borderless
									className="link"
									onClick={() => {
										onFilter({
											...INITIAL_FILTER,
											searchTerm: filters.searchTerm,
										});
									}}
									small
								>
									<ClayIcon
										className="ml-n2 mr-1"
										symbol="times-circle"
									/>
									Clear All Filters
								</ClayButton>
							)}
						</div>
					</div>

					<DropDownWithDrillDown
						className=""
						initialActiveMenu="x0a0"
						menus={getDropDownFilterMenus(getFilters())}
						trigger={
							<ClayButton borderless className="btn-secondary">
								<span className="inline-item inline-item-before">
									<ClayIcon symbol="filter" />
								</span>
								Filter
							</ClayButton>
						}
					/>
				</div>

				<div className="mb-2 mb-lg-0">
					{!!mdfRequestItems?.length &&
						actions?.includes(PermissionActionType.EXPORT) && (
							<CSVLink
								className="btn btn-secondary mr-2"
								data={mdfRequestListItemsCSV}
								filename="MDF Requests.csv"
							>
								Export MDF Report
							</CSVLink>
						)}

					{actions?.includes(PermissionActionType.CREATE) && (
						<ClayButton
							className="mr-2 mr-md-2"
							onClick={() =>
								Liferay.Util.navigate(
									`${siteURL}/${PRMPageRoute.CREATE_MDF_REQUEST}`
								)
							}
						>
							New Request
						</ClayButton>
					)}
				</div>
			</TableHeader>

			{!isValidating &&
				getTable(data?.totalCount, mdfRequestListItems, columns)}

			{isValidating && <ClayLoadingIndicator />}
		</div>
	);
};
export default MDFRequestList;
