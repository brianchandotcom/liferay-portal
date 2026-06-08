/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldProps} from 'formik';
import React from 'react';
import ToggleSwitch from '~/shared/components/ToggleSwitch';

const FormToggleSwitch: React.FC<
	FieldProps & React.HTMLAttributes<HTMLElement>
> = ({field: {value: checked, ...otherFields}, ...fieldProps}) => (
	<ToggleSwitch {...otherFields} {...fieldProps} checked={!!checked} />
);

export default FormToggleSwitch;
