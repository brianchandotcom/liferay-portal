/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useStore} from 'react-redux';
import {useParams} from 'react-router-dom';

import {SummaryCompletedCard} from './SummaryCompletedCard';
import {SummaryDraftCard} from './SummaryDraftCard';
import {SummaryNoWinnerCard} from './SummaryNoWinnerCard';
import {SummaryRunningCard} from './SummaryRunningCard';
import {SummaryTerminatedCard} from './SummaryTerminatedCard';
import {SummaryWinnerCard} from './SummaryWinnerCard';
import {IExperiment, Status} from './types';

const Component: Record<
	string,
	React.FC<{
		experiment: IExperiment & Record<string, any>;
		timeZoneId: string;
	}>
> = {
	[Status.Completed]: SummaryCompletedCard as React.FC<any>,
	[Status.Draft]: SummaryDraftCard as React.FC<any>,
	[Status.Running]: SummaryRunningCard as React.FC<any>,
	[Status.FinishedNoWinner]: SummaryNoWinnerCard as React.FC<any>,
	[Status.Terminated]: SummaryTerminatedCard as React.FC<any>,
	[Status.FinishedWinner]: SummaryWinnerCard as React.FC<any>,
};

export const SummaryCard = function SummaryCard({
	experiment,
}: {
	experiment: IExperiment;
}) {
	const {groupId} = useParams<{groupId: string}>();
	const store = useStore();
	const timeZoneId = store
		.getState()
		.getIn(['projects', groupId, 'data', 'timeZone', 'timeZoneId']);
	const SummaryComponent = Component[experiment.status.toLowerCase()];

	return <SummaryComponent experiment={experiment} timeZoneId={timeZoneId} />;
};
