/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../../../liferay/liferay';
import consoleOAuth2 from '../../../services/oauth/Console';

const INSUFICIENT_RESOURCES = 0;
const ONE_GB = 1024;

const compareResource = (required: number, avaliable: number) =>
	avaliable >= required;

type convertMegabyteToGigabyteProps = {
	inverseOperation?: boolean;
	value: number;
};

const convertMegabyteToGigabyte = ({
	inverseOperation = false,
	value,
}: convertMegabyteToGigabyteProps) => {
	if (inverseOperation) {
		return Number((value / ONE_GB).toFixed(2));
	}

	return value * ONE_GB;
};

const useGetResourceInfo = ({
	product,
	selectedProject,
	shouldFetch,
}: {
	product: any;
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

	const suficientInstances =
		project &&
		project?.rootProjectPlanUsage?.instance?.limit -
			project?.rootProjectPlanUsage?.instance?.used >
			INSUFICIENT_RESOURCES;

	let validateRamAndCpu = false;

	if (project && selectedProject) {
		validateRamAndCpu = ['ram', 'cpu']
			.map(
				(requirement) =>
					product?.productSpecifications.find(
						(specification: ProductSpecification) =>
							specification.specificationKey === requirement
					) || {}
			)
			.some((requirement) => {
				if (requirement.specificationKey === 'ram') {
					return compareResource(
						convertMegabyteToGigabyte({
							inverseOperation: true,
							value: requirement.value,
						}),
						project?.rootProjectPlanUsage?.memory.limit -
							project?.rootProjectPlanUsage?.memory.used
					);
				}

				if (requirement.specificationKey === 'cpu') {
					return compareResource(
						requirement.value,
						project?.rootProjectPlanUsage?.cpu.limit -
							project?.rootProjectPlanUsage?.cpu.used
					);
				}
			});
	}

	return {
		hasConsoleProjectsAvailable: !shouldFetch
			? true
			: productUsages?.userProjects.length && !isLoading,
		hasResources: suficientInstances && validateRamAndCpu,
		isLoading,
		project,
		resourceRequest: productUsages,
	};
};

export {convertMegabyteToGigabyte};

export default useGetResourceInfo;
