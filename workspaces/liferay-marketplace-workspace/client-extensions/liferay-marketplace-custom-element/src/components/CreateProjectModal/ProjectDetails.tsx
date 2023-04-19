import calendarIcon from '../../assets/icons/calendar_month.svg';
import githubIcon from '../../assets/icons/github-icon.svg';
import guideIcon from '../../assets/icons/guide-icon.svg';
import listIcon from '../../assets/icons/list_alt.svg';
import serverIcon from '../../assets/icons/serverIcon.svg';
import {Input} from '../Input/Input';

import './ProjectDetails.scss';

interface ProjectDetailsProps {
	githubUsername: string;
	onGithubUsernameChange: (value: string) => void;
	onProjectNameChange: (value: string) => void;
	projectName: string;
}

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

export function ProjectDetails({
	githubUsername,
	onGithubUsernameChange,
	onProjectNameChange,
	projectName,
}: ProjectDetailsProps) {
	return (
		<>
			<div className="create-project-modal-inputs-contianer">
				<Input
					label="Project name"
					onChange={(e) => onProjectNameChange(e.target.value)}
					placeholder="Type your environment name"
					required
					value={projectName}
				/>

				<Input
					label="Github username"
					onChange={(e) => onGithubUsernameChange(e.target.value)}
					placeholder="Type your github username"
					required
					value={githubUsername}
				/>
			</div>

			<div className="create-project-modal-project-details-card">
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
		</>
	);
}
