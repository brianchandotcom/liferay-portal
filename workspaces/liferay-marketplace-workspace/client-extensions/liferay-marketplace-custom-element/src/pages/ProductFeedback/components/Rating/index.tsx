/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {forwardRef, useState} from 'react';

import './index.scss';

type RatingProps = {
	label?: string;
	name?: string;
	onBlur?: (event: any) => void;
	onChange?: (event: any) => void;
	size?: number;
	value?: number;
};

const items = [1, 2, 3, 4, 5];

export const Rating = forwardRef<HTMLInputElement, RatingProps>(
	({label, name, onBlur, onChange, size = 44, value = 0}, ref) => {
		const [selected, setSelected] = useState<number>(value);

		const handleSelect = (num: number) => {
			setSelected(num);

			if (onChange) {
				onChange({
					target: {
						name,
						value: num,
					},
				});
			}
		};

		return (
			<div className="rating-wrapper">
				{label && <label className="rating-title">{label}</label>}
				<div className="rating">
					<input
						name={name}
						onBlur={onBlur}
						readOnly
						ref={ref}
						type="hidden"
						value={selected}
					/>

					{items.map((num, index) => (
						<div
							className="rating-item"
							key={num}
							onClick={() => handleSelect(num)}
						>
							<div
								className={classNames('rating-circle', {
									'rating-circle--selected': selected === num,
								})}
								style={{height: size, width: size}}
							>
								{num}
							</div>

							{index === 0 && (
								<span className="rating-label">Poor</span>
							)}

							{index === items.length - 1 && (
								<span className="rating-label">Excellent</span>
							)}
						</div>
					))}
				</div>
			</div>
		);
	}
);

Rating.displayName = 'Rating';
