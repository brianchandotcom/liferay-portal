/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useEffect, useState} from 'react';
import {z} from 'zod';

import {Header} from '../../../components/Header/Header';
import FormInput from '../../../components/Input/formInput';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import Select from '../../../components/Select/Select';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import {getListTypeDefinitionByExternalReferenceCode} from '../../../utils/api';
import {StepType} from '../PurchasedSolutions';
import {getPhones} from '../PurchasedSolutionsUtil';
import useAccountForm from '../hooks/useAccountForm';
import useHandleAccount from '../hooks/useHandleAccount';

export type UserForm = z.infer<typeof zodSchema.accountCreator>;

const externalReferenceCode = 'INDUSTRIES';

type AccountFormType = {
	accountForm: ReturnType<typeof useAccountForm>;
	disabledButton: boolean;
	setStep: React.Dispatch<React.SetStateAction<StepType>>;
	submitOrder: (aacount?: Account) => void;
};

enum AccountQuantities {
	SINGLE = 1,
	NO_ACCOUNT = 0,
}
const AccountForm: React.FC<AccountFormType> = ({
	accountForm,
	disabledButton,
	setStep,
	submitOrder,
}) => {
	const [currentPhonesFlags, setCurrentPhonesFlags] = useState({
		code: '+1',
		flag: 'en-us',
	});
	const [industries, setIndustries] = useState<Industries[]>();
	const [phonesFlags, setPhonesFlags] = useState<PhonesFlags[]>();

	const {mutateMyUserAccout, myUserAccount} = useMarketplaceContext();

	const {createAccount, formDataTransform, updateAccount} = useHandleAccount({
		mutateMyUserAccout,
		myUserAccount,
	});

	useEffect(() => {
		(async () => {
			const flags = getPhones();

			setPhonesFlags(flags);

			const industriesListTypeEntries = await getListTypeDefinitionByExternalReferenceCode(
				externalReferenceCode
			);

			setIndustries(industriesListTypeEntries?.listTypeEntries);
		})();
	}, []);

	const inputProps = {
		error: accountForm.formUtil.formState.errors,
		register: accountForm.formUtil.register,
		required: true,
	};

	const handleNextStep = async () => {
		const form = accountForm.formUtil.form;

		switch (accountForm.formUtil.form.accountQuantity) {
			case AccountQuantities.SINGLE: {
				await updateAccount({
					accountId: Number(form?.accountSelected?.id),
					data: formDataTransform(form),
				});
				submitOrder();

				setStep(StepType.CHECKOUT);

				break;
			}

			case AccountQuantities.NO_ACCOUNT: {
				const response: Account = await createAccount(
					formDataTransform(form)
				);

				submitOrder(response);

				setStep(StepType.CHECKOUT);

				break;
			}

			default:
				setStep(StepType.ACCOUNT);
				break;
		}
	};

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
					<div className="d-flex flex-column justify-content-center text-center w-100">
						<p className="m-0">Create a Trial</p>
					</div>
				}
			/>

			<ClayForm>
				<div className="align-items-baseline d-flex">
					<div className="align-items-center d-flex">
						<label className="font-weight-bold mr-4 title-label">
							Profile Info
						</label>
					</div>
				</div>

				<hr className="solid" />

				<ClayForm.Group>
					<div className="d-flex justify-content-between">
						<div className="form-group mb-0 pr-2 w-50">
							<FormInput
								{...inputProps}
								boldLabel
								disabled
								label="First Name"
								name="givenName"
							/>
						</div>

						<div className="form-group mb-0 pl-2 w-50">
							<FormInput
								{...inputProps}
								boldLabel
								disabled
								label="Last Name"
								name="familyName"
							/>
						</div>
					</div>

					<div className="form-group mb-5">
						<FormInput
							{...inputProps}
							boldLabel
							label="Company"
							name="companyName"
							placeholder="Enter company name"
						/>
					</div>

					<div className="form-group mb-5">
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
					</div>

					<ClayForm.Group>
						<div className="align-items-baseline d-flex">
							<div className="align-items-center d-flex">
								<label
									className="font-weight-bold mr-4 title-label"
									htmlFor="emailAddress"
								>
									Contact Info
								</label>
							</div>
						</div>

						<hr className="solid" />

						<div className="form-group mb-5">
							<FormInput
								{...inputProps}
								boldLabel
								disabled
								label="Email"
								name="emailAddress"
								type="email"
							/>
						</div>

						<label className="required" htmlFor="phone">
							Phone
						</label>

						<div className="d-flex justify-content-between purchased-solutions-phone">
							<div className="col-3 p-0">
								<DropDown
									closeOnClick
									trigger={
										<div className="align-items-center custom-select d-flex form-control p-2 rounded-xs">
											<ClayIcon
												className="mr-2"
												symbol={currentPhonesFlags.flag}
											/>

											{currentPhonesFlags.code}
										</div>
									}
								>
									<DropDown.ItemList items={phonesFlags}>
										{(item) => {
											const itemList = item as PhonesFlags;

											return (
												<DropDown.Item
													onClick={() => {
														setCurrentPhonesFlags({
															code: itemList.code,
															flag: itemList.flag,
														});

														accountForm.formUtil.setValue(
															'phone',
															{
																code:
																	itemList.code,
																flag:
																	itemList.flag,
															}
														);
													}}
												>
													<ClayIcon
														className="mr-2"
														symbol={itemList.flag}
													/>

													{itemList.code}
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
									className="w-100"
									description="Phone number"
									name="phoneNumber"
									placeholder="___–___–____"
								/>
							</div>

							<div className="col-3 p-0">
								<FormInput
									{...inputProps}
									className="custom-input mr-0 pl-1 text-nowrap"
									description="Extension (optional)"
									name="extension"
									placeholder="Enter +ext"
								/>
							</div>
						</div>
					</ClayForm.Group>

					<ClayForm.Group>
						<div className="d-flex justify-content-start">
							<>
								<ClayCheckbox
									checked={accountForm.formUtil.watch(
										'agreeToTermsAndConditions'
									)}
									className="danger"
									id="newsSubscription"
									onChange={() =>
										accountForm.formUtil.setValue(
											'agreeToTermsAndConditions',
											!accountForm.formUtil.watch(
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
								<label className="ml-2">
									<ClayLink
										displayType="primary"
										href="https://www.liferay.com/en/legal/marketplace-terms-of-service"
									>
										Terms & Conditions
									</ClayLink>
								</label>
							</>
						</div>
					</ClayForm.Group>

					<div className="purchased-solutions-button-container">
						<div className="align-items-center d-flex justify-content-between mb-4 w-100">
							<div>
								<ClayButton
									displayType="unstyled"
									onClick={() => {
										window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`;
									}}
								>
									Cancel
								</ClayButton>
							</div>

							<ClayButton
								disabled={
									!accountForm.formUtil.hasAllValidations ||
									disabledButton
								}
								onClick={handleNextStep}
							>
								{accountForm.formUtil.form.accountQuantity <= 1
									? 'Start Trial'
									: 'Continue'}
							</ClayButton>
						</div>
					</div>
				</ClayForm.Group>
			</ClayForm>
		</div>
	);
};

export default AccountForm;
