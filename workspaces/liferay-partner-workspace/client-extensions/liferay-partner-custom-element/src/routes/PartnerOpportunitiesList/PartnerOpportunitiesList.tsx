/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTabs from '@clayui/tabs';
import {useState} from 'react';
import {CSVLink} from 'react-csv';

import Modal from '../../common/components/Modal';
import Table from '../../common/components/Table';
import TableHeader from '../../common/components/TableHeader';
import Search from '../../common/components/TableHeader/Search';
import {PartnerOpportunitiesColumnKey} from '../../common/enums/partnerOpportunitiesColumnKey';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePagination from '../../common/hooks/usePagination';
import {Liferay} from '../../common/services/liferay';
import getDoubleParagraph from '../../common/utils/getDoubleParagraph';
import ModalContent from './components/ModalContent';
import useFilters from './hooks/useFilters';
import useGetListItemsFromPartnerOpportunities from './hooks/useGetListItemsFromPartnerOpportunities';
import PartnerOpportunitiesItem from './interfaces/partnerOpportunitiesItem';
import getItemPartnerOpportunity from './utils/getItemPartnerOpportunity';
import getOpportunityByErc from './utils/getOpportunityByErc';

interface IProps {
	closedOpportunitiesFilter: string;
	getFilteredItems: (
		items: PartnerOpportunitiesItem[],
		opportunitiesFilter: string
	) => PartnerOpportunitiesItem[];
	name: string;
	newButtonDeal?: boolean;
	openOpportunitiesFilter: string;
	sort: string;
}

const BASE_PAGE = 1;
const MAX_ITEMS = 200;

const PartnerOpportunitiesList = ({
	closedOpportunitiesFilter,
	getFilteredItems,
	name,
	newButtonDeal,
	openOpportunitiesFilter,
	sort,
}: IProps) => {
	const [opportunitiesFilter, setOpportunitiesFilter] = useState(
		openOpportunitiesFilter
	);

	const {filters, filtersTerm, onFilter} = useFilters(opportunitiesFilter);
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
	const {data, isValidating} = useGetListItemsFromPartnerOpportunities(
		pagination.activePage,
		pagination.activeDelta,
		filtersTerm,
		sort
	);

	const {data: dataCSV} = useGetListItemsFromPartnerOpportunities(
		BASE_PAGE,
		MAX_ITEMS,
		filtersTerm,
		sort
	);

	const {totalCount: totalPagination} = data;
	const filteredData =
		data.items && getFilteredItems(data.items, opportunitiesFilter);
	const filteredCSVData =
		dataCSV.items && getFilteredItems(dataCSV.items, opportunitiesFilter);

	const siteURL = useLiferayNavigate();
	const columns = [
		{
			columnKey: PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME,
			label: 'Partner Account Name',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.ACCOUNT_NAME,
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
			columnKey: PartnerOpportunitiesColumnKey.START_DATE,
			label: 'Start Date',
		},
		{
			columnKey: PartnerOpportunitiesColumnKey.END_DATE,
			label: 'End Date',
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
		const opportunityERC = String(row['OPPORTUNITY']);

		if (opportunityERC) {
			const opportunity = await getOpportunityByErc(opportunityERC);
			const rowModal = getItemPartnerOpportunity(opportunity);
			setModalContent(rowModal);
		}
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
						columns={columns}
						customClickOnRow={handleCustomClickOnRow}
						rows={items}
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
						active={opportunitiesFilter === openOpportunitiesFilter}
						className="nav-item"
						onClick={() =>
							setOpportunitiesFilter(openOpportunitiesFilter)
						}
					>
						Open
					</ClayTabs.Item>
					<ClayTabs.Item
						active={
							opportunitiesFilter === closedOpportunitiesFilter
						}
						className="nav-item"
						onClick={() =>
							setOpportunitiesFilter(closedOpportunitiesFilter)
						}
					>
						Closed
					</ClayTabs.Item>
				</ClayTabs>
			</div>

			<TableHeader>
				<div className="d-flex">
					<div>
						<Search
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

					{newButtonDeal && (
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
export default PartnerOpportunitiesList;
