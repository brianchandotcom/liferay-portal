/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import i18n from '~/i18n';
import {DATA_COLORS, Statuses} from '~/util/constants';
import {getDurationTime} from '~/util/date';

const RoutineDurationReport = () => {
	const {routineId} = useParams();

	return (
		<Container>
			<ListView
				initialContext={{
					pageSize: 50,
				}}
				managementToolbarProps={{
					applyFilters: true,
					filterSchema: 'routineDuration',
					title: i18n.translate('duration-report'),
					visible: true,
				}}
				resource={`/testray-routine-duration-report/${routineId}`}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'testrayCaseFlaky',
							render: (
								_,
								{testrayCaseFlaky, testrayCaseName}
							) => (
								<>
									{testrayCaseFlaky && (
										<ClayTooltipProvider>
											<span
												className="tr-table__row__flaky-icon"
												data-tooltip-align="top"
												title={i18n.translate(
													'this-is-a-possible-flaky-test'
												)}
											>
												<ClayIcon symbol="flag-full" />
											</span>
										</ClayTooltipProvider>
									)}
									{testrayCaseName}
								</>
							),
							size: 'md',
							value: i18n.translate('case'),
							width: '350',
						},
						{
							clickable: true,
							key: 'testrayCaseTypeName',
							value: i18n.translate('case-type'),
						},
						{
							clickable: true,
							key: 'testrayCasePriority',
							value: i18n.translate('priority'),
						},
						{
							clickable: true,
							key: 'testrayTeamName',
							value: i18n.translate('team'),
						},
						{
							clickable: true,
							key: 'testrayComponentName',
							value: i18n.translate('component'),
						},
						{
							clickable: true,
							key: 'testrayCaseResultStatus',
							render: (testrayCaseResultStatus) => (
								<>
									{testrayCaseResultStatus.map(
										(result: string, index: number) => (
											<StatusBadge
												key={index}
												type={result as StatusBadgeType}
											>
												{result}
											</StatusBadge>
										)
									)}
								</>
							),
							size: 'md',
							value: i18n.translate('results'),
						},
						{
							clickable: true,
							key: 'testrayCaseResultDurations',
							render: (testrayCaseResultDurations) => (
								<div className="graph-container graph-container-sm">
									<div className="tr-duration-chart">
										<ClayChart
											axis={{
												type: 'area',
												x: {
													tick: {
														show: false,
														text: {
															show: false,
														},
													},
												},
												y: {
													tick: {
														show: false,
														text: {
															show: false,
														},
													},
												},
											}}
											data={{
												colors: {
													[Statuses.TEST_FIX]:
														DATA_COLORS[
															'metrics.testfix'
														],
												},
												columns: [
													[
														'DURATION',
														...testrayCaseResultDurations.map(
															(
																duration: number
															) => duration
														),
													],
												],
												stack: {
													normalize: false,
												},
												type: 'area',
											}}
											grid={{
												lines: {
													front: false,
												},
												x: {
													show: false,
												},
												y: {
													show: false,
												},
											}}
											legend={{
												inset: {
													anchor: 'top-right',
													step: 1,
													x: 10,
													y: -30,
												},
												item: {
													tile: {
														height: 3,
														width: 3,
													},
												},
												position: 'inset',
											}}
											padding={{bottom: 10, top: 20}}
											tooltip={{
												format: {
													title: (index: number) =>
														getDurationTime(
															testrayCaseResultDurations[
																index
															]
														),
												},
											}}
										/>
									</div>
								</div>
							),
							size: 'md',
							value: i18n.translate('duration'),
						},
						{
							clickable: true,
							key: 'testrayCaseResultAvgDuration',
							render: (testrayCaseResultAvgDuration) =>
								getDurationTime(testrayCaseResultAvgDuration),
							value: i18n.translate('average-duration'),
						},
					],
					navigateTo: ({testrayCaseId, testrayCaseResultDurations}) =>
						`../../../cases/${testrayCaseId}?filter=%7B"testrayRoutineIds"%3A%5B${routineId}%5D%2C"status"%3A%5B"FAILED"%2C"PASSED"%2C"BLOCKED"%2C"INPROGRESS"%2C"TESTFIX"%5D%7D&filterSchema=buildResultsHistory&pageSize=${testrayCaseResultDurations.length}`,
					rowWrap: true,
				}}
			></ListView>
		</Container>
	);
};

export default RoutineDurationReport;
