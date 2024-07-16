/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../common/I18n';
import {Skeleton, StatusTag} from '../../../../../../common/components';
import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../common/utils/getDateCustomFormat';
import getKebabCase from '../../../../../../common/utils/getKebabCase';

const statusReport = {
	[SLA_STATUS_TYPES.active]: i18n.translate('ends-on'),
	[SLA_STATUS_TYPES.future]: i18n.translate('starts-on'),
	[SLA_STATUS_TYPES.expired]: i18n.translate('ended-on'),
};

const ProjectCard = ({compressed, loading, onClick, ...koroneikiAccount}) => {
	const showSLAStatus = Boolean(
		koroneikiAccount.slaCurrent ||
			koroneikiAccount.slaExpired ||
			koroneikiAccount.slaFuture
	);

	const SLAStatus = () => {
		if (loading) {
			return <Skeleton height={20} width={54} />;
		}

		return <StatusTag currentStatus={koroneikiAccount.status} />;
	};

	const SLAStatusDate = () => {
		if (loading) {
			return <Skeleton className="mt-1" height={20} width={100} />;
		}

		const displayDate = {
			[SLA_STATUS_TYPES.active]: koroneikiAccount.slaCurrentEndDate,
			[SLA_STATUS_TYPES.future]: koroneikiAccount.slaFutureStartDate,
			[SLA_STATUS_TYPES.expired]: koroneikiAccount.slaExpiredEndDate,
		};

		return (
			<div className="text-neutral-5 text-paragraph-sm">
				{statusReport[koroneikiAccount.status]}

				<span className="font-weight-bold ml-1 text-paragraph">
					{getDateCustomFormat(
						displayDate[koroneikiAccount.status],
						FORMAT_DATE_TYPES.day2DMonthSYearN
					)}
				</span>
			</div>
		);
	};

	const SupportRegion = () => {
		if (loading) {
			return <Skeleton className="mt-1" height={20} width={120} />;
		}

		return (
			<div className="text-align-end text-neutral-5 text-paragraph-sm">
				{i18n.translate('support-region')}

				<span className="font-weight-bold ml-1">
					{i18n.translate(getKebabCase(koroneikiAccount.region))}
				</span>
			</div>
		);
	};

	return (
		<ClayCard
			className={classNames(
				'border border-brand-primary-lighten-4 card-interactive shadow-none',
				{
					'card-horizontal mb-3': compressed,
					'cp-project-card-lg mr-5 mb-4': !compressed,
				}
			)}
			onClick={onClick}
		>
			<ClayCard.Body
				className={classNames({
					'mx-2 py-4 my-3': !compressed,
					'py-4': compressed,
				})}
			>
				<ClayCard.Row
					className={classNames({
						'flex-column': !compressed,
					})}
				>
					<div
						className={classNames('text-truncate-inline', {
							'autofit-col autofit-col-expand': compressed,
						})}
					>
						<h4 className="mb-1 text-neutral-7 text-truncate">
							{loading ? (
								<Skeleton
									className="mb-1"
									height={34}
									width={300}
								/>
							) : (
								koroneikiAccount.name
							)}
						</h4>

						{compressed &&
							(loading ? (
								<Skeleton
									className="mb-1"
									height={24}
									width={120}
								/>
							) : (
								<div className="text-neutral-5 text-paragraph text-truncate text-uppercase">
									{koroneikiAccount.code}
								</div>
							))}
					</div>

					<div
						className={classNames({
							'autofit-col text-right align-items-end h-100': compressed,
							'd-block': !loading,
							'mt-6 pt-3': !compressed,
						})}
					>
						{showSLAStatus && <SLAStatus />}

						{showSLAStatus && <SLAStatusDate />}

						{compressed && <SupportRegion />}
					</div>
				</ClayCard.Row>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default memo(ProjectCard);
