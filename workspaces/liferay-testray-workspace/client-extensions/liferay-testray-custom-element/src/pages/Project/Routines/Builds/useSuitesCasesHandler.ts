/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Dispatch,
	SetStateAction,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';
import SearchBuilder from '~/core/SearchBuilder';
import {
	TestraySuite,
	testrayCaseImpl,
	testraySuiteCaseImpl,
	testraySuiteImpl,
} from '~/services/rest';
import {getUniqueList} from '~/util';

import {
	getCaseParameters,
	getCaseValues,
} from '../../Suites/useSuiteCaseFilter';

export enum TestraySuiteTypes {
	SMART = 'smart',
	STATIC = 'static',
}

const useSuitesCasesHandler = (
	buildId: string | undefined,
	caseIds: number[],
	setCaseIds: Dispatch<SetStateAction<number[]>>,
	setCaseIdsLoading: Dispatch<SetStateAction<boolean>>,
	setTotalCases?: Dispatch<SetStateAction<number>>
) => {
	const [selectedCaseIds, setSelectedCaseIds] = useState<number[]>([]);
	const [suites, setSuites] = useState<TestraySuite[]>([]);
	const [filter, setFilter] = useState<string>('');
	const [modalContext, setModalContext] = useState<'suites' | 'cases'>(
		'cases'
	);

	const caseLock = useMemo(() => {
		let locked = false;

		const lockFunction = (casesNumber?: number) => {
			if (casesNumber && !locked) {
				locked = true;

				return false;
			}

			return locked;
		};

		return lockFunction;
	}, []);

	const setCaseIdsState = useCallback(
		(newCaseIds: number[], updateCases = true) => {
			if (newCaseIds.length) {
				if (updateCases) {
					setCaseIds(newCaseIds);
				}

				return setSelectedCaseIds(newCaseIds);
			}

			setCaseIds([]);
			setSelectedCaseIds([]);
		},
		[setCaseIds]
	);

	const setSuitesState = useCallback(
		(newSuiteIds: number[]) => {
			if (newSuiteIds.length) {
				return testraySuiteImpl
					.getAll({
						fields: 'id,caseParameters,type',
						filter: SearchBuilder.in('id', newSuiteIds),
					})
					.then((response) => {
						if (response?.totalCount) {
							setSuites(response.items);
						}
					})
					.catch(console.error);
			}

			setSuites([]);
			if (setTotalCases) {
				setTotalCases(selectedCaseIds.length);
			}
		},
		[selectedCaseIds, setTotalCases]
	);

	const setSuitesCases = (idsArray: number[]) => {
		switch (modalContext) {
			case 'suites':
				setSuitesState(idsArray);
				break;

			default:
				setCaseIdsState(idsArray);
				break;
		}
	};

	useEffect(() => {
		if (buildId && caseIds.length && !caseLock(caseIds.length)) {
			setCaseIdsState(caseIds, false);
		}
	}, [buildId, caseIds, caseLock, setCaseIdsState]);

	useEffect(() => {
		(async () => {
			if (suites.length || selectedCaseIds.length) {
				let casesAggregation: number[] = [...selectedCaseIds];
				const caseParametersAggregation: {[keys: string]: string[]} = {
					testrayCaseTypes: [],
					testrayComponents: [],
					testrayRequirements: [],
				};

				for (const testraySuite of suites) {
					if (testraySuite.type === TestraySuiteTypes.STATIC) {
						const response = await testraySuiteCaseImpl.getAll({
							fields: 'r_caseToSuitesCases_c_caseId',
							filter: SearchBuilder.eq(
								'suiteId',
								testraySuite.id
							),
						});

						if (response?.totalCount) {
							const newCaseIds = response?.items.map(
								(suitesCases) =>
									suitesCases.r_caseToSuitesCases_c_caseId
							);

							casesAggregation = getUniqueList([
								...casesAggregation,
								...newCaseIds,
							]);
						}

						continue;
					}

					const caseParameters = getCaseParameters(testraySuite);

					if (caseParameters?.testrayCaseTypes?.length) {
						caseParametersAggregation.testrayCaseTypes.push(
							...getCaseValues(caseParameters.testrayCaseTypes)
						);
					}

					if (caseParameters?.testrayComponents?.length) {
						caseParametersAggregation.testrayComponents.push(
							...getCaseValues(caseParameters.testrayComponents)
						);
					}

					if (caseParameters?.testrayRequirements?.length) {
						caseParametersAggregation.testrayRequirements.push(
							...getCaseValues(caseParameters.testrayRequirements)
						);
					}
				}

				const searchBuilder = new SearchBuilder();

				if (caseParametersAggregation?.testrayCaseTypes?.length) {
					searchBuilder
						.in(
							'caseTypeId',
							getUniqueList(
								caseParametersAggregation.testrayCaseTypes
							)
						)
						.or();
				}

				if (caseParametersAggregation?.testrayComponents?.length) {
					searchBuilder
						.in(
							'componentId',
							getUniqueList(
								caseParametersAggregation.testrayComponents
							)
						)
						.or();
				}

				if (caseParametersAggregation?.testrayRequirements?.length) {
					searchBuilder
						.in(
							'requerimentsId',
							getUniqueList(
								caseParametersAggregation.testrayRequirements
							)
						)
						.or();
				}

				if (casesAggregation.length) {
					searchBuilder.in('id', getUniqueList(casesAggregation));
				}

				return setFilter(searchBuilder.build());
			}

			setCaseIds([]);
			setFilter('');
		})();
	}, [suites, selectedCaseIds, setCaseIds]);

	useEffect(() => {
		(async () => {
			if (filter && modalContext === 'suites') {
				setCaseIdsLoading(true);
				setCaseIds(selectedCaseIds);

				await testrayCaseImpl
					.getAll({
						fields: 'id',
						filter,
						pageSize: '500',
					})
					.then((response) => {
						if (response?.totalCount) {
							const newIds = response.items.map(({id}) => id);

							if (setTotalCases) {
								setTotalCases(response?.totalCount);
							}

							setCaseIds((previousCases) =>
								getUniqueList([...previousCases, ...newIds])
							);
						}
					})
					.catch(console.error);

				setCaseIdsLoading(false);
			}
		})();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filter, setCaseIds]);

	return {
		filter,
		setModalContext,
		suiteIds: suites.map(({id}) => id),
		suitesCasesHandler: setSuitesCases,
	};
};

export default useSuitesCasesHandler;
