/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EmptyState from '../../../components/EmptyState';
import Container from '../../../components/Layout/Container';
import i18n from '../../../i18n';

const RoutineArchived = () => (
	<Container>
		<EmptyState
			description=" "
			title={i18n.translate('no-content-yet')}
			type="EMPTY_SEARCH"
		/>
	</Container>
);

export default RoutineArchived;
