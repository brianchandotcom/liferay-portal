import calendarIcon from '../../assets/icons/calendar_month.svg';
import githubIcon from '../../assets/icons/github-icon.svg';
import guideIcon from '../../assets/icons/guide-icon.svg';
import liferayIcon from '../../assets/icons/liferay-icon.svg';
import listIcon from '../../assets/icons/list_alt.svg';
import serverIcon from '../../assets/icons/serverIcon.svg';

const projectDetailsCardValues = [
	{
		description: '1 Site',
		icon: guideIcon,
		title: 'Sites',
	},
	{
		description: '10 GB',
		icon: serverIcon,
		title: 'Storage',
	},
	{
		description: 'Yes',
		icon: listIcon,
		title: 'Extensions Environment',
	},
	{
		description: 'Yes',
		icon: githubIcon,
		title: 'Github Access',
	},
	{
		description: '60 days',
		icon: calendarIcon,
		title: 'Duration',
	},
];

interface ProjectDetailsCardProps {
	showHeader?: boolean;
}

export function ProjectDetailsCard({
	showHeader = false,
}: ProjectDetailsCardProps) {
	return (
		<div className="create-project-modal-project-details-card">
			{showHeader && (
				<div className="create-project-modal-project-details-card-header">
					<img
						alt="Liferay Icon"
						className="create-project-modal-project-details-card-header-icon"
						src={liferayIcon}
					/>

					<div className="create-project-modal-project-details-card-header-text-container">
						<span className="create-project-modal-project-details-card-header-title">
							Solutions in progress
						</span>

						<span className="create-project-modal-project-details-card-header-dscription">
							Created by Gloria Davis (you)
						</span>
					</div>
				</div>
			)}

			<span className="create-project-modal-project-details-card-title">
				Project details
			</span>

			<div className="create-project-modal-project-details-card-info-block-container">
				{projectDetailsCardValues.map((cardValues) => (
					<div className="create-project-modal-project-details-card-info-block">
						<div className="create-project-modal-project-details-card-info-block-icon-container">
							<img src={cardValues.icon} />
						</div>

						<div className="create-project-modal-project-details-card-info-block-text-container">
							<span className="create-project-modal-project-details-card-info-block-text-title">
								{cardValues.title}
							</span>

							<span className="create-project-modal-project-details-card-info-block-text-description">
								{cardValues.description}
							</span>
						</div>
					</div>
				))}
			</div>
		</div>
	);
}
