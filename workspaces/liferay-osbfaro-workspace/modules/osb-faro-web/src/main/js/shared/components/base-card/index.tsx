/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';
import Card from '~/shared/components/Card';
import BasePage from '~/shared/components/base-page';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';
import {RangeSelectors} from '~/shared/types';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

import {ReportContainer} from '../download-report/DownloadPDFReport';
import HeaderDefault, {BaseCardHeaderDefaultIProps} from './HeaderDefault';

interface BaseCardIProps {
	Header?: React.FC<BaseCardHeaderDefaultIProps>;
	children: (val: any) => React.ReactNode;
	className?: string;
	description?: string;
	headerProps?: {[key: string]: any};
	id?: string;
	label: string;
	legacyDropdownRangeKey: boolean;
	minHeight?: number;
	reportContainer?: ReportContainer;
	showInterval?: boolean;
}

const BaseCard: React.FC<BaseCardIProps> = ({
	Header = HeaderDefault,
	children,
	className,
	description = '',
	headerProps = {},
	id,
	label,
	legacyDropdownRangeKey = true,
	minHeight,
	reportContainer,
	showInterval = false,
}) => {
	const context = useContext(BasePage.Context);

	const {
		experienceId,
		filters,
		rangeSelectors: contextRangeSelectors,
		router,
	} = context;

	const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);

	const initialRangeSelectors = useQueryRangeSelectors();

	const [localRangeSelectors, setLocalRangeSelectors] =
		useState<RangeSelectors>(initialRangeSelectors);

	const currentRangeSelectors = contextRangeSelectors || localRangeSelectors;

	const isGlobal = !!contextRangeSelectors;

	const otherProps = {
		experienceId,
		filters,
		interval,
		onChangeInterval: setInterval,
		onRangeSelectorsChange: isGlobal ? undefined : setLocalRangeSelectors,
		rangeSelectors: currentRangeSelectors,
		router,
	};

	return (
		<Card
			className={className}
			id={id}
			minHeight={minHeight}
			reportContainer={reportContainer}
		>
			<Header
				{...otherProps}
				{...headerProps}
				description={description}
				label={label}
				legacy={legacyDropdownRangeKey}
				showInterval={showInterval}
				showRangeKey={
					isGlobal ? false : headerProps.showRangeKey ?? true
				}
			/>

			{children({...otherProps})}
		</Card>
	);
};

export default BaseCard;
