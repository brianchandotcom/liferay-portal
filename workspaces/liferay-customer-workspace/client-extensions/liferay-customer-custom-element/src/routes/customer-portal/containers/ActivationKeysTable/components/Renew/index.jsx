/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import i18n from '~/common/I18n';

const RenewButton = ({
	activationKeysByStatusPaginatedChecked,
	activationKeysChecked,
	bulkRenewAvailable,
	children,
	currentActivationKeyModal,
	filterCheckedActivationKeys,
	identifier,
	isComplimentaryKey,
	isRenewTable,
	isVisibleModal,
	keysSelectedCount,
	project,
	renewKeysFilterChecked,
}) => {
	const navigate = useNavigate();
	const [isDisable, setIsDisable] = useState('');

	const renewUrl = `/${project?.accountKey}/activation/dxp/new`;

	useEffect(() => {
		const isDisableRenewButton = () => {
			if (isRenewTable) {
				if (keysSelectedCount > 1 && !bulkRenewAvailable) {
					return setIsDisable(true);
				}

				if (!keysSelectedCount) {
					return setIsDisable(true);
				}
			}

			return setIsDisable(false);
		};

		isDisableRenewButton();
	}, [
		bulkRenewAvailable,
		isComplimentaryKey,
		isRenewTable,
		keysSelectedCount,
	]);

	const handleRedirectPage = () => {
		if (isVisibleModal) {
			return navigate(renewUrl, {
				state: {
					activationKeys: [currentActivationKeyModal],
					id: identifier,
				},
			});
		}

		if (isRenewTable) {
			if (activationKeysChecked.length === 1) {
				return navigate(renewUrl, {
					state: {
						activationKeys: [activationKeysChecked[0]],
						id: identifier,
					},
				});
			}

			return navigate(renewUrl, {
				state: {
					activationKeys: activationKeysChecked,
					id: identifier,
					renewKeysFilterChecked,
				},
			});
		}

		navigate('new', {
			state: {
				activationKeys: activationKeysByStatusPaginatedChecked,
				filterCheckedActivationKeys,
				id: identifier,
			},
		});
	};

	return (
		<>
			<ClayButton
				aria-label={i18n.translate('renew')}
				className={classNames('btn mx-2 px-3 py-2', {
					'btn-outline-dark cp-deactivate-button  text-dark':
						!isVisibleModal && !isRenewTable,
				})}
				disabled={isDisable}
				onClick={() => {
					handleRedirectPage();
				}}
			>
				{children}
			</ClayButton>
		</>
	);
};

export default RenewButton;
