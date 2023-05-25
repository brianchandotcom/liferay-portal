/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
