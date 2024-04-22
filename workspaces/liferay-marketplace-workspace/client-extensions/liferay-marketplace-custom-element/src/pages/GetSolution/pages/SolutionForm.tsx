/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useLayoutEffect, useState} from 'react';
import {
	NavigateFunction,
	useNavigate,
	useOutletContext,
} from 'react-router-dom';
import {z} from 'zod';

import {Header} from '../../../components/Header/Header';
import FormInput from '../../../components/Input/formInput';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import Loading from '../../../components/Loading';
import Select from '../../../components/Select/Select';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useCommerceRegions from '../../../hooks/useCommerceRegions';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import {phones} from '../../../utils/phones';
import {usePurchasedOrders} from '../../CustomerDashboard/usePurchasedOrders';

export type UserForm = z.infer<typeof zodSchema.accountCreator>;

const AccountForm = () => {
	const {properties} = useMarketplaceContext();
	const {accountSelected, accounts} = useOutletContext<{
		accountSelected: Account;
		accounts: Account[];
	}>();
	const {data: commerceRegionsResponse} = useCommerceRegions(
		new URLSearchParams({fields: 'name,title_i18n', pageSize: '-1'})
	);
	const [agreeToTermsAndConditions, setAgreeToTermsAndConditions] = useState(
		false
	);
	const [currentPhonesFlags, setCurrentPhonesFlags] = useState({
		code: '+1',
		flag: 'en-us',
	});
	const navigate = useNavigate();
	const {accountForm, onSubmit} = useOutletContext<any>();

	const commerceRegions = commerceRegionsResponse?.items ?? [];

	const inputProps = {
		errors: accountForm.formState.errors,
		register: accountForm.register,
		required: true,
	};

	const {
		data: placedOrderResponse = {items: []},
		isLoading,
		isValidating,
	} = usePurchasedOrders({
		accountId: accountSelected?.id,
		channelId: Liferay.CommerceContext.commerceChannelId,
		orderTypeExternalReferenceCodes: ['SOLUTION30', 'SOLUTIONS7'],
		page: 1,
		pageSize: 200,
	});

	const submitTrial = async (form: UserForm) => {
		Liferay.fire(`submit-marketo-form/${properties.marketoFormId}`, {
			Company: form.companyName,
			Country: form.country,
			Email: form.emailAddress,
			FirstName: form.givenName,
			LastName: form.familyName,
			Phone: `${form.phone.code} ${form.phoneNumber} ${
				form.extension || ''
			}`,
			Title: 'Developer',
		});

		await onSubmit();
	};

	if (isLoading || isValidating) {
		return <Loading />;
	}

	if (placedOrderResponse.items.length) {
		return (
			<div>
				<h1 className="text-center">Trial not available.</h1>

				<p className="mt-7">
					Dear <strong>{Liferay.ThemeDisplay.getUserName()}</strong>,
					based on our records, you have already completed a trial.
					Therefore currently we are unable to start your trial.
					Please contact our sales department via email -
					<a className="ml-1" href="mailto:sales@liferay.com">
						sales@liferay.com
					</a>
					.
				</p>

				<ClayButton
					displayType="secondary"
					onClick={() => {
						Liferay.Util.navigate(getSiteURL() + '/p/try-it-now');
					}}
				>
					Return to trial page
				</ClayButton>
			</div>
		);
	}

	const singleAccount = accounts.length === 1;

	return (
		<div className="align-items-center d-flex flex-column justify-content-center">
			<Header
				description={
					<div className="d-flex flex-column justify-content-center text-center w-100">
						<p className="m-0">
							Your trial is provisioned by the Liferay
							Marketplace.
						</p>

						<p>
							To continue, please enter the required information.
						</p>
					</div>
				}
				title={
					<div className="d-flex flex-column justify-content-center text-center">
						Create a Trial
					</div>
				}
			/>

			<ClayForm onSubmit={accountForm.handleSubmit(submitTrial)}>
				<label className="font-weight-bold mr-4 title-label">
					Profile Info
				</label>

				<hr className="solid" />

				<ClayForm.Group>
					<div className="d-flex justify-content-between">
						<div className="form-group mb-0 pr-2 w-50">
							<FormInput
								{...inputProps}
								boldLabel
								label="First Name"
								name="givenName"
							/>
						</div>

						<div className="form-group mb-0 pl-2 w-50">
							<FormInput
								{...inputProps}
								boldLabel
								label="Last Name"
								name="familyName"
							/>
						</div>
					</div>

					<FormInput
						{...inputProps}
						boldLabel
						label="Company"
						name="companyName"
						placeholder="Enter company name"
					/>

					<Select
						{...inputProps}
						boldLabel
						className="p-2"
						defaultOption
						defaultOptionLabel="Select..."
						label="Country"
						name="country"
						options={commerceRegions.map((region) => {
							const country =
								region.title_i18n[
									Liferay.ThemeDisplay.getLanguageId()
								] ||
								region.title_i18n[
									Liferay.ThemeDisplay.getDefaultLanguageId()
								] ||
								region.name;

							return {
								key: country,
								name: country,
							};
						})}
					/>

					<ClayForm.Group>
						<label className="font-weight-bold mr-4 title-label">
							Contact Info
						</label>

						<hr className="solid" />

						<FormInput
							{...inputProps}
							boldLabel
							label="Email"
							name="emailAddress"
							type="email"
						/>

						<label className="required" htmlFor="phone">
							Phone
						</label>

						<div className="d-flex justify-content-between purchased-solutions-phone">
							<div className="col-3 p-0">
								<DropDown
									closeOnClick
									trigger={
										<div className="align-items-center d-flex form-control p-2 rounded-xs">
											<ClayIcon
												className="mr-2"
												symbol={currentPhonesFlags.flag}
											/>

											{currentPhonesFlags.code}
										</div>
									}
								>
									<DropDown.ItemList items={phones as any}>
										{(item) => {
											const phone = item as any;

											return (
												<DropDown.Item
													onClick={() => {
														setCurrentPhonesFlags({
															code: phone.code,
															flag: phone.flag,
														});

														accountForm.setValue(
															'phone',
															{
																code:
																	phone.code,
																flag:
																	phone.flag,
															}
														);
													}}
												>
													<ClayIcon
														className="mr-2"
														symbol={phone.flag}
													/>

													{phone.code}
												</DropDown.Item>
											);
										}}
									</DropDown.ItemList>
								</DropDown>

								<div className="form-feedback-group">
									<div className="form-text">Intl. code</div>
								</div>
							</div>

							<div className="col-6">
								<FormInput
									{...inputProps}
									description="Phone number"
									name="phoneNumber"
								/>
							</div>

							<FormInput
								{...inputProps}
								description="Extension (optional)"
								name="extension"
								placeholder="Enter +ext"
							/>
						</div>
					</ClayForm.Group>

					<ClayCheckbox
						checked={agreeToTermsAndConditions}
						id="agreeToTermsAndConditions"
						label={
							((
								<span>
									I agree to the
									<ClayLink
										className="ml-1"
										displayType="primary"
										href="https://www.liferay.com/en/legal/marketplace-terms-of-service"
										target="_blank"
									>
										Terms & Conditions
									</ClayLink>
								</span>
							) as unknown) as string
						}
						onChange={() =>
							setAgreeToTermsAndConditions(
								!agreeToTermsAndConditions
							)
						}
					/>

					<div className="align-items-center d-flex justify-content-between mb-4 mt-4 w-100">
						<ClayButton
							displayType="unstyled"
							onClick={() => {
								if (singleAccount) {
									Liferay.Util.navigate(
										`${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`
									);
								}

								navigate('/');
							}}
						>
							{singleAccount ? 'Cancel' : 'Back'}
						</ClayButton>

						<ClayButton
							disabled={
								accountForm.formState.isSubmitting ||
								!accountForm.formState.isValid ||
								!agreeToTermsAndConditions
							}
							type="submit"
						>
							Start Trial
						</ClayButton>
					</div>
				</ClayForm.Group>
			</ClayForm>
		</div>
	);
};

const GetSolutionForm = () => {
	const {accountSelected, accounts, navigate} = useOutletContext<{
		accountSelected?: Account;
		accounts: Account[];
		navigate: NavigateFunction;
	}>();

	useLayoutEffect(() => {
		if (accounts.length > 1 && !accountSelected) {
			navigate('/', {replace: true});
		}
	}, [accountSelected, accounts.length, navigate]);

	return <AccountForm />;
};

export default GetSolutionForm;
