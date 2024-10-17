/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {FieldArray, Formik} from 'formik';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import useProvisioningLicenseKeys from '~/common/hooks/useProvisioningLicenseKeys';
import {Liferay} from '~/common/services/liferay';
import i18n from '../../../../../common/I18n';
import {Badge, Button, Input} from '../../../../../common/components';
import Layout from '../../../../../common/containers/setup-forms/Layout';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {patchOrderItemByExternalReferenceCode} from '../../../../../common/services/liferay/graphql/queries';
import {putSubscriptionInKey} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import getInitialGenerateNewKey from '../../../../../common/utils/constants/getInitialGenerateNewKey';
import GenerateCardLayout from '../GenerateCardLayout';
import KeyInputs from '../KeyInputs';
import KeySelect from '../KeySelect';
import {getLicenseEntryTypeSelected} from '../utils/licenseEntryType';
import {getLicenseKeyEndDatesByLicenseType} from '../utils/licenseKeyEndDate';
import {getRenewKeySubtitle} from '../utils/renewKeySubtitle';

const RequiredInformation = ({
	accountKey,
	errors,
	expirationRenewDate,
	hasComplimentaryKey,
	licenseEntryTypeName,
	oAuthToken,
	selectedKeyData,
	setErrors,
	setStep,
	setTouched,
	startRenewDate,
	state,
	submitKeyAction,
	touched,
	urlPreviousPage,
	values,
}) => {
	const {
		client,
		featureFlags,
		provisioningServerAPI,
	} = useAppPropertiesContext();

	const provisioningService = useProvisioningLicenseKeys();

	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [addButtonDisabled, setAddButtonDisabled] = useState(false);
	const [showKeyEmptyError, setShowKeyEmptyError] = useState(false);
	const [isLoadingGenerateKey, setIsLoadingGenerateKey] = useState(false);
	const [availableKeys, setAvailableKeys] = useState(1);
	const [checkedBoxSubscription, setCheckedBoxSubscription] = useState(true);
	const navigate = useNavigate();

	const hasTouched = !Object.keys(touched).length;
	const hasError = Object.keys(errors).length;

	const isRenew = state?.id === 'renew';
	const renewKey = state?.activationKeys[0];
	const renewKeySubtitle = getRenewKeySubtitle(state);

	const avaliableKeysMaximumCount =
		selectedKeyData?.selectedSubscription?.quantity;
	const usedKeysCount =
		selectedKeyData?.selectedSubscription?.provisionedCount;

	const hasFilledAtLeastOneField = values?.keys?.every((key) => {
		if (isRenew) {
			return true;
		}

		const fieldValues = Object.values(key).filter(Boolean);

		return !!fieldValues.length;
	});

	const isComplimentaryKey =
		selectedKeyData?.selectedSubscription.complimentary;

	const newUsedKeys = usedKeysCount + values?.keys?.length;
	const hasReachedMaximumKeys = newUsedKeys === avaliableKeysMaximumCount;

	const isOemOrEnterprise =
		selectedKeyData?.licenseEntryType.includes('OEM') ||
		selectedKeyData?.licenseEntryType.includes('Enterprise') ||
		(state.activationKeys.length &&
			(state?.activationKeys[0].licenseEntryType.includes('oem') ||
				state?.activationKeys[0].licenseEntryType.includes(
					'enterprise'
				)));

	const hasNotPermanentLicenseRenewKeyType =
		(state.activationKeys.length &&
			renewKey?.licenseEntryType.includes('virtual-cluster')) ||
		renewKey?.licenseEntryType.includes('oem') ||
		renewKey?.licenseEntryType.includes('enterprise');

	const handleHasNotPermanentLicenseValidation = state?.activationKeys.length
		? !selectedKeyData.hasNotPermanentLicense &&
		  !hasNotPermanentLicenseRenewKeyType
		: !selectedKeyData.hasNotPermanentLicense;

	useEffect(() => {
		const getVerificationDisabledType = () => {
			if (
				selectedKeyData.hasNotPermanentLicense ||
				hasNotPermanentLicenseRenewKeyType
			) {
				if (isOemOrEnterprise) {
					return !values.name;
				}

				return !values.name || !values.maxClusterNodes;
			}

			return !hasFilledAtLeastOneField || hasError;
		};

		setBaseButtonDisabled(getVerificationDisabledType());

		setAddButtonDisabled(
			hasReachedMaximumKeys || !hasFilledAtLeastOneField
		);
	}, [
		hasError,
		hasFilledAtLeastOneField,
		hasNotPermanentLicenseRenewKeyType,
		hasReachedMaximumKeys,
		selectedKeyData.hasNotPermanentLicense,
		isOemOrEnterprise,
		values.maxClusterNodes,
		values.name,
	]);

	const addActivationKeyProp = hasReachedMaximumKeys
		? {
				title: i18n.translate(
					'maximum-number-of-activation-keys-reached-for-this-subscription'
				),
		  }
		: {};

	const licenseKey = useMemo(
		() => ({
			accountKey,
			active: true,
			complimentary: selectedKeyData?.selectedSubscription.complimentary,
			description: values?.description,
			expirationDate:
				getLicenseKeyEndDatesByLicenseType(selectedKeyData) ??
				selectedKeyData?.selectedSubscription.endDate,
			licenseEntryType: getLicenseEntryTypeSelected(selectedKeyData),
			maxClusterNodes: values?.maxClusterNodes || 0,
			name: values?.name,
			productKey: selectedKeyData?.selectedSubscription.productKey,
			productName: `${selectedKeyData?.productType} ${selectedKeyData?.licenseEntryType}`,
			productPurchaseKey:
				selectedKeyData?.selectedSubscription.productPurchaseKey,
			productVersion: selectedKeyData?.productVersion,
			sizing: `Sizing ${
				selectedKeyData?.selectedSubscription?.instanceSize || 1
			}`,
			startDate: selectedKeyData?.selectedSubscription.startDate,
		}),
		[
			accountKey,
			selectedKeyData,
			values?.description,
			values?.maxClusterNodes,
			values?.name,
		]
	);

	const submitKey = useCallback(async () => {
		if (
			!selectedKeyData.hasNotPermanentLicense &&
			!hasFilledAtLeastOneField
		) {
			setErrors({
				keys: [...new Array(values.keys?.length)].map(() => ({
					hostName: true,
					ipAddresses: true,
					macAddresses: true,
				})),
			});

			setTouched(
				{
					keys: [...new Array(values.keys?.length)].map(() => ({
						hostName: true,
						ipAddresses: true,
						macAddresses: true,
					})),
				},
				false
			);

			setShowKeyEmptyError(true);

			return;
		}

		const saveSubscriptionKey = async (id) => {
			return putSubscriptionInKey(oAuthToken, provisioningServerAPI, id);
		};

		try {
			if (selectedKeyData.hasNotPermanentLicense) {
				setIsLoadingGenerateKey(true);

				const response = await provisioningService.createNewGenerateKey(
					accountKey,
					licenseKey
				);

				if (checkedBoxSubscription) {
					await saveSubscriptionKey(response?.items?.[0]?.id);
				}

				setIsLoadingGenerateKey(false);

				navigate(urlPreviousPage, {
					state: {newKeyGeneratedAlert: true},
				});
			} else {
				setIsLoadingGenerateKey(true);

				const results = await Promise.all(
					values?.keys?.map(
						({hostName, ipAddresses, macAddresses}) => {
							licenseKey.macAddresses = macAddresses.replace(
								'\n',
								','
							);
							licenseKey.hostName = hostName.replace('\n', ',');
							licenseKey.ipAddresses = ipAddresses.replace(
								'\n',
								','
							);

							return provisioningService.createNewGenerateKey(
								accountKey,
								licenseKey
							);
						}
					)
				);

				if (checkedBoxSubscription && isComplimentaryKey) {
					await saveSubscriptionKey(results[0]?.items[0]?.id);
				}

				setIsLoadingGenerateKey(false);

				if (!isComplimentaryKey) {
					await client.mutate({
						context: {
							displaySuccess: false,
						},
						mutation: patchOrderItemByExternalReferenceCode,
						variables: {
							externalReferenceCode:
								licenseKey.productPurchaseKey,
							orderItem: {
								customFields: [
									{
										customValue: {
											data:
												selectedKeyData
													.selectedSubscription
													.provisionedCount + 1,
										},
										name: 'provisionedCount',
									},
								],
							},
						},
					});
				}

				navigate(urlPreviousPage, {
					state: {newKeyGeneratedAlert: true},
				});
			}
		} catch (error) {
			Liferay.Util.openToast({
				message:
					error?.info?.title ??
					i18n.translate('an-unexpected-error-occurred'),
				title: i18n.translate('error'),
				type: 'danger',
			});

			console.error(error);

			setIsLoadingGenerateKey(false);
		}
	}, [
		accountKey,
		checkedBoxSubscription,
		client,
		hasFilledAtLeastOneField,
		selectedKeyData.hasNotPermanentLicense,
		selectedKeyData.selectedSubscription.provisionedCount,
		isComplimentaryKey,
		licenseKey,
		navigate,
		oAuthToken,
		provisioningServerAPI,
		provisioningService,
		setErrors,
		setTouched,
		urlPreviousPage,
		values.keys,
	]);

	const CheckboxSubscriptionNotification = () => {
		if (
			featureFlags.includes('LPS-180001') &&
			(selectedKeyData?.hasNotPermanentLicense || isComplimentaryKey)
		) {
			return (
				<>
					<div className="d-flex mb-3 pt-2">
						<div className="pr-2 pt-1">
							<ClayCheckbox
								checked={checkedBoxSubscription}
								id="expiration-checkbox"
								onChange={() =>
									setCheckedBoxSubscription(
										(checkedBoxSubcription) =>
											!checkedBoxSubcription
									)
								}
							/>
						</div>

						<label htmlFor="expiration-checkbox">
							{i18n.sub(
								'receive-expiration-notifications-through-email-when-this-activation-key-is-about-to-expire-x-days-before-x-days-before-and-on-the-day-of-expiration-unsubscribe-at-any-time',
								[30, 15]
							)}
						</label>
					</div>
				</>
			);
		}
	};

	const HandleButtonValue = () => {
		if (isRenew) {
			return state?.activationKeys.length === 1 ? i18n.sub('renew-x-key', [state?.activationKeys.length]) : i18n.sub('renew-x-keys', [state?.activationKeys.length]);
		}

		if (selectedKeyData?.licenseEntryType.includes('Virtual Cluster')) {
			return i18n.sub(
				Number(values.maxClusterNodes) === 1
					? 'generate-cluster-x-key'
					: 'generate-cluster-x-keys',
				[values.maxClusterNodes]
			);
		}

		return i18n.sub(
			availableKeys > 1 ? 'generate-x-keys' : 'generate-x-key',
			[availableKeys]
		);
	};

	return (
		<div className="d-flex justify-content-end">
			<Layout
				footerProps={{
					footerClass: 'mx-5 mb-2',
					leftButton: (
						<Link to={urlPreviousPage}>
							<Button
								className="btn btn-borderless btn-style-neutral"
								displayType="secondary"
							>
								{i18n.translate('cancel')}
							</Button>
						</Link>
					),
					rightButton: (
						<div>
							<Button
								className="btn btn-secondary mr-3"
								displayType="secundary"
								onClick={() =>
									setStep(isComplimentaryKey ? 1 : 0)
								}
							>
								{i18n.translate('previous')}
							</Button>

							<Button
								disabled={
									baseButtonDisabled || isLoadingGenerateKey
								}
								displayType="primary"
								isLoading={isLoadingGenerateKey}
								onClick={() => {
									if (!hasComplimentaryKey && isRenew) {
										setIsLoadingGenerateKey(true);

										return submitKeyAction.submitKey();
									}

									submitKey();
								}}
							>
								<HandleButtonValue />
							</Button>
						</div>
					),
				}}
				headerProps={{
					headerClass: 'ml-5 my-4',
					helper: isRenew ? renewKeySubtitle : i18n.translate(
						'fill-out-the-information-required-to-generate-the-activation-key'
					),
					title: i18n.translate(isRenew ? 'renew-activation-keys' : 'generate-activation-keys'),
				}}
				layoutType="cp-required-info"
			>
				<FieldArray
					name="keys"
					render={({pop, push}) => (
						<>
							<div className="px-6">
								<h4>{i18n.translate('environment-details')}</h4>

								<div className="dropdown-divider mb-4 mt-2"></div>

								<div className="mb-3">
									<div className="cp-input-generate-label">
										<Input
											disabled={isRenew}
											label={i18n.translate(
												'environment-name'
											)}
											name="name"
											placeholder="e.g. Liferay Ecommerce Site"
											required
											type="text"
										/>
									</div>

									<div className="font-weight-normal h6 ml-3 mt-1">
										{i18n.translate(
											'name-this-environment-this-cannot-be-edited-later'
										)}
									</div>
								</div>

								<div className="mb-3">
									<div className="cp-input-generate-label">
										<Input
											component="textarea"
											disabled={
												hasComplimentaryKey || isRenew
											}
											label={i18n.translate(
												'description'
											)}
											name="description"
											placeholder="e.g. Liferay Dev Environment – ECOM DXP 7.2"
											type="text"
										/>
									</div>
									
									{!hasComplimentaryKey && (
										<h6 className="font-weight-normal ml-3 mr-0 mt-1">
											{i18n.translate(
												'include-a-description-to-uniquely-identify-this-environment-this-cannot-be-edited-later'
											)}
										</h6>
									)}
								</div>
							</div>

							{handleHasNotPermanentLicenseValidation ? (
								<div className="px-6">
									<h4 className="mt-5">
										{i18n.translate(
											'activation-key-server-details'
										)}
									</h4>

									<div className="dropdown-divider mb-4 mt-2"></div>

									<ClayAlert
										className="px-3 py-1"
										displayType="info"
									>
										<span>
											{i18n.translate(
												'please-provide-static-server-identifiers-that-do-not-change-over-time'
											)}
										</span>
									</ClayAlert>

									{values?.keys?.map((_, index) => (
										<KeyInputs
											id={index}
											isRenew={isRenew}
											key={index}
										/>
									))}

									{showKeyEmptyError && !!hasError && (
										<Badge badgeClassName="m-0">
											<span className="pl-1">
												{i18n.translate(
													'one-host-name-per-instance-or-ip-address-is-required'
												)}
											</span>
										</Badge>
									)}

									{values?.keys?.length > 1 && (
										<Button
											className="btn btn-secondary mb-3 mr-3 mt-4 py-2"
											displayType="secundary"
											onClick={() => {
												pop();
												setAvailableKeys(
													(previousAdmins) =>
														previousAdmins - 1
												);
												setBaseButtonDisabled(
													hasTouched || hasError
												);
											}}
										>
											<ClayIcon
												className="cp-button-icon-plus mr-2"
												symbol="hr"
											/>

											{i18n.translate(
												'remove-activation-key'
											)}
										</Button>
									)}

									<ClayTooltipProvider
										contentRenderer={({title}) => (
											<div>
												<p className="font-weight-bold m-0"></p>

												<p className="font-weight-normal m-0 text-paragraph">
													{title}
												</p>
											</div>
										)}
										delay={200}
									>
										<Button
											className="btn btn-secondary mb-3 mt-4 p-0"
											disabled={addButtonDisabled}
											displayType="secundary"
											onClick={() => {
												push(
													getInitialGenerateNewKey()
												);

												setAvailableKeys(
													(
														previousAvailableAdminsRoles
													) =>
														previousAvailableAdminsRoles +
														1
												);
											}}
										>
											<div
												className="mx-1 p-2"
												{...addActivationKeyProp}
											>
												<ClayIcon
													className="cp-button-icon-plus mr-2"
													symbol="plus"
												/>

												{i18n.translate(
													'add-activation-key'
												)}
											</div>
										</Button>
									</ClayTooltipProvider>

									<CheckboxSubscriptionNotification />

									<div className="dropdown-divider"></div>
								</div>
							) : (
								<div className="mx-6">
									{!(isComplimentaryKey && isRenew) && !isOemOrEnterprise && (
										<div className="cp-input-generate-label">
											<KeySelect
												avaliableKeysMaximumCount={
													avaliableKeysMaximumCount
												}
												isRenew={isRenew}
												minAvaliableKeysCount={
													avaliableKeysMaximumCount -
													usedKeysCount
												}
												selectedClusterNodes={
													values.maxClusterNodes
												}
											/>
										</div>
									)}

									<CheckboxSubscriptionNotification />
								</div>
							)}
						</>
					)}
				/>
			</Layout>

			<GenerateCardLayout
				expirationRenewDate={expirationRenewDate}
				isRenew={isRenew}
				licenseEntryTypeName={licenseEntryTypeName}
				selectedKeyData={selectedKeyData}
				startRenewDate={startRenewDate}
			/>
		</div>
	);
};

const RequiredInformationForm = (props) => {
	const isRenew = props.state?.id === 'renew';
	const renewKey = props.state?.activationKeys[0];

	const handleDescriptionValue = () => {
		if (props?.hasComplimentaryKey) {
			return props?.purposeDescription;
		}

		if (isRenew) {
			return renewKey.description;
		}

		return '';
	};

	return (
		<Formik
			initialValues={{
				description: handleDescriptionValue(),
				keys: [getInitialGenerateNewKey(isRenew, renewKey)],
				maxClusterNodes: isRenew ? renewKey.maxClusterNodes : '',
				name: isRenew ? renewKey.name : '',
			}}
		>
			{(formikProps) => (
				<RequiredInformation {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default RequiredInformationForm;
