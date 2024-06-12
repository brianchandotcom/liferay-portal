/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTabs from '@clayui/tabs';
import {useState} from 'react';
import {CSVLink} from 'react-csv';

import './index.css';
import Modal from '../../common/components/Modal';
import Table from '../../common/components/Table';
import TableHeader from '../../common/components/TableHeader';
import DropDownWithDrillDown from '../../common/components/TableHeader/Filter/components/DropDownWithDrillDown';
import DateFilter from '../../common/components/TableHeader/Filter/components/filters/DateFilter';
import Search from '../../common/components/TableHeader/Search';
import {DealRegistrationColumnKey} from '../../common/enums/dealRegistrationColumnKey';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import {SortableTable} from '../../common/enums/sortableTable';
import useDebounce from '../../common/hooks/useDebounce';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import useQueryParams from '../../common/hooks/useQueryParams';
import {DealRegistrationListItem} from '../../common/interfaces/dealRegistrationListItem';
import TableColumn from '../../common/interfaces/tableColumn';
import {Liferay} from '../../common/services/liferay';
import {
	currentFiscalYearStart,
	previousFiscalYearStart,
} from '../../common/utils/constants/filters';
import {maxPagination} from '../../common/utils/constants/maxPagination';
import getDoubleParagraph from '../../common/utils/getDoubleParagraph';
import getDropDownFilterMenus from '../../common/utils/getDropDownFilterMenus';
import ModalContent from './components/ModalContent';
import useFilters from './hooks/useFilters';
import useGetListItemsFromDealRegistration from './hooks/useGetListItemsFromDealRegistration';
import {INITIAL_FILTER} from './utils/constants/initialFilter';
export type DealRegistrationItem = {
	[key in DealRegistrationColumnKey]?: any;
};

