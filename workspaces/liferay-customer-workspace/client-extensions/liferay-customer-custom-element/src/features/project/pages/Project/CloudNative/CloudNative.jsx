/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useGetCloudNativeEnvironments} from '~/services/liferay/graphql/cloud-native-environments';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';
import ActivationStatus from '~/features/project/containers/ActivationStatus';
import PopoverIcon from '~/features/project/containers/ActivationStatus/DXPCloud/components/PopoverIcon';
import {useAppContext} from '~/features/project/context';
import DeveloperKeysLayouts from '~/features/project/layouts/DeveloperKeysLayout';
import {LIST_TYPES, PRODUCT_TYPES} from '~/features/project/utils/constants';
import i18n from '~/utils/I18n';

const CloudNative = () => {
	const [
		{project, subscriptionGroups, userAccount},
		dispatch,
	] = useAppContext();
	const {setHasSideMenu} = useOutletContext();

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	const [oAuthToken, setOAuthToken] = useState();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	const {data} = useGetCloudNativeEnvironments({
		filter: `accountKey eq '${project?.accountKey}'`,
	});

	const cloudNativeEnvironments =
		data?.c?.cloudNativeEnvironments?.items;

	if (!project || !subscriptionGroups) {
		return <span> {i18n.translate('loading')}...</span>;
	}

	return (
		<>
			<h1>{i18n.translate('cloud-native-environments')}</h1>

			<div className="mt-4">
				{cloudNativeEnvironments?.map(
					(cloudNativeEnvironment) => (
						<ClayTable striped={false}>
							<ClayTable.Head>
								<ClayTable.Row>
									<ClayTable.Cell
										className='bg-neutral-1 font-weight-bold text-neutral-10'
									>
										{i18n.translate("environment")}
									</ClayTable.Cell>
									<ClayTable.Cell
										className='bg-neutral-1 font-weight-bold text-neutral-10'
									>
										{i18n.translate("subscription-id")}

										<PopoverIcon
											symbol="question-circle-full"
											title="please-copy-and-paste-this-subscription-id-to-your-cloud-native-instance"
										/>
									</ClayTable.Cell>
									<ClayTable.Cell
										className='bg-neutral-1 font-weight-bold text-neutral-10'
									>
										{i18n.translate("maximum-cluster-nodes")}

										<PopoverIcon
											symbol="question-circle-full"
											title="maximum-number-of-active-nodes-available-for-this-environment-this-does-not-include-expired-or-future-nodes"
										/>
									</ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								<ClayTable.Row>
									<ClayTable.Cell>
										{i18n.translate("production")}
									</ClayTable.Cell>
									<ClayTable.Cell>
										{cloudNativeEnvironment.productionSubscriptionUuid}
									</ClayTable.Cell>
									<ClayTable.Cell>
										{cloudNativeEnvironment.maxClusterNodes}
									</ClayTable.Cell>
								</ClayTable.Row>
								<ClayTable.Row>
									<ClayTable.Cell>
										{i18n.translate("non-production")}
									</ClayTable.Cell>
									<ClayTable.Cell>
										{cloudNativeEnvironment.nonProductionSubscriptionUuid}
									</ClayTable.Cell>
									<ClayTable.Cell>
										{i18n.translate("unlimited")}
									</ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Body>
						</ClayTable>
					)
				)}

				{!cloudNativeEnvironments?.length && (
					<div className="p-3">
						{i18n.translate("no-cloud-native-environments-were-found")}
					</div>
				)}
			</div>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'to-activate-a-local-instance-of-liferay-dxp-download-a-developer-key-for-your-liferay-dxp-version'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.dxpMajorVersion}
					oAuthToken={oAuthToken}
					productName="DXP"
					projectName={project.name}
				/>
			</DeveloperKeysLayouts>
		</>
	);
};

export default CloudNative;
