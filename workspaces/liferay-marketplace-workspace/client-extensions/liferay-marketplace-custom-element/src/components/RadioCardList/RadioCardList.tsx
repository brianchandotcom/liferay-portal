/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import RadioCard from './components/RadioCard';

interface RadioCardListProps<T> {
	contentList: RadioCardContent<T>[];
	leftRadio?: boolean;
	onSelect: (value: RadioOption<T>) => void;
	showImage?: boolean;
}

export interface RadioCardContent<T> {
	customization?: any;
	description?: string;
	imageURL?: string;
	label?: string;
	selected: boolean;
	title: string;
	value: T;
}

const RadioCardList = <T extends unknown>({
	contentList,
	leftRadio,
	onSelect,
	showImage,
}: RadioCardListProps<T>) => {
	const handleSelectRadio = (selectedRadio: RadioOption<T>) => {
		onSelect(selectedRadio);
	};

	return (
		<div className="d-flex flex-column w-100">
			<div className="mb-0 pr-3 w-100">
				{contentList.map((content, index) => (
					<RadioCard
						activeRadio={content.selected}
						customization={content.customization}
						description={content.description}
						imageURL={content.imageURL}
						index={index}
						key={index}
						label={content.label}
						leftRadio={leftRadio}
						selectRadio={() =>
							handleSelectRadio({index, value: content.value})
						}
						showImage={showImage}
						title={content.title}
					/>
				))}
			</div>
		</div>
	);
};

export default RadioCardList;
