/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import classNames from 'classnames';

import checkFill from '../../assets/icons/check_fill_icon.svg';
import circleFill from '../../assets/icons/circle_fill_icon.svg';
import radioSelected from '../../assets/icons/radio_button_checked_2_icon.svg';

interface Steps {
	checked: boolean;
	name: string;
	selected: boolean;
}

const getIcon = ({
	checked,
	selected,
}: {
	checked: boolean;
	selected: boolean;
}) => {
	if (checked) {
		return checkFill;
	}

	if (selected) {
		return radioSelected;
	}

	return circleFill;
};

export function StepTracker({
	freeApp,
	steps,
}: {
	freeApp: boolean;
	steps: Steps[];
}) {
	return (
		<div className="steps">
			<div className="get-app-modal-text-divider">
				{freeApp ? (
					<span>{steps[0].name}</span>
				) : (
					steps.map((step, i) => {
						return (
							<div className="get-app-modal-step-item" key={i}>
								<img
									className={classNames(
										'get-app-modal-step-icon',
										{
											'get-app-modal-step-icon-checked':
												step.checked,
											'get-app-modal-step-icon-selected':
												step.selected,
										}
									)}
									src={getIcon(step)}
								/>

								<span
									className={classNames({
										'get-app-modal-step-item-active':
											step.checked || step.selected,
									})}
								>
									{step.name}
								</span>
							</div>
						);
					})
				)}
			</div>
		</div>
	);
}
