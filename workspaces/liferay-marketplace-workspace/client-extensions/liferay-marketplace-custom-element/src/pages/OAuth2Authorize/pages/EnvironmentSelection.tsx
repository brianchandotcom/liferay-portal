/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import {Navigate, useNavigate} from 'react-router-dom';

import RadioCard from '../../../components/RadioCardList/components/RadioCard';
import i18n from '../../../i18n';
import ContactSupport from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/ContactSupport';
import SelectedProjectBanner from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/SelectedProjectBanner';
import {ConsoleUserProjectWithExtension} from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/pages/CloudProvisioningOutlet';
import {ProductCardRevamp} from '../../GetApp/components/ProductCard/ProductCard';
import {useOAuth2OutletContext} from '../OAuth2AuthorizeOutlet';

const EnvironmentSelection = () => {
	const {
		environment,
		myUserAccount,
		project,
		selectedAccount,
		setValue,
		singleProject,
	} = useOAuth2OutletContext();

	const navigate = useNavigate();

	if (!selectedAccount || !project) {
		return <Navigate to="/" />;
	}

	return (
		<div>
			<ProductCardRevamp
				icon={selectedAccount?.logoURL as string}
				subtitle={myUserAccount.name}
				title={selectedAccount?.name as string}
			>
				<SelectedProjectBanner
					project={project as ConsoleUserProjectWithExtension}
				/>
			</ProductCardRevamp>

			<div className="border my-7 p-3 pb-3 pt-6 rounded">
				<h1 className="d-flex justify-content-center">
					{i18n.translate('environment-selection')}
				</h1>

				<small className="d-flex py-4">
					Environments available in
					<strong className="ml-1">{project?.rootProjectId}</strong>
				</small>

				{project?.environments?.map((projectEnvironment, index) => {
					const handleSelectRadio = (
						selectedRadio: RadioOption<{
							isExtensionEnvironment: boolean;
							projectId: string;
						}>
					) => setValue('environment', selectedRadio.value);

					const [, environmentName = ''] =
						projectEnvironment.projectId.split('-');

					return (
						<RadioCard
							activeRadio={
								projectEnvironment.projectId ===
								environment?.projectId
							}
							disabled={false}
							key={index}
							leftRadio
							selectRadio={() =>
								handleSelectRadio({
									index,
									value: projectEnvironment,
								})
							}
							title={
								<>
									<span className="h5 mr-3">
										{project.rootProjectId.toUpperCase()}
									</span>

									<ClayBadge
										className="text-uppercase"
										label={environmentName.toUpperCase()}
									/>
								</>
							}
						/>
					);
				})}

				<ContactSupport />

				<div className="d-flex justify-content-end mt-4">
					<ClayButton
						className="btn-outline-secondary mr-3"
						onClick={() =>
							navigate(singleProject ? '/' : '/project-selection')
						}
					>
						{i18n.translate('back')}
					</ClayButton>

					<ClayButton
						disabled={!environment}
						onClick={() => navigate('/congratulations')}
					>
						{i18n.translate('continue')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default EnvironmentSelection;
