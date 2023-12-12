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
	matrixData: number[][];
	title: string;
};

const columns = [
	i18n.translate('passed'),
	i18n.translate('failed'),
	i18n.translate('blocked'),
	i18n.translate('test-fix'),
	i18n.translate('dnr'),
];

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

const TableChart: React.FC<TableChartProps> = ({matrixData, title}) => {
	const {runA, runB} = useParams();

	return (
		<table className="table table-borderless table-sm tr-table-chart">
			<thead>
				<tr>
					<td className="border-0 pb-2" colSpan={6}>
						{title}
					</td>
				</tr>
			</thead>

			<tbody>
				<tr>
					<th></th>

					{columns.map((horizontalColumn, index) => (
						<td
							className="text-paragraph-xs tr-table-chart__column-title"
							key={index}
						>
							B {horizontalColumn}
						</td>
					))}
				</tr>

				{columns.map((verticalColumn, verticalColumnIndex) => (
					<tr key={verticalColumnIndex}>
						<td className="text-paragraph-xs tr-table-chart__column-title">
							A {verticalColumn}
						</td>

						{columns.map((_, horizontalColumnIndex) => {
							const value =
								matrixData[verticalColumnIndex][
									horizontalColumnIndex
								];

							return (
								<td
									className={classNames(
										'border py-2 tr-table-chart__data-area text-center',
										colors[verticalColumnIndex][
											horizontalColumnIndex
										]
									)}
									key={`${verticalColumnIndex}-${horizontalColumnIndex}`}
								>
									{value > 0 && (
										<Link
											className="font-weight-bold"
											to={`/compare-runs/${runA}/${runB}/cases?dueStatusA=${columnsDueStatus[verticalColumnIndex]}&dueStatusB=${columnsDueStatus[horizontalColumnIndex]}`}
										>
											{value}
										</Link>
									)}
								</td>
							);
						})}
					</tr>
				))}
			</tbody>
		</table>
	);
};

export default memo(TableChart);
