/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useNavigate} from 'react-router-dom';

import {Section} from '../../../../../components/Section/Section';
import {useNewAppContext} from '../../../../../context/NewAppContext';
import i18n from '../../../../../i18n';

import '../../NewAppFlow/pages/Submit/Submit.scss';
import {ProductTypeOptions} from '../../Apps/AppCreationFlow/ProvideAppBuildPage/constants/productTypes';
import SubmitSection from '../../NewAppFlow/pages/Submit/SubmitSection';

const SubmitNewBuild = () => {
	const [context] = useNewAppContext();
	const navigate = useNavigate();

	const productTypeOption = ProductTypeOptions.find(
		(productType) => productType.value === context.build.appType
	);

	return (
		<Section
			disabled
			label={i18n.translate('app-submission')}
			required
			tooltip={i18n.translate('more-info')}
			tooltipText={i18n.translate('more-info')}
		>
			<hr />

			<div className="border p-5 rounded-lg">
				<div className="align-items-center d-flex">
					{context.profile.file.preview ? (
						<img
							alt="App logo"
							className="submit-app-logo-icon"
							src={context.profile.file.preview}
						/>
					) : (
						<ClayIcon
							aria-label="New App logo"
							className="submit-app-logo-icon text-muted"
							symbol="picture"
						/>
					)}

					<div className="d-flex flex-column pl-5">
						<span className="submit-app-name">
							{context.profile.name}
						</span>
						<span className="submit-app-version">
							{context.version.version}
						</span>
					</div>
				</div>

				<hr />

				<SubmitSection
					editNavigate={() => navigate('../')}
					isLastSection
					required
					title={i18n.translate('build')}
				>
					{productTypeOption && (
						<>
							<div className="border p-4 rounded-lg">
								<div>
									<div className="align-items-center d-flex">
										<span className="mr-2 submit-app-pricing-title">
											{productTypeOption?.label}
										</span>
									</div>

									<span className="submit-app-pricing-description">
										{productTypeOption?.description}
									</span>
								</div>
							</div>
							{context.build.liferayPackages.map(
								(liferayPackage) => {
									return (
										<div
											className="d-flex flex-column pt-4"
											key={liferayPackage.id}
										>
											<div className="align-items-center d-flex">
												<div className="submit-app-file-container">
													<ClayIcon
														aria-label="Folder Icon"
														className="submit-app-file-container-icon"
														symbol="document-text"
													/>
												</div>

												<span className="ml-3 submit-app-file-name">
													{
														liferayPackage?.file
															?.fileName
													}
												</span>
											</div>
											<div className="p-4">
												<p className="font-weight-bold mb-0">
													{i18n.translate(
														'compatible-versions'
													)}
												</p>
												{liferayPackage.versions.map(
													(version, index) => (
														<small key={index}>
															{version}
															{index + 1 <
																liferayPackage
																	.versions
																	.length &&
																','}{' '}
														</small>
													)
												)}
											</div>
										</div>
									);
								}
							)}
						</>
					)}
				</SubmitSection>
			</div>
		</Section>
	);
};

export default SubmitNewBuild;
