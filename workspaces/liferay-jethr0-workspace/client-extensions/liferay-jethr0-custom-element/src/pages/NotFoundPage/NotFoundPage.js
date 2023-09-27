/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';

import Jethr0Breadcrumbs from '../../components/Jethr0Breadcrumbs/Jethr0Breadcrumbs';
import Jethr0Card from '../../components/Jethr0Card/Jethr0Card';

function NotFoundPage() {
	const breadcrumbs = [
		{active: false, link: '/', name: 'Home'},
		{active: true, link: '/not-found', name: 'Not Found'},
	];

	return (
		<ClayLayout.Container>
			<Jethr0Breadcrumbs breadcrumbs={breadcrumbs} />
			<Jethr0Card>
				<div>404 - Not Found</div>
			</Jethr0Card>
		</ClayLayout.Container>
	);
}

export default NotFoundPage;
