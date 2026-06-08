/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export namespace Alert {
	export enum Types {
		Alert = 'ALERT',
		Default = 'DEFAULT',
		Error = 'ERROR',
		Pending = 'PENDING',
		Success = 'SUCCESS',
		Warning = 'WARNING',
	}

	export type AddAlert = ({
		alertType,
		message,
		timeout,
	}: {
		alertType: Types;
		message: string;
		timeout?: boolean;
	}) => Promise<any>;

	export type RemoveAlert = () => (action: {
		payload: {
			id: string;
		};
		type: 'REMOVE_ALERT';
	}) => void;
}
