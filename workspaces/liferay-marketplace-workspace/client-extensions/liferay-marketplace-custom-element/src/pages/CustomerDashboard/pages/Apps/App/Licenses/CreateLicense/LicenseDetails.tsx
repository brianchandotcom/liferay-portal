/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldErrors, UseFormRegister} from 'react-hook-form';

import {RequiredMask} from '../../../../../../../components/FieldBase';
import FormInput from '../../../../../../../components/Input/formInput';
import i18n from '../../../../../../../i18n';
import {CreateLicenseForm} from './types';

type InputPropsLicense = {
	inputProps: {
		errors: FieldErrors<CreateLicenseForm>;
		register: UseFormRegister<CreateLicenseForm>;
		required: boolean;
	};
};

const LicenseDetails = ({inputProps}: InputPropsLicense) => (
	<div className="license-details-form">
		<div className="h4">
			Environment Details <RequiredMask />
		</div>

		<hr className="mt-2" />

		<FormInput
			{...inputProps}
			boldLabel
			className="custom-input"
			helpMessage="Include a description to uniquely identify this environment. This cannot be edited later."
			label="Description"
			name="description"
		/>

		<p className="h4 mt-7">
			{i18n.translate('activation-key-server-details')}
		</p>

		<small>
			At least one of the following pieces of information is required.
		</small>

		<hr className="mt-2" />

		<FormInput
			{...inputProps}
			boldLabel
			className="custom-input"
			helpMessage="Input one Host name per instance"
			label="Host Name"
			name="hostname"
			placeholder="Enter Host Name"
		/>

		<FormInput
			{...inputProps}
			boldLabel
			className="custom-input"
			component="textarea"
			helpMessage="Add one IP addresses per line. IPv6 addresses are not supported."
			label="IP Addresses"
			name="ipAddress"
			placeholder={`1.1.1.1` + '\n' + `2.2.2.2`}
		/>

		<FormInput
			{...inputProps}
			boldLabel
			className="custom-input"
			component="textarea"
			helpMessage="Add one MAC addresses per line"
			label="Mac Addresses"
			name="macAddress"
			placeholder={`XX-XX-XX-XX-XX-XX` + '\n' + `XX-XX-XX-XX-XX-XX`}
		/>
	</div>
);

export default LicenseDetails;
