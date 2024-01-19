/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import FormInput from '../../../components/Input/formInput';
import i18n from '../../../i18n';
import {useAppContext} from '../../../manage-app-state/AppManageState';
import {TYPES} from '../../../manage-app-state/actionTypes';

import './index.scss';

const ResourceRequirements = () => {
	const [{resourceRequirements}, dispatch] = useAppContext();

	return (
		<div className="d-flex justify-content-between resource-requirements-content">
			<FormInput
				boldLabel
				className="custom-input resource-requirements-content-input"
				helpMessage={i18n.translate('if-no-cpus-please-enter-0')}
				label={i18n.translate('number-of-cpus')}
				name="numberOfCPUs"
				onChange={({target: {value}}) =>
					dispatch({
						payload: {key: 'cpu', value},
						type: TYPES.UPDATE_RESOURCE_REQUIREMENTS,
					})
				}
				placeholder={i18n.translate('enter-the-number-of-cpus')}
				type="number"
				value={resourceRequirements.cpu ?? ''}
			/>

			<FormInput
				boldLabel
				className="custom-input resource-requirements-content-input"
				helpMessage={i18n.translate(
					'if-no-ram-required-please-enter-0'
				)}
				label={i18n.translate('ram')}
				name="ram"
				onChange={({target: {value}}) => {
					dispatch({
						payload: {key: 'ram', value},
						type: TYPES.UPDATE_RESOURCE_REQUIREMENTS,
					});
				}}
				placeholder={i18n.translate('enter-the-required-ram')}
				type="number"
				value={resourceRequirements.ram ?? ''}
			/>
		</div>
	);
};

export default ResourceRequirements;
