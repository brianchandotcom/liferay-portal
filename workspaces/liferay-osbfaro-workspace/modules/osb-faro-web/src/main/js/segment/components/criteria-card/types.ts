/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {Criterion} from '~/segment/segment-editor/dynamic/utils/types';
import {SegmentTypes} from '~/shared/util/constants';
import {Property} from '~/shared/util/records';

export interface IDisplayComponentProps {
	criterion: Criterion;
	property: Property;
	segmentType: SegmentTypes;
	timeZoneId?: string;
}

export interface ICustomDisplayComponentProps extends IDisplayComponentProps {
	criterion: Criterion & {value: Map<string, any>};
}
