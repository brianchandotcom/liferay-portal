/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import i18n from '~/common/I18n';
import {Button} from '~/common/components';
import {Radio} from '~/common/components/Radio';
import Layout from '~/common/containers/setup-forms/Layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useProvisioningLicenseKeys from '~/common/hooks/useProvisioningLicenseKeys';
import {patchOrderItemByExternalReferenceCode} from '~/common/services/liferay/graphql/queries';
import {
	getNewGenerateKeyFormValues,
	putSubscriptionInKey,
} from '~/common/services/liferay/rest/raysource/LicenseKeys';
import {FORMAT_DATE_TYPES} from '~/common/utils/constants';
import getDateCustomFormat from '~/common/utils/getDateCustomFormat';
import {useCustomerPortal} from '../../../context';
import {has100YearsDifference} from '../../ActivationKeysTable/utils';
import GenerateNewKeySkeleton from '../Skeleton';
import {getLicenseKeyEndDatesByLicenseType} from '../utils/licenseKeyEndDate';
import {getRenewKeySubtitle} from '../utils/renewKeySubtitle';

const SelectSubscription = ({
	accountKey,
	hasComplimentaryKey,
	oAuthToken,
	productGroupName,
	selectedKeyData,
	setExpirationRenewDate,
	setHasComplimentaryKey,
	setLicenseEntryTypeName,
	setSelectedKeyData,
	setStep,
	setSubmitKeyAction,
	state,
	urlPreviousPage,
}) => {
	const [{subscriptionGroups}] = useCustomerPortal();
	const {articleDeactivateKey, client, featureFlags, provisioningServerAPI} =
		useAppPropertiesContext();

	const [generateFormValues, setGenerateFormValues] = useState();
	const provisioningService = useProvisioningLicenseKeys();
	const [isLoadingGenerateKey, setIsLoadingGenerateKey] = useState(false);

	const navigate = useNavigate();
	const [availableActivationKeysTotal, setAvailableActivationKeysTotal] =
		useState();

	useEffect(() => {
		const fetchGenerateFormData = async () => {
			const data = await getNewGenerateKeyFormValues(
				accountKey,
				oAuthToken,
				provisioningServerAPI,
				productGroupName
			);

			if (data) {
				setGenerateFormValues(data);
			}
		};

		if (oAuthToken) {
			fetchGenerateFormData();
		}
	}, [accountKey, oAuthToken, provisioningServerAPI, productGroupName]);

	const [selectedSubscription, setSelectedSubscription] = useState(
		selectedKeyData?.selectedSubscription
	);
	const [selectedVersion, setSelectedVersion] = useState(
		selectedKeyData?.productVersion
	);
	const [selectedKeyType, setSelectedKeyType] = useState(
		selectedKeyData?.licenseEntryType
	);

	const doesNotAllowPermanentLicense =
		!generateFormValues?.allowPermanentLicenses;

	const allowComplimentary = generateFormValues?.allowComplimentary;

	const hasNotPermanentLicense =
		selectedKeyType?.includes('Virtual Cluster') ||
		selectedKeyType?.includes('OEM') ||
		selectedKeyType?.includes('Enterprise');

	const typesProduct = generateFormValues?.versions[0]?.types;

	const handleProduct = useCallback(() => {
		const filteredTypes = typesProduct?.find(
			(type) =>
				type.licenseEntryDisplayName ===
				productGroupName + ' ' + selectedKeyType
		);

		return filteredTypes?.productKey;
	}, [typesProduct, productGroupName, selectedKeyType]);

	const isRenew = state?.id === 'renew';
	const keyCount = state?.activationKeys?.length;
	const renewKeySubtitle = getRenewKeySubtitle(state);
	const isSingleComplimentaryKey = hasComplimentaryKey && keyCount === 1;

	const mockedValuesForComplimentaryKeys = useMemo(() => {
		return {
			instanceSize: 4,
			productKey: handleProduct(),
			provisionedCount: 0,
			quantity: 5,
		};
	}, [handleProduct]);

	const productVersions = useMemo(() => {
		if (generateFormValues?.versions) {
			return generateFormValues.versions.sort((a, b) =>
				a.label >= b.label ? 1 : -1
			);
		}

		return [];
	}, [generateFormValues?.versions]);

	useEffect(() => {
		if (productVersions?.length && !selectedKeyData?.productVersion) {
			setSelectedVersion(productVersions[0].label);
		}
	}, [selectedKeyData?.productVersion, productVersions]);

	const selectedVersionIndex = useMemo(() => {
		if (selectedVersion) {
			return productVersions
				?.map((label) => label.label)
				.indexOf(selectedVersion);
		}

		return 0;
	}, [productVersions, selectedVersion]);

	const productKeyTypes = useMemo(
		() =>
			productVersions?.map(({types}) =>
				types
					.map((licenseKey) =>
						licenseKey.licenseEntryDisplayName.replace(
							`${productGroupName} `,
							''
						)
					)
					.sort()
			),
		[productGroupName, productVersions]
	);

	useEffect(() => {
		if (productKeyTypes?.length && !selectedKeyData?.licenseEntryType) {
			setSelectedKeyType(productKeyTypes[selectedVersionIndex][0]);
		}

		if (isRenew) {
			setSelectedKeyType(productNames[0]);
		}
	}, [
		selectedKeyData?.licenseEntryType,
		productKeyTypes,
		selectedVersionIndex,
	]);

	const versionsOfTheSelectedKeys = state.activationKeys?.map((item) => {
		return item.productVersion;
	});

	const uniqueVersionOfTheSelectedKey = [
		...new Set(versionsOfTheSelectedKeys),
	].join(', ');

	const productNames = [
		...new Set(
			state?.activationKeys?.map((key) => {
				const productName = key.productName.replace(
					`${productGroupName} `,
					''
				);

				const licenseEntryTypeNamesFormatted = key.licenseEntryType
					.split('-')
					.map(
						(item) => item.charAt(0).toUpperCase() + item.substr(1)
					)
					.join(' ');

				return productName.toLowerCase() ===
					key.licenseEntryType.toLowerCase()
					? productName
					: `${productName} (${licenseEntryTypeNamesFormatted})`;
			})
		),
	];

	setLicenseEntryTypeName(productNames[0]);

	const productName = [...new Set(productNames)].join(', ');

	const selectedProductName = state.activationKeys?.map((item) => {
		return item.productName;
	});

	const uniqueSelectedProductName = [...new Set(selectedProductName)].join(
		', '
	);

	const productKey = typesProduct?.find(
		(item) =>
			item.licenseEntryName.toLowerCase().replace(/[- ]+/g, '-') ===
			uniqueSelectedProductName
				.toString()
				.toLowerCase()
				.replace(/[- ]+/g, '-')
	)?.productKey;

	const mockedValuesForComplimentaryKeysOfTheSelectedKeys = useMemo(() => {
		return {
			productKey,
		};
	}, [productKey]);

	const matchingProductKeys = state.activationKeys?.map((activationKey) => {
		const productName = activationKey.productName;
		const licenseEntryType = activationKey.licenseEntryType;
		const productVersionLabel = activationKey.productVersion;

		const matchingProductType = productVersions
			.find((versionData) => versionData.label === productVersionLabel)
			?.types.find((productType) => {
				const displayNameMatch =
					productType.licenseEntryName.includes(productName);
				const typeMatch =
					productType.licenseEntryType.includes(licenseEntryType);

				if (displayNameMatch && typeMatch) {
					return true;
				}

				return false;
			});

		return matchingProductType ? matchingProductType.productKey : 'N/A';
	});

	const selectedProductKey = useMemo(
		() =>
			productVersions &&
			productVersions[selectedVersionIndex]?.types?.find(
				(key) =>
					key.licenseEntryDisplayName.replace(
						`${productGroupName} `,
						''
					) === selectedKeyType
			)?.productKey,
		[
			productGroupName,
			productVersions,
			selectedKeyType,
			selectedVersionIndex,
		]
	);

	const subscriptionTerms = useMemo(
		() =>
			generateFormValues?.subscriptionTerms?.filter((key) =>
				isRenew
					? matchingProductKeys.includes(key.productKey)
					: key.productKey === selectedProductKey
			),
		[
			generateFormValues?.subscriptionTerms,
			selectedProductKey,
			isRenew,
			matchingProductKeys,
		]
	);

	const licenseEntryTypes = state.activationKeys?.map((key) => {
		return key.licenseEntryType;
	});

	const selectedProductNames = [...new Set(licenseEntryTypes)]
		.join(', ')
		.toLowerCase();

	const selectedProductItem = selectedSubscription?.licenseKeyEndDates?.find(
		(item) => item.licenseEntryType.includes(selectedProductNames)
	);

	const selectedEndDate = selectedProductItem
		? selectedProductItem.endDate
		: null;

	setExpirationRenewDate(selectedEndDate);

	const submitKey = useCallback(async () => {
		const licenseEntryType =
			licenseEntryTypes?.includes('virtual-cluster') ||
			licenseEntryTypes?.includes('oem') ||
			licenseEntryTypes?.includes('enterprise');

		const selectedFields = [
			'active',
			'description',
			'licenseEntryType',
			'maxClusterNodes',
			'name',
			'productName',
			'productVersion',
		];

		if (!licenseEntryType) {
			selectedFields.push('macAddresses', 'hostName', 'ipAddresses');
		}

		const saveSubscriptionKey = async (id) => {
			return putSubscriptionInKey(oAuthToken, provisioningServerAPI, id);
		};

		const generateLicenseKey = async (item) => {
			const licenseKey = {
				accountKey,
				expirationDate: selectedEndDate,
				productKey: selectedSubscription?.productKey,
				productPurchaseKey: selectedSubscription?.productPurchaseKey,
				sizing: 'Sizing ' + selectedSubscription?.instanceSize,
				startDate: selectedSubscription?.startDate,
			};
			selectedFields.forEach((field) => {
				licenseKey[field] = item[field];
			});
			const response = await provisioningService.createNewGenerateKey(
				accountKey,
				licenseKey
			);

			await saveSubscriptionKey(response?.items?.[0]?.id);
		};

		setIsLoadingGenerateKey(true);

		try {
			if (has100YearsDifference()) {
				const createKeyPromises = state.activationKeys?.map(
					async (item) => {
						await generateLicenseKey(item);
					}
				);

				await Promise.all(createKeyPromises);

				setIsLoadingGenerateKey(false);

				return true;
			}
			else {
				const results = await Promise.all(
					state.activationKeys?.map(async (item) => {
						await generateLicenseKey(item, hasComplimentaryKey);
					})
				);

				if (hasComplimentaryKey) {
					await saveSubscriptionKey(results?.items?.[0]?.id);
				}

				await Promise.all(results);

				setIsLoadingGenerateKey(false);

				try {
					if (!hasComplimentaryKey) {
						await client.mutate({
							context: {
								displaySuccess: false,
							},
							mutation: patchOrderItemByExternalReferenceCode,
							variables: {
								externalReferenceCode:
									selectedSubscription?.productPurchaseKey,
								orderItem: {
									customFields: [
										{
											customValue: {
												data:
													selectedSubscription?.provisionedCount +
													1,
											},
											name: 'provisionedCount',
										},
									],
								},
							},
						});
					}
				}
				catch (error) {
					console.error(error);
				}

				navigate(urlPreviousPage, {
					state: {newKeyGeneratedAlert: true},
				});
			}

			return true;
		}
		catch (error) {
			Liferay.Util.openToast({
				message:
					error?.info?.title ??
					i18n.translate('an-unexpected-error-occurred'),
				title: i18n.translate('error'),
				type: 'danger',
			});

			console.error(error);

			setIsLoadingGenerateKey(false);

			return false;
		}
	}, [
		accountKey,
		client,
		selectedEndDate,
		hasComplimentaryKey,
		licenseEntryTypes,
		navigate,
		oAuthToken,
		provisioningServerAPI,
		provisioningService,
		selectedSubscription?.instanceSize,
		selectedSubscription?.productKey,
		selectedSubscription?.productPurchaseKey,
		selectedSubscription?.provisionedCount,
		selectedSubscription?.startDate,
		state.activationKeys,
		urlPreviousPage,
	]);

	const CustomComplimentaryKeyAlert = () => {
		return (
			<ClayAlert className="px-4 py-3" displayType="info">
				<span className="text-paragraph">
					{`${i18n.translate(
						'this-option-is-available-to-use-a-single-time-please-contact-your-liferay-representative-if-you-need-to-use-it-later'
					)} `}
				</span>
			</ClayAlert>
		);
	};

	const GetCustomAlert = ({activeKeysAvailable, subscriptionTerm}) => {
		if (activeKeysAvailable === 0) {
			return (
				<ClayAlert className="px-4 py-3" displayType="warning">
					<span className="text-paragraph">
						{`${i18n.translate(
							'key-activations-available-is-zero-to-deactivate-a-key-or-reach-out-to-provisioning-read'
						)} `}

						<a
							href={articleDeactivateKey}
							rel="noreferrer noopener"
							target="_blank"
						>
							<u className="font-weight-semi-bold warning-content-link">
								{i18n.translate('this-article')}
							</u>
						</a>
					</span>
				</ClayAlert>
			);
		}

		const handleAlertFirstDate = () => {
			if (subscriptionTerm.perpetual) {
				return getDateCustomFormat(
					new Date(),
					FORMAT_DATE_TYPES.day2DMonthSYearN
				);
			}

			return getDateCustomFormat(
				subscriptionTerm.startDate,
				FORMAT_DATE_TYPES.day2DMonthSYearN
			);
		};

		const handleAlertEndDate = () => {
			const endDateToFormat = isRenew
				? selectedEndDate
				: getLicenseKeyEndDatesByLicenseType({
						...selectedKeyData,
						selectedSubscription: {
							...subscriptionTerm,
						},
					});

			return getDateCustomFormat(
				endDateToFormat,
				FORMAT_DATE_TYPES.day2DMonthSYearN
			);
		};

		return (
			<ClayAlert className="px-4 py-3" displayType="info">
				<span className="text-paragraph">
					{hasNotPermanentLicense || doesNotAllowPermanentLicense
						? i18n.sub('activation-keys-will-be-valid-x-x', [
								handleAlertFirstDate(),
								handleAlertEndDate(),
							])
						: i18n.sub(
								'activation-keys-will-be-valid-indefinitely-starting-x-or-until-manually-deactivated',
								[
									getDateCustomFormat(
										subscriptionTerm.startDate,
										FORMAT_DATE_TYPES.day2DMonthSYearN
									),
								]
							)}
				</span>
			</ClayAlert>
		);
	};

	if (!generateFormValues || !accountKey || !oAuthToken) {
		return <GenerateNewKeySkeleton />;
	}

	return (
		<Layout
			footerProps={{
				footerClass: 'mx-5 mb-2',
				leftButton: (
					<Link to={urlPreviousPage}>
						<Button
							aria-label={i18n.translate('cancel')}
							className="btn btn-borderless btn-style-neutral"
							displayType="secondary"
						>
							{i18n.translate('cancel')}
						</Button>
					</Link>
				),
				middleButton: (
					<Button
						aria-label={i18n.translate('next')}
						disabled={
							(keyCount > availableActivationKeysTotal &&
								!hasComplimentaryKey) ||
							!selectedSubscription ||
							isLoadingGenerateKey ||
							!Object.keys(selectedSubscription).length
						}
						displayType="primary"
						isLoading={isLoadingGenerateKey}
						onClick={() => {
							const updatedSelectedKeyData = {
								doesNotAllowPermanentLicense,
								hasNotPermanentLicense,
								selectedSubscription: {...selectedSubscription},
							};

							if (!hasComplimentaryKey && isRenew) {
								if (keyCount === 1) {
									setStep(2);
									setSubmitKeyAction({submitKey});
								}
								else {
									submitKey();
								}
							}
							else {
								setStep(hasComplimentaryKey ? 1 : 2);
							}

							setSelectedKeyData((previousSelectedKeyData) => ({
								...previousSelectedKeyData,
								...updatedSelectedKeyData,
							}));
						}}
					>
						{!hasComplimentaryKey && isRenew && keyCount > 1
							? i18n.sub('renew-x-keys', [keyCount])
							: i18n.translate('next')}
					</Button>
				),
			}}
			headerProps={{
				headerClass: 'mb-3 ml-5 mt-4',
				helper: isRenew
					? renewKeySubtitle
					: i18n.translate(
							'select-the-subscription-and-key-type-you-would-like-to-generate'
						),
				title: i18n.translate(
					isRenew
						? 'renew-activation-keys'
						: 'generate-activation-keys'
				),
			}}
			layoutType="cp-generateKey"
		>
			<div className="px-6">
				<div className="d-flex justify-content-between mb-2">
					<div className="mr-3 w-100">
						<label htmlFor="basicInput">
							{i18n.translate('product')}
						</label>

						<div className="cp-select-card position-relative">
							<ClaySelect
								className="cp-select-card mr-2"
								disabled={true}
							>
								{subscriptionGroups?.map((product) => (
									<ClaySelect.Option
										key={product.name}
										label={productGroupName}
									/>
								))}
							</ClaySelect>

							<ClayIcon
								aria-label="Caret Icon Bottom"
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>

					<div className="ml-3 w-100">
						<label htmlFor="basicInput">
							{i18n.translate('version')}
						</label>

						<div className="position-relative">
							<ClaySelect
								className="cp-select-card mr-2"
								disabled={isRenew}
								onChange={({target}) => {
									setSelectedKeyData({
										licenseEntryType: selectedKeyType,
										productType: productGroupName,
										productVersion: target.value,
									});
									setSelectedVersion(target.value);
								}}
								value={selectedVersion}
							>
								{isRenew ? (
									<ClaySelect.Option
										key={uniqueVersionOfTheSelectedKey}
										label={uniqueVersionOfTheSelectedKey}
									/>
								) : (
									productVersions?.map((version) => (
										<ClaySelect.Option
											key={version.label}
											label={version.label}
										/>
									))
								)}
							</ClaySelect>

							<ClayIcon
								aria-label="Caret Icon Bottom"
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>
				</div>

				<div className="mt-4 w-100">
					<label htmlFor="basicInput">
						{i18n.translate('key-type')}
					</label>

					<div className="position-relative">
						<ClaySelect
							className="cp-select-card mr-2 pr-6 w-100"
							disabled={isRenew}
							onChange={({target}) => {
								setSelectedKeyType(target.value);
								setSelectedSubscription({});
								setHasComplimentaryKey(false);
							}}
							value={selectedKeyType}
						>
							{isRenew ? (
								<ClaySelect.Option
									key={productNames}
									label={productNames}
								/>
							) : (
								productKeyTypes &&
								productKeyTypes[selectedVersionIndex]?.map(
									(keyType) => (
										<ClaySelect.Option
											key={keyType}
											label={keyType}
										/>
									)
								)
							)}
						</ClaySelect>

						<ClayIcon
							aria-label="Caret Icon Bottom"
							className="select-icon"
							symbol="caret-bottom"
						/>
					</div>
				</div>

				<div>
					<div className="mb-3 mt-4">
						<h5>{i18n.translate('subscription')}</h5>
					</div>

					<div>
						{subscriptionTerms
							?.filter((subscriptionTerm) => {
								return (
									(new Date() <
										new Date(subscriptionTerm.endDate) ||
										subscriptionTerm.perpetual) &&
									subscriptionTerm
								);
							})
							.sort(
								(
									firstSubscriptionTerm,
									secondSubscriptionTerm
								) => {
									const firstAvailableKeysQty =
										firstSubscriptionTerm.quantity -
										firstSubscriptionTerm.provisionedCount;

									const secondAvailableKeysQty =
										secondSubscriptionTerm.quantity -
										secondSubscriptionTerm.provisionedCount;

									return (
										secondAvailableKeysQty -
										firstAvailableKeysQty
									);
								}
							)
							?.map((subscriptionTerm, index) => {
								const selected =
									JSON.stringify(selectedSubscription) ===
									JSON.stringify({
										...subscriptionTerm,
										index,
									});
								const currentStartAndEndDate = `${getDateCustomFormat(
									subscriptionTerm.startDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
								)} - ${getDateCustomFormat(
									subscriptionTerm.endDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
								)}`;

								const selectedKeyData = {
									index,
									licenseEntryType: selectedKeyType,
									productType: productGroupName,
									productVersion: selectedVersion,
								};

								let numberOfActivationKeysAvailable =
									subscriptionTerm.quantity -
									subscriptionTerm.provisionedCount;
								numberOfActivationKeysAvailable =
									numberOfActivationKeysAvailable < 0
										? 0
										: numberOfActivationKeysAvailable;

								const displayAlertType = (
									<GetCustomAlert
										activeKeysAvailable={
											numberOfActivationKeysAvailable
										}
										subscriptionTerm={subscriptionTerm}
									/>
								);

								const HandleCustomAlert = () => {
									if (numberOfActivationKeysAvailable === 0) {
										return displayAlertType;
									}

									return selected && displayAlertType;
								};

								return (
									<Radio
										description={i18n.sub(
											'key-activation-available-x-of-x',
											[
												numberOfActivationKeysAvailable,
												subscriptionTerm.quantity,
											]
										)}
										hasCustomAlert={<HandleCustomAlert />}
										isActivationKeyAvailable={
											subscriptionTerm.quantity -
												subscriptionTerm.provisionedCount >
											0
										}
										key={index}
										label={
											subscriptionTerm?.perpetual
												? i18n.sub('perpetual-duration')
												: currentStartAndEndDate
										}
										onChange={(event) => {
											setSelectedSubscription({
												...event.target.value,
												index,
											});
											setAvailableActivationKeysTotal(
												numberOfActivationKeysAvailable
											);
											setSelectedKeyData(selectedKeyData);
											setHasComplimentaryKey(false);
										}}
										selected={selected}
										subtitle={i18n.sub('instance-size-x', [
											subscriptionTerm?.instanceSize || 1,
										])}
										value={subscriptionTerm}
									/>
								);
							})}
					</div>

					{featureFlags.includes('LPS-148342') &&
						allowComplimentary && (
							<Radio
								hasCustomAlert={
									hasComplimentaryKey && (
										<CustomComplimentaryKeyAlert />
									)
								}
								isActivationKeyAvailable={5}
								label={i18n.translate('complimentary')}
								onChange={(event) => {
									setSelectedSubscription({
										...event.target.value,
									});
									setHasComplimentaryKey(true);

									setSelectedKeyData({
										licenseEntryType: isRenew
											? productName
											: selectedKeyType,
										productType: productGroupName,
										productVersion: isRenew
											? uniqueVersionOfTheSelectedKey
											: selectedVersion,
									});
								}}
								selected={hasComplimentaryKey}
								subtitle={i18n.translate(
									'choose-this-option-if-you-want-an-activation-key-for-30-days'
								)}
								value={
									isRenew && !isSingleComplimentaryKey
										? mockedValuesForComplimentaryKeysOfTheSelectedKeys
										: mockedValuesForComplimentaryKeys
								}
							/>
						)}

					<div className="dropdown-divider mt-3"></div>
				</div>
			</div>
		</Layout>
	);
};

SelectSubscription.Skeleton = GenerateNewKeySkeleton;
export default SelectSubscription;
