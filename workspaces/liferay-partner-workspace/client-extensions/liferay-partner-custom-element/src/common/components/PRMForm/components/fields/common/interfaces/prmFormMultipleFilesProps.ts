/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikProps} from 'formik';

import MDFClaim from '../../../../../../interfaces/mdfClaim';
import PRMFormFieldProps from './prmFormFieldProps';

export default interface PRMFormMultipleFilesProps extends PRMFormFieldProps {
	form?: FormikProps<MDFClaim>;
}
