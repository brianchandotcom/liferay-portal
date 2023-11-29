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
	checkedProperties: {
		[keys: string]: boolean | undefined;
	};
	currency: string;
	headcount: {
		[keys: string]: number;
	};
	opportunitiesCount: number;
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
	checkedProperties,
	currency,
	opportunitiesCount,
	partnerLevel,
}: IPropsPartnershipLevel) => {
	const getTotalARR = () => {
		if (partnerLevel.partnerLevelType.key === PartnershipLevels.PLATINUM) {
			return aRRResults.targetArr;
		}

		if (partnerLevel.partnerLevelType.key === PartnershipLevels.GOLD) {
			return partnerLevelProperties[partnerLevel.partnerLevelType.key]
				.goalARR;
		}

		return aRRResults.growthArrTotal;
	};

	const getHeadcount = (partnerLevelKey: PartnershipLevels) => {
		if (partnerLevel.partnerLevelType) {
			return `0/${partnerLevelProperties[partnerLevelKey].partnerMarketingUser}
				Marketing & 0/${partnerLevelProperties[partnerLevelKey].partnerSalesUser} Sales`;
		}

		return '';
	};

	return (
		<div>
			<h3
				className={classNames('d-flex', {
					'mb-4':
						partnerLevel.partnerLevelType.key ===
							PartnershipLevels.AUTHORIZED ||
						partnerLevel.partnerLevelType.key ===
							PartnershipLevels.GLOBAL,
					'mb-5':
						partnerLevel.partnerLevelType.key !==
							PartnershipLevels.AUTHORIZED &&
						partnerLevel.partnerLevelType.key !==
							PartnershipLevels.GLOBAL,
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
				PartnershipLevels.AUTHORIZED &&
				partnerLevel.partnerLevelType.key !==
					PartnershipLevels.GLOBAL && (
					<div>
						{partnerLevel.partnerLevelType.key !==
							PartnershipLevels.SILVER && (
							<CheckBoxItem
								completed={checkedProperties.arr}
								title="ARR"
							>
								<LevelProgressBar
									currency={currency}
									currentValue={aRRResults.growthArrTotal}
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
											currency={currency}
											currentValue={opportunitiesCount}
											total={
												partnerLevelProperties[
													partnerLevel
														.partnerLevelType.key
												].opportunitiesCount
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
