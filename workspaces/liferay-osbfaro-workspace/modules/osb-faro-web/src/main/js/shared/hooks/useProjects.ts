/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fromJS} from 'immutable';
import {useEffect, useState} from 'react';
import {fetchJoinableProjects, fetchMany} from '~/shared/api/projects';
import {Project} from '~/shared/util/records';

export const useFetchProjects = function useFetchProjects() {
	const [loading, setLoading] = useState(true);
	const [data, setData] = useState<any[]>([]);

	useEffect(() => {
		async function fetch() {
			try {
				const projects = await fetchMany();

				setData(
					projects.map((result: any) => new Project(fromJS(result)))
				);
				setLoading(false);
			}
			catch {
				throw new Error('Error on fetchProjects');
			}
		}

		fetch();
	}, []);

	return {
		data,
		loading,
	};
};

export const useFetchJoinableProjects = function useFetchJoinableProjects() {
	const [loading, setLoading] = useState(true);
	const [data, setData] = useState<any[]>([]);

	useEffect(() => {
		async function fetch() {
			try {
				const projects = await fetchJoinableProjects();

				setData(projects);
				setLoading(false);
			}
			catch {
				throw new Error('Error on fetchJoinableProjects');
			}
		}

		fetch();
	}, []);

	return {
		data,
		loading,
	};
};
