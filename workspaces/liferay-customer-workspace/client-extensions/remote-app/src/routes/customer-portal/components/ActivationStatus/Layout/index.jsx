/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import classNames from 'classnames';
import {StatusTag} from '../../../../../common/components';
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';

const ActivationStatusLayout = ({
	activationStatus,
	activationStatusDate,
	iconPath: IconSVG,
	project,
	subscriptionGroupActivationStatus,
}) => {
	return (
		<div className="mb-5">
			<h2>{activationStatus.title}</h2>

			<p className="font-weight-normal text-neutral-7 text-paragraph">
				{activationStatus.subtitle}
			</p>

			<div>
				<ClayCard className="border border-light cp-activation-status-container m-0 rounded shadow-none">
					<ClayCard.Body className="pl-4 pr-2 py-3">
						<div className="align-items-center d-flex position-relative">
							<IconSVG
								className={classNames(
									'ml-2 mr-4 cp-img-activation-status',
									{
										'in-progress':
											subscriptionGroupActivationStatus ===
											STATUS_TAG_TYPE_NAMES.inProgress,
										'not-active':
											subscriptionGroupActivationStatus ===
												STATUS_TAG_TYPE_NAMES.notActivated ||
											!subscriptionGroupActivationStatus,
									}
								)}
								draggable={false}
								height={30.55}
								width={30.55}
							/>

							<ClayCard.Description
								className="col-8 h5 ml-2 px-0"
								displayType="title"
								tag="h5"
								title={null}
								truncate={false}
							>
								{project.name}

								<p className="font-weight-normal mb-2 text-neutral-7 text-paragraph">
									{activationStatusDate}
								</p>

								{activationStatus.buttonLink}
							</ClayCard.Description>

							<div className="d-flex justify-content-between ml-auto">
								<ClayCard.Description
									className="cp-label-activation-status position-absolute"
									displayType="text"
									tag="div"
									title={null}
									truncate={false}
								>
									<div className="align-items-center d-flex">
										<StatusTag
											currentStatus={activationStatus.id}
										/>

										{activationStatus.dropdownIcon}
									</div>
								</ClayCard.Description>
							</div>
						</div>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</div>
	);
};

export default ActivationStatusLayout;
