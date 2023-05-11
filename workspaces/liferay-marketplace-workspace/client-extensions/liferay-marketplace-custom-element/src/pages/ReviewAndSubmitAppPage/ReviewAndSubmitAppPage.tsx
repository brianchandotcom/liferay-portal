import {useEffect, useState} from 'react';

import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import documentationIcon from '../../assets/icons/documentation_icon.svg';
import globeIcon from '../../assets/icons/globe_icon.svg';
import guideIcon from '../../assets/icons/guide_icon.svg';
import phoneIcon from '../../assets/icons/phone_icon.svg';
import scheduleIcon from '../../assets/icons/schedule_icon.svg';
import usageTermsIcon from '../../assets/icons/usage_terms_icon.svg';
import {Checkbox} from '../../components/Checkbox/Checkbox';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	getProduct,
	getProductSKU,
	getProductSpecifications,
	getProductSubscriptionConfiguration,
} from '../../utils/api';
import {CardSection} from './CardSection';
import {ReviewAndSubmitAppPageUtilProps} from './ReviewAndSubmitAppPageUtil';
import {showAppImage} from '../../utils/util';

import './ReviewAndSubmitAppPage.scss';

interface ReviewAndSubmitAppPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
	readonly?: boolean;
}

export function ReviewAndSubmitAppPage({
	onClickBack,
	onClickContinue,
	readonly = false,
}: ReviewAndSubmitAppPageProps) {
	const [
		{
			appERC,
			appLicensePrice,
			appLogo,
			appProductId,
			appStorefrontImages,
			buildZIPFiles,
			dayTrial,
			priceModel,
		},
		dispatch,
	] = useAppContext();
	const [checked, setChecked] = useState(false);
	const [name, setName] = useState('');
	const [description, setDescription] = useState('');
	const [version, setVersion] = useState('');
	const [notes, setNotes] = useState('');

	const [cardInfos, setCardInfos] = useState<
		{icon: string; link: string; title: string}[]
	>([]);
	const [reviewAndSubmitAppPageItems, setReviewAndSubmitAppPageItems] =
		useState<ReviewAndSubmitAppPageUtilProps[]>([]);

	const buildZIPTitles = buildZIPFiles?.map(
		(buildZIPFile) => buildZIPFile.fileName
	);

	useEffect(() => {
		const getData = async () => {
			const productResponse = await getProduct({
				appERC,
			});

			const productCategories = {
				section: 'Categories',
				tags: productResponse.categories
					.filter((category: any) => {
						return (
							category.vocabulary ===
							'marketplace-solution-category'
						);
					})
					.map((category: any) => {
						return category.name;
					}),
			};

			const productTags = {
				section: 'Tags',
				tags: productResponse.categories
					.filter((tag: any) => {
						return tag.vocabulary === 'marketplace-solution-tags';
					})
					.map((tag: any) => {
						return tag.name;
					}),
			};

			const skuResponse = await getProductSKU({
				appProductId,
			});

			const pricing = {
				icon: brightnessEmptyIcon,
				section: 'Pricing',
				title: priceModel.value,
			};

			if (dayTrial === 'yes' && priceModel.value === 'Paid') {
				dispatch({
					payload: {
						value: skuResponse.items[1].price,
					},
					type: TYPES.UPDATE_APP_LICENSE_PRICE,
				});
			}
			else {
				dispatch({
					payload: {
						value: skuResponse.items[0].price,
					},
					type: TYPES.UPDATE_APP_LICENSE_PRICE,
				});
			}

			const productSubscriptionConfigurationResponse =
				await getProductSubscriptionConfiguration({
					appERC,
				});

			const licensing = {
				description:
					productSubscriptionConfigurationResponse.subscriptionType
						? 'License must be renewed annually.'
						: 'License never expires.',
				icon: scheduleIcon,
				section: 'Licensing',
				title: productSubscriptionConfigurationResponse.subscriptionType
					? 'Non-Perpetual License'
					: 'Perpetual License',
			};

			const storefront = {
				section: 'Storefront',
			};

			const versioning = {
				description: notes,
				section: 'Version',
				title: 'Release Notes',
				version,
			};

			const supportHelp = {
				section: 'Support & Help',
			};

			setReviewAndSubmitAppPageItems([
				productCategories,
				productTags,
				pricing,
				licensing,
				storefront,
				versioning,
				supportHelp,
			]);

			const productSpecificationsResponse =
				await getProductSpecifications({
					appProductId,
				});

			// const productImages = await getProductImages({ appProductId });

			// let productAppLogo;

			// if (appLogo) {
			// 	productAppLogo = productImages.items.find(
			// 		(item: { title: { [key: string]: string } }) =>
			// 			item.title['en_US'] === appLogo.fileName
			// 	);

			// 	setLogo(productAppLogo?.src.replace('?download=true', ''));
			// } else {
			// 	productAppLogo = productResponse.thumbnail;

			// 	setLogo(productAppLogo);
			// }

			setName(productResponse.name['en_US']);
			setDescription(productResponse.description['en_US']);

			const newCardInfos: {icon: string; link: string; title: string}[] =
				[];

			productSpecificationsResponse.map((specification) => {
				const {specificationKey, value} = specification;
				const localizedValue = value['en_US'];

				if (specificationKey === 'version') {
					setVersion(localizedValue);
				}
				else if (specificationKey === 'notes') {
					setNotes(localizedValue);
				}
				else if (specificationKey === 'supporturl') {
					newCardInfos.push({
						icon: phoneIcon,
						link: localizedValue,
						title: 'Support URL',
					});
				}
				else if (specificationKey === 'publisherwebsiteurl') {
					newCardInfos.push({
						icon: globeIcon,
						link: localizedValue,
						title: 'Publisher website URL',
					});
				}
				else if (specificationKey === 'appusagetermsurl') {
					newCardInfos.push({
						icon: usageTermsIcon,
						link: localizedValue,
						title: 'App usage terms (EULA) URL',
					});
				}
				else if (specificationKey === 'appdocumentationurl') {
					newCardInfos.push({
						icon: documentationIcon,
						link: localizedValue,
						title: 'App documentation URL',
					});
				}
				else if (specificationKey === 'appinstallationguideurl') {
					newCardInfos.push({
						icon: guideIcon,
						link: localizedValue,
						title: 'App installation guide URL',
					});
				}
			});

			setCardInfos(newCardInfos);
		};

		getData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [appERC, appProductId, dispatch]);

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
												appLogo?.preview
											)})`,
											backgroundPosition: '50% 50%',
											backgroundRepeat: 'no-repeat',
											backgroundSize: 'cover',
										}}
									/>
								</div>

								<div className="review-and-submit-app-page-card-header-title">
									<span className="review-and-submit-app-page-card-header-title-text">
										{name}
									</span>

									<span className="review-and-submit-app-page-card-header-title-version">
										{version}
									</span>
								</div>
							</div>
						</div>
					)}

					<div className="review-and-submit-app-page-card-body">
						<CardSection
							enableEdit={!readonly}
							localized
							paragraph={description}
							required
							sectionName="Description"
						/>

						{reviewAndSubmitAppPageItems.map((item, index) => {
							const cardTitle = () => {
								if (item.section === 'Pricing') {
									return priceModel.value;
								}
								else if (item.section === 'Licensing') {
									return item.title;
								}
								else if (item.section === 'Version') {
									return item.title;
								}
							};

							const cardDescription = () => {
								if (item.section === 'Pricing') {
									if (priceModel.value === 'Free') {
										return 'The app is offered in the Marketplace with no charge.';
									}
									else {
										return 'To enable paid apps, you must be a business and enter payment information in your Marketplace account profile.';
									}
								}
								else {
									return item.description;
								}
							};

							const description = () => {
								if (item.section === 'Version') {
									return notes;
								}
								else {
									return item.description;
								}
							};

							return (
								<CardSection
									build={false}
									buildZIPTitles={buildZIPTitles}
									cardDescription={cardDescription()}
									cardInfos={cardInfos}
									cardLink={item.section === 'Support & Help'}
									cardTags={item.cardTags}
									cardTitle={cardTitle()}
									cardView={
										item.section === 'Pricing' ||
										item.section === 'Licensing'
									}
									description={description()}
									enableEdit={!readonly}
									files={appStorefrontImages}
									icon={item.icon}
									key={index}
									price={appLicensePrice}
									required
									sectionName={item.section}
									storefront={item.section === 'Storefront'}
									tags={item.tags}
									title={
										item.section === 'Build'
											? item.fileName
											: item.title
									}
									version={
										item.section === 'Version'
											? version
											: null
									}
								/>
							);
						})}
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
					onClickContinue={() => {
						dispatch({
							type: TYPES.SUBMIT_APP,
						});

						onClickContinue();
					}}
					showBackButton={true}
				/>
			)}
		</div>
	);
}
