/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

type HelpMessageProps = {
	children: ReactNode;
};

export function HelpMessage({children}: HelpMessageProps) {
	return <small className="marketplace-form-help-message">{children}</small>;
}
