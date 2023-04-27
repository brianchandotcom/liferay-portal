import {useState} from 'react';

import {CreateProjectModal} from '../../components/CreateProjectModal/CreateProjectModal';
import {ProjectDetailsCard} from '../../components/CreateProjectModal/ProjectDetailsCard';
import {
	DashboardTable,
	TableHeaders,
} from '../../components/DashboardTable/DashboardTable';
import {DashboardListItems} from '../DashBoardPage/DashboardPage';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {NextStepPage} from '../NextStepPage/NextStepPage';
import {ProjectsTableRow} from './ProjectsTableRow';

interface ProjectsPageProps {
	dashboardNavigationItems: DashboardListItems[];
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
	selectedAccount,
	setShowDashboardNavigation,
}: ProjectsPageProps) {
	const [visible, setVisible] = useState(false);
	const [showNextStepsPage, setShowNextStepsPage] = useState(false);

	return (
		<>
			{!showNextStepsPage ? (
				<>
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
						<DashboardTable
							emptyStateMessage={{
								description1:
									'Publish projects and they will show up here.',
								description2:
									'Click on “New Projects” to start.',
								title: 'No projects yet',
							}}
							items={[0, 2]}
							tableHeaders={projectsTableHeaders}
						>
							{() => <ProjectsTableRow />}
						</DashboardTable>
					</DashboardPage>

					{visible && (
						<CreateProjectModal
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
