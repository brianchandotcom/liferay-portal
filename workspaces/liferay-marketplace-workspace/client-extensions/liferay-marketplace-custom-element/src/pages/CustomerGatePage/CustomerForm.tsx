import "./CustomerGatePage.scss";
import { Header } from "../../components/Header/Header";
import { updateMyUserAccount, updateUserImage } from "../../utils/api";
import { InputHTMLAttributes, useRef } from "react";
import { useForm } from "react-hook-form";
import ClayAlert from "@clayui/alert";
import ClayButton from "@clayui/button";
import ClayForm, { ClayCheckbox, ClayInput } from "@clayui/form";
import ClayIcon from "@clayui/icon";
import ClayLabel from "@clayui/label";
import ClaySticker from "@clayui/sticker";
import emptyPictureIcon from "../../assets/icons/avatar.svg";
import zodSchema, { zodResolver } from "../../schema/zod";
import BaseWrapper from "../../components/Input/base/BaseWrapper";
import { z } from "zod";

type CreateCustomerAccountForm = {
	user?: UserAccount;
	setStep: React.Dispatch<
		React.SetStateAction<{
			page: string;
		}>
	>;
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

const { origin } = window.location;

const acceptedImageFormat = ["image/jpeg", "image/bmp", "image/png"];

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
			component={type === "textarea" ? "textarea" : "input"}
			disabled={disabled}
			id={id}
			name={name}
			type={type}
			value={value}
			{...otherProps}
			{...register(name, { onBlur, required })}
		/>
	</BaseWrapper>
);

const CreateCustomerAccountForm: React.FC<CreateCustomerAccountForm> = ({
	user,
	setStep,
}) => {
	const inputRef = useRef<HTMLInputElement>(null);

	const {
		clearErrors,
		formState: { errors },
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
			imageBlob: "",
			newsSubscription: user?.newsSubscription,
		},
		resolver: zodResolver(zodSchema.newCustomer),
	});
	console.log("errors:", errors);

	const _submit = async (form: UserForm) => {
		try {
			if (form.imageBlob) {
				var formData = new FormData();

				formData.append("image", form.imageBlob);

				await updateUserImage(Number(user?.id), formData);
			}

			delete form.imageBlob;
			delete form.image;

			await updateMyUserAccount(Number(user?.id), form);

			window.location.href = `${origin}/web/marketplace/loading`;
		} catch (error) {
			console.log(error);
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
		event: React.ChangeEvent<HTMLInputElement>,
	) => {
		const inputElement = event.target as HTMLInputElement;
		const fileList = inputElement?.files;

		let fileObj: File = fileList?.[0] as File;

		const getIsResourceFromAPI = (apis: string[]) =>
			apis.some((api) => fileObj.type.toString().includes(api));

		if (!fileObj) {
			return;
		}

		if (fileObj.size > 300000) {
			return setError("image", {
				message: "The image could not be greater than 300kb",
			});
		}

		if (!getIsResourceFromAPI(acceptedImageFormat)) {
			return setError("image", {
				message: "This file is not image",
			});
		}

		const userImageURL = URL.createObjectURL(fileObj);

		setValue("image", userImageURL);

		clearErrors();

		setValue("imageBlob", fileObj);
	};

	const newsSubscription = watch("newsSubscription");
	const image = watch("image");

	return (
		<div className="customer-gate-page-container">
			<div className="customer-gate-page-body">
				<Header
					description="Enter your new customer account details. This information will be used for purchasing, downloading trials, collaboration, and customer support purposes."
					title="Create a new customer Account"
				/>
				<ClayForm>
					<div className="d-flex align-items-baseline">
						<div className="d-flex align-items-center">
							<label
								className="mr-4 required font-weight-bold title-label"
								htmlFor="emailAddress"
							>
								Profile Info
							</label>
							<ClayLabel
								displayType="info"
								className="label-tonal-info rounded-xs text-capitalize"
							>
								<div className="label btn-info-panel text-capitalize flex-shrink-0 rounded-sx m-0 p-0 justify-content-center ms-auto label-tonal-info label-secondary">
									<span className="flex-shrink-0 py-1 text-center text-paragraph-sm">
										More Info
									</span>

									<span className="inline-item inline-item-after">
										<ClayIcon symbol={"question-circle"} />
									</span>
								</div>
							</ClayLabel>
						</div>
					</div>

					<hr className="solid" />

					<div className="d-flex align-items-center justify-center mb-4">
						{image ? (
							<ClaySticker size="xl" shape="circle" className="mr-4">
								<ClaySticker.Image alt="placeholder" src={image} />
							</ClaySticker>
						) : (
							<img alt="Circle Icon" src={emptyPictureIcon} />
						)}

						<input
							style={{ display: "none" }}
							ref={inputRef}
							name="image"
							type="file"
							onChange={handleFileChange}
							accept={acceptedImageFormat.join()}
						/>
						<ClayButton size="sm" className="rounded-xs" onClick={handleClick}>
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
									name="givenName"
									label="First Name"
									boldLabel
								/>
							</div>

							<div className="form-group pl-3 w-50">
								<Input
									{...inputProps}
									name="familyName"
									label="Last Name"
									boldLabel
								/>
							</div>
						</div>

						<div className="d-flex align-items-baseline">
							<div className="d-flex align-items-center">
								<label
									className="mr-4 required font-weight-bold title-label"
									htmlFor="emailAddress"
								>
									Contact Info
								</label>

								<ClayLabel className="label-tonal-info rounded-xs text-capitalize">
									<div className="label btn-info-panel text-capitalize flex-shrink-0 rounded-sx m-0 p-0 justify-content-center ms-auto label-tonal-info label-secondary">
										<span className="flex-shrink-0 py-1 text-center text-paragraph-sm">
											More Info
										</span>

										<span className="inline-item inline-item-after">
											<ClayIcon symbol={"question-circle"} />
										</span>
									</div>
								</ClayLabel>
							</div>
						</div>

						<hr className="solid mb-5" />

						<ClayForm.Group>
							<div className="form-group mb-5">
								<Input
									disabled
									{...inputProps}
									name="emailAddress"
									label="Email"
									boldLabel
								/>
							</div>
						</ClayForm.Group>

						<ClayForm.Group>
							<div className="d-flex justify-content-end flex-row-reverse">
								<label
									className="control-label pb-1 ml-3"
									htmlFor="emailAddress"
								>
									I would like more information about joining Liferay's Customer
									network
								</label>

								<ClayCheckbox
									checked={newsSubscription}
									onChange={() =>
										setValue("newsSubscription", !newsSubscription)
									}
								/>
							</div>
						</ClayForm.Group>

						<hr className="solid mb-5" />

						<div className="customer-gate-page-button-container">
							<div className="d-flex align-items-center justify-content-between mb-4 w-100">
								<div>
									<ClayButton
										displayType="unstyled"
										onClick={() => (window.location.href = origin)}
									>
										Cancel
									</ClayButton>
								</div>
								<div>
									<ClayButton
										displayType="secondary"
										className=" mr-4"
										onClick={() => setStep({ page: "onboarding" })}
									>
										Back
									</ClayButton>
									<ClayButton onClick={handleSubmit(_submit)}>Next</ClayButton>
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
