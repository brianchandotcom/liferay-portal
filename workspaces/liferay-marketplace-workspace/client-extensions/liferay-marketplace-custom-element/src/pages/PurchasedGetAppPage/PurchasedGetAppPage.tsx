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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {InputHTMLAttributes, useEffect, useMemo, useRef, useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {Header} from '../../components/Header/Header';
import BaseWrapper from '../../components/Input/base/BaseWrapper';
import zodSchema, {zodResolver} from '../../schema/zod';
import {
	getUserAccount,
	updateMyUserAccount,
	updateUserImage,
} from '../../utils/api';

import './PurchasedGetAppPage.scss';
import Select from '../../components/Select/Select';
import {getPhones} from './PurchasedGetAppPageUtil';

type Steps = {
	page: 'onboarding' | 'customerGateForm';
};

type PurchasedGetAppPage = {
	setStep: React.Dispatch<Steps>;
	user?: UserAccount;
};

type UserForm = z.infer<typeof zodSchema.accountCreator>;

type InputProps = {
	boldLabel?: boolean;
	className?: string;
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

const Input: React.FC<InputProps> = ({
	boldLabel,
	className,
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

export function PurchasedGetAppPage({setStep, user}: PurchasedGetAppPage) {
	const inputRef = useRef<HTMLInputElement>(null);

	const [phonesFlags, setPhonesFlags] = useState<PhonesFlags[]>();

	const [currentUserAccount, setCurrentUserAccount] = useState<UserAccount>();

	useEffect(() => {
		(async () => {
			const items = await getUserAccount();

			setCurrentUserAccount(items);
		})();

		const flags = getPhones();

		setPhonesFlags(flags);
	}, []);

	const jsonBody = useMemo(
		() => ({
			emailAddress: currentUserAccount?.emailAddress,
			familyName: currentUserAccount?.familyName,
			givenName: currentUserAccount?.givenName,
		}),
		[
			currentUserAccount?.emailAddress,
			currentUserAccount?.givenName,
			currentUserAccount?.familyName,
		]
	);

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
			agreetOTermsAndConditions: false,
			companyName: '',
			emailAddress: '',
			extension: '',
			familyName: jsonBody?.familyName,
			givenName: jsonBody?.givenName,
			industry: '',
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: '',
		},
		resolver: zodResolver(zodSchema.accountCreator),
	});

	const _submit = async (form: UserForm) => {};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const agreetOTermsAndConditions = watch('agreetOTermsAndConditions');
	const phone = watch('phone');

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
									label="First Name"
									name="givenName"
								/>
							</div>

							<div className="form-group mb-0 pl-3 w-50">
								<Input
									{...inputProps}
									boldLabel
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
							/>
						</div>

						<div className="form-group mb-5">
							<Select
								{...inputProps}
								boldLabel
								className="p-2"
								label="Industry"
								name="industry"
								options={[]}
								placeholder="Enter job description"
								type="select"
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
									label="Email"
									name="emailAddress"
									type="email"
								/>
							</div>

							<label className="required" htmlFor="phone">
								Phone
							</label>

							<div className="align-items-center d-flex justify-content-between purchased-get-app-page-phone">
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
										name="phoneNumber"
										placeholder="___–___–____"
									/>

									<div className="form-feedback-group">
										<div className="form-text">
											Phone number
										</div>
									</div>
								</div>

								<div className="col-3">
									<Input
										{...inputProps}
										className="mr-0 w-75"
										name="extension"
										placeholder="Enter +ext"
									/>

									<div className="form-feedback-group">
										<div className="form-text">
											Extension (optional)
										</div>
									</div>
								</div>
							</div>
						</ClayForm.Group>

						<ClayForm.Group>
							<div className="d-flex flex-row-reverse justify-content-end">
								<label
									className="control-label ml-3 pb-1"
									htmlFor="agreetOTermsAndConditions"
								>
									I agree to the{' '}

									<ClayLink>Terms & Conditions</ClayLink>
								</label>

								<ClayCheckbox
									checked={agreetOTermsAndConditions}
									id="newsSubscription"
									onChange={() =>
										setValue(
											'agreetOTermsAndConditions',
											!agreetOTermsAndConditions
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

								<ClayButton onClick={handleSubmit(_submit)}>
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
