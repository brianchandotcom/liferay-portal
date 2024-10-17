/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Navigate, useOutletContext} from 'react-router-dom';
import {useGetMyUserAccount} from '~/common/services/liferay/graphql/user-accounts';
import i18n from '../../../../common/I18n';
import Table from '../../../../common/components/Table';
import {getOrRequestToken} from '../../../../common/services/liferay/security/auth/getOrRequestToken';
import {useCustomerPortal} from '../../context';
import useGetActivationKeysData from '../ActivationKeysTable/hooks/useGetActivationKeysData';
import usePagination from '../ActivationKeysTable/hooks/usePagination';
import useStatusCountNavigation from '../ActivationKeysTable/hooks/useStatusCountNavigation';
import {
	EnvironmentTypeColumn,
	ExpirationDateColumn,
	KeyTypeColumn,
	StatusColumn,
} from '../ActivationKeysTable/utils/constants/columns-definitions';
import {getTooltipContentRenderer} from '../ActivationKeysTable/utils/getTooltipContentRenderer';
import {hasAdminOrPartnerManager} from '../ActivationKeysTable/utils/hasAdminOrPartnerManager';
import {hasAdminUserAccount} from '../ActivationKeysTable/utils/hasAdminUserAccount';
import DeactivateKeysSkeleton from './Skeleton';
import DeactivateKeysTableFooter from './components/Footer';
import DeactivationKeysTableHeader from './components/Header';
import useFilters from './components/Header/hooks/useFilters';
import {DEACTIVATE_COLUMNS} from './utils/constants';

const DeactivateKeysTable = ({initialFilter, productName}) => {
	const {data: myAccount} = useGetMyUserAccount();
	const [oAuthToken, setOAuthToken] = useState();
	const [{project, userAccount}] = useCustomerPortal();
	const {setHasSideMenu} = useOutletContext();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	useEffect(() => {
		setHasSideMenu(false);
	}, [setHasSideMenu]);

	const {
		activationKeysState: [activationKeys, setActivationKeys],
		loading,
		setFilterTerm,
	} = useGetActivationKeysData(project, initialFilter);

	const {
		statusfilterByTitle: [statusFilter],
	} = useStatusCountNavigation(activationKeys);

	const {activationKeysByStatusPaginated, paginationConfig} = usePagination(
		activationKeys,
		statusFilter
	);

	const [filters, setFilters] = useFilters(
		setFilterTerm,
		productName,
		initialFilter
	);

	const [activationKeysIdChecked, setActivationKeysIdChecked] = useState([]);

	const activationKeysByStatusPaginatedChecked = useMemo(
		() =>
			activationKeys.filter(({id}) =>
				activationKeysIdChecked.includes(id)
			) || [],
		[activationKeys, activationKeysIdChecked]
	);

	const getDeactivationKeysRows = useCallback(
		(activationKey) => ({
			envName: (
				<div title={[activationKey.name, activationKey.description]}>
					<p className="font-weight-bold m-0 text-neutral-10 text-truncate">
						{activationKey.name}
					</p>

					<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
						{activationKey.description}
					</p>
				</div>
			),
			envType: <EnvironmentTypeColumn activationKey={activationKey} />,
			expirationDate: (
				<ExpirationDateColumn activationKey={activationKey} />
			),
			id: activationKey.id,
			keyType: <KeyTypeColumn activationKey={activationKey} />,
			status: <StatusColumn activationKey={activationKey} />,
		}),
		[]
	);

	const isAdminUserAccount = hasAdminUserAccount(myAccount);

	const isAdminOrPartnerManager = hasAdminOrPartnerManager(
		project,
		userAccount
	);

	if (!isAdminUserAccount && !isAdminOrPartnerManager) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
	}

	return (
		<div className="deactivate-table">
			<div className="d-flex flex-column">
				<div className="text-left">
					<h3>
						{i18n.sub('deactivate-x-activation-keys', [
							productName,
						])}
					</h3>

					<p>
						{i18n.translate(
							'select-the-activation-key-you-want-to-deactivate'
						)}
					</p>
				</div>
			</div>

			<ClayTooltipProvider
				contentRenderer={({title}) => getTooltipContentRenderer(title)}
				delay={100}
			>
				<div>
					<div className="mt-4 py-2">
						<DeactivationKeysTableHeader
							activationKeysState={[
								activationKeys,
								setActivationKeys,
							]}
							filterState={[filters, setFilters]}
							loading={loading}
						/>
					</div>

					{!!activationKeysByStatusPaginated.length && (
						<Table
							checkboxConfig={{
								checkboxesChecked: activationKeysIdChecked,
								setCheckboxesChecked:
									setActivationKeysIdChecked,
							}}
							className="border-0 cp-activation-key-table"
							columns={DEACTIVATE_COLUMNS}
							hasCheckbox
							hasPagination
							isLoading={loading}
							paginationConfig={paginationConfig}
							rows={activationKeysByStatusPaginated.map(
								(activationKey) =>
									getDeactivationKeysRows(activationKey)
							)}
						/>
					)}
				</div>
			</ClayTooltipProvider>

			<DeactivateKeysTableFooter
				accountKey={project?.accountKey}
				activationKeysByStatusPaginatedChecked={
					activationKeysByStatusPaginatedChecked
				}
				activationKeysState={[setActivationKeys]}
				oAuthToken={oAuthToken}
				productName={productName}
			/>
		</div>
	);
};

DeactivateKeysTable.Skeleton = DeactivateKeysSkeleton;

export default DeactivateKeysTable;
