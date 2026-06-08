/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get, omit} from 'lodash';
import React from 'react';
import {compose} from 'redux';
import ErrorDisplay from '~/shared/components/ErrorDisplay';
import Loading from '~/shared/components/Loading';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import ErrorPage from '~/shared/pages/ErrorPage';
import {sub} from '~/shared/util/lang';
import {toRoute} from '~/shared/util/router';

/**
 * HOC for ErrorDisplay.
 * @param {Object} - Options object to pass as props to ErrorDisplay
 * @returns {Function} Returns the ErrorDisplay or Wrapped Component.
 */
export const withError = function withError(options = {}) {
	return (Component) =>
		({
			error,
			errorProps = {},
			pageDisplay = true,
			refetch,
			...otherProps
		}) => {
			const otherOptions = omit(options, 'page');

			if (error) {
				return get(options, 'page', pageDisplay) ? (
					<ErrorPage {...errorProps} {...otherOptions} />
				) : (
					<ErrorDisplay
						onReload={refetch}
						spacer
						{...errorProps}
						{...otherOptions}
					/>
				);
			}

			return <Component refetch={refetch} {...otherProps} />;
		};
};

/**
 * HOC for NoResultsDisplay.
 * @param {Object} - Options object to pass as props to NoResultsDisplay.
 * @returns {Function} Returns the NoResultsDisplay or WrappedComponent.
 */
export const withEmpty = function withEmpty(options = {}) {
	return (Component) =>
		({
			data,
			error,
			loading,
			noResultsRenderer,
			noResultsRendererProps,
			total,
			...props
		}) => {
			if (
				((data && data.total === 0) || total === 0) &&
				!loading &&
				!error
			) {
				if (noResultsRenderer) {
					const NoResults = noResultsRenderer;

					return <NoResults />;
				}

				return (
					<NoResultsDisplay
						{...options}
						{...noResultsRendererProps}
					/>
				);
			}

			return (
				<Component
					data={data}
					error={error}
					loading={loading}
					total={total}
					{...props}
				/>
			);
		};
};

/**
 * HOC for Loading display.
 * @param {Object} - Options object to pass as props to Loading component.
 * @returns {Function} Returns the Loading or WrappedComponent.
 */
export const withLoading = function withLoading(options = {}) {
	return (Component) =>
		({loading, ...otherProps}) => {
			if (loading) {
				return <Loading {...options} />;
			}

			return <Component {...otherProps} />;
		};
};

export const withNull = function withNull(key, errorProps = {}) {
	return (Component) => (props) => {
		const {entityType = Liferay.Language.get('page'), linkRoute} =
			errorProps;

		if (key && !props[key]) {
			return (
				<ErrorPage
					{...props}
					href={toRoute(linkRoute, props.router.params)}
					linkLabel={sub(Liferay.Language.get('go-to-x'), [
						entityType,
					])}
					message={sub(
						Liferay.Language.get(
							'the-x-you-are-looking-for-does-not-exist'
						),
						[entityType.toLowerCase()]
					)}
					subtitle={sub(Liferay.Language.get('x-not-found'), [
						entityType,
					])}
					title={Liferay.Language.get('404')}
				/>
			);
		}

		return <Component {...props} />;
	};
};

/**
 * HOC for displaying results.
 */
export const SafeResults = compose(
	withLoading({spacer: true}),
	withError()
)(({children, data}) => children(data));

export const WrapSafeResults = compose(
	withLoading(),
	withError()
)(({children}) => children);
