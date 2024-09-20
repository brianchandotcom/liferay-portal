/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UseFormSetValue} from 'react-hook-form';
import {z} from 'zod';

import NewRadioCard from '../../../../../../../components/RadioCardList/components/RadioCard';
import zodSchema from '../../../../../../../schema/zod';
import {UserProject} from '../Types';

type EnvironmentRadio = {
	selectedEnvironment: string;
	selectedProject: UserProject;
	setValue: UseFormSetValue<z.infer<typeof zodSchema.installProductSchema>>;
};

const EnvironmentRadio: React.FC<EnvironmentRadio> = ({
	selectedEnvironment,
	selectedProject,
	setValue,
}) => {
	const handleSelectRadio = (selectedRadio: RadioOption<any>) => {
		setValue('environment', selectedRadio.value);
	};

	return selectedProject?.environments?.map(
		(projectEnvironment: any, index: number) => {
			const [projectName, environmentId] = projectEnvironment.split('-');

			return (
				<NewRadioCard
					activeRadio={projectEnvironment === selectedEnvironment}
					fullTitle
					key={index}
					leftRadio
					selectRadio={() =>
						handleSelectRadio({
							index,
							value: projectEnvironment,
						})
					}
					title={
						<div className="d-flex justify-content-between w-100">
							<div className="d-flex font-weight-bold">
								<div className="mr-6">
									{projectName.toUpperCase()}
								</div>

								<div className="provisioning-pill text-white">
									{environmentId.toUpperCase()}
								</div>
							</div>
						</div>
					}
				/>
			);
		}
	);
};

export default EnvironmentRadio;
