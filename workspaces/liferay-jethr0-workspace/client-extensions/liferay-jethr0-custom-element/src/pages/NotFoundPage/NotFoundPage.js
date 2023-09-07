/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayLayout from '@clayui/layout';

function NotFoundPage() {
	return (
		<ClayLayout.Container>
			<ClayCard>
				<div>404 - Not Found</div>
			</ClayCard>
		</ClayLayout.Container>
	);
}

export default NotFoundPage;
