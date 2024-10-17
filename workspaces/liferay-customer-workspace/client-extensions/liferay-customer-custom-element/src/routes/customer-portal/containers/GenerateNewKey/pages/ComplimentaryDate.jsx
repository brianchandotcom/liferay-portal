/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {ClayCheckbox, ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import useProvisioningLicenseKeys from '~/common/hooks/useProvisioningLicenseKeys';
import {putSubscriptionInKey} from '~/common/services/liferay/rest/raysource/LicenseKeys';
import i18n from '../../../../../common/I18n';
import {Button} from '../../../../../common/components';
import Layout from '../../../../../common/containers/setup-forms/Layout';
import useGetPurposeComplimentaryKeyList from './hooks/useGetPurposeComplimentaryKeyList';
import {getRenewKeySubtitle} from '../utils/renewKeySubtitle';

const now = new Date();
const NAVIGATION_YEARS_RANGE = 2;
const SELECTED_PURPOSE_OTHER = 'Other, please specify';

const ComplimentaryDate = ({
	accountKey,
	oAuthToken,
	purposeDescription,
	selectedKeyData,
	setPurposeDescription,
	setSelectedKeyData,
	setStep,
	state,
	urlPreviousPage,
}) => {
	const {provisioningServerAPI} = useAppPropertiesContext();
	const provisioningService = useProvisioningLicenseKeys();
	const currentDate = now.toISOString().split('T')[0];
	const [selectedSubscription] = useState(
		selectedKeyData?.selectedSubscription
	);
	const purposeComplimentaryKeyList = useGetPurposeComplimentaryKeyList();
	const [selectedPurpose, setSelectedPurpose] = useState('');

	useEffect(() => {
		if (purposeComplimentaryKeyList.length) {
			setPurposeDescription(purposeComplimentaryKeyList[0]?.value);
		}
	}, [purposeComplimentaryKeyList]);

	const [expandedOnOrAfter, setExpandedOnOrAfter] = useState(false);
	const [selectedStartDate, setSelectedStartDate] = useState(currentDate);
	const [isLoadingGenerateKey, setIsLoadingGenerateKey] = useState(false);
	const checkedBoxSubscription = true;
	const [checkBoxConfirmationTerms, setCheckBoxConfirmationTerms] = useState(
		false
	);

	const navigate = useNavigate();

	const {endDate, startDate} = useMemo(() => {
		const inputStartDate = new Date(selectedStartDate);
		const timestamp = inputStartDate.getTime();
		const timezoneOffset = inputStartDate.getTimezoneOffset() * 60000;
		const startDateFormatted = new Date(timestamp + timezoneOffset);
		const startDate = new Date(timestamp + timezoneOffset);
		const endDate = new Date(
			startDateFormatted.setDate(startDateFormatted.getDate() + 30)
		);

		return {
			endDate,
			inputStartDate,
			startDate,
			startDateFormatted,
		};
	}, [selectedStartDate]);

	const updatedSelectedSubscription = useMemo(() => {
		return {
			...selectedSubscription,
			complimentary: true,
			endDate,
			startDate,
		};
	}, [selectedSubscription, endDate, startDate]);

	const hasDateLimitExceeded = useMemo(() => {
		const daysLimit = 29;

		const startDateLimit = new Date();

		startDateLimit.setDate(startDateLimit.getDate() - daysLimit);

		return startDate < startDateLimit;
	}, [startDate]);

	const isRenew = state?.id === 'renew';
	const keyCount = state?.activationKeys?.length;	
	const renewKeySubtitle = getRenewKeySubtitle(state);

	const isComplimentaryKeys = state.activationKeys?.map((item) => {
		return item.complimentary;
	});
	const isComplimentaryKey = [...new Set(isComplimentaryKeys)].join(', ');

	const hasDesiredEntry = state.activationKeys.some(
		(item) =>
			item.licenseEntryType === 'oem' ||
			item.licenseEntryType === 'virtual-cluster' ||
			item.licenseEntryType === 'enterprise'
	);

	const submitKey = useCallback(async () => {
		const selectedFields = [
			'active',
			'description',
			'hostName',
			'ipAddresses',
			'licenseEntryType',
			'macAddresses',
			'maxClusterNodes',
			'name',
			'productName',
			'productVersion',
			'sizing',
		];

		const saveSubscriptionKey = async (id) => {
			return putSubscriptionInKey(oAuthToken, provisioningServerAPI, id);
		};

		const generateLicenseKey = async (item, isComplimentary = false) => {
			const licenseKey = {
				accountKey,
				complimentary: 'true',
				expirationDate: endDate,
				productKey: selectedKeyData.selectedSubscription.productKey,
				startDate,
			};
			selectedFields.forEach((field) => {
				licenseKey[field] = item[field];
			});
			const response = await provisioningService.createNewGenerateKey(
				accountKey,
				licenseKey
			);

			if (checkedBoxSubscription && isComplimentary) {
				await saveSubscriptionKey(response?.items?.[0]?.id);
			}
		};

		setIsLoadingGenerateKey(true);

		try {
			const updatedActivationKeysItem = state.activationKeys.map(item => ({
				...item,
				description: purposeDescription,
			}));

			if (hasDesiredEntry) {
				const createKeyPromises = updatedActivationKeysItem.map(
					async (item) => {
						await generateLicenseKey(item);
					}
				);

				await Promise.all(createKeyPromises);

				setIsLoadingGenerateKey(false);

				navigate(urlPreviousPage, {
					state: {isMultipleKeys: keyCount > 1, newKeyGeneratedAlert: true},
				});

				return true;
			} else {
				const results = await Promise.all(
					updatedActivationKeysItem.map(async (item) => {
						await generateLicenseKey(item, isComplimentaryKey);
					})
				);

				await Promise.all(results);

				setIsLoadingGenerateKey(false);

				navigate(urlPreviousPage, {
					state: {isMultipleKeys: keyCount > 1, newKeyGeneratedAlert: true},
				});

				return true;
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

			return false;
		}
	}, [
		accountKey,
		checkedBoxSubscription,
		endDate,
		hasDesiredEntry,
		isComplimentaryKey,
		selectedKeyData,
		navigate,
		oAuthToken,
		provisioningServerAPI,
		provisioningService,
		purposeDescription,
		state.activationKeys,
		startDate,
		urlPreviousPage,
	]);

	return (
		<div>
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
					middleButton: (
						<div>
							<Button
								className="btn btn-secondary mr-3"
								displayType="secundary"
								onClick={() => {
									setSelectedKeyData(() => ({
										selectedSubscription: {},
									}));
									setStep(0);
								}}
							>
								{i18n.translate('previous')}
							</Button>

							<Button
								disabled={
									!checkBoxConfirmationTerms ||
									!purposeDescription ||
									!selectedStartDate ||
									hasDateLimitExceeded ||
									isLoadingGenerateKey
								}
								displayType="primary"
								isLoading={isLoadingGenerateKey}
								onClick={() => {
									if (isRenew && keyCount > 1) {
										submitKey();
									} else {
										setSelectedKeyData(
											(previousSelectedKeyData) => ({
												...previousSelectedKeyData,
												selectedSubscription: updatedSelectedSubscription,
											})
										);

										setStep(2);
									}
								}}
							>
								{isRenew && keyCount > 1
									? i18n.sub(
										'renew-x-keys',
											[keyCount]
									  )
									: i18n.translate('next')}
							</Button>
						</div>
					),
				}}
				headerProps={{
					headerClass: 'ml-5 mt-4 mb-3',
					helper: isRenew ? renewKeySubtitle : i18n.translate('select-the-subscription-and-key-type-you-would-like-to-generate'),
					title: i18n.translate(isRenew ? 'renew-activation-keys' : 'generate-activation-keys'),
				}}
				layoutType="cp-generateKey"
			>
				<div className="h-50 mx-6">
					<h2>{i18n.translate('complimentary')}</h2>

					<p>
						{i18n.translate(
							'you-can-use-this-option-to-generate-complimentary-activation-keys-with-a-duration-of-30-days'
						)}
					</p>

					<h5>{i18n.translate('start-date')}</h5>

					<ClayDatePicker
						dateFormat="yyyy-MM-dd"
						expanded={expandedOnOrAfter}
						onChange={(value, eventType) => {
							setSelectedStartDate(value);

							if (eventType === 'click') {
								setExpandedOnOrAfter(false);
							}
						}}
						onExpandedChange={setExpandedOnOrAfter}
						placeholder={i18n.translate('yyyy-mm-dd')}
						value={selectedStartDate}
						years={{
							end: now.getFullYear() + NAVIGATION_YEARS_RANGE,
							start:
								now.getFullYear() -
								(now.getMonth() === 0 ? 1 : 0),
						}}
					/>

					{hasDateLimitExceeded && (
						<p className="text-danger">
							{i18n.translate(
								'the-start-date-must-be-less-than-30-days-ago'
							)}
						</p>
					)}

					<p>
						{i18n.translate(
							'choose-the-date-you-would-like-this-option-to-start'
						)}
					</p>

					<h5>{i18n.translate('purpose-of-complimentary-key')}</h5>

					<div className="position-relative">
						<ClaySelect
							className="mr-2 pr-6 w-100"
							onChange={({target}) => {
								setSelectedPurpose(target.value);
								setPurposeDescription(() =>
									target.value === SELECTED_PURPOSE_OTHER
										? ''
										: target.value
								);
							}}
							value={selectedPurpose}
						>
							{[
								...purposeComplimentaryKeyList,
								{
									label: i18n.translate(
										'other-please-specify'
									),
									value: SELECTED_PURPOSE_OTHER,
								},
							]?.map((item) => (
								<ClaySelect.Option
									key={item.label}
									label={item.label}
								/>
							))}
						</ClaySelect>

						<ClayIcon
							aria-label="Caret Icon Bottom"
							className="select-icon"
							symbol="caret-bottom"
						/>
					</div>

					{selectedPurpose === SELECTED_PURPOSE_OTHER && (
						<div className="pt-3">
							<ClayInput
								component="textarea"
								name="description"
								onChange={(event) =>
									setPurposeDescription(event.target.value)
								}
								placeholder={i18n.translate(
									'enter-the-purpose'
								)}
								type="text"
								value={purposeDescription}
							/>
						</div>
					)}

					<h5 className="mt-4">
						{i18n.translate('confirmation-terms')}
					</h5>

					<div className="d-flex mt-4">
						<div className="pr-2 pt-1">
							<ClayCheckbox
								checked={checkBoxConfirmationTerms}
								id="expiration-checkbox"
								onChange={() =>
									setCheckBoxConfirmationTerms(
										(checkedBoxSubcription) =>
											!checkedBoxSubcription
									)
								}
							/>
						</div>

						<label>
							{i18n.translate(
								'the-requested-activation-key-exceeds-the-purchased-subscriptions-for-this-liferay-project-in-case-of-unauthorized-use-liferay-can-request-financial-compensation-for-breach-of-use'
							)}
						</label>
					</div>

					<div className="dropdown-divider mt-6"></div>
				</div>
			</Layout>
		</div>
	);
};

export default ComplimentaryDate;
