/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0FieldLabel from '../../components/Jethr0FieldLabel/Jethr0FieldLabel';
import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import {createJenkinsCohort} from '../../objects/jenkins/JenkinsUtil';

function CreateJenkinsCohortPage() {
	const [jenkinsCohortName, setJenkinsCohortName] = useState(null);

	function redirectToJenkinsCohortPage(data) {
		if (data !== null && data.id !== null) {
			window.location.replace('/#/jenkins-cohorts/' + data.id);
		}
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: false, link: '/jenkins-cohorts', name: 'Jenkins Cohorts'},
		{
			active: true,
			link: '/jenkins-cohorts/create',
			name: 'Create Jenkins Cohort',
		},
	];

	const jenkinsCohortData = {
		name: jenkinsCohortName,
	};

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Jenkins" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Heading level={3} weight="lighter">
					Create Jenkins Cohort
				</Heading>

				<ClayForm.Group>
					<Jethr0FieldLabel
						labelKey="jenkinsCohortName"
						labelName="Jenkins Cohort Name"
					/>

					<Jethr0Input
						id="jenkinsCohortName"
						onChange={(event) => {
							setJenkinsCohortName(event.target.value);
						}}
						placeholder="Insert your name here"
						type="text"
						value={jenkinsCohortName}
					/>
				</ClayForm.Group>

				<Jethr0ButtonsRow
					buttons={[
						{
							displayType: 'secondary',
							link: '/jenkins-cohorts',
							title: 'Cancel',
						},
						{
							onClick: () => {
								createJenkinsCohort({
									data: jenkinsCohortData,
									redirect: redirectToJenkinsCohortPage,
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

export default CreateJenkinsCohortPage;
