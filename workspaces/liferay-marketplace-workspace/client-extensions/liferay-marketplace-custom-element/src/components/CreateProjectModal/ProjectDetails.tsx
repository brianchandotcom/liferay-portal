/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Input} from '../Input/Input';
import {ProjectDetailsCard} from './ProjectDetailsCard';

import './ProjectDetails.scss';

interface ProjectDetailsProps {
	githubUsername?: string;
	onGithubUsernameChange?: (value: string) => void;
	onProjectNameChange?: (value: string) => void;
	projectName?: string;
	showInputs?: boolean;
}

export function ProjectDetails({
	githubUsername,
	onGithubUsernameChange,
	onProjectNameChange,
	projectName,
	showInputs = true,
}: ProjectDetailsProps) {
	return (
		<>
			{showInputs && (
				<div className="create-project-modal-inputs-container">
					<Input
						label="Project name"
						onChange={(event) =>
							onProjectNameChange &&
							onProjectNameChange(event.target.value)
						}
						placeholder="Type your environment name"
						required
						value={projectName}
					/>

					<Input
						label="Github username"
						onChange={(event) =>
							onGithubUsernameChange &&
							onGithubUsernameChange(event.target.value)
						}
						placeholder="Type your github username"
						required
						value={githubUsername}
					/>
				</div>
			)}

			<ProjectDetailsCard />
		</>
	);
}
