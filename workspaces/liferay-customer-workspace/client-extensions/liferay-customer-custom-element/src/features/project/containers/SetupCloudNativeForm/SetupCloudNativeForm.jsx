/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Formik} from 'formik';
import {useState} from 'react';
import {Button} from '~/components';
import Layout from '~/components/FormLayout';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import SetupHighPriorityContactForm from '~/features/project/containers/HighPriorityContacts/SetupHighPriorityContact';
import {STATUS_CODE, STATUS_TAG_TYPE_NAMES} from '~/features/project/utils/constants';
import {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	addContactRoleLiferay,
	addContactRoleRaysource,
	removeContactRoleLiferay,
	removeContactRoleRaysource,
	updateLiferayContact,
	updateRaysourceContact,
} from '~/features/project/utils/getHighPriorityContacts';
import {patchAccountSubscriptionGroups} from '~/services/liferay/graphql/account-subscription-groups/queries/patchAccountSubscriptionGroups';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';
import i18n from '~/utils/I18n';

const SetupCloudNativePage = ({
	client,
	handlePage,
	leftButton,
	project,
	subscriptionGroupId,
}) => {
	const [isLoadingSubmitButton, setIsLoadingSubmitButton] = useState(false);
	const [addHighPriorityContact, setAddHighPriorityContact] = useState([]);
	const [removeHighPriorityContact, setRemoveHighPriorityContact] = useState(
		[]
	);

	const {provisioningServerAPI} = useAppPropertiesContext();

	const handleSubmit = async () => {
		setIsLoadingSubmitButton(true);

		try {
			const oAuthToken = await getOrRequestToken();

			try {
				await updateRaysourceContact(
					addContactRoleRaysource,
					addHighPriorityContact,
					oAuthToken,
					project,
					provisioningServerAPI
				);

				await updateLiferayContact(
					addHighPriorityContact,
					addContactRoleLiferay,
					project,
					client
				);
			}
			catch (error) {
				if (error.cause === STATUS_CODE.conflict) {
					await updateLiferayContact(
						addHighPriorityContact,
						addContactRoleLiferay,
						project,
						client
					);
				}
				else {
					throw new Error('Error', {cause: error.cause});
				}
			}

			await updateRaysourceContact(
				removeContactRoleRaysource,
				removeHighPriorityContact,
				oAuthToken,
				project,
				provisioningServerAPI
			);

			await updateLiferayContact(
				removeHighPriorityContact,
				removeContactRoleLiferay,
				project,
				client
			);

			await client.mutate({
				context: {type: 'liferay-rest'},
				mutation: patchAccountSubscriptionGroups,
				variables: {
					accountSubscriptionGroup: {
						accountKey: project.accountKey,
						activationStatus: STATUS_TAG_TYPE_NAMES.inProgress,
						r_accountEntryToAccountSubscriptionGroup_accountEntryId:
							project.id,
					},
					id: subscriptionGroupId,
				},
			});

			handlePage();
		}
		catch (error) {
			console.error(error);
		}
		finally {
			setIsLoadingSubmitButton(false);
		}
	};

	return (
		<Layout
			className="pt-1 px-4"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={handlePage}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={isLoadingSubmitButton}
						displayType="primary"
						isLoading={isLoadingSubmitButton}
						onClick={handleSubmit}
					>
						{i18n.translate('submit')}
					</Button>
				),
			}}
			headerProps={{
				helper: i18n.translate(
					'team-members-who-will-have-access-to-cloud-native'
				),
				title: i18n.translate('set-up-cloud-native'),
			}}
		>
			<SetupHighPriorityContactForm
				addContactList={setAddHighPriorityContact}
				disableSubmit={() => {}}
				filter={HIGH_PRIORITY_CONTACT_CATEGORIES.cloudNative}
				removedContactList={setRemoveHighPriorityContact}
			/>
		</Layout>
	);
};

const SetupCloudNativeForm = (props) => (
	<Formik initialValues={{}}>
		{(formikProps) => <SetupCloudNativePage {...props} {...formikProps} />}
	</Formik>
);

export default SetupCloudNativeForm;
