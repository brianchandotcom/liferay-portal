/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

// This span only stops click propagation (to prevent row selection); it is
// intentionally not an interactive control.

const StopClickPropagation = ({children}: {children: React.ReactNode}) => (
	<span onClick={(event) => event.stopPropagation()}>{children}</span>
);

export default StopClickPropagation;
