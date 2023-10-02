/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';

import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

const Checkbox = ({
	field,
}: PRMFormFieldProps & PRMFormFieldStateProps<boolean>) => (
	<ClayForm.Group className="mb-0">
		<ClayCheckbox
			checked={field.value}
			name={field.name}
			onChange={field.onChange}
		/>
	</ClayForm.Group>
);
export default Checkbox;
