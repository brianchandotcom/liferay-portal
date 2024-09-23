/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import ProductPurchase from '../../../../../../../components/ProductPurchase';
import i18n from '../../../../../../../i18n';
import ContactSupport from '../components/ContactSupport';
import ProjectRadio from '../components/ProjectRadio';
import {CloudProvisioningOutletContext} from './CloudProvisioningOutlet';

const ProjectSelection = () => {
	const {
		form: {setValue, watch},
		navigate,
		onClickCancel,
		projects,
	} = useOutletContext<CloudProvisioningOutletContext>();

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				cancelButtonProps: {onClick: onClickCancel},
				continueButtonProps: {
					disabled: !watch('project'),
					onClick: () => navigate('./environment'),
				},
			}}
			subtitle={
				<span
					dangerouslySetInnerHTML={{
						__html: i18n.sub('x-available-for-you', 'projects'),
					}}
				/>
			}
			title={i18n.translate('project-selection')}
		>
			<ProjectRadio
				projects={projects}
				selectedProject={watch('project')}
				setValue={setValue}
			/>

			<ContactSupport />
		</ProductPurchase.Shell>
	);
};

export default ProjectSelection;
