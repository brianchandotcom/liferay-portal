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

import ClayButton from '@clayui/button';

import githubIcon from '../../assets/icons/github_icon.svg';
import linkIcon from '../../assets/icons/link_icon.svg';
import AutoComplete from '../AutoComplete';

import './GithubCard.scss';

interface GithubCard {
	user: string;
}

export function GithubCard({user}: GithubCard) {
	return (
		<div className="github-card-container">
			<div className="github-card-header">
				<div className="github-card-header-title">
					<div className="github-card-header-circle">
						<img
							className="github-card-header-icon-github"
							src={githubIcon}
						/>
					</div>

					<img
						className="github-card-header-icon-link"
						src={linkIcon}
					/>

					<span>
						Connected to <b>{user}</b> account
					</span>
				</div>

				<div className="github-card-header-button">
					<ClayButton displayType="secondary" size="sm">
						<span>Remove</span>
					</ClayButton>
				</div>
			</div>

			<div>
				<hr className="github-card-divider"></hr>
			</div>

			<div className="github-card-content">
				<AutoComplete
					emptyStateMessage="Not found"
					items={[]}
					label="Repo"
					onChangeQuery={() => {}}
					onSelectItem={() => {}}
					query=""
					required
				>
					{() => <div></div>}
				</AutoComplete>

				<AutoComplete
					emptyStateMessage="Not found"
					items={[]}
					label="Branch"
					onChangeQuery={() => {}}
					onSelectItem={() => {}}
					query=""
					required
				>
					{() => <div></div>}
				</AutoComplete>
			</div>
		</div>
	);
}
