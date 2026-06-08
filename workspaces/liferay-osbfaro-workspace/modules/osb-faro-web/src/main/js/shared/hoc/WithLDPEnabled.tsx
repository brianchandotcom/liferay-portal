/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useLDPEnabled} from '~/shared/hooks/useLDPEnabled';

const WithLDPEnabled = <P extends {groupId: string}>(
	Component: React.ComponentType<P>
) =>
	function WithLDPEnabledComponent(props: P) {
		const {groupId} = props;

		const LDPEnabled = useLDPEnabled({groupId});

		return <Component {...props} LDPEnabled={LDPEnabled} />;
	};

export default WithLDPEnabled;
