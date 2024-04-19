/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {memo} from 'react';
import {Link, useParams} from 'react-router-dom';
import i18n from '~/i18n';
import {CaseResultStatuses} from '~/util/statuses';

type TableChartProps = {
	matrixData: any;
	title?: string;
};

const columns = [
	i18n.translate('passed'),
	i18n.translate('failed'),
	i18n.translate('blocked'),
	i18n.translate('test-fix'),
	i18n.translate('dnr'),
];

const columnsStatus = {
	BLOCKED: 'Blocked',
	DNR: 'DNR',
	FAILED: 'Failed',
	PASSED: 'Passed',
	TEST_FIX: 'Test Fix',
};

const columnsDueStatus = [
	CaseResultStatuses.PASSED,
	CaseResultStatuses.FAILED,
	CaseResultStatuses.BLOCKED,
	CaseResultStatuses.TEST_FIX,
	CaseResultStatuses.DID_NOT_RUN,
];

const STATUS_COLOR = {
	BLOCKED: 'blocked',
	DNR: 'dnr',
	FAILED: 'failed',
	PASSED: 'passed',
	TEST_FIX: 'test-fix',
};

const colors = [
	[
		STATUS_COLOR.PASSED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.PASSED,
	],
	[
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.FAILED,
	],
	[
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.BLOCKED,
	],
	[
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.TEST_FIX,
	],
	[
		STATUS_COLOR.PASSED,
		STATUS_COLOR.FAILED,
		STATUS_COLOR.BLOCKED,
		STATUS_COLOR.TEST_FIX,
		STATUS_COLOR.DNR,
	],
];

const formattedColumnName = (columnName: string) => {
	const formattedName = {
		[columnsStatus.DNR]: CaseResultStatuses.DID_NOT_RUN,
		[columnsStatus.TEST_FIX]: CaseResultStatuses.TEST_FIX,
	};

	return formattedName[columnName] || columnName.toUpperCase();
};

const TableChart: React.FC<TableChartProps> = ({matrixData, title}) => {
	const {runA: runAId, runB: runBId} = useParams();

	const getURLParams = (
		columnsDueStatus: CaseResultStatuses[],
		verticalColumnIndex: number,
		horizontalColumnIndex: number
	) => {
		const urlParams = {
			testrayCaseResultStatus1: [columnsDueStatus[verticalColumnIndex]],
			testrayCaseResultStatus2: [columnsDueStatus[horizontalColumnIndex]],
		};

		return new URLSearchParams({
			filter: JSON.stringify(urlParams),
			filterSchema: 'compareRunsCases',
		});
	};

	return (
		<table className="table table-borderless table-sm tr-table-chart">
			<thead>
				<tr>
					<td className="border-0 h6" colSpan={4}>
						{title}
					</td>
				</tr>
			</thead>

			<tbody>
				<tr>
					<th></th>

					{columns.map((horizontalColumn, index) => (
						<td
							className="tr-table-chart__column-title"
							key={index}
						>
							<div className="text-center">
								B <br />
								{horizontalColumn}
							</div>
						</td>
					))}
				</tr>

				{columns.map((verticalColumnName, verticalColumnIndex) => (
					<tr key={verticalColumnIndex}>
						<td className="tr-table-chart__column-title">
							<div className="text-center">
								A <br />
								{verticalColumnName}
							</div>
						</td>

						{columns.map(
							(horizontalColumnName, horizontalColumnIndex) => {
								const params = getURLParams(
									columnsDueStatus,
									verticalColumnIndex,
									horizontalColumnIndex
								);

								const verticalName = formattedColumnName(
									verticalColumnName
								);
								const horizontalName = formattedColumnName(
									horizontalColumnName
								);

								const value =
									matrixData && matrixData[verticalName]
										? matrixData[verticalName][
												horizontalName
										  ]
										: '';

								return (
									<td
										className={classNames(
											'border py-1 tr-table-chart__data-area text-center',
											colors[verticalColumnIndex][
												horizontalColumnIndex
											]
										)}
										key={`${verticalColumnIndex}-${horizontalColumnIndex}`}
									>
										<Link
											className="font-weight-bold"
											to={`/compare-runs/${runAId}/${runBId}/details?${params}`}
										>
											{value}
										</Link>
									</td>
								);
							}
						)}
					</tr>
				))}
			</tbody>
		</table>
	);
};

export default memo(TableChart);
