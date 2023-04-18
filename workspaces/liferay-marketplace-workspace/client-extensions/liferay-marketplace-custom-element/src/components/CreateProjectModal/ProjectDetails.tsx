import calendarIcon from '../../assets/icons/calendar_month.svg';
import githubIcon from '../../assets/icons/github-icon.svg';
import guideIcon from '../../assets/icons/guide-icon.svg';
import listIcon from '../../assets/icons/list_alt.svg';
import serverIcon from '../../assets/icons/serverIcon.svg';
import {Input} from '../Input/Input';

import './ProjectDetails.scss';

const projectDetailsCardValues = [
	{
		icon: guideIcon,
		title: 'Sites',
		description: '1 Site',
	},
	{
		icon: serverIcon,
		title: 'Storage',
		description: '10 GB',
	},
	{
		icon: listIcon,
		title: 'Extensions Environment',
		description: 'Yes',
	},
	{
		icon: githubIcon,
		title: 'Github Access',
		description: 'Yes',
	},
	{
		icon: calendarIcon,
		title: 'Duration',
		description: '60 days',
	},
];

export function ProjectDetails() {
	return (
		<>
			<div className="create-project-modal-inputs-contianer">
				<Input
					label="Project name"
					placeholder="Type your environment name"
					required
				/>

				<Input
					label="Github username"
					placeholder="Type your github username"
					required
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
