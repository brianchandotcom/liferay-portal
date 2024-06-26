/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import useSWR from 'swr';

import {Checkbox} from '../../../components/Checkbox/Checkbox';
import {ContentModal} from '../../../components/ContentModal/ContentModal';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {PRODUCT_SUPPORT_SPECIFICATION_KEY} from '../../../enums/Product';
import i18n from '../../../i18n';
import {getEulaDescription} from '../../../utils/util';
import {useGetAppContext} from '../GetAppContextProvider';

const LicenseTermsCheckbox = () => {
	const [
		{
			payment: {eulaCheckbox},
			product: {productSpecifications},
		},
		dispatch,
	] = useGetAppContext();
	const {data: eula = ''} = useSWR('/eula', getEulaDescription);
	const {properties} = useMarketplaceContext();
	const eulaModal = useModal();

	const appUsageTerms = productSpecifications?.find(
		(specification) =>
			specification?.specificationKey ===
			PRODUCT_SUPPORT_SPECIFICATION_KEY.APP_USAGE_TERMS_URL
	);

	const formattedProtocolUrl = appUsageTerms?.value?.startsWith('https://')
		? appUsageTerms?.value
		: 'https://' + appUsageTerms?.value;

	return (
		<>
			{eulaModal.open && (
				<ContentModal
					description={eula}
					header={i18n.translate('end-user-license-agreement')}
					{...eulaModal}
				/>
			)}

			<div className="align-items-start d-flex eula-container mt-4">
				<Checkbox
					checked={eulaCheckbox}
					onChange={() =>
						dispatch({
							payload: !eulaCheckbox,
							type: 'SET_EULA_CHECKBOX',
						})
					}
				/>
				<span>
					I have read and agree to the
					<a
						onClick={() =>
							appUsageTerms?.value
								? window.open(
										formattedProtocolUrl as string,
										'_blank'
									)
								: eulaModal.onOpenChange(true)
						}
						rel="noopener noreferrer"
					>
						&nbsp;End User License Agreement&nbsp;
					</a>
					and the
					<a
						href={properties.eulaBaseURL}
						rel="noopener noreferrer"
						target="_blank"
					>
						&nbsp;Terms&nbsp;
					</a>
					of Service.
				</span>
			</div>
		</>
	);
};

export default LicenseTermsCheckbox;
