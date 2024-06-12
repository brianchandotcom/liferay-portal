/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {DealRegistrationColumnKey} from '../../../common/enums/dealRegistrationColumnKey';
import DealRegistrationDTO from '../../../common/interfaces/dto/dealRegistrationDTO';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import useGet from '../../../common/services/liferay/object/useGet';
import getDealDates from '../utils/getDealDates';
import getDealStatus from '../utils/getDealStatus';

export default function useGetListItemsFromDealRegistration(
	page: number,
	pageSize: number,
	urlParams: URLSearchParams
) {
	const swrResponse = useGet<LiferayItems<DealRegistrationDTO[]>>(
		urlParams &&
			`/o/${LiferayAPIs.OBJECT}/${
				ResourceName.LEADS_SALESFORCE
			}?${urlParams.toString()}&page=${page}&pageSize=${pageSize}`
	);

	const listItems = useMemo(
		() =>
			swrResponse.data?.items.map((item) => ({
				[DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME]:
					item.partnerAccountName ? item.partnerAccountName : ' - ',
				[DealRegistrationColumnKey.PARTNER_NAME]:
					item.partnerFirstName && item.partnerFirstName
						? `${
								item.partnerFirstName
									? item.partnerFirstName
									: ''
							}${
								item.partnerLastName
									? ' ' + item.partnerLastName
									: ''
							}`
						: ' - ',
				[DealRegistrationColumnKey.ACCOUNT_NAME]:
					item.prospectAccountName ? item.prospectAccountName : ' - ',
				...getDealDates(item.dateCreated, item.dateCreated),

				[DealRegistrationColumnKey.STATUS]: item.leadStatus
					? getDealStatus(item.leadStatus)
					: ' - ',
				...getDealDates(item.dateCreated, item.dateCreated),
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME]: `${
					item.primaryProspectFirstName
						? item.primaryProspectFirstName
						: ''
				}${
					item.primaryProspectLastName
						? ' ' + item.primaryProspectLastName
						: ''
				}`,
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL]:
					item.primaryProspectEmailAddress
						? item.primaryProspectEmailAddress
						: ' - ',
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE]:
					item.primaryProspectPhone
						? item.primaryProspectPhone
						: ' - ',
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_BUSINESS_UNIT]:
					item.primaryProspectBusinessUnit
						? item.primaryProspectBusinessUnit
						: ' - ',
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_DEPARTMENT]:
					item.primaryProspectDepartment
						? item.primaryProspectDepartment
						: ' - ',
				[DealRegistrationColumnKey.PRIMARY_PROSPECT_JOB_ROLE]:
					item.primaryProspectJobRole
						? item.primaryProspectJobRole
						: ' - ',
				[DealRegistrationColumnKey.STATUS_DETAIL]: item.leadStatusDetail
					? item.leadStatusDetail
					: ' - ',
				[DealRegistrationColumnKey.TYPE]: item.leadType
					? item.leadType
					: ' - ',
				[DealRegistrationColumnKey.PROSPECT_ADDRESS]:
					item.prospectAddress ? item.prospectAddress : ' - ',
				[DealRegistrationColumnKey.PROSPECT_CITY]: item.prospectCity
					? item.prospectCity
					: ' - ',
				[DealRegistrationColumnKey.PROSPECT_INDUSTRY]:
					item.prospectIndustry ? item.prospectIndustry : ' - ',
				[DealRegistrationColumnKey.PROSPECT_COUNTRY]:
					item.prospectCountryCode ? item.prospectCountryCode : ' - ',
				[DealRegistrationColumnKey.PROSPECT_STATE]:
					item.prospectStateCode ? item.prospectStateCode : ' - ',
				[DealRegistrationColumnKey.PROSPECT_POSTAL_CODE]:
					item.prospectPostalCode ? item.prospectPostalCode : ' - ',
				[DealRegistrationColumnKey.ADDITIONAL_CONTACTS]:
					item.additionalContacts ? item.additionalContacts : ' - ',
				[DealRegistrationColumnKey.ISCONVERTED]: item.isConverted
					? item.isConverted
					: false,
				[DealRegistrationColumnKey.EXTERNAL_REFERENCE_CODE]:
					item.externalReferenceCode
						? item.externalReferenceCode
						: undefined,
			})),
		[swrResponse.data?.items]
	);

	return {
		...swrResponse,
		data: {
			...swrResponse.data,
			items: listItems,
		},
	};
}
