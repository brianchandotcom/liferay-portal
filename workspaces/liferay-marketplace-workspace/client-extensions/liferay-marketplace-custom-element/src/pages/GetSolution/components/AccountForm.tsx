/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import useSWR from 'swr';
import {z} from 'zod';

import {Header} from '../../../components/Header/Header';
import FormInput from '../../../components/Input/formInput';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import Select from '../../../components/Select/Select';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import {getListTypeDefinitionByExternalReferenceCode} from '../../../utils/api';
import {phones} from '../../../utils/phones';
import useAccountForm from '../hooks/useAccountForm';
import useHandleAccount from '../hooks/useHandleAccount';

export type UserForm = z.infer<typeof zodSchema.accountCreator>;

type AccountFormType = {
	accountForm: ReturnType<typeof useAccountForm>;
	submitOrder: (responeAccount?: Account) => Promise<void>;
};

enum AccountQuantities {
	SINGLE = 1,
	NO_ACCOUNT = 0,
}

const AccountForm: React.FC<AccountFormType> = ({accountForm, submitOrder}) => {
	const navigate = useNavigate();
	const [currentPhonesFlags, setCurrentPhonesFlags] = useState({
		code: '+1',
		flag: 'en-us',
	});

	const {
		data: industriesListTypeEntries = {listTypeEntries: []},
	} = useSWR('/industries', () =>
		getListTypeDefinitionByExternalReferenceCode('INDUSTRIES')
	);

	const industries = industriesListTypeEntries.listTypeEntries;

	const {mutateMyUserAccount, myUserAccount} = useMarketplaceContext();

	const {formDataTransform, updateAccount} = useHandleAccount({
		mutateMyUserAccount,
		myUserAccount,
	});

	const inputProps = {
		errors: accountForm.formState.errors,
		register: accountForm.register,
		required: true,
	};

	const startTrial = async () => {
		const form = accountForm.getValues();

		if (AccountQuantities.SINGLE === accountForm.accountQuantity) {
			await updateAccount({
				accountId: Number(form?.accountSelected?.id),
				data: formDataTransform(form),
			});
		}

		await submitOrder();
	};

	const agreeToTermsAndConditions = accountForm.watch(
		'agreeToTermsAndConditions'
	);

	const hasAllValidations =
		agreeToTermsAndConditions && accountForm.formState.isValid;

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

			<ClayForm onSubmit={accountForm.handleSubmit(startTrial)}>
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
						defaultOptionLabel="Select an industry"
						label="Industry"
						name="industry"
						options={industries}
						placeholder="Select an industry"
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

					<ClayForm.Group>
						<div className="d-flex justify-content-start">
							<ClayCheckbox
								checked={accountForm.watch(
									'agreeToTermsAndConditions'
								)}
								className="danger"
								id="newsSubscription"
								onChange={() =>
									accountForm.setValue(
										'agreeToTermsAndConditions',
										!accountForm.watch(
											'agreeToTermsAndConditions'
										)
									)
								}
							/>

							<label
								className="ml-4"
								htmlFor="agreeToTermsAndConditions"
							>
								I agree to the
							</label>

							<ClayLink
								className="ml-2"
								displayType="primary"
								href="https://www.liferay.com/en/legal/marketplace-terms-of-service"
							>
								Terms & Conditions
							</ClayLink>
						</div>
					</ClayForm.Group>

					<div className="align-items-center d-flex justify-content-between mb-4 w-100">
						<ClayButton
							displayType="unstyled"
							onClick={() => {
								if (
									AccountQuantities.SINGLE ===
									accountForm.accountQuantity
								) {
									Liferay.Util.navigate(
										`${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`
									);
								}

								navigate('/');
							}}
						>
							{AccountQuantities.SINGLE ===
							accountForm.accountQuantity
								? 'Cancel'
								: 'Back'}
						</ClayButton>

						<ClayButton
							disabled={
								!hasAllValidations ||
								accountForm.formState.isSubmitting
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

export default AccountForm;
