/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import './index.scss';

import {format, isBefore} from 'date-fns';

import i18n from '../../../../i18n';
import {LicenseKey} from '../../../../services/oauth/types';

type LicenseKeyModalProps = {
	Header: React.ReactNode;
	modalData: LicenseKey;
};

const LicenceKeyModalContent = ({Header, modalData}: LicenseKeyModalProps) => {
	const {
		active,
		expirationDate,
		hostName,
		ipAddresses,
		keyType,
		licenseType = '',
		macAddresses,
		startDate,
	} = modalData;

	const isActive = active && isBefore(new Date(), new Date(expirationDate));

	return (
		<div className="container mkt-license-details-content">
			<div className="mb-7 mt-3">{Header}</div>

			<div className="row">
				<div className="col-3">
					<div className="h4">{i18n.translate('environment')}</div>

					<small className="font-weight-bold">
						{i18n.translate('environment-type')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-lighten mt-1 px-3 py-2 rounded text-capitalize">
						{licenseType.toLowerCase()}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('instance-size')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-lighten mt-1 px-3 py-2 rounded">
						1
					</p>
				</div>

				<div className="col-5">
					<div className="h4">Server</div>

					<small className="font-weight-bold">
						{i18n.translate('key-type')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded">
						{keyType}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('host-name')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded">
						{hostName || '-'}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('ip-addresses')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded wrap-ip">
						{ipAddresses || '-'}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('mac-addresses')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded wrap-mac">
						{macAddresses || '-'}
					</p>
				</div>

				<div className="col-4">
					<div className="h4">
						{' '}
						{i18n.translate('activation-status')}
					</div>

					<small className="font-weight-bold">
						{i18n.translate('status')}
					</small>

					<p
						className={classNames(
							'align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1',
							{
								'text-danger license-paragraph-danger':
									!isActive,
								'text-success license-paragraph-success':
									isActive,
							}
						)}
					>
						{isActive ? 'Activated' : 'Expired'}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('start-date')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded">
						{format(new Date(startDate), 'MMM dd, yyyy')}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('expiration-date')}
					</small>

					<p className="align-items-center d-flex font-weight-bold license-paragraph-gray mt-1 px-3 py-2 rounded">
						{expirationDate
							? format(new Date(expirationDate), 'MMM dd, yyyy')
							: 'DNE'}
					</p>
				</div>
			</div>
		</div>
	);
};

export default LicenceKeyModalContent;
