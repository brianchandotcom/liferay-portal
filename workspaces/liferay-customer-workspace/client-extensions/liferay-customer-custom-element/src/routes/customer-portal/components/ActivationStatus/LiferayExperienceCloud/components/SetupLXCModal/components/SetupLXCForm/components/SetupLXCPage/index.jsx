/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {FieldArray} from 'formik';
import {useEffect, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {HIGH_PRIORITY_CONTACT_CATEGORIES} from '~/routes/customer-portal/utils/getHighPriorityContacts';
import i18n from '../../../../../../../../../../../common/I18n';
import {
	Button,
	Input,
	Select,
} from '../../../../../../../../../../../common/components';
import SetupHighPriorityContactForm from '../../../../../../../../../../../common/components/HighPriorityContacts/SetupHighPriorityContact';
import Layout from '../../../../../../../../../../../common/containers/setup-forms/Layout';
import useBannedDomains from '../../../../../../../../../../../common/hooks/useBannedDomains';
import {isValidEmail} from '../../../../../../../../../../../common/utils/validations.form';
import getInitialLxcAdmins from '../../utils/getInitialLxcAdmins';
import AdminInputs from './components/AdminsInput';
import useGetPrimaryRegionList from './hooks/useGetPrimaryRegionList';
import useSubmitLXCEnvironment from './hooks/useSubmitLXCEnvironment';

const INITIAL_SETUP_ADMIN_COUNT = 1;
const SetupLiferayExperienceCloudPage = ({
	errors,
	handleChangeForm,
	handleOnLeftButtonClick,
	leftButton,
	project,
	setFieldValue,
	setFormAlreadySubmitted,
	subscriptionGroupLxcId,
	touched,
	values,
}) => {
	const {featureFlags} = useAppPropertiesContext();
	const [isLoadingSubmitButton, setIsLoadingSubmitButton] = useState(false);
	const [baseButtonDisabled, setBaseButtonDisabled] = useState(true);
	const [inputErrors, setInputErrors] = useState({});
	const [step, setStep] = useState(1);
	const [addHighPriorityContact, setAddHighPriorityContact] = useState({
		criticalIncident: [],
		privacyBreach: [],
		securityBreach: [],
	});
	const [removeHighPriorityContact, setRemoveHighPriorityContact] = useState({
		criticalIncident: [],
		privacyBreach: [],
		securityBreach: [],
	});

	const handlePreviousStep = () => {
		setStep(step - 1);
	};

	const handleNextStep = () => {
		setStep(step + 1);
	};

	const bannedDomains = useBannedDomains(
		values?.lxc?.incidentManagementEmail,
		500
	);
	const handleButtonClick = () => {
		// eslint-disable-next-line no-unused-expressions
		step === 1 ? handleOnLeftButtonClick() : handlePreviousStep();
	};

	const handleHighPriorityContacts = (
		contactList,
		highPriorityCategory,
		handleSetState
	) => {
		handleSetState((previousContacts) => {
			const updatedContacts = {...previousContacts};

			if (!updatedContacts.hasOwnProperty(highPriorityCategory)) {
				updatedContacts[highPriorityCategory] = [];
			}

			updatedContacts[highPriorityCategory] = updatedContacts[
				highPriorityCategory
			].filter((previousContact) =>
				contactList.some(
					(contact) =>
						previousContact.category?.role ===
							contact.category?.role &&
						previousContact?.id === contact?.id
				)
			);

			const uniqueContacts = contactList.filter(
				(contact) =>
					!updatedContacts[highPriorityCategory].some(
						(previousContact) =>
							previousContact.category?.role ===
								contact.category?.role &&
							previousContact?.id === contact?.id
					)
			);

			updatedContacts[highPriorityCategory] = [
				...updatedContacts[highPriorityCategory],
				...uniqueContacts,
			];

			return updatedContacts;
		});
	};

	const primaryRegionList = useGetPrimaryRegionList();

	useEffect(() => {
		if (primaryRegionList.length) {
			setFieldValue('lxc.primaryRegion', primaryRegionList[0].value);
		}
	}, [primaryRegionList, setFieldValue]);

	useEffect(() => {
		const hasTouched = !Object.keys(touched).length;
		const hasError = Object.keys(errors).length;

		setBaseButtonDisabled(hasTouched || hasError);
	}, [touched, errors]);

	const handleLoadingSubmitButton = (state) => {
		return setIsLoadingSubmitButton(state);
	};

	const combinedHighPriorityContactsToAdd = Object.values(
		addHighPriorityContact
	).flatMap((array) => array);

	const combinedHighPriorityContactsToRemove = Object.values(
		removeHighPriorityContact
	).flatMap((array) => array);

	const handleSubmitLxcEnvironment = useSubmitLXCEnvironment(
		handleChangeForm,
		project,
		setFormAlreadySubmitted,
		combinedHighPriorityContactsToAdd,
		combinedHighPriorityContactsToRemove,
		subscriptionGroupLxcId,
		handleLoadingSubmitButton,
		values
	);

	const updateMultiSelectEmpty = (error, inputName) => {
		setInputErrors((prevErrors) => ({
			...prevErrors,
			[inputName]: error,
		}));
	};

	const isSubmitDisable = () => {
		return Object.values(inputErrors).some((error) => !!error);
	};

	return featureFlags.includes('LPS-159127') ? (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={() => {
							handleButtonClick();
						}}
					>
						{step === 1 ? leftButton : i18n.translate('previous')}
					</Button>
				),
				middleButton: (
					<Button
						disabled={
							step === 1
								? baseButtonDisabled
								: isSubmitDisable() || isLoadingSubmitButton
						}
						displayType="primary"
						isLoading={isLoadingSubmitButton}
						onClick={
							step === 1
								? handleNextStep
								: handleSubmitLxcEnvironment
						}
					>
						{step === 1
							? i18n.translate('next')
							: i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'we-ll-need-a-few-details-to-finish-creating-your-liferay-saas-workspace'
				),
				title: i18n.translate('set-up-liferay-saas'),
			}}
		>
			{step === 1 && (
				<FieldArray
					name="lxc.admins"
					render={({pop, push}) => (
						<>
							<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
								<div className="mr-4 pr-2">
									<label>
										{i18n.translate('organization-name')}
									</label>

									<p className="dxp-cloud-project-name text-neutral-6 text-paragraph-lg">
										<strong>{project.name}</strong>
									</p>
								</div>
							</div>
							<ClayForm.Group className="mb-0">
								<ClayForm.Group className="mb-0 pb-1">
									<Input
										groupStyle="pb-1"
										helper={i18n.translate(
											'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
										)}
										label={i18n.translate('project-id')}
										name="lxc.projectId"
										required
										type="text"
									/>

									<Select
										groupStyle="mb-0"
										key={primaryRegionList}
										label={i18n.translate('primary-region')}
										name="lxc.primaryRegion"
										options={primaryRegionList}
										required
									/>
								</ClayForm.Group>

								<ClayForm.Group className="mb-0">
									{values.lxc.admins.map((admin, index) => (
										<AdminInputs
											admin={admin}
											id={index}
											key={index}
										/>
									))}
								</ClayForm.Group>
							</ClayForm.Group>

							{values?.lxc?.admins?.length >
								INITIAL_SETUP_ADMIN_COUNT && (
								<Button
									className="ml-3 my-2 text-brandy-secondary"
									displayType="secondary"
									onClick={() => {
										pop();
										setBaseButtonDisabled(false);
									}}
									prependIcon="hr"
									small
								>
									{i18n.translate('remove-project-admin')}
								</Button>
							)}

							<Button
								className="cp-btn-add-dxp-cloud ml-3 my-2 rounded-xs"
								onClick={() => {
									push(
										getInitialLxcAdmins(values?.lxc?.admins)
									);
									setBaseButtonDisabled(true);
								}}
								prependIcon="plus"
								small
							>
								{i18n.translate('add-another-admin')}
							</Button>

							<hr />
						</>
					)}
				/>
			)}

			{step === 2 && (
				<div>
					<SetupHighPriorityContactForm
						addContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'criticalIncident',
								setAddHighPriorityContact
							)
						}
						disableSubmit={updateMultiSelectEmpty}
						filter={
							HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
						}
						removedContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'criticalIncident',
								setRemoveHighPriorityContact
							)
						}
					/>

					<SetupHighPriorityContactForm
						addContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'privacyBreach',
								setAddHighPriorityContact
							)
						}
						disableSubmit={updateMultiSelectEmpty}
						filter={HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach}
						removedContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'privacyBreach',
								setRemoveHighPriorityContact
							)
						}
					/>

					<SetupHighPriorityContactForm
						addContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'securityBreach',
								setAddHighPriorityContact
							)
						}
						disableSubmit={updateMultiSelectEmpty}
						filter={HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach}
						removedContactList={(contactList) =>
							handleHighPriorityContacts(
								contactList,
								'securityBreach',
								setRemoveHighPriorityContact
							)
						}
					/>
				</div>
			)}
		</Layout>
	) : (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						onClick={() => handleOnLeftButtonClick()}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={baseButtonDisabled}
						displayType="primary"
						onClick={() => handleSubmitLxcEnvironment()}
					>
						{i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'we-ll-need-a-few-details-to-finish-creating-your-liferay-saas-workspace'
				),
				title: i18n.translate('set-up-liferay-saas'),
			}}
		>
			<FieldArray
				name="lxc.admins"
				render={({pop, push}) => (
					<>
						<div className="d-flex justify-content-between mb-2 pb-1 pl-3">
							<div className="mr-4 pr-2">
								<label>
									{i18n.translate('organization-name')}
								</label>

								<p className="dxp-cloud-project-name text-neutral-6 text-paragraph-lg">
									<strong>{project.name}</strong>
								</p>
							</div>
						</div>
						<ClayForm.Group className="mb-0">
							<ClayForm.Group className="mb-0 pb-1">
								<Input
									groupStyle="pb-1"
									helper={i18n.translate(
										'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
									)}
									label={i18n.translate('project-id')}
									name="lxc.projectId"
									required
									type="text"
								/>

								<Select
									groupStyle="mb-0"
									key={primaryRegionList}
									label={i18n.translate('primary-region')}
									name="lxc.primaryRegion"
									options={primaryRegionList}
									required
								/>
							</ClayForm.Group>

							<ClayForm.Group className="mb-0">
								{values.lxc.admins.map((admin, index) => (
									<AdminInputs
										admin={admin}
										id={index}
										key={index}
									/>
								))}
							</ClayForm.Group>
						</ClayForm.Group>

						{values?.lxc?.admins?.length >
							INITIAL_SETUP_ADMIN_COUNT && (
							<Button
								className="ml-3 my-2 text-brandy-secondary"
								displayType="secondary"
								onClick={() => {
									pop();
									setBaseButtonDisabled(false);
								}}
								prependIcon="hr"
								small
							>
								{i18n.translate('remove-project-admin')}
							</Button>
						)}

						<Button
							className="cp-btn-add-dxp-cloud ml-3 my-2 rounded-xs"
							onClick={() => {
								push(getInitialLxcAdmins(values?.lxc?.admins));
								setBaseButtonDisabled(true);
							}}
							prependIcon="plus"
							small
						>
							{i18n.translate('add-another-admin')}
						</Button>

						<hr />

						<ClayForm.Group className="mb-0">
							<Input
								groupStyle="pb-1"
								label={i18n.translate(
									'incident-management-contacts-first-and-last-name'
								)}
								name="lxc.incidentManagementFullName"
								required
								type="text"
							/>

							<Input
								groupStyle="pb-1"
								helper={i18n.translate(
									'lowercase-letters-and-numbers-only-project-ids-cannot-be-changed'
								)}
								label={i18n.translate(
									'incident-management-contacts-email-address'
								)}
								name="lxc.incidentManagementEmail"
								required
								type="text"
								validations={[
									(value) =>
										isValidEmail(value, bannedDomains),
								]}
							/>
						</ClayForm.Group>
					</>
				)}
			/>
		</Layout>
	);
};

export default SetupLiferayExperienceCloudPage;
