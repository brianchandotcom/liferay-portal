/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Sticker from '~/shared/components/Sticker';
import {SegmentStates} from '~/shared/util/constants';

const SegmentSticker = function SegmentSticker({state}) {
	const disabled = state === SegmentStates.Disabled;

	const getSymbol = () => {
		if (disabled) {
			return 'warning';
		}
		else {
			return 'individual_dynamic_segment';
		}
	};

	return (
		<Sticker
			className="segment-sticker-root"
			display={disabled ? 'warning' : 'dark'}
			symbol={getSymbol()}
		/>
	);
};

export default SegmentSticker;
