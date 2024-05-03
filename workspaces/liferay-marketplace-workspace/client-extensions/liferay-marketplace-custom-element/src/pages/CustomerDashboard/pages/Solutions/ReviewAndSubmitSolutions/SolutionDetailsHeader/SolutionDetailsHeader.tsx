/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useMemo} from 'react';
import {useNavigate} from 'react-router-dom';

import i18n from '../../../../../../i18n';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../../../utils/util';

type SolutionDetailsHeader = {
	selectedSolution: {
		images: ProductAttachment[];
		name: {
			en_US: string;
		};
		productSpecifications: ProductSpecification[];
		workflowStatusInfo: {
			code: number;
			label: string;
			label_i18n: string;
		};
	};
};
const SolutionsDetailsHeader = ({selectedSolution}: SolutionDetailsHeader) => {
	const navigate = useNavigate();

	const thumbnail = getThumbnailByProductAttachment(selectedSolution?.images);
	const appVersion = useMemo(
		() =>
			getProductVersionFromSpecifications(
				selectedSolution?.productSpecifications ?? []
			),
		[selectedSolution?.productSpecifications]
	);

	return (
		<>
			<ClayButton
				className="align-items-center d-flex"
				displayType="unstyled"
				onClick={() => navigate('..')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				<h5 className="mt-1">{i18n.translate('back-to-solutions')}</h5>
			</ClayButton>
			<div className="align-items-center d-flex justify-content-between mb-4 mt-4">
				<div className="align-items-center d-flex solution-details-page-header-left-container">
					<div>
						<img
							alt="App Logo"
							className="solution-details-page-icon"
							src={showAppImage(thumbnail)}
						/>
					</div>

					<div>
						<span className="solution-details-page-header-title">
							{selectedSolution.name?.en_US}
						</span>

						<div className="align-items-center d-flex solution-details-page-header-subtitle-container">
							{appVersion && (
								<span className="solution-details-page-header-subtitle-text">
									{appVersion}
								</span>
							)}
							<ClayIcon
								className={classNames(
									'solution-details-page-header-subtitle-icon',
									{
										'solution-details-page-header-subtitle-icon-hidden':
											selectedSolution.workflowStatusInfo
												.label === 'draft',
										'solution-details-page-header-subtitle-icon-pending':
											selectedSolution.workflowStatusInfo
												.label === 'pending',
										'solution-details-page-header-subtitle-icon-published':
											selectedSolution.workflowStatusInfo
												.label === 'approved',
									}
								)}
								symbol="circle"
							/>

							<span className="solution-details-page-header-subtitle-text">
								{selectedSolution.workflowStatusInfo.label_i18n}
							</span>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default SolutionsDetailsHeader;
