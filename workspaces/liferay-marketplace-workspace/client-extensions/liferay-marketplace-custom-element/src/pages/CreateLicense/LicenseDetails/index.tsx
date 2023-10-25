/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldErrors, UseFormRegister} from 'react-hook-form';

import {RequiredMask} from '../../../components/FieldBase';
import FormInput from '../../../components/Input/formInput';
import {CreateLicenseForm} from '../Types';

type InputPropsLicense = {
	inputProps: {
		errors: FieldErrors<CreateLicenseForm>;
		register: UseFormRegister<CreateLicenseForm>;
		required: boolean;
	};
};
const LicenseDetails = ({inputProps}: InputPropsLicense) => {
	return (
		<>
			<h5>
				Environment Details <RequiredMask />
			</h5>
			<hr className="mt-2"></hr>
			<FormInput
				{...inputProps}
				boldLabel
				className="custom-input"
				helpMessage="Include a description to uniquely identify this environment. This cannot be edited later."
				label="Description"
				name="description"
			/>

			<h5 className="mt-7">
				Activation Key Server Details <RequiredMask />
			</h5>

			<hr className="mt-2"></hr>
			<FormInput
				{...inputProps}
				boldLabel
				className="custom-input"
				helpMessage="Input one Host name per instance"
				label="Host Name"
				name="hostName"
				placeholder="Enter Host Name"
			/>

			<FormInput
				{...inputProps}
				boldLabel
				className="custom-input"
				component="textarea"
				helpMessage="Add one IP addresses per line. IPv6 addresses are not supported."
				label="IP Addresses"
				name="IP"
				placeholder={`1.1.1.1` + '\n' + `2.2.2.2`}
			/>

			<FormInput
				{...inputProps}
				boldLabel
				className="custom-input"
				component="textarea"
				helpMessage="Add one MAC addresses per line"
				label="Mac Addresses"
				name="macAddresses"
				placeholder={`XX-XX-XX-XX-XX-XX` + '\n' + `XX-XX-XX-XX-XX-XX`}
			/>
		</>
	);
};

export default LicenseDetails;
