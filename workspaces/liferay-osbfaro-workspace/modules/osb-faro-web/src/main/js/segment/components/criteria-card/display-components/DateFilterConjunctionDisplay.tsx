/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {getInitialConjunction} from '~/segment/segment-editor/dynamic/inputs/components/DateFilterConjunctionInput';
import {
	EVER,
	FunctionalOperators,
	INPUT_DATE_FORMAT,
	SINCE,
	TIME_CONJUNCTION_OPTIONS,
} from '~/segment/segment-editor/dynamic/utils/constants';
import {getTimePeriodLabel} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {Criterion} from '~/segment/segment-editor/dynamic/utils/types';
import {formatUTCDate} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';

const formatDate = (date: string): string =>
	formatUTCDate(date, INPUT_DATE_FORMAT);

const DateFilterConjunctionDisplay: React.FC<{
	conjunctionCriterion: Criterion;
}> = ({conjunctionCriterion}) => {
	const {value: dateFilter} = conjunctionCriterion;

	const conjunction = getInitialConjunction(conjunctionCriterion);

	const {label: conjunctionLabel = ''} =
		TIME_CONJUNCTION_OPTIONS.find(({value}) => value === conjunction) || {};

	const getDateFilter = (): React.ReactNode => {
		switch (conjunction) {
			case FunctionalOperators.Between:
				return (
					<span>
						{sub(Liferay.Language.get('x-to-x'), [
							formatDate(dateFilter.start),
							formatDate(dateFilter.end),
						])}
					</span>
				);
			case SINCE:
				return (
					<span>
						<strong>{getTimePeriodLabel(dateFilter)}</strong>
					</span>
				);
			case EVER:
				return;
			default:
				return <span>{formatDate(dateFilter)}</span>;
		}
	};

	return (
		<>
			<span>{conjunctionLabel}</span>

			{getDateFilter()}
		</>
	);
};

export default DateFilterConjunctionDisplay;
