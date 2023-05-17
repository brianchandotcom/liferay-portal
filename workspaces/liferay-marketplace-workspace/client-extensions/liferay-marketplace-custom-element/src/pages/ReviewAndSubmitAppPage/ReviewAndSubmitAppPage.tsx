import {useEffect, useState} from 'react';

import {Checkbox} from '../../components/Checkbox/Checkbox';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {
	getProduct,
	getProductSKU,
	getProductSpecifications,
} from '../../utils/api';
import {showAppImage} from '../../utils/util';
import {CardSectionsBody} from './CardSectionsBody';
import {App, supportAndHelpMap} from './ReviewAndSubmitAppPageUtil';

import './ReviewAndSubmitAppPage.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

interface ReviewAndSubmitAppPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
	productERC: string;
	productId: number;
	readonly?: boolean;
}

export function ReviewAndSubmitAppPage({
	onClickBack,
	onClickContinue,
	productERC,
	productId,
	readonly = false,
}: ReviewAndSubmitAppPageProps) {
	const [checked, setChecked] = useState(false);
	const [app, setApp] = useState<App>();
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const getData = async () => {
			setLoading(true);

			const productResponse = await getProduct({
				appERC: productERC,
				nestedFields: 'images,attachments',
			});

			const productCategories: string[] = [];
			const productTags: string[] = [];

			productResponse.categories.forEach((category: any) => {
				if (category.vocabulary === 'marketplace app category') {
					productCategories.push(category.name);
				}
				else if (category.vocabulary === 'marketplace app tags') {
					productTags.push(category.name);
				}
			});

			const skuResponse = await getProductSKU({
				appProductId: productId,
			});

			const productSpecificationsResponse =
				await getProductSpecifications({
					appProductId: productId,
				});

			const nonTrialSKU = skuResponse.items.find(
				({skuOptions: [trialOption]}) => trialOption.value === 'no'
			);
			let version = '';
			let versionDescription = '';

			nonTrialSKU?.customFields?.forEach(({customValue, name}) => {
				if (name === 'version') {
					version = customValue.data;
				}

				if (name === 'version description') {
					versionDescription = customValue.data;
				}
			});

			const supportAndHelpCardInfos: {
				icon: string;
				link: string;
				title: string;
			}[] = [];
			let licenseType = '';
			let priceModel = '';

			productSpecificationsResponse.map((specification) => {
				const {specificationKey, value} = specification;
				const localizedValue = value['en_US'];

				if (
					specificationKey === 'supporturl' ||
					specificationKey === 'publisherwebsiteurl' ||
					specificationKey === 'appusagetermsurl' ||
					specificationKey === 'appdocumentationurl' ||
					specificationKey === 'appinstallationguideurl'
				) {
					const supportAndHelItem =
						supportAndHelpMap.get(specificationKey);
					supportAndHelpCardInfos.push({
						...(supportAndHelItem as {icon: string; title: string}),
						link: localizedValue,
					});
				}

				if (specificationKey === 'price-model') {
					priceModel = localizedValue;
				}

				if (specificationKey === 'license-type') {
					licenseType = localizedValue;
				}
			});

			const attachment = productResponse.attachments.find(
				({customFields}) =>
					customFields?.find(
						({
							customValue: {
								data: [value],
							},
							name,
						}) => name === 'App Icon' && value === 'No'
					)
			);

			const newApp: App = {
				attachmentTitle: attachment?.title['en_US'] as string,
				categories: productCategories,
				description: productResponse.description['en_US'],
				licenseType,
				name: productResponse.name['en_US'],
				price: nonTrialSKU?.price as number,
				priceModel,
				supportAndHelp: supportAndHelpCardInfos,
				storefront: productResponse.images,
				tags: productTags,
				thumbnail: productResponse.thumbnail,
				version,
				versionDescription,
			};

			setApp(newApp);

			setLoading(false);
		};

		getData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productERC, productId]);

	return (
		<div className="review-and-submit-app-page-container">
			{!readonly && (
				<div className="review-and-submit-app-page-header">
					<Header
						description="Please, review before submitting. Once sent, you will not be able to edit any information until this submission is completely reviewed by Liferay."
						title="Review and submit app"
					/>
				</div>
			)}

			<Section
				disabled={readonly}
				label={!readonly ? 'App Submission' : ''}
				required
				tooltip={!readonly ? 'More info' : ''}
				tooltipText={!readonly ? 'More Info' : ''}
			>
				<div className="review-and-submit-app-page-card-container">
					{!readonly && (
						<div className="review-and-submit-app-page-card-header">
							<div className="review-and-submit-app-page-card-header-left-content">
								<div className="review-and-submit-app-page-card-header-icon-container">
									<div
										className="upload-logo-icon"
										style={{
											backgroundImage: `url(${showAppImage(
												app?.thumbnail
											)})`,
											backgroundPosition: '50% 50%',
											backgroundRepeat: 'no-repeat',
											backgroundSize: 'cover',
										}}
									/>
								</div>

								<div className="review-and-submit-app-page-card-header-title">
									<span className="review-and-submit-app-page-card-header-title-text">
										{app?.name}
									</span>

									<span className="review-and-submit-app-page-card-header-title-version">
										{app?.version}
									</span>
								</div>
							</div>
						</div>
					)}

					<div className="review-and-submit-app-page-card-body">
						{loading ? (
							<ClayLoadingIndicator
								displayType="primary"
								shape="circle"
								size="md"
							/>
						) : (
							<CardSectionsBody
								app={app as App}
								readonly={readonly}
							/>
						)}
					</div>
				</div>
			</Section>

			{!readonly && (
				<div className="review-and-submit-app-page-agreement">
					<Checkbox
						checked={checked}
						onChange={() => {
							setChecked(!checked);
						}}
					></Checkbox>

					<span>
						<span className="review-and-submit-app-page-agreement-highlight">
							{'Attention: this cannot be undone. '}
						</span>
						I am aware I cannot edit any data or information
						regarding this app submission until Liferay completes
						its review process and I agree with the Liferay
						Marketplace <a href="#">terms</a> and{' '}
						<a href="#">privacy</a>
					</span>
				</div>
			)}

			{!readonly && (
				<NewAppPageFooterButtons
					continueButtonText="Submit App"
					disableContinueButton={!checked}
					onClickBack={() => onClickBack()}
					onClickContinue={() => onClickContinue()}
					showBackButton={true}
				/>
			)}
		</div>
	);
}
