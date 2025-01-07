/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import RadioCard from '../../../components/RadioCardList/components/RadioCard';
import ContactSupport from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/ContactSupport';
import SelectedProjectBanner from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/components/SelectedProjectBanner';
import {ProductCardRevamp} from '../../GetApp/components/ProductCard/ProductCard';
import {EnvironmentSelectionStepType} from '../types';

const EnvironmentSelectionStep = ({
	environment,
	myUserAccount,
	project,
	selectedAccount,
	setValue,
}: EnvironmentSelectionStepType) => {
	const navigate = useNavigate();

	useEffect(() => {
		if (!selectedAccount) {
			navigate('/');
		}
	}, [selectedAccount, navigate]);

	return (
		<div>
			<ProductCardRevamp
				icon={selectedAccount?.logoURL || ''}
				subtitle={myUserAccount.name}
				title={selectedAccount?.name || 'Account Name'}
			>
				<SelectedProjectBanner project={project} />
			</ProductCardRevamp>
			<div className="border my-7 p-3 py-6 rounded">
				<h1 className="d-flex justify-content-center">
					Environment Selection
				</h1>

				<p className="d-flex">
					Environments available in{' '}
					<strong>{project?.rootProjectId}</strong>{' '}
				</p>

				{project?.environments?.map((projectEnvironment, index) => {
					const handleSelectRadio = (
						selectedRadio: RadioOption<{
							isExtensionEnvironment: boolean;
							projectId: string;
						}>
					) => setValue('environment', selectedRadio.value);

					const [projectName = '', environmentName = ''] =
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
									<div>
										<span className="h5 mr-3">
											{projectName.toUpperCase()}
										</span>

										<ClayBadge
											className="text-uppercase"
											label={environmentName}
										>
											{environmentName}
										</ClayBadge>
									</div>
								</>
							}
						/>
					);
				})}

				<ContactSupport />

				<div className="d-flex justify-content-end mt-4">
					<ClayButton
						className="btn-outline-secondary mr-3"
						onClick={() => navigate('/project-selection')}
					>
						Back
					</ClayButton>

					<ClayButton
						disabled={!environment}
						onClick={() => navigate('/congratulations')}
					>
						Continue
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default EnvironmentSelectionStep;
