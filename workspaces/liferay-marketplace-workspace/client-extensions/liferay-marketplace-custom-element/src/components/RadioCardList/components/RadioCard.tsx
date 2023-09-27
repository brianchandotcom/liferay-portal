/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';

import './RadioCard.scss';
import emptyPictureIcon from '../../../assets/icons/avatar.svg';

interface RadioCardProps<T> {
	activeRadio: RadioOption<T> | undefined;
	description?: string;
	imageURL?: string;
	index: number;
	leftRadio?: boolean;
	selectRadio: () => void;
	showImage?: boolean;
	title: string;
}

const NewRadioCard = <T extends unknown>({
	activeRadio,
	description,
	imageURL,
	index,
	leftRadio,
	selectRadio,
	showImage,
	title,
}: RadioCardProps<T>) => {
	return (
		<div
			className={classNames(
				'align-items-center d-flex justify-content-between form-control mb-5 cursor-pointer py-4 px-0',
				{
					'radio-selected': activeRadio?.index === index,
				}
			)}
			key={index}
			onClick={() => selectRadio()}
		>
			<div className="col">
				<div
					className={classNames('d-flex align-items-center col', {
						'mb-2': description,
					})}
				>
					{leftRadio && (
						<div className="col-1">
							<ClayRadio
								checked={activeRadio?.index === index}
								onChange={() => selectRadio()}
								type="radio"
								value={title}
							/>
						</div>
					)}

					<div className="align-items-center col d-flex px-0">
						{showImage && (
							<div
								className={classNames(
									'd-flex justify-content-center',
									{
										'col-2 pr-0': leftRadio,
										'col-3': !leftRadio,
									}
								)}
							>
								<ClaySticker shape="circle" size="lg">
									<ClaySticker.Image
										alt="placeholder"
										src={imageURL ?? emptyPictureIcon}
									/>
								</ClaySticker>
							</div>
						)}

						<h5
							className={classNames('col-10 mb-0', {
								'pl-0': !leftRadio,
							})}
						>
							{title}
						</h5>
					</div>
				</div>

				{description && (
					<div className="col d-flex justify-content-end">
						<p
							className={classNames('mb-0 text-paragraph', {
								'col-10': showImage,
								'col-11': !showImage,
								'pl-6': !leftRadio && showImage,
							})}
						>
							{description}
						</p>
					</div>
				)}
			</div>

			{!leftRadio && (
				<div className="col-2">
					<ClayRadio
						checked={activeRadio?.index === index}
						onChange={() => selectRadio()}
						type="radio"
						value={title}
					/>
				</div>
			)}
		</div>
	);
};

export default NewRadioCard;
