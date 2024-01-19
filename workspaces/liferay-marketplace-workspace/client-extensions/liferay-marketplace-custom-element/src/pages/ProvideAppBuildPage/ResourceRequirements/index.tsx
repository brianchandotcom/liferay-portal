/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import FormInput from '../../../components/Input/formInput';
import {useAppContext} from '../../../manage-app-state/AppManageState';
import {TYPES} from '../../../manage-app-state/actionTypes';

import './index.scss';

export type ResourceRequirementsForm = {
	numberOfCPUs: number;
	ram: number;
};

const ResourceRequirements = () => {
	const [{resourceRequirements}, dispatch] = useAppContext();

	return (
		<div className="d-flex justify-content-between resource-requirements-content">
			<FormInput
				boldLabel
				className="custom-input resource-requirements-content-input"
				helpMessage="If no CPUs please enter 0"
				label="Number of CPUs"
				name="numberOfCPUs"
				onChange={({target}) => {
					const {value} = target;

					dispatch({
						payload: {key: 'cpu', value},
						type: TYPES.UPDATE_RESOURCE_REQUIREMENTS,
					});
				}}
				placeholder="Enter the number of CPUs"
				type="number"
				value={resourceRequirements.cpu ?? ''}
			/>

			<FormInput
				boldLabel
				className="custom-input resource-requirements-content-input"
				helpMessage="If no RAM required please enter 0"
				label="RAM"
				name="ram"
				onChange={({target}) => {
					const {value} = target;
					dispatch({
						payload: {key: 'ram', value},
						type: TYPES.UPDATE_RESOURCE_REQUIREMENTS,
					});
				}}
				placeholder="Enter the required RAM"
				type="number"
				value={resourceRequirements.ram ?? ''}
			/>
		</div>
	);
};

export default ResourceRequirements;
