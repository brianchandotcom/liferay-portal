/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
