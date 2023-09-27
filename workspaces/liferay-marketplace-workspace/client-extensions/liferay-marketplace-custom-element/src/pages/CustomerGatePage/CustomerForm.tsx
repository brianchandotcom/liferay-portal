/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CustomerGatePage.scss';

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClaySticker from '@clayui/sticker';
import {InputHTMLAttributes, useRef} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {Header} from '../../components/Header/Header';
import BaseWrapper from '../../components/Input/base/BaseWrapper';
import {Liferay} from '../../liferay/liferay';
import zodSchema, {zodResolver} from '../../schema/zod';
import {updateMyUserAccount, updateUserImage} from '../../utils/api';

type Steps = {
	page: 'onboarding' | 'customerGateForm';
};

type CreateCustomerAccountForm = {
	setStep: React.Dispatch<Steps>;
	user?: UserAccount;
};

type UserForm = z.infer<typeof zodSchema.newCustomer>;

type InputProps = {
	boldLabel?: boolean;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const {origin} = window.location;

const acceptedImageFormat = ['image/jpeg', 'image/bmp', 'image/png'];

const Input: React.FC<InputProps> = ({
	boldLabel,
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
	...otherProps
}) => (
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

const CreateCustomerAccountForm: React.FC<CreateCustomerAccountForm> = ({
	setStep,
	user,
}) => {
	const getSiteURL = () => {
		const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

		if (layoutRelativeURL.includes('web')) {
			return layoutRelativeURL.split('/').slice(0, 3).join('/');
		}

		return '';
	};

	const inputRef = useRef<HTMLInputElement>(null);

	const {
		clearErrors,
		formState: {errors},
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<UserForm>({
		defaultValues: {
			...user,
			accountBriefs: user?.accountBriefs,
			emailAddress: user?.emailAddress,
			familyName: user?.familyName,
			givenName: user?.givenName,
			image: user?.image ?? emptyPictureIcon,
			imageBlob: '',
			newsSubscription: user?.newsSubscription,
		},
		resolver: zodResolver(zodSchema.newCustomer),
	});

	const _submit = async (form: UserForm) => {
		try {
			if (form.imageBlob) {
				const formData = new FormData();

				formData.append('image', form.imageBlob);

				await updateUserImage(Number(user?.id), formData);
			}

			delete form.imageBlob;
			delete form.image;

			await updateMyUserAccount(Number(user?.id), form);

			window.location.href = `${origin}${getSiteURL()}/loading`;
		}
		catch (error) {
			console.error(error);
		}
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const handleClick = () => {
		inputRef?.current?.click();
	};

	const handleFileChange = async (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const inputElement = event.target as HTMLInputElement;
		const fileList = inputElement?.files;

		const fileObj: File = fileList?.[0] as File;

		const getIsResourceFromAPI = (apis: string[]) =>
			apis.some((api) => fileObj.type.toString().includes(api));

		if (!fileObj) {
			return;
		}

		if (fileObj.size > 300000) {
			return setError('image', {
				message: 'The image could not be greater than 300kb',
			});
		}

		if (!getIsResourceFromAPI(acceptedImageFormat)) {
			return setError('image', {
				message: 'This file is not an image',
			});
		}

		const userImageURL = URL.createObjectURL(fileObj);

		setValue('image', userImageURL);

		clearErrors();

		setValue('imageBlob', fileObj);
	};

	const newsSubscription = watch('newsSubscription');
	const image = watch('image');

	return (
		<div className="customer-gate-page-container">
			<div className="customer-gate-page-body">
				<Header
					description="Enter your new customer account details. This information will be used for purchasing, downloading trials, collaboration, and customer support purposes."
					title="Create a new customer Account"
				/>

				<ClayForm>
					<div className="align-items-baseline d-flex">
						<div className="align-items-center d-flex">
							<label
								className="font-weight-bold mr-4 required title-label"
								htmlFor="emailAddress"
							>
								Profile Info
							</label>

							<ClayLabel
								className="label-tonal-info rounded-xs text-capitalize"
								displayType="info"
							>
								<div className="btn-info-panel flex-shrink-0 justify-content-center label label-secondary label-tonal-info m-0 ms-auto p-0 rounded-sx text-capitalize">
									<span className="flex-shrink-0 py-1 text-center text-paragraph-sm">
										More Info
									</span>

									<span className="inline-item inline-item-after">
										<ClayIcon symbol="question-circle" />
									</span>
								</div>
							</ClayLabel>
						</div>
					</div>

					<hr className="solid" />

					<div className="align-items-center d-flex justify-center mb-4">
						{image ? (
							<ClaySticker
								className="mr-4"
								shape="circle"
								size="xl"
							>
								<ClaySticker.Image
									alt="placeholder"
									src={image}
								/>
							</ClaySticker>
						) : (
							<img alt="Circle Icon" src={emptyPictureIcon} />
						)}

						<input
							accept={acceptedImageFormat.join()}
							name="image"
							onChange={handleFileChange}
							ref={inputRef}
							style={{display: 'none'}}
							type="file"
						/>

						<ClayButton
							className="rounded-xs"
							onClick={handleClick}
							size="sm"
						>
							Upload Image
						</ClayButton>
					</div>

					{errors?.image?.message && (
						<ClayAlert displayType="danger">
							{errors?.image?.message.toString()}
						</ClayAlert>
					)}

					<ClayForm.Group>
						<div className="d-flex justify-content-between mb-2">
							<div className="form-group pr-3 w-50">
								<Input
									{...inputProps}
									boldLabel
									label="First Name"
									name="givenName"
								/>
							</div>

							<div className="form-group pl-3 w-50">
								<Input
									{...inputProps}
									boldLabel
									label="Last Name"
									name="familyName"
								/>
							</div>
						</div>

						<div className="align-items-baseline d-flex">
							<div className="align-items-center d-flex">
								<label
									className="font-weight-bold mr-4 required title-label"
									htmlFor="emailAddress"
								>
									Contact Info
								</label>

								<ClayLabel className="label-tonal-info rounded-xs text-capitalize">
									<div className="btn-info-panel flex-shrink-0 justify-content-center label label-secondary label-tonal-info m-0 ms-auto p-0 rounded-sx text-capitalize">
										<span className="flex-shrink-0 py-1 text-center text-paragraph-sm">
											More Info
										</span>

										<span className="inline-item inline-item-after">
											<ClayIcon symbol="question-circle" />
										</span>
									</div>
								</ClayLabel>
							</div>
						</div>

						<hr className="mb-5 solid" />

						<ClayForm.Group>
							<div className="form-group mb-5">
								<Input
									disabled
									{...inputProps}
									boldLabel
									label="Email"
									name="emailAddress"
								/>
							</div>
						</ClayForm.Group>

						<ClayForm.Group>
							<div className="d-flex flex-row-reverse justify-content-end">
								<label
									className="control-label ml-3 pb-1"
									htmlFor="newsSubscription"
								>
									I would like more information about joining
									Liferay&apos;s Customer network
								</label>

								<ClayCheckbox
									checked={newsSubscription}
									id="newsSubscription"
									onChange={() =>
										setValue(
											'newsSubscription',
											!newsSubscription
										)
									}
								/>
							</div>
						</ClayForm.Group>

						<hr className="mb-5 solid" />

						<div className="customer-gate-page-button-container">
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

								<div>
									<ClayButton
										className="mr-4"
										displayType="secondary"
										onClick={() =>
											setStep({page: 'onboarding'})
										}
									>
										Back
									</ClayButton>

									<ClayButton onClick={handleSubmit(_submit)}>
										Next
									</ClayButton>
								</div>
							</div>
						</div>
					</ClayForm.Group>
				</ClayForm>
			</div>
		</div>
	);
};

export default CreateCustomerAccountForm;
