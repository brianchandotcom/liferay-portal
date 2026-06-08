/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';
import PaginationBar from '~/shared/components/PaginationBar';
import {paginationDefaults} from '~/shared/util/pagination';

const defaultOptions = {defaultDelta: paginationDefaults.delta};

export default (options = {}) =>
	(WrappedComponent) => {
		const {defaultDelta} = {...defaultOptions, ...options};

		class WithPaginationBar extends React.Component {
			static defaultProps = {
				delta: defaultDelta,
				page: paginationDefaults.page,
			};

			static propTypes = {
				delta: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.number,
				]),
				onDeltaChange: PropTypes.func,
				onPageChange: PropTypes.func,
				page: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
				showDeltaDropdown: PropTypes.bool,
				total: PropTypes.number,
			};

			render() {
				const {
					delta,
					onDeltaChange,
					onPageChange,
					page,
					showDeltaDropdown,
					total,
				} = this.props;

				return (
					<>
						<WrappedComponent {...this.props} />

						{!!total && (
							<PaginationBar
								href={window.location.href}
								key="PAGINATION_BAR"
								onDeltaChange={onDeltaChange}
								onPageChange={onPageChange}
								page={page}
								selectedDelta={delta}
								showDeltaDropdown={showDeltaDropdown}
								totalItems={total}
							/>
						)}
					</>
				);
			}
		}

		return WithPaginationBar;
	};
