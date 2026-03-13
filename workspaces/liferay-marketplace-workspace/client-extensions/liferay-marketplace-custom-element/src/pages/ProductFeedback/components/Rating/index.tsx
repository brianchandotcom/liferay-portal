import {forwardRef, useState} from 'react';
import classNames from 'classnames';

import './index.scss';

type RatingProps = {
	name?: string;
	value?: number;
	label?: string;
	onChange?: (event: any) => void;
	onBlur?: (event: any) => void;
	size?: number;
};

const items = [1, 2, 3, 4, 5];

export const Rating = forwardRef<HTMLInputElement, RatingProps>(
	({name, value = 0, label, onChange, onBlur, size = 44}, ref) => {
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
						type="hidden"
						name={name}
						ref={ref}
						value={selected}
						onBlur={onBlur}
						readOnly
					/>

					{items.map((num, index) => (
						<div
							key={num}
							className="rating-item"
							onClick={() => handleSelect(num)}
						>
							<div
								className={classNames('rating-circle', {
									'rating-circle--selected': selected === num,
								})}
								style={{width: size, height: size}}
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
