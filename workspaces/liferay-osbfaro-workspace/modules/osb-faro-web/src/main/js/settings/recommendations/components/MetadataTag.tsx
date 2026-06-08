/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Label from '~/shared/components/Label';

const MetadataTag: React.FC<{value: string}> = ({value}) => (
	<Label className="metadata-tag-root" size="lg">
		{value}
	</Label>
);

export default MetadataTag;
