/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

declare module 'react-router-dom' {
	export function useParams<
		Params extends Record<string, string | undefined> = Record<
			string,
			string | undefined
		>,
	>(): Params;

	interface BrowserRouterProps {
		children?: ReactNode;
	}

	interface StaticRouterProps {
		children?: ReactNode;
	}

	interface HashRouterProps {
		children?: ReactNode;
	}

	interface MemoryRouterProps {
		children?: ReactNode;
	}

	interface RouteProps {
		children?: ReactNode | ((props: any) => ReactNode);
	}

	interface SwitchProps {
		children?: ReactNode;
	}

	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	interface LinkProps<_S = unknown> {
		children?: ReactNode;
	}

	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	interface NavLinkProps<_S = unknown> {
		children?: ReactNode;
	}

	interface RedirectProps {
		children?: ReactNode;
	}

	interface PromptProps {
		children?: ReactNode;
	}
}

declare module 'react-router' {
	interface RouterProps {
		children?: ReactNode;
	}

	interface StaticRouterProps {
		children?: ReactNode;
	}

	interface RouteProps {
		children?: ReactNode | ((props: any) => ReactNode);
	}

	interface SwitchProps {
		children?: ReactNode;
	}

	interface MemoryRouterProps {
		children?: ReactNode;
	}

	interface RedirectProps {
		children?: ReactNode;
	}

	interface PromptProps {
		children?: ReactNode;
	}
}
