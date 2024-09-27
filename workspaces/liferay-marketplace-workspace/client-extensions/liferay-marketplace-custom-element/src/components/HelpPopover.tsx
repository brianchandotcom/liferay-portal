/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {ComponentProps} from 'react';

type HelpPopoverProps = ComponentProps<typeof ClayPopover>;

const HelpPopover: React.FC<HelpPopoverProps> = (props) => (
	<ClayPopover
		alignPosition="bottom"
		closeOnClickOutside
		disableScroll={false}
		trigger={<ClayIcon color="#6B6C7E" symbol="question-circle-full" />}
		{...props}
	/>
);

export default HelpPopover;
