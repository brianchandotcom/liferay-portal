/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {getRemoteCriterionTypeByPropertyKey} from '~/segment/segment-editor/dynamic/criterion-types/registry';

import {IDisplayComponentProps} from '../types';
import AccountDisplay from './AccountDisplay';
import BehaviorDisplay from './BehaviorDisplay';
import EventDisplay from './EventDisplay';
import IndividualDisplay from './IndividualDisplay';
import InterestDisplay from './InterestDisplay';
import OrganizationDisplay from './OrganizationDisplay';
import SessionDisplay from './SessionDisplay';

const NON_REMOTE_DISPLAYS: Record<string, React.ComponentType<any>> = {
	account: AccountDisplay,
	event: EventDisplay,
	individual: IndividualDisplay,
	interest: InterestDisplay,
	organization: OrganizationDisplay,
	session: SessionDisplay,
	web: BehaviorDisplay,
};

const DisplayComponent: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	segmentType,
}) => {
	const Display = (getRemoteCriterionTypeByPropertyKey(property.propertyKey)
		?.DisplayComponent ??
		NON_REMOTE_DISPLAYS[property.propertyKey] ??
		IndividualDisplay) as React.FC<IDisplayComponentProps>;

	return (
		<Display
			criterion={criterion}
			property={property}
			segmentType={segmentType}
		/>
	);
};

export default DisplayComponent;
