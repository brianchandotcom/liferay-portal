/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Component, useMemo} from 'react';
import {Navigate, useMatch, useOutletContext} from 'react-router-dom';
import Loading from '~/components/Loading/Loading';
import Rest from '~/core/Rest';
import {useFetch} from '~/hooks/useFetch';
import {ObjectActionsItems} from '~/services/rest';

type PageProperties = {
	createPath?: string;
	deniedChildren?: React.ReactNode | string;
	redirectTo?: string;
	restImpl: Rest;
};

type CheckPermissionProps = {
	children: any;
	properties: PageProperties;
};

const CheckPermission: React.FC<CheckPermissionProps> = ({
	children,
	properties: {
		createPath = '',
		deniedChildren,
		redirectTo = `/404?type=permission`,
		restImpl,
	},
}) => {
	const outletContext = useOutletContext<{actions: ObjectActionsItems}>();
	const isCreatePathMatching = useMatch(createPath);
	const restImplMemoized = useMemo(() => restImpl, [restImpl]);

	const updatePermission = useMemo(
		() =>
			!!(
				outletContext?.actions?.update ??
				outletContext?.actions?.replace
			),
		[outletContext?.actions?.replace, outletContext?.actions?.update]
	);

	const {data: createPermission, loading} = useFetch<
		ReturnType<typeof restImplMemoized.getPagePermission>
	>(restImplMemoized.uri, {
		swrConfig: {
			fetcher: restImplMemoized.getPagePermission.bind(restImplMemoized),
			shouldFetch: !!isCreatePathMatching,
		},
	});

	if (loading) {
		return <Loading />;
	}

	if (createPermission || updatePermission) {
		return children;
	}

	if (deniedChildren) {
		return deniedChildren;
	}

	return <Navigate to={redirectTo} />;
};

export function withPagePermission<T extends object>(
	WrappedComponent: React.ComponentType<T>,
	properties: PageProperties
) {
	return class extends Component<T> {
		render() {
			return (
				<CheckPermission properties={properties}>
					<WrappedComponent {...this.props} />
				</CheckPermission>
			);
		}
	};
}
