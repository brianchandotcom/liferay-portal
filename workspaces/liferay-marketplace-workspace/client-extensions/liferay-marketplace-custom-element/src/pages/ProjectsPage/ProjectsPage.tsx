import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import {CreateProjectModal} from '../../components/CreateProjectModal/CreateProjectModal';
import {ProjectDetailsCard} from '../../components/CreateProjectModal/ProjectDetailsCard';
import {
	DashboardTable,
	TableHeaders,
} from '../../components/DashboardTable/DashboardTable';
import {getChannels, getPlacedOrders} from '../../utils/api';
import {DashboardListItems} from '../DashBoardPage/DashboardPage';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {NextStepPage} from '../NextStepPage/NextStepPage';
import {ProjectsTableRow} from './ProjectsTableRow';

import './ProjectsPage.scss';

interface ProjectsPageProps {
	dashboardNavigationItems: DashboardListItems[];
	icon: string;
	selectedAccount: Account;
	setShowDashboardNavigation: (value: boolean) => void;
}

const projectsTableHeaders: TableHeaders = [
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

export function ProjectsPage({
	dashboardNavigationItems,
	icon,
	selectedAccount,
	setShowDashboardNavigation,
}: ProjectsPageProps) {
	const [visible, setVisible] = useState(false);
	const [showNextStepsPage, setShowNextStepsPage] = useState(false);
	const [currentChannel, setCurrentChannel] = useState<Channel>();
	const [projectOrders, setProjectOrders] = useState<PlacedOrder[]>([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			const channels = await getChannels();

			const marketplaceChannel =
				channels.find(
					(channel) => channel.name === 'Marketplace Channel'
				) ?? channels[0];

			const {items} = await getPlacedOrders(
				selectedAccount.id,
				marketplaceChannel.id
			);

			const filteredOrders = items.filter(
				({orderTypeExternalReferenceCode}) =>
					orderTypeExternalReferenceCode === 'PROJECT60'
			);

			setCurrentChannel(marketplaceChannel);
			setProjectOrders(filteredOrders);

			setLoading(false);
		};

		makeFetch();
	}, [selectedAccount, showNextStepsPage]);

	return (
		<>
			{!showNextStepsPage ? (
				<>
					{!loading ? (
						<DashboardPage
							buttonHref="#"
							buttonMessage="+ New Project"
							dashboardNavigationItems={dashboardNavigationItems}
							messages={{
								description:
									'Manage projects to build and test your apps and solutions',
								title: 'Projects',
							}}
							onButtonClick={() => setVisible(true)}
						>
							<DashboardTable<PlacedOrder>
								emptyStateMessage={{
									description1:
										'Publish projects and they will show up here.',
									description2:
										'Click on “New Projects” to start.',
									title: 'No projects yet',
								}}
								icon={icon}
								items={projectOrders}
								tableHeaders={projectsTableHeaders}
							>
								{(projectOrder) => {
									const date = new Date(
										projectOrder.createDate
									);
									const options: Intl.DateTimeFormatOptions =
										{
											year: 'numeric',
											month: 'short',
											day: 'numeric',
										};
									const formattedCreateDate =
										date.toLocaleDateString(
											'en-US',
											options
										);

									date.setDate(date.getDate() + 60);
									const formattedEndDate =
										date.toLocaleDateString(
											'en-US',
											options
										);

									return (
										<ProjectsTableRow
											author={projectOrder.author}
											createdAt={formattedCreateDate}
											endDate={formattedEndDate}
											projectName={
												projectOrder.customFields[
													'Project Name'
												]
											}
											status={
												projectOrder.orderStatusInfo
													.label_i18n
											}
										/>
									);
								}}
							</DashboardTable>
						</DashboardPage>
					) : (
						<ClayLoadingIndicator
							className="projects-page-loading-indicator"
							displayType="primary"
							shape="circle"
							size="md"
						/>
					)}

					{visible && (
						<CreateProjectModal
							currentChannel={currentChannel as Channel}
							handleClose={() => setVisible(false)}
							selectedAccount={selectedAccount}
							setShowDashboardNavigation={
								setShowDashboardNavigation
							}
							setShowNextStepsPage={setShowNextStepsPage}
						/>
					)}
				</>
			) : (
				<NextStepPage
					continueButtonText="Go to Dashboard"
					header={{
						description:
							'Solutions in progress project has been created and is now being processed. You will get an email notification when the trial is ready.',
						title: 'Next steps',
					}}
					linkText="Learn more about Projects"
					onClickContinue={() => {
						setShowDashboardNavigation(true);
						setShowNextStepsPage(false);
					}}
					showBackButton={false}
					showOrderId={false}
					size="lg"
				>
					<ProjectDetailsCard showHeader />
				</NextStepPage>
			)}
		</>
	);
}
