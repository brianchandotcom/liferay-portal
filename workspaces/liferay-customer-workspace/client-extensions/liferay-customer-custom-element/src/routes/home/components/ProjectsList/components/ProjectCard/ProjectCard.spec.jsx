/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import ProjectCard from '.';

describe('Project Card', () => {
	it('contains Project Name', () => {
		render(<ProjectCard name="Test Account 01" />);

		const projectName = screen.queryByRole('heading');
		expect(projectName).toHaveTextContent('Test Account 01');
	});

	it('contains Project Status', () => {
		render(<ProjectCard status="Active" />);

		const projectStatus = screen.getByText('Active');
		expect(projectStatus).toHaveTextContent('Active');
	});

	it('contains Subscription End Date.', () => {
		render(<ProjectCard slaCurrentEndDate="2014-12-31T00:00:00-05:00" />);

		const projectEndDate = screen.getByText('Dec 31, 2014');
		expect(projectEndDate).toHaveTextContent('Dec 31, 2014');
	});

	it('displays projects as cards if has less than 05 projects', () => {
		const {container} = render(<ProjectCard compressed={false} />);

		expect(
			container.getElementsByClassName('cp-project-card-lg').length
		).toBe(1);
	});

	it('displays projects as a list if has more than 05 projects', () => {
		const {container} = render(<ProjectCard compressed />);

		expect(container.getElementsByClassName('card-horizontal').length).toBe(
			1
		);
	});
});
