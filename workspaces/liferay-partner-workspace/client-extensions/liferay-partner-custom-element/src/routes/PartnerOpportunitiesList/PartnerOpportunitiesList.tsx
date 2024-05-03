/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
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
import Search from '../../common/components/TableHeader/Search';
import {PartnerOpportunitiesColumnKey} from '../../common/enums/partnerOpportunitiesColumnKey';
import {SortableTable} from '../../common/enums/sortableTable';
import useDebounce from '../../common/hooks/useDebounce';
import usePagination from '../../common/hooks/usePagination';
import useQueryParams from '../../common/hooks/useQueryParams';
import getDoubleParagraph from '../../common/utils/getDoubleParagraph';
import setURLParams from '../../common/utils/setURLParams';
import ModalContent from './components/ModalContent';
import useFilters from './hooks/useFilters';
import useGetListItemsFromPartnerOpportunities from './hooks/useGetListItemsFromPartnerOpportunities';
import PartnerOpportunitiesItem from './interfaces/partnerOpportunitiesItem';

interface IProps {
	isRenewalListing?: boolean;
	name: string;
}

const PartnerOpportunitiesList = ({isRenewalListing, name}: IProps) => {
	const [openOpportunitiesFilter, setOpenOpportunitiesFilter] = useState(
		JSON.parse(sessionStorage.getItem('openOpportunitiesFilter')!) === null
			? true
			: (JSON.parse(
					sessionStorage.getItem('openOpportunitiesFilter')!
			  ) as boolean)
	);

	const {filters, filtersTerm, onFilter} = useFilters(
		openOpportunitiesFilter,
		isRenewalListing
	);
	const [isVisibleModal, setIsVisibleModal] = useState(false);
	const [modalContent, setModalContent] = useState<
		PartnerOpportunitiesItem
	>();
	const {observer, onClose} = useModal({
		onClose: () => {
			setIsVisibleModal(false);
			setModalContent(undefined);
		},
	});

	const pagination = usePagination();

	const urlParams = useQueryParams();

	const [opportunitiesTableSort, setOpportunitiesTableSort] = useState<
		string
	>('partnerAccountName:asc');

	const debouncedDealRegistrationTableSort = useDebounce(
		opportunitiesTableSort,
		1000
	);

	const {data, isValidating} = useGetListItemsFromPartnerOpportunities(
		pagination.activePage,
		pagination.activeDelta,
		setURLParams({
			filter: filtersTerm,
			sort: debouncedDealRegistrationTableSort,
			urlParams,
		})
	);

	const {data: dataCSV} = useGetListItemsFromPartnerOpportunities(
		pagination.activePage,
		pagination.maxItemsSF,
		setURLParams({filter: filtersTerm, urlParams})
	);

	const {totalCount: totalPagination} = data;
	const filteredData = data.items;
	const filteredCSVData = dataCSV.items;

	const columns = [
		{
			columnKey: PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME,
			label: 'Partner Account Name',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.OPPORTUNITY_ACCOUNT_NAME,
			label: 'Account Name',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.SUBSCRIPTION_ARR,
			label: 'Subscription ARR',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.STAGE,
			label: 'Stage',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.CLOSE_DATE,
			label: 'Close Date',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.SUBSCRIPTION_TERM,
			label: 'Subscription Term',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.PARTNER_REP_NAME,
			label: getDoubleParagraph('Partner Rep', 'Name'),
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL,
			label: getDoubleParagraph('Partner Rep', 'Email'),
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.LIFERAY_REP,
			label: 'Liferay Rep',
		},
	];

	const handleCustomClickOnRow = async (row: PartnerOpportunitiesItem) => {
		setIsVisibleModal(true);
		setModalContent(row);
	};

	const getModal = () => {
		return (
			<Modal observer={observer} size="lg">
				<ModalContent content={modalContent} onClose={onClose} />
			</Modal>
		);
	};

	const getTable = (
		totalCount: number,
		items?: PartnerOpportunitiesItem[]
	) => {
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

			return (
				<div className="mt-3">
					<Table<PartnerOpportunitiesItem>
						className="custom-table"
						columns={columns}
						customClickOnRow={handleCustomClickOnRow}
						rows={items}
						setTableSort={setOpportunitiesTableSort}
						sortable={[
							SortableTable.ACCOUNT_NAME,
							SortableTable.CLOSE_DATE,
							SortableTable.PARTNER_ACCOUNT_NAME,
							SortableTable.STAGE,
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

	return (
		<div className="border-0 my-4">
			<div className="align-items-center d-md-flex justify-content-between mb-3 mr-4">
				<h1>{name}</h1>
				<ClayTabs className="h-100 nav nav-segment nav-tabs">
					<ClayTabs.Item
						active={openOpportunitiesFilter}
						className="nav-item"
						onClick={() => setOpenOpportunitiesFilter(true)}
					>
						Open
					</ClayTabs.Item>
					<ClayTabs.Item
						active={!openOpportunitiesFilter}
						className="nav-item"
						onClick={() => setOpenOpportunitiesFilter(false)}
					>
						Closed
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
						</div>
					</div>
				</div>

				<div>
					{!!filteredCSVData?.length && (
						<CSVLink
							className="btn btn-secondary mb-2 mb-lg-0 mr-2"
							data={filteredCSVData}
							filename={`${name}.csv`}
						>
							Export {name}
						</CSVLink>
					)}
				</div>
			</TableHeader>

			{isVisibleModal && getModal()}

			{isValidating && <ClayLoadingIndicator />}

			{!isValidating && getTable(filteredData?.length || 0, filteredData)}
		</div>
	);
};
export default PartnerOpportunitiesList;
