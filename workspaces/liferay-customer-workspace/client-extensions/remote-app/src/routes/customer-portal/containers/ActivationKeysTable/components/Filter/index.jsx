/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';
import i18n from '../../../../../../common/I18n';
import {Button} from '../../../../../../common/components';
import getAvailableFieldsCheckboxs from '../../../../components/CheckboxFilter/utils/getAvailableFieldsCheckboxs';
import DropDownWithDrillDown from '../../../../components/DropDownWithDrillDown';
import SearchBar from '../../../../components/SearchBar';
import {
	getDoesNotExpire,
	getDropDownAvailableFields,
	getEnvironmentType,
	getInstanceSize,
	getProductDescription,
	getStatusActivationTag,
	hasVirtualCluster,
} from '../../utils';
import {hasCluster} from '../../utils/hasCluster';

const MAX_UPDATE = 3;

const Filter = ({activationKeys, filtersState: [filters, setFilters]}) => {
	const countFetchActivationKeysRef = useRef(0);

	const [availableFields, setAvailableFields] = useState({
		environmentTypes: [],
		hasCluster: false,
		hasDNE: false,
		hasVirtualCluster: false,
		instanceSizes: [],
		productVersions: [],
		status: [],
	});

	useEffect(() => {
		if (activationKeys) {
			countFetchActivationKeysRef.current = ++countFetchActivationKeysRef.current;
		}
	}, [activationKeys]);

	useEffect(() => {
		if (
			activationKeys &&
			countFetchActivationKeysRef?.current < MAX_UPDATE
		) {
			setAvailableFields({
				environmentTypes: [
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({productName}) => getEnvironmentType(productName)
					),
					...getAvailableFieldsCheckboxs(
						activationKeys,
						({complimentary}) =>
							getProductDescription(complimentary)
					),
				],
				hasCluster: activationKeys?.some(({licenseEntryType}) =>
					hasCluster(licenseEntryType)
				),
				hasDNE: activationKeys?.some(({expirationDate}) =>
					getDoesNotExpire(expirationDate)
				),
				hasVirtualCluster: activationKeys?.some(({licenseEntryType}) =>
					hasVirtualCluster(licenseEntryType)
				),
				instanceSizes: getAvailableFieldsCheckboxs(
					activationKeys,
					({sizing}) => +getInstanceSize(sizing)
				),
				productVersions: getAvailableFieldsCheckboxs(
					activationKeys,
					({productVersion}) => productVersion
				),
				status: getAvailableFieldsCheckboxs(
					activationKeys,
					(activationKey) =>
						getStatusActivationTag(activationKey)?.title
				),
			});
		}
	}, [activationKeys]);

	return (
		<div className="d-flex flex-column">
			<div className="d-flex">
				<SearchBar
					onSearchSubmit={(term) => {
						setFilters((previousFilters) => ({
							...previousFilters,
							searchTerm: term,
						}));
					}}
				/>

				<DropDownWithDrillDown
					className="align-items-center d-flex"
					initialActiveMenu="x0a0"
					menus={getDropDownAvailableFields(
						availableFields,
						filters,
						setFilters
					)}
					trigger={
						<Button
							borderless
							className="btn-secondary px-3 py-2"
							prependIcon="filter"
						>
							{i18n.translate('filter')}
						</Button>
					}
				/>
			</div>
		</div>
	);
};

export default Filter;
