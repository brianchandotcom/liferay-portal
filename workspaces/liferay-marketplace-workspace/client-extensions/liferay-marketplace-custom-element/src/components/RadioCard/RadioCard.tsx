import {ClayToggle} from '@clayui/form';
import classNames from 'classnames';

import radioChecked from '../../assets/icons/radio_button_checked_icon.svg';
import radioUnchecked from '../../assets/icons/radio_button_unchecked_icon.svg';
import paypal from '../../assets/images/paypal.png';

import './RadioCard.scss';

import {useState} from 'react';

import {Tooltip} from '../Tooltip/Tooltip';

interface RadioCardProps {
	description?: string;
	disabled?: boolean;
	icon?: string;
	onChange: (value?: boolean) => void;
	position?: string;
	selected: boolean;
	small?: boolean;
	title?: string;
	toggle?: boolean;
	tooltip?: string;
}

export function RadioCard({
	description,
	disabled = false,
	icon,
	onChange,
	position = 'left',
	selected,
	small,
	title,
	toggle = false,
	tooltip,
}: RadioCardProps) {
	return (
		<div
			className={classNames('radio-card-container', {
				'radio-card-container-disabled': disabled,
				'radio-card-container-selected': selected,
				'radio-card-container-small': small,
			})}
		>
			<div className="radio-card-main-info">
				<div className="radio-card-title">
					{position === 'right' && icon && (
						<img
							alt="Icon"
							className="radio-card-title-icon-rounded"
							src={icon}
						/>
					)}

					{position === 'left' &&
						(toggle ? (
							<ClayToggle
								onToggle={(toggleValue) =>
									onChange(toggleValue)
								}
								toggled={selected}
							/>
						) : (
							<button
								className={classNames('radio-card-button', {
									'radio-card-button-disabled': disabled,
								})}
								onClick={() => !disabled && onChange()}
							>
								<img
									alt={
										selected
											? 'Radio Checked'
											: 'Radio unchecked'
									}
									className="radio-card-button-icon"
									src={
										selected ? radioChecked : radioUnchecked
									}
								/>
							</button>
						))}

					{small ? (
						<div className="radio-card-main-info-small">
							<div className="radio-card-main-info-small-background">
								<img alt="paypal" src={paypal} />
							</div>

							<span className="radio-card-main-info-small-text-small">
								{title}
							</span>
						</div>
					) : (
						title && (
							<span
								className={classNames('radio-card-title-text', {
									'radio-card-title-text-selected': selected,
								})}
							>
								{title}
							</span>
						)
					)}

					{position === 'left' && icon && (
						<img
							alt="Icon"
							className={classNames('radio-card-title-icon', {
								'radio-card-title-icon-selected': selected,
							})}
							src={icon}
						/>
					)}
				</div>

				{tooltip && (
					<div className="radio-card-title-tooltip">
						<Tooltip tooltip={tooltip} />
					</div>
				)}

				{position === 'right' &&
					(toggle ? (
						<ClayToggle
							onToggle={(toggleValue) => onChange(toggleValue)}
							toggled={selected}
						/>
					) : (
						<button
							className={classNames('radio-card-button', {
								'radio-card-button-disabled': disabled,
							})}
							onClick={() => !disabled && onChange()}
						>
							<img
								alt={
									selected
										? 'Radio Checked'
										: 'Radio unchecked'
								}
								className="radio-card-button-icon"
								src={selected ? radioChecked : radioUnchecked}
							/>
						</button>
					))}
			</div>

			<span className="radio-card-description">{description}</span>
		</div>
	);
}
