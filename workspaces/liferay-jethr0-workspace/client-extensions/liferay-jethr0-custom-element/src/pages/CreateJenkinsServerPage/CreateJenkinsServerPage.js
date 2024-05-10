/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';
import {useParams} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0FieldLabel from '../../components/Jethr0FieldLabel/Jethr0FieldLabel';
import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0SelectWithOption from '../../components/Jethr0SelectWithOption/Jethr0SelectWithOption';
import {
	createJenkinsServer,
	getJenkinsCohortsPage,
} from '../../objects/jenkins/JenkinsUtil';

const jenkinsServerURLRegExp = new RegExp(
	'https?://(test-\\d+-\\d+)(.liferay.com)?'
);

function CreateJenkinsServerPage() {
	const [jenkinsCohort, setJenkinsCohort] = useState(null);
	const {jenkinsCohortId} = useParams();
	const [jenkinsCohortsPage, setJenkinsCohortsPage] = useState(null);
	const [jenkinsServerURL, setJenkinsServerURL] = useState(null);

	if (!jenkinsCohortsPage) {
		getJenkinsCohortsPage({setJenkinsCohortsPage});

		return;
	}

	const jenkinsCohortNames = jenkinsCohortsPage?.jenkinsCohorts.map(
		(jenkinsCohort) => {
			return {
				label: jenkinsCohort.name,
				value: jenkinsCohort.id,
			};
		}
	);

	if (!jenkinsCohort) {
		for (const availableJenkinsCohort of jenkinsCohortsPage?.jenkinsCohorts) {
			if (availableJenkinsCohort.id === parseInt(jenkinsCohortId, 10)) {
				setJenkinsCohort(availableJenkinsCohort);

				return;
			}
		}
	}

	function redirectToJenkinsServerPage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/jenkins-servers/' + data.id);
		}
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jenkins-servers', name: 'Jenkins Servers'},
		{
			active: true,
			link: '/jenkins-servers/create',
			name: 'Create Jenkins Server',
		},
	];

	let jenkinsName = '';
	let jenkinsURL = '';

	if (jenkinsServerURL) {
		const jenkinsServerURLMatch = jenkinsServerURL.match(
			jenkinsServerURLRegExp
		);

		if (jenkinsServerURLMatch) {
			jenkinsName = jenkinsServerURLMatch[1];
			jenkinsURL = 'https://' + jenkinsServerURLMatch[1] + '.liferay.com';
		}
	}

	const jenkinsServerData = {
		name: jenkinsName,
		r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId: jenkinsCohort?.id,
		url: jenkinsURL,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jenkins" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Jenkins Server
				</Heading>

				<ClayForm.Group>
					<Jethr0FieldLabel
						labelKey="jenkinsCohort"
						labelName="Jenkins Cohort"
					/>

					<Jethr0SelectWithOption
						ariaLabel="Jenkins Cohort"
						id="jenkinsCohort"
						onChange={(event) => {
							for (const availableJenkinsCohort of jenkinsCohortsPage?.jenkinsCohorts) {
								if (
									availableJenkinsCohort.id ===
									parseInt(event.target.value, 10)
								) {
									setJenkinsCohort(availableJenkinsCohort);

									return;
								}
							}
						}}
						options={jenkinsCohortNames}
						value={jenkinsCohort?.id}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<Jethr0FieldLabel
						labelKey="jenkinsServerURL"
						labelName="Jenkins Server URL"
					/>

					<Jethr0Input
						id="jenkinsServerURL"
						onChange={(event) => {
							setJenkinsServerURL(event.target.value);
						}}
						placeholder="Insert your URL here"
						type="text"
						value={jenkinsServerURL}
					/>
				</ClayForm.Group>

				<Jethr0ButtonsRow
					buttons={[
						{
							displayType: 'secondary',
							link: '/jenkins-servers',
							title: 'Cancel',
						},
						{
							onClick: () => {
								createJenkinsServer({
									data: jenkinsServerData,
									redirect: redirectToJenkinsServerPage,
								});
							},
							title: 'Save',
						},
					]}
				/>
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default CreateJenkinsServerPage;
