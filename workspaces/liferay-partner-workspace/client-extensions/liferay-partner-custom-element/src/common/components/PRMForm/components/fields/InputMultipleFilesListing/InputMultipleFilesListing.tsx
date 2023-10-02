/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PRMForm from '../../..';
import LiferayFile from '../../../../../interfaces/liferayFile';
import PRMFormik from '../../../../PRMFormik';
import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';

interface IProps {
	acceptedFilesExtensions: string;
	description: string;
	label: string;
	name: string;
	onAccept: (liferayFiles: LiferayFile[]) => void;
	value?: LiferayFile[] | Object[];
}

const InputMultipleFilesListing = ({
	acceptedFilesExtensions,
	description,
	label,
	name,
	onAccept,
	required,
	value,
}: PRMFormFieldProps & IProps) => (
	<PRMFormik.Field
		acceptedFilesExtensions={acceptedFilesExtensions}
		component={PRMForm.InputMultipleFiles}
		description={description}
		label={label}
		name={name}
		onAccept={onAccept}
		required={required}
		value={value}
	/>
);

export default InputMultipleFilesListing;
