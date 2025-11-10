/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';

import {getIconSpriteMap} from '../../liferay/constants';
import {FieldBase} from '../FieldBase';

type MultiSelectProps<T> = {
	className?: string;
	disabledClearAll?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	inputName: string;
	label?: string;
	localized?: boolean;
	multiselectKey: string;
	onChange?: (values: T) => void;
	onItemsChange: (values: T) => void;
	placeholder?: string;
	required?: boolean;
	selectedItems: T[];
	sourceItems: T[];
	tooltip?: string;
	value?: string;
};

const MultiSelect: React.FC<MultiSelectProps<any>> = ({
	className,
	disabledClearAll = false,
	errorMessage,
	helpMessage,
	hideFeedback,
	inputName,
	label,
	localized,
	multiselectKey,
	onChange,
	onItemsChange,
	required,
	selectedItems,
	sourceItems,
	tooltip,
	value,
}) => {
	return (
		<FieldBase
			className={className}
			errorMessage={errorMessage}
			helpMessage={helpMessage}
			hideFeedback={hideFeedback}
			label={label}
			localized={localized}
			required={required}
			tooltip={tooltip}
		>
			<ClayMultiSelect
				disabledClearAll={disabledClearAll}
				inputName={inputName}
				items={selectedItems}
				key={multiselectKey}
				onChange={onChange}
				onItemsChange={onItemsChange}
				sourceItems={sourceItems}
				spritemap={getIconSpriteMap()}
				value={value}
			/>
		</FieldBase>
	);
};

export default MultiSelect;
