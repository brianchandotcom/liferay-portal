/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo, useState} from 'react';
import {Link} from 'react-router-dom';
import {Button} from '~/common/components';
import i18n from '../../../../../../common/I18n';
import DeactivateButton from '../DeactivateButton';

const ACTIVATION_ROOT_ROUTER = 'activation';

const DeactivateKeysTableFooter = ({
	accountKey,
	activationKeysByStatusPaginatedChecked,
	activationKeysState,
	oAuthToken,
	productName,
}) => {
	const [status, setStatus] = useState({
		deactivate: '',
	});
	const [setActivationKeys] = activationKeysState;

	const urlPreviousPage = `/${accountKey}/${ACTIVATION_ROOT_ROUTER}/${productName.toLowerCase()}`;

	const handleDeactivate = useCallback(
		() =>
			setActivationKeys((previousActivationKeys) =>
				previousActivationKeys.filter(
					(activationKey) =>
						!activationKeysByStatusPaginatedChecked.find(
							({id}) => activationKey.id === id
						)
				)
			),
		[activationKeysByStatusPaginatedChecked, setActivationKeys]
	);

	const filterCheckedActivationKeys = useMemo(
		() =>
			activationKeysByStatusPaginatedChecked.reduce(
				(
					filterCheckedActivationKeysAccumulator,
					activationKeyChecked,
					index
				) =>
					`${filterCheckedActivationKeysAccumulator}${
						index > 0 ? '&' : ''
					}licenseKeyIds=${activationKeyChecked.id}`,
				''
			),

		[activationKeysByStatusPaginatedChecked]
	);

	return (
		<div className="d-flex justify-content-between">
			<Link to={urlPreviousPage}>
				<Button
					className="btn btn-borderless btn-style-neutral"
					displayType="secondary"
				>
					{i18n.translate('cancel')}
				</Button>
			</Link>

			<DeactivateButton
				activationKeysByStatusPaginatedChecked={
					activationKeysByStatusPaginatedChecked
				}
				deactivateKeysStatus={status.deactivate}
				filterCheckedActivationKeys={filterCheckedActivationKeys}
				handleDeactivate={handleDeactivate}
				oAuthToken={oAuthToken}
				setDeactivateKeysStatus={(value) =>
					setStatus((previousStatus) => ({
						...previousStatus,
						deactivate: value,
					}))
				}
				urlPreviousPage={urlPreviousPage}
			/>
		</div>
	);
};

export default DeactivateKeysTableFooter;
