/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useContext} from 'react';

import {ApplicationPropertiesContext} from '../../context/ApplicationPropertiesContext';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';

type JiraLinkProps = {
	displayViewInJira?: boolean;
	issue?: string | string[];
};

const JiraLink: React.FC<JiraLinkProps> = ({
	displayViewInJira = true,
	issue,
}) => {
	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	if (!issue || !issue.length) {
		return <span>-</span>;
	}

	const issues = Array.isArray(issue)
		? issue
		: issue
				.split(',')
				.map((name) => name.trim())
				.filter(Boolean);

	const Link = ({name}: {name: string}) => (
		<a
			className="mr-2"
			href={`${jiraBaseURL}/browse/${name}`}
			target="_blank"
		>
			{name}
		</a>
	);

	return (
		<div className="d-flex flex-column">
			{displayViewInJira && (
				<a
					href={`${jiraBaseURL}/issues/?jql=${SearchBuilder.in(
						'key',
						issues
					).replaceAll("'", '')}`}
					target="_blank"
				>
					{i18n.translate('view-in-jira')}

					<ClayIcon
						className="ml-2"
						fontSize={12}
						symbol="shortcut"
					/>
				</a>
			)}

			<div>
				{issues.map((name, index) => (
					<Link key={index} name={name} />
				))}
			</div>
		</div>
	);
};

export default JiraLink;
