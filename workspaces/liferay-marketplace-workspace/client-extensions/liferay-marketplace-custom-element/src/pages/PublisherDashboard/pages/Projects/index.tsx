/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {CreateProjectModal} from '../../../../components/CreateProjectModal/CreateProjectModal';
import {ProjectDetailsCard} from '../../../../components/CreateProjectModal/ProjectDetailsCard';
import {DashboardTable} from '../../../../components/DashboardTable/DashboardTable';
import Page from '../../../../components/Page';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import HeadlessCommerceDeliveryOrder from '../../../../services/rest/HeadlessCommerceDeliveryOrder';
import {NextSteps} from '../../../NextSteps';
import {ProjectsTableRow} from './ProjectsTableRow';

const projectsTableHeaders = [
	{
		title: 'Project Name',
	},
	{
		title: 'Created By',
	},
	{
		title: 'Type',
	},
	{
		title: 'End Date',
	},
	{
		title: 'Provisioning',
	},
	{
		title: 'Project',
	},
];

const options: Intl.DateTimeFormatOptions = {
	day: 'numeric',
	month: 'short',
	year: 'numeric',
};

export default function ProjectsPage() {
	const [showNextStepsPage, setShowNextStepsPage] = useState(false);
	const [visible, setVisible] = useState(false);
	const {channel} = useMarketplaceContext();
	const {selectedAccount} = useOutletContext<any>();

	const {
		data: placedOrderResponse,
		error,
		isLoading,
	} = useSWR('/publisher-dashboard/projects', () =>
		HeadlessCommerceDeliveryOrder.getPlacedOrders(
			channel.id,
			selectedAccount?.id,
			new URLSearchParams({nestedFields: 'placedOrderItems'})
		)
	);

	const {items = []} = placedOrderResponse ?? {};

	const projectOrders = items.filter(
		({orderTypeExternalReferenceCode}: any) =>
			orderTypeExternalReferenceCode === 'PROJECT60'
	);

	if (showNextStepsPage) {
		return (
			<NextSteps
				continueButtonText="Go to Dashboard"
				header={{
					description:
						'Solutions in progress project has been created and is now being processed. You will get an email notification when the trial is ready.',
					title: 'Next steps',
				}}
				linkText="Learn more about Projects"
				onClickContinue={() => {
					setShowNextStepsPage(false);
				}}
				showBackButton={false}
				showOrderId={false}
				size="lg"
			>
				<ProjectDetailsCard showHeader />
			</NextSteps>
		);
	}

	return (
		<Page
			description="Manage projects to build and test your apps and solutions"
			pageRendererProps={{error, isLoading}}
			rightButton={
				<ClayButton onClick={() => setVisible(true)}>
					New Project
				</ClayButton>
			}
			title="Projects"
		>
			<DashboardTable<PlacedOrder>
				emptyStateMessage={{
					description1:
						'Publish projects and they will show up here.',
					description2: 'Click on “New Projects” to start.',
					title: 'No projects yet',
				}}
				icon="sheets"
				items={projectOrders || []}
				tableHeaders={projectsTableHeaders}
			>
				{(projectOrder) => {
					const date = new Date(projectOrder.createDate);

					date.setDate(date.getDate() + 60);

					const formattedCreateDate = date.toLocaleDateString(
						'en-US',
						options
					);

					const formattedEndDate = date.toLocaleDateString(
						'en-US',
						options
					);

					return (
						<ProjectsTableRow
							author={projectOrder.author}
							createdAt={formattedCreateDate}
							endDate={formattedEndDate}
							projectName={
								projectOrder.customFields['Project Name']
							}
							status={projectOrder.orderStatusInfo.label}
						/>
					);
				}}
			</DashboardTable>

			<>
				{visible && (
					<CreateProjectModal
						currentChannel={channel}
						handleClose={() => setVisible(false)}
						selectedAccount={selectedAccount}
						setShowNextStepsPage={setShowNextStepsPage}
					/>
				)}
			</>
		</Page>
	);
}
