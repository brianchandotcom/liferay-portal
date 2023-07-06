/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {InputHTMLAttributes, useEffect, useMemo, useRef, useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import {Header} from '../../components/Header/Header';
import BaseWrapper from '../../components/Input/base/BaseWrapper';
import zodSchema, {zodResolver} from '../../schema/zod';
import {
	getListTypeDefinitionByExternalReferenceCode,
	getUserAccount,
} from '../../utils/api';

import './PurchasedGetAppPage.scss';
import Select from '../../components/Select/Select';
import {getPhones} from './PurchasedGetAppPageUtil';

type Steps = {
	page: 'accountCreation' | 'accountSelection';
};

type UserForm = z.infer<typeof zodSchema.accountCreator>;

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	description?: string;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	options?: {label: string; value: string} | [];
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const {origin} = window.location;
const externalReferenceCode = 'MARKETPLACE-INDUSTRIES';

const Input: React.FC<InputProps> = ({
	boldLabel,
	className,
	description,
	disabled = false,
	errors = {},
	label,
	name,
	register = () => {},
	id = name,
	type,
	value,
	required = false,
	onBlur,
	options,
	...otherProps
}) => {
	return (
		<BaseWrapper
			boldLabel={boldLabel}
			description={description}
			disabled={disabled}
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<ClayInput
				className="rounded-xs"
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={id}
				name={name}
				type={type}
				value={value}
				{...otherProps}
				{...register(name, {onBlur, required})}
			/>
		</BaseWrapper>
	);
};

export function PurchasedGetAppPage() {
	const [phonesFlags, setPhonesFlags] = useState<PhonesFlags[]>();

	const [currentUserAccount, setCurrentUserAccount] = useState<UserAccount>();
	const [insdustries, setInsdustries] = useState<Industries[]>();

	useEffect(() => {
		(async () => {
			const items = await getUserAccount();
			const insdustriesListTypeEntries =
				await getListTypeDefinitionByExternalReferenceCode(
					externalReferenceCode
				);
			setInsdustries(insdustriesListTypeEntries?.listTypeEntries);

			setCurrentUserAccount(items);
		})();

		const flags = getPhones();

		setPhonesFlags(flags);
	}, []);

	const {
		clearErrors,
		formState: {errors},
		getValues,
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<UserForm>({
		defaultValues: {
			agreeToTermsAndConditions: false,
			companyName: '',
			emailAddress: '',
			extension: '',
			familyName: '',
			givenName: '',
			industry: '',
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: '',
		},
		resolver: zodResolver(zodSchema.accountCreator),
	});

	useEffect(() => {
		if (currentUserAccount) {
			const {emailAddress, familyName, givenName} = currentUserAccount;
			setValue('emailAddress', emailAddress || '');
			setValue('givenName', givenName || '');
			setValue('familyName', familyName || '');
		}
	}, [currentUserAccount, setValue]);

	const _submit = async (form: UserForm) => {};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const agreeToTermsAndConditions = watch('agreeToTermsAndConditions');

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-get-app-page-container w-100">
			<div className="border p-8 purchased-get-app-page-body rounded">
				<Header description title="Marketplace Account Creation" />

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
							<div className="form-group mb-0 pr-3 w-50">
								<Input
									{...inputProps}
									boldLabel
									disabled
									label="First Name"
									name="givenName"
								/>
							</div>

							<div className="form-group mb-0 pl-3 w-50">
								<Input
									{...inputProps}
									boldLabel
									disabled
									label="Last Name"
									name="familyName"
								/>
							</div>
						</div>

						<div className="form-group mb-5">
							<Input
								{...inputProps}
								boldLabel
								label="Company name"
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
								defaultOptionLabel="Enter job description"
								label="Industry"
								name="industry"
								options={insdustries}
								placeholder="Enter job description"
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
								<Input
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

							<div className="d-flex justify-content-between purchased-get-app-page-phone">
								<div className="col-3 pl-0">
									<DropDown
										closeOnClick
										items={phonesFlags}
										trigger={
											<div className="align-items-center custom-select d-flex form-control p-2 rounded-xs">
												<ClayIcon
													className="mr-2"
													symbol={
														getValues('phone').flag
													}
												/>{' '}
												{getValues('phone').code}
											</div>
										}
									>
										{(item) => (
											<DropDown.Item
												onClick={() => {
													return setValue('phone', {
														code: item.code,
														flag: item.flag,
													});
												}}
											>
												<ClayIcon
													className="mr-2"
													symbol={item.flag}
												/>

												{item.code}
											</DropDown.Item>
										)}
									</DropDown>

									<div className="form-feedback-group">
										<div className="form-text">
											Intl. code
										</div>
									</div>
								</div>

								<div className="col-6">
									<Input
										{...inputProps}
										className="w-100"
										description="Phone number"
										name="phoneNumber"
										placeholder="___–___–____"
									/>
								</div>

								<div className="col-3">
									<Input
										{...inputProps}
										className="mr-0 w-75"
										description="Extension (optional)"
										name="extension"
										placeholder="Enter +ext"
									/>
								</div>
							</div>
						</ClayForm.Group>

						<ClayForm.Group>
							<div className="d-flex flex-row-reverse justify-content-end">
								<label
									className="control-label ml-3 pb-1"
									htmlFor="agreeToTermsAndConditions"
								>
									I agree to the{' '}
									<ClayLink>Terms & Conditions</ClayLink>
								</label>

								<ClayCheckbox
									checked={agreeToTermsAndConditions}
									className="danger"
									id="newsSubscription"
									onChange={() =>
										setValue(
											'agreeToTermsAndConditions',
											!agreeToTermsAndConditions
										)
									}
								/>
							</div>
						</ClayForm.Group>

						<div className="purchased-get-app-page-button-container">
							<div className="align-items-center d-flex justify-content-between mb-4 w-100">
								<div>
									<ClayButton
										displayType="unstyled"
										onClick={() => {
											window.location.href = origin;
										}}
									>
										Cancel
									</ClayButton>
								</div>

								<ClayButton
									disabled={!agreeToTermsAndConditions}
									onClick={handleSubmit(_submit)}
								>
									Continue
								</ClayButton>
							</div>
						</div>
					</ClayForm.Group>
				</ClayForm>
			</div>
		</div>
	);
}
