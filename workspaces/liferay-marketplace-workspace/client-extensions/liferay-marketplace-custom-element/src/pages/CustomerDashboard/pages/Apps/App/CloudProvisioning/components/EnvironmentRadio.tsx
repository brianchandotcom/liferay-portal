/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {UseFormSetValue} from 'react-hook-form';
import {z} from 'zod';

import RadioCard from '../../../../../../../components/RadioCardList/components/RadioCard';
import zodSchema from '../../../../../../../schema/zod';
import {ConsoleUserProject} from '../../../../../../../services/oauth/types';

type EnvironmentRadio = {
	selectedEnvironment?: z.infer<
		typeof zodSchema.installProductSchema
	>['environment'];
	selectedProject: ConsoleUserProject;
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

	return selectedProject?.environments?.map((projectEnvironment, index) => {
		const [projectName = '', environment = ''] =
			projectEnvironment.projectId.split('-');

		return (
			<RadioCard
				activeRadio={
					projectEnvironment.projectId ===
					selectedEnvironment?.projectId
				}
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
							{projectName.toUpperCase()}
						</span>

						<ClayBadge
							className="text-uppercase"
							label={environment}
						>
							{environment}
						</ClayBadge>
					</>
				}
			/>
		);
	});
};

export default EnvironmentRadio;
