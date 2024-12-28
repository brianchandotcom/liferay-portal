/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {PRODUCT_SPECIFICATION_KEY} from '../../../enums/Product';
import {Liferay} from '../../../liferay/liferay';
import consoleOAuth2 from '../../../services/oauth/Console';
import {ConsoleUserProject} from '../../../services/oauth/types';
import {convertSize} from '../../../utils/filesize';
import {getProductSpecificationValue} from '../../../utils/productUtils';

const INSUFICIENT_RESOURCES = 0;

const checkResources = (
	product: DeliveryProduct,
	project: ConsoleUserProject
) => {
	if (!project) {
		return false;
	}

	const instancesAvailable =
		project.rootProjectPlanUsage.instance.limit -
			project.rootProjectPlanUsage?.instance.used >
		INSUFICIENT_RESOURCES;

	if (!instancesAvailable) {
		return false;
	}

	const compareResource = (
		key: keyof ConsoleUserProject['rootProjectPlanUsage'],
		resource: number | string
	) => {
		const limit = project?.rootProjectPlanUsage?.[key].limit ?? 0;
		const used = project?.rootProjectPlanUsage?.[key].used ?? 0;

		return limit - used > Number(resource);
	};

	const cpu = getProductSpecificationValue(
		PRODUCT_SPECIFICATION_KEY.APP_BUILD_NUMBER_OF_CPUS,
		product
	);

	const ram = getProductSpecificationValue(
		PRODUCT_SPECIFICATION_KEY.APP_BUILD_RAM_IN_GBS,
		product,
		'0'
	);

	return (
		compareResource('cpu', cpu) &&
		compareResource('memory', convertSize(ram, 'GB', 'MB'))
	);
};

const useGetResourceInfo = ({
	product,
	selectedProject,
	shouldFetch,
}: {
	product?: any;
	selectedProject?: string;
	shouldFetch: boolean;
}) => {
	const {data: productUsages, isLoading} = useSWR(
		shouldFetch
			? `/product-usages/${Liferay.ThemeDisplay.getUserEmailAddress()}`
			: null,
		() => consoleOAuth2.getProjectsUsage()
	);

	const project = productUsages?.userProjects.find(
		(projects) => projects.rootProjectId === selectedProject
	);

	return {
		hasConsoleProjectsAvailable: !shouldFetch
			? true
			: productUsages?.userProjects.length && !isLoading,
		hasResources: checkResources(product, project as ConsoleUserProject),
		isLoading,
		project,
		resourceRequest: productUsages,
	};
};

export default useGetResourceInfo;
