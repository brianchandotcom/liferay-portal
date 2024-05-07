/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Heading} from '@clayui/core';
import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {Link} from 'react-router-dom';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0ButtonsRow from '../../components/Jethr0ButtonsRow/Jethr0ButtonsRow';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';
import Jethr0ContainerFluid from '../../components/Jethr0ContainerFluid/Jethr0ContainerFluid';
import Jethr0NavigationBar from '../../components/Jethr0NavigationBar/Jethr0NavigationBar';
import Jethr0Table from '../../components/Jethr0Table/Jethr0Table';
import {getRoutinesPage} from '../../objects/routines/RoutineUtil';
import {toLocaleString} from '../../services/DateUtil';

function RoutinesPage() {
	const [routinesPage, setRoutinesPage] = useState(null);

	if (!routinesPage) {
		getRoutinesPage({page: 1, pageSize: 25, setRoutinesPage});
	}

	function setActiveDelta({activeDelta, routinesPage}) {
		getRoutinesPage({
			page: routinesPage.page,
			pageSize: activeDelta,
			setRoutinesPage,
		});
	}

	function setActivePage({activePage, routinesPage}) {
		getRoutinesPage({
			page: activePage,
			pageSize: routinesPage.pageSize,
			setRoutinesPage,
		});
	}

	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/routines', name: 'Routines'},
	];

	const deltas = [
		{
			label: 25,
		},
		{
			label: 50,
		},
		{
			label: 100,
		},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Card>
				<Jethr0NavigationBar active="Routines" />

				<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />

				<Jethr0ContainerFluid>
					<ClayLayout.Row justify="between">
						<Heading level={3} weight="lighter">
							Routines
						</Heading>
						<Jethr0ButtonsRow
							buttons={[
								{
									link: '/routines/create',
									title: 'Create Routine',
								},
							]}
						/>
					</ClayLayout.Row>
				</Jethr0ContainerFluid>

				<Jethr0Table>
					<thead>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Type</th>
							<th>Create Date</th>
							<th>Job Name</th>
							<th>Job Priority</th>
							<th>Job Type</th>
						</tr>
					</thead>
					<tbody>
						{routinesPage?.routines.map((routine) => {
							return (
								<tr key={routine.id}>
									<th className="font-weight-semi-bold">
										<Link
											title={routine.id}
											to={'/routines/' + routine.id}
										>
											{routine.id}
										</Link>
									</th>
									<td>{routine.name}</td>
									<td>{routine.type.name}</td>
									<td>
										{toLocaleString(routine.dateCreated)}
									</td>
									<td>{routine.jobName}</td>
									<td>{routine.jobPriority}</td>
									<td>{routine.jobType.name}</td>
								</tr>
							);
						})}
					</tbody>
				</Jethr0Table>
			</Jethr0Card>

			{routinesPage && (
				<ClayPaginationBarWithBasicItems
					activeDelta={routinesPage.pageSize}
					defaultActive={routinesPage.page}
					deltas={deltas}
					ellipsisBuffer={3}
					onActiveChange={(activePage) => {
						setActivePage({activePage, routinesPage});
					}}
					onDeltaChange={(activeDelta) => {
						setActiveDelta({activeDelta, routinesPage});
					}}
					showDeltasDropDown={true}
					totalItems={routinesPage.totalCount}
				/>
			)}
		</ClayLayout.Container>
	);
}

export default RoutinesPage;