const DealRegistrationList = () => {
	const [submittedDealsFilter, setSubmittedDealsFilter] = useState(
		JSON.parse(sessionStorage.getItem('submittedDealsFilter')!) === null
			? true
			: (JSON.parse(
					sessionStorage.getItem('submittedDealsFilter')!
				) as boolean)
	);

	const [dealRegistrationTableSort, setDealRegistrationTableSort] =
		useState<string>('partnerAccountName:asc');

	const debouncedDealRegistrationTableSort = useDebounce(
		dealRegistrationTableSort,
		1000
	);

	const urlParams = useQueryParams();

	const {filters, onFilter} = useFilters(
		debouncedDealRegistrationTableSort,
		urlParams,
		submittedDealsFilter
	);

	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [modalContent, setModalContent] = useState<DealRegistrationItem>({});

	const {observer, onClose} = useModal({
		onClose: () => {
			setIsVisibleModal(false);
		},
	});

	const pagination = usePagination();

	const siteURL = useLiferayNavigate();

	const {data, isValidating} = useGetListItemsFromDealRegistration(
		pagination.activePage,
		pagination.activeDelta,
		urlParams
	);

	const {data: dataCSV} = useGetListItemsFromDealRegistration(
		pagination.activePage,
		maxPagination.MAX_ITEMS_SF.size,
		urlParams
	);

	const actions = usePermissionActions(ObjectActionName.DEAL_REGISTRATION);

	const filteredData = data.items;
	const filteredCSVData = dataCSV.items;

	const columns: TableColumn<DealRegistrationItem>[] = [
		{
			columnKey: DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME,
			label: 'Partner Account Name',
			size: 'md',
		},
		{
			columnKey: DealRegistrationColumnKey.PARTNER_NAME,
			label: 'Partner Name',
			size: 'md',
		},
		{
			columnKey: DealRegistrationColumnKey.ACCOUNT_NAME,
			label: 'Account Name',
			size: 'sm',
		},
		{
			columnKey: DealRegistrationColumnKey.DEAL_DATE_SUBMITTED,
			label: 'Date Submitted',
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME,
			label: getDoubleParagraph('Primary Prospect', 'Name'),
			size: 'sm',
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL,
			label: getDoubleParagraph('Primary Prospect', 'Email'),
			size: 'sm',
		},
		{
			columnKey: DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE,
			label: getDoubleParagraph('Primary Prospect', 'Phone'),
			size: 'sm',
		},
		{
			columnKey: DealRegistrationColumnKey.STATUS,
			label: 'Status',
		},
	];

	const handleCustomClickOnRow = (item: DealRegistrationItem) => {
		setIsVisibleModal(true);
		setModalContent(item);
	};

	const getModal = () => {
		return (
			<Modal observer={observer} size="lg">
				<ModalContent content={modalContent} onClose={onClose} />
			</Modal>
		);
	};

	const getTable = (totalCount: number, items?: DealRegistrationItem[]) => {
		if (items) {
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
			const {totalCount: totalPagination} = data;

			return (
				<div className="mt-3">
					<Table<DealRegistrationListItem>
						className="custom-table"
						columns={columns}
						customClickOnRow={handleCustomClickOnRow}
						rows={items}
						setTableSort={setDealRegistrationTableSort}
						sortable={[
							SortableTable.ACCOUNT_NAME,
							SortableTable.DATE_SUBMITTED,
							SortableTable.PARTNER_ACCOUNT_NAME,
							SortableTable.PARTNER_NAME,
						]}
						tableLayoutAuto
					/>

					<ClayPaginationBarWithBasicItems
						{...pagination}
						totalItems={totalPagination as number}
					/>
				</div>
			);
		}
	};

	const todayDate = new Date();
	const formattedDate = todayDate.toISOString().slice(0, 10);

	const rangeDataPicker = submittedDealsFilter
		? {
				end: formattedDate,
				start: previousFiscalYearStart,
			}
		: {
				end: formattedDate,
				start: currentFiscalYearStart,
			};

	const filterFields = [
		{
			component: (
				<DateFilter
					clearInputs={filters?.dataSubmitted}
					dateFilters={(dates: {
						endDate: string;
						startDate: string;
					}) => {
						onFilter({
							dataSubmitted: {
								dates,
							},
						});
					}}
					filterDescription="Date Submitted "
					initialDates={filters.dataSubmitted?.dates}
					years={rangeDataPicker}
				/>
			),
			name: 'Date Submitted',
		},
	];

	return (
		<div className="border-0 my-4">
			<div className="align-items-center d-md-flex justify-content-between mb-3 mr-4">
				<h1>Partner Deal Registration</h1>
				<ClayTabs className="h-100 nav nav-segment nav-tabs">
					<ClayTabs.Item
						active={submittedDealsFilter}
						className="nav-item"
						onClick={() => setSubmittedDealsFilter(true)}
					>
						Submitted
					</ClayTabs.Item>
					<ClayTabs.Item
						active={!submittedDealsFilter}
						className="nav-item"
						onClick={() => setSubmittedDealsFilter(false)}
					>
						Rejected
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
								!!filteredData?.length &&
								!isValidating && (
									<div>
										<p className="font-weight-semi-bold m-0 ml-1 mt-3 text-paragraph-sm">
											{filteredData?.length > 1
												? `${filteredData?.length} results for ${filters.searchTerm}`
												: `${filteredData?.length} result for ${filters.searchTerm}`}
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
						menus={getDropDownFilterMenus(filterFields)}
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

				<div>
					{!!filteredCSVData?.length &&
						actions?.includes(PermissionActionType.EXPORT) && (
							<CSVLink
								className="btn btn-secondary mb-2 mb-lg-0 mr-2"
								data={filteredCSVData}
								filename="Partner Deal Registration.csv"
							>
								Export Deal Registrations
							</CSVLink>
						)}

					{actions?.includes(PermissionActionType.CREATE) && (
						<ClayButton
							className="mb-2 mb-lg-0 mr-2"
							onClick={() =>
								Liferay.Util.navigate(
									`${siteURL}/${PRMPageRoute.CREATE_DEAL_REGISTRATION}`
								)
							}
						>
							Register New Deal
						</ClayButton>
					)}
				</div>
			</TableHeader>

			{isVisibleModal && getModal()}

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating && getTable(filteredData?.length || 0, filteredData)}
		</div>
	);
};
export default DealRegistrationList;
