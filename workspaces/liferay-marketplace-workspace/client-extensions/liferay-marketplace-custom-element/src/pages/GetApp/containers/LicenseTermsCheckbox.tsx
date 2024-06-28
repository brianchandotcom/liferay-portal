/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Checkbox} from '../../../components/Checkbox/Checkbox';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {PRODUCT_SUPPORT_SPECIFICATION_KEY} from '../../../enums/Product';
import {Liferay} from '../../../liferay/liferay';
import {useGetAppContext} from '../GetAppContextProvider';

const LicenseTermsCheckbox = () => {
	const [
		{
			payment: {eulaCheckbox},
			product: {productSpecifications},
		},
		dispatch,
	] = useGetAppContext();
	const {properties} = useMarketplaceContext();

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
						className="cursor-pointer"
						href={
							appUsageTerms?.value
								? formattedProtocolUrl
								: Liferay.ThemeDisplay.getLayoutURL().replace(
										'/get-app',
										`/license-agreement`
									)
						}
						rel="noopener noreferrer"
						target="_blank"
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
