/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {RequiredMask} from '../../../components/FieldBase';
import {Input} from '../../../components/Input/Input';

interface LicenseDetailsType {
	[key: string]: string;
}

const LicenseDetails = ({expDate, keyType, startDate}: LicenseDetailsType) => {
	// eslint-disable-next-line no-console
	console.log(expDate, keyType, startDate);

	const formInformation = {
		IP: '',
		description: '',
		hostName: '',
		macAddresses: '',
	};

	const [form, setForm] = useState<LicenseDetailsType>(formInformation);

	return (
		<>
			<h4>
				Environment Details <RequiredMask />
			</h4>
			<hr></hr>
			<Input
				helpMessage="Include a description to uniquely identify this environment. This cannot be edited later."
				label="Description"
				onChange={({target}) =>
					setForm({
						...form,
						description: target.value,
					})
				}
			/>

			<h4>
				Activation Key Server Details <RequiredMask />
			</h4>

			<hr></hr>
			<Input
				helpMessage="Input one Host name per instance"
				label="Host Name"
				onChange={({target}) =>
					setForm({
						...form,
						hostName: target.value,
					})
				}
			/>
			<Input
				component="textarea"
				helpMessage="Add one IP addresses per line. IPv6 addresses are not supported."
				label="IP Addresses"
				onChange={({target}) =>
					setForm({
						...form,
						IP: target.value,
					})
				}
				placeholder={`1.1.1.1` + '\n' + `2.2.2.2`}
			/>
			<Input
				component="textarea"
				helpMessage="Add one MAC addresses per line"
				label="Mac Addresses"
				onChange={({target}) =>
					setForm({
						...form,
						macAddresses: target.value,
					})
				}
				placeholder={`XX-XX-XX-XX-XX-XX` + '\n' + `XX-XX-XX-XX-XX-XX`}
			/>
		</>
	);
};

export default LicenseDetails;
