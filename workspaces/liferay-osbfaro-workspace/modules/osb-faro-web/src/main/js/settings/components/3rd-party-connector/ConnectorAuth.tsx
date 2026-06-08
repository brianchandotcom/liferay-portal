/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {
	createConnector,
	generateConnectorToken,
	updateConnector,
} from '~/shared/api/connector';
import {Alert} from '~/shared/types';
import {CredentialTypes} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';

import {CopyInputValue} from '../CopyInputValue';
import {ConnectorConfig} from './types';

interface IConnectorAuthProps {
	addAlert: Alert.AddAlert;
	buttonProps?: {[key: string]: any};
	config: ConnectorConfig;
	dataSource?: DataSource;
	disabled?: boolean;
	groupId: string;
	onCancel?: () => void;
	onSubmit: (dataSource: any) => void;
}

const ConnectorAuth: React.FC<IConnectorAuthProps> = ({
	addAlert,
	buttonProps,
	config,
	dataSource,
	disabled = false,
	groupId,
	onCancel,
	onSubmit,
}) => {
	const endpointURL = `${window.location.origin}${config.endpointPath}`;

	const [token, setToken] = useState('');
	const [isSubmitting, setIsSubmitting] = useState(false);

	useEffect(() => {
		const fetchConnectorTokenForGroup = async () => {
			try {
				const data = await generateConnectorToken({
					groupId,
					type: config.slug,
				});

				if (data?.token) {
					setToken(data.token);
				}
			}
			catch (error) {
				addAlert({
					alertType: Alert.Types.Error,
					message: (error as Error).message,
					timeout: false,
				});
			}
		};

		fetchConnectorTokenForGroup();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [config.slug, groupId]);

	return (
		<ClayForm
			onSubmit={async (event) => {
				event.preventDefault();
				setIsSubmitting(true);

				try {
					if (dataSource) {
						const updatedDataSource = await updateConnector(
							config.slug,
							{
								channelsConfiguration: dataSource
									.getIn([
										'provider',
										'channelsConfiguration',
									])
									?.toJS(),
								credentials: {
									privateKey: token,
									publicKey: '',
									type: CredentialTypes.Token,
								},
								groupId,
								id: dataSource.id ?? '',
								name: dataSource.name,
							}
						);

						onSubmit(updatedDataSource);
					}
					else {
						const newDataSource = await createConnector(
							config.slug,
							{
								credentials: {
									privateKey: token,
									publicKey: '',
									type: CredentialTypes.Token,
								},
								groupId,
								name: config.displayName,
							}
						);

						onSubmit(newDataSource);
					}
				}
				catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						),
					});
				}
				finally {
					setIsSubmitting(false);
				}
			}}
		>
			<label htmlFor="endpoint">
				<Text weight="semi-bold">{config.languages.endpointLabel}</Text>
			</label>

			<Text as="p" color="secondary" size={3}>
				{config.languages.endpointHelper}
			</Text>

			<CopyInputValue
				addAlert={addAlert}
				disabled={disabled}
				value={endpointURL}
			/>

			<label htmlFor="token">
				<Text weight="semi-bold">{config.languages.tokenLabel}</Text>
			</label>

			<CopyInputValue
				addAlert={addAlert}
				disabled={disabled}
				value={token}
			/>

			{!disabled && (
				<div className="mt-4">
					<ClayButton
						{...buttonProps}
						disabled={isSubmitting || !token}
						loading={isSubmitting}
						type="submit"
					>
						{Liferay.Language.get('continue')}
					</ClayButton>

					{onCancel && (
						<ClayButton
							block
							borderless
							displayType="secondary"
							onClick={onCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					)}
				</div>
			)}
		</ClayForm>
	);
};

export default ConnectorAuth;
