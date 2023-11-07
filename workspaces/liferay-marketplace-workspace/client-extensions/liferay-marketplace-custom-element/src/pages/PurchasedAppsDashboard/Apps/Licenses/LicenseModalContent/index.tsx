/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import classNames from 'classnames';

import './index.scss';
import {ModalDataProps} from '../Licenses';
import i18n from '../../../../../i18n';

type LicenseKeyModalProps = {
	modalData: ModalDataProps;
	Header: React.FC;
};

const LicenceKeyModalContent = ({modalData, Header}: LicenseKeyModalProps) => {
	const {
		environmentType,
		instanceSize,
		keyType,
		hostName,
		ipAddresses,
		macAddresses,
		status,
		startDate,
		expirationDate,
	} = modalData;

	return (
		<div className="container mkt-license-details-content">
			<div className="mb-5 mt-3">
				<Header />
			</div>

			<div className="row">
				<div className="col-3">
					<h4>{i18n.translate('environment')}</h4>

					<small className="font-weight-bold">
						{i18n.translate('environment-type')}
					</small>

					<p className="license-paragraph-lighten align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{environmentType}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('instance-size')}
					</small>

					<p className="license-paragraph-lighten align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{instanceSize}
					</p>
				</div>

				<div className="col-5">
					<h4>Server</h4>

					<small className="font-weight-bold">
						{i18n.translate('key-type')}
					</small>

					<p className="license-paragraph-gray align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{keyType}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('host-name')}
					</small>

					<p className="license-paragraph-gray align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{hostName}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('ip-addresses')}
					</small>

					<p className="license-paragraph-gray wrap-ip align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{ipAddresses}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('mac-addresses')}
					</small>

					<p className="license-paragraph-gray wrap-mac align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{macAddresses}
					</p>
				</div>

				<div className="col-4">
					<h4> {i18n.translate('activation-status')}</h4>

					<small className="font-weight-bold">
						{i18n.translate('status')}
					</small>

					<p
						className={classNames(
							'align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1',
							{
								'text-success license-paragraph-success':
									status === 'Activated',
								'text-danger license-paragraph-danger':
									status === 'Expired',
							}
						)}
					>
						{status}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('start-date')}
					</small>

					<p className="license-paragraph-gray align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{startDate}
					</p>

					<small className="font-weight-bold">
						{i18n.translate('expiration-date')}
					</small>

					<p className="license-paragraph-gray align-items-center d-flex px-3 py-2 rounded font-weight-bold mt-1">
						{expirationDate}
					</p>
				</div>
			</div>
		</div>
	);
};

export default LicenceKeyModalContent;
