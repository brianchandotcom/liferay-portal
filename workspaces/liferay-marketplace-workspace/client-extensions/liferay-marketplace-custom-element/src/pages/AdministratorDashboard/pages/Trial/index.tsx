/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';

import Page from '../../../../components/Page';
import i18n from '../../../../i18n';
import InfoCard from '../../components/InfoCard';
import useTrialMetrics from '../../hooks/useTrialMetrics';
import TrialTable from './TrialTable';

const getAvailabilityResourceLabel = (availability: Availability) => {
	if (availability.fallback) {
		return i18n.translate('deactivated');
	}

	return i18n.translate(availability.active ? 'active' : 'limit-reached');
};

const Trial = () => {
	const {availability, isLoading, mutate, orderTableData, totalCount} =
		useTrialMetrics('week');

	return (
		<Page pageRendererProps={{isLoading}}>
			<div className="d-flex flex-column">
				<div className="d-flex info-container justify-content-between mb-6">
					<div
						className={classNames(
							'p-4 d-flex flex-column trial-card w-100'
						)}
					>
						<div className="align-items-start d-flex justify-content-between">
							<h1>{i18n.translate('trial-resources')}</h1>

							<ClayLabel
								displayType={
									availability.active ? 'success' : 'danger'
								}
							>
								{getAvailabilityResourceLabel(availability)}
							</ClayLabel>
						</div>

						<div className="d-flex justify-content-between mt-3 w-100">
							<div className="d-flex">
								<div className="d-flex flex-column mr-3">
									<span className="font-weight-lighter text-black-50">
										{i18n.translate('resources')}
									</span>

									<h2 className="align-items-center d-flex justify-content-center my-0">
										{availability?.resourcesAvailable ?? 0}
									</h2>
								</div>
								<span className="align-items-end d-flex">
									<ClayIcon
										className="text-primary"
										fontSize={32}
										symbol="sheets"
									/>
								</span>
							</div>
							<div className="d-flex">
								<div className="d-flex flex-column mr-3">
									<span className="font-weight-lighter text-black-50">
										{i18n.translate('available')}
									</span>

									<h2 className="align-items-center d-flex justify-content-center my-0">
										{availability?.available ?? 0}
									</h2>
								</div>
								<span className="align-items-end d-flex">
									<ClayIcon
										className="text-primary"
										fontSize={32}
										symbol="plus-squares"
									/>
								</span>
							</div>
							<div className="d-flex">
								<div className="d-flex flex-column mr-3">
									<span className="font-weight-lighter text-black-50">
										{i18n.translate('on-hold')}
									</span>

									<h2 className="align-items-center d-flex justify-content-center my-0">
										{totalCount.onHold}
									</h2>
								</div>
								<span className="align-items-end d-flex">
									<ClayIcon
										className="text-primary"
										fontSize={32}
										symbol="squares-clock"
									/>
								</span>
							</div>
						</div>
					</div>

					<InfoCard
						className="col-2"
						symbol="shopping-cart"
						title={i18n.translate('all-orders')}
						value={totalCount.all}
					/>

					<InfoCard
						className="col-2"
						growth={0}
						symbol="date-time"
						title={i18n.translate('expired')}
						value={totalCount.expired}
					/>
				</div>

				<div className="border d-flex flex-column justify-content-center p-6 rounded-lg">
					<TrialTable
						items={orderTableData?.items || []}
						revalidate={() =>
							mutate((data: any) => data, {revalidate: true})
						}
					/>
				</div>
			</div>
		</Page>
	);
};

export default Trial;
