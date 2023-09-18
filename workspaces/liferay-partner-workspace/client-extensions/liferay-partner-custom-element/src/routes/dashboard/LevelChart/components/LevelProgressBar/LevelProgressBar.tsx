/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import {ChartTypes} from '../../../../../common/components/dashboard/enums/chartTypes';
import {naNToZero} from '../../../../../common/components/dashboard/utils';
import formatCurrency from '../../../../../common/components/dashboard/utils/formatCurrency';

interface IProps {
	currentValue: number;
	total: number;
	type: ChartTypes;
}

const Legend = ({currentValue, total, type}: IProps) => {
	const leftToTotal =
		currentValue >= total ? 0 : Math.floor(total - currentValue);

	const formatLeftToTotal = () => {
		if (leftToTotal) {
			if (type === ChartTypes.ARR) {
				return `${formatCurrency(leftToTotal)} more`;
			}

			return `${leftToTotal} more project`;
		}

		return '';
	};

	return (
		<div className="d-flex justify-content-between mb-1">
			<div className="align-items-center d-flex">
				<div
					className={classNames('mr-1 p-1 rounded-circle', {
						'bg-brand-primary-darken-5':
							type === ChartTypes.NP_OR_NB,
						'bg-brand-primary-lighten-4': type === ChartTypes.ARR,
					})}
				></div>

				<span className="mr-1 text-neutral-5 text-paragraph-sm">
					{type}
				</span>

				<span className="font-weight-bold text-paragraph">
					{formatLeftToTotal()}
				</span>
			</div>

			<div>
				<span className="font-weight-bold mr-1 text-paragraph">
					{type === ChartTypes.ARR
						? formatCurrency(currentValue)
						: currentValue}
				</span>

				<span className="font-weight-bold text-neutral-5">
					<span className="mr-1">/</span>

					<span>
						{type === ChartTypes.ARR
							? formatCurrency(total)
							: total}
					</span>
				</span>
			</div>
		</div>
	);
};

const MAX_PERCENTAGE = 100;

const LevelProgressBar = ({currentValue, total, type}: IProps) => {
	const percentage = naNToZero(currentValue / total) * 100;
	const barPercentage =
		percentage <= MAX_PERCENTAGE ? percentage : MAX_PERCENTAGE;

	return (
		<div className="mb-3">
			<Legend currentValue={currentValue} total={total} type={type} />

			<div className="bg-neutral-2 w-100">
				<div
					className={classNames('p-2', {
						'bg-brand-primary-darken-5':
							type === ChartTypes.NP_OR_NB,
						'bg-brand-primary-lighten-4': type === ChartTypes.ARR,
					})}
					style={{width: `${barPercentage}%`}}
				></div>
			</div>
		</div>
	);
};

export default LevelProgressBar;
