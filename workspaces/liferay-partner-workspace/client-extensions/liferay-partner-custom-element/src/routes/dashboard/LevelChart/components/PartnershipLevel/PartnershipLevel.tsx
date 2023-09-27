/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import AuthorizedPartnerIcon from '../../../../../common/components/dashboard/components/icons/AuthorizedPartnerIcon';
import GoldPartnerIcon from '../../../../../common/components/dashboard/components/icons/GoldPartnerIcon';
import PlatinumPartnerIcon from '../../../../../common/components/dashboard/components/icons/PlatinumPartnerIcon';
import SilverPartnerIcon from '../../../../../common/components/dashboard/components/icons/SilverPartnerIcon';
import {ChartTypes} from '../../../../../common/components/dashboard/enums/chartTypes';
import {PartnershipLevels} from '../../../../../common/components/dashboard/enums/partnershipLevels';
import {partnerLevelProperties} from '../../../../../common/components/dashboard/mock';
import AccountEntry from '../../../../../common/interfaces/accountEntry';
import PartnerLevel from '../../../../../common/interfaces/partnerLevel';
import CheckBoxItem from '../CheckBoxItem';
import LevelProgressBar from '../LevelProgressBar';

interface IPropsPartnerIcon {
	level: PartnershipLevels;
}

interface IPropsPartnershipLevel {
	aRRResults: {
		[keys: string]: number;
	};
	account: AccountEntry;
	checkedProperties: {
		[keys: string]: boolean | undefined;
	};
	headcount: {
		[keys: string]: number;
	};
	partnerLevel: PartnerLevel;
}

const PartnerIcon = ({level}: IPropsPartnerIcon) => {
	if (level === PartnershipLevels.SILVER) {
		return <SilverPartnerIcon />;
	}

	if (level === PartnershipLevels.GOLD) {
		return <GoldPartnerIcon />;
	}

	if (level === PartnershipLevels.PLATINUM) {
		return <PlatinumPartnerIcon />;
	}

	return <AuthorizedPartnerIcon />;
};

const PartnershipLevel = ({
	aRRResults,
	account,
	checkedProperties,
	headcount,
	partnerLevel,
}: IPropsPartnershipLevel) => {
	const getTotalARR = () => {
		if (partnerLevel.partnerLevelType.key === PartnershipLevels.GOLD) {
			return partnerLevelProperties[partnerLevel.partnerLevelType.key]
				.goalARR;
		}

		return aRRResults.aRRAmountTotal;
	};

	const getHeadcount = (partnerLevelKey: PartnershipLevels) => {
		if (partnerLevel.partnerLevelType) {
			return `${headcount.partnerMarketingUser}/${partnerLevelProperties[partnerLevelKey].partnerMarketingUser}
             Marketing / ${headcount.partnerSalesUser}/${partnerLevelProperties[partnerLevelKey].partnerSalesUser} Sales`;
		}

		return '';
	};

	return (
		<div>
			<h3
				className={classNames('d-flex', {
					'mb-4':
						partnerLevel.partnerLevelType.key ===
						PartnershipLevels.AUTHORIZED,
					'mb-5':
						partnerLevel.partnerLevelType.key !==
						PartnershipLevels.AUTHORIZED,
				})}
			>
				<PartnerIcon level={partnerLevel.partnerLevelType.key} />

				<span
					className={classNames('ml-2 mr-1', {
						'text-brand-secondary-darken-2':
							partnerLevel.partnerLevelType.key ===
							PartnershipLevels.GOLD,
						'text-info':
							partnerLevel.partnerLevelType.key ===
							PartnershipLevels.AUTHORIZED,
						'text-neutral-7':
							partnerLevel.partnerLevelType.key ===
							PartnershipLevels.SILVER,
						'text-neutral-10':
							partnerLevel.partnerLevelType.key ===
							PartnershipLevels.PLATINUM,
					})}
				>
					{partnerLevel.partnerLevelType.name}
				</span>

				<span className="font-weight-lighter">Partner</span>
			</h3>

			{partnerLevel.partnerLevelType.key !==
				PartnershipLevels.AUTHORIZED && (
				<div>
					{partnerLevel.partnerLevelType.key !==
						PartnershipLevels.SILVER && (
						<CheckBoxItem
							completed={checkedProperties.arr}
							title="ARR"
						>
							<LevelProgressBar
								currentValue={aRRResults.aRRAmountTotal}
								total={getTotalARR()}
								type={ChartTypes.ARR}
							/>

							{partnerLevel.partnerLevelType.key ===
								PartnershipLevels.GOLD && (
								<>
									<div className="font-weight-bold text-center text-neutral-5 text-paragraph-sm">
										or
									</div>

									<LevelProgressBar
										currentValue={
											account.newProjectExistingBusiness
										}
										total={
											partnerLevelProperties[
												partnerLevel.partnerLevelType
													.key
											].newProjectExistingBusiness
										}
										type={ChartTypes.NP_OR_NB}
									/>
								</>
							)}
						</CheckBoxItem>
					)}

					<CheckBoxItem
						completed={checkedProperties.headcount}
						text={getHeadcount(
							partnerLevel.partnerLevelType
								.key as PartnershipLevels
						)}
						title="Headcount"
					/>

					<CheckBoxItem
						completed={checkedProperties.marketingPlan}
						title="Marketing Plan"
					/>

					<CheckBoxItem
						completed={checkedProperties.marketingPerformance}
						text={`${account.marketingPerformance} Leads`}
						title="Marketing Performance"
					/>

					<CheckBoxItem
						completed={
							checkedProperties.solutionDeliveryCertification
						}
						title="Solution Delivery Certification"
					/>
				</div>
			)}
		</div>
	);
};

export default PartnershipLevel;
