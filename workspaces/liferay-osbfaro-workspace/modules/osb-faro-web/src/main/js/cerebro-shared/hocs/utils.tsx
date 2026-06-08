/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {Sizes} from '~/shared/util/constants';

interface IIconProps {
	border?: boolean;
	size?: Sizes;
	symbol: string;
}

type TWithError = () => (
	Component: React.JSXElementConstructor<any>
) => (props: any) => React.ReactElement;

const withError: TWithError =
	() =>
	(Component) =>
	({error, errorMessage, ...props}) => {
		if (error) {
			return (
				<NoResultsDisplay
					title={
						errorMessage ||
						Liferay.Language.get('sorry-an-error-occurred')
					}
				/>
			);
		}

		return <Component {...props} />;
	};

type TWithEmpty = (params?: {
	emptyDescription?: string | React.ReactElement;
	emptyIcon?: IIconProps;
	emptyTitle?: string;
	primary?: boolean;
}) => (
	Component: React.JSXElementConstructor<any>
) => (props: any) => React.ReactElement;

const withEmpty: TWithEmpty =
	({emptyDescription, emptyIcon, emptyTitle, primary} = {}) =>
	(Component) =>
	({
		filterEnabled = false,
		items,
		noResultsRenderer,
		query,
		total,
		...otherProps
	}) => {
		if (items && !items.length && !total) {
			if (query || filterEnabled) {
				return (
					<NoResultsDisplay
						description={Liferay.Language.get(
							'please-try-a-different-search-term'
						)}
						icon={{
							border: false,
							size: Sizes.XXXLarge,
							symbol: 'ac_no_results_found',
						}}
						title={Liferay.Language.get(
							'there-are-no-results-found'
						)}
					/>
				);
			}
			else if (noResultsRenderer) {
				return noResultsRenderer;
			}

			return (
				<NoResultsDisplay
					description={emptyDescription}
					icon={emptyIcon}
					primary={primary}
					title={emptyTitle}
				/>
			);
		}

		return (
			<Component
				{...otherProps}
				items={items}
				query={query}
				total={total}
			/>
		);
	};

export {withEmpty, withError};
