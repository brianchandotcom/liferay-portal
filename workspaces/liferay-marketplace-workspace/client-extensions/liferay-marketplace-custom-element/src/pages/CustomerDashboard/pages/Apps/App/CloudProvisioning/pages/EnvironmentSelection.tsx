/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';

import ProductPurchase from '../../../../../../../components/ProductPurchase';
import i18n from '../../../../../../../i18n';
import ContactSupport from '../components/ContactSupport';
import EnvironmentRadio from '../components/EnvironmentRadio';
import {CloudProvisioningOutletContext} from './CloudProvisioningOutlet';

const EnvironmentSelection = () => {
	const {
		form: {setValue, watch},
		navigate,
		onClickCancel,
		onSubmit,
	} = useOutletContext<CloudProvisioningOutletContext>();

	const project = watch('project');

	useEffect(() => {
		if (!project) {
			navigate('');
		}
	}, [navigate, project]);

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {onClick: () => navigate('')},
				cancelButtonProps: {onClick: onClickCancel},
				continueButtonProps: {
					children: i18n.translate('install'),
					disabled: !watch('environment'),
					onClick: onSubmit,
				},
			}}
			subtitle={
				<span
					dangerouslySetInnerHTML={{
						__html: i18n.sub('x-available-for-you', 'environments'),
					}}
				/>
			}
			title={i18n.translate('environment-selection')}
		>
			<EnvironmentRadio
				selectedEnvironment={watch('environment')}
				selectedProject={watch('project')}
				setValue={setValue}
			/>

			<ContactSupport />
		</ProductPurchase.Shell>
	);
};

export default EnvironmentSelection;
