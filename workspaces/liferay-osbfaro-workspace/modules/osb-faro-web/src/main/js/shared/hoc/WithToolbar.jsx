/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {get, pickBy} from 'lodash';
import PropTypes from 'prop-types';
import React from 'react';
import {DropdownRangeKey} from '~/shared/components/dropdown-range-key/DropdownRangeKey';
import Toolbar from '~/shared/components/toolbar';
import FaroConstants, {RangeKeyTimeRanges} from '~/shared/util/constants';
import {paginationDefaults} from '~/shared/util/pagination';
import {hasChanges} from '~/shared/util/react';
import {removeUriQueryParam, setUriQueryValues} from '~/shared/util/router';

import withHistory from './WithHistory';

const {
	pagination: {cur: DEFAULT_CUR},
} = FaroConstants;

export default function WithToolbar(configs) {
	return (WrappedComponent) => {
		class WithToolbarBar extends React.Component {
			static defaultProps = {
				disableSearch: false,
				query: paginationDefaults.query,
				rangeSelectors: {
					rangeEnd: '',
					rangeKey: RangeKeyTimeRanges.Last30Days,
					rangeStart: '',
				},
				showCheckbox: false,
			};

			static propTypes = {
				alwaysShowSearch: PropTypes.bool,
				disableSearch: PropTypes.bool,
				history: PropTypes.object,
				onRangeSelectorsChange: PropTypes.func,
				onSearchValueChange: PropTypes.func,
				onSelectEntirePage: PropTypes.func,
				orderByOptions: PropTypes.array,
				orderIOMap: PropTypes.object,
				query: PropTypes.string,
				rangeKeys: PropTypes.array,
				rangeSelectors: PropTypes.object,
				renderNav: PropTypes.func,
				renderViewSelectedToggle: PropTypes.func,
				selectEntirePage: PropTypes.bool,
				selectEntirePageIndeterminate: PropTypes.bool,
				showCheckbox: PropTypes.bool,
				showFilterAndOrder: PropTypes.bool,
				total: PropTypes.number,
			};

			constructor(props) {
				super(props);

				this.state = {
					searchValue: props.query,
				};
			}

			componentDidUpdate(prevProps) {
				const {
					props: {query},
					state: {searchValue},
				} = this;

				if (
					hasChanges(prevProps, this.props, 'query') &&
					query !== searchValue
				) {
					this.setState({searchValue: query});
				}
			}

			@autobind
			handleRangeSelectorsChange(rangeSelectors) {
				const {history, onRangeSelectorsChange} = this.props;

				const {rangeEnd, rangeKey, rangeStart} = rangeSelectors;

				if (onRangeSelectorsChange) {
					onRangeSelectorsChange(rangeSelectors);
				}
				else {
					history.push(
						setUriQueryValues(
							pickBy({
								page: DEFAULT_CUR,
								rangeEnd,
								rangeKey,
								rangeStart,
							}),
							removeUriQueryParam(
								window.location.href,
								'rangeEnd',
								'rangeStart'
							)
						)
					);
				}
			}

			@autobind
			handleSearchValueChange(value) {
				const {onSearchValueChange} = this.props;

				if (onSearchValueChange) {
					onSearchValueChange(value);
				}

				this.setState({
					searchValue: value,
				});
			}

			render() {
				const {
					props: {
						alwaysShowSearch,
						disableSearch,
						onFilterByChange,
						onOrderIOMapChange,
						onQueryChange,
						onSelectEntirePage,
						orderByOptions,
						orderIOMap,
						query,
						rangeKeys,
						rangeSelectors,
						renderNav,
						renderViewSelectedToggle,
						selectEntirePage,
						selectEntirePageIndeterminate,
						showCheckbox,
						showDropdownRangeKey,
						showFilterAndOrder,
						total,
						...otherProps
					},
					state: {searchValue},
				} = this;

				return (
					<>
						<Toolbar
							{...otherProps}
							alwaysShowSearch={alwaysShowSearch}
							disableSearch={get(
								configs,
								'disableSearch',
								disableSearch
							)}
							onFilterByChange={onFilterByChange}
							onOrderIOMapChange={onOrderIOMapChange}
							onQueryChange={onQueryChange}
							onSearchValueChange={this.handleSearchValueChange}
							onSelectEntirePage={onSelectEntirePage}
							orderByOptions={orderByOptions}
							orderIOMap={orderIOMap}
							query={query}
							renderViewSelectedToggle={renderViewSelectedToggle}
							searchValue={searchValue}
							selectEntirePage={selectEntirePage}
							selectEntirePageIndeterminate={
								selectEntirePageIndeterminate
							}
							showCheckbox={showCheckbox}
							showFilterAndOrder={get(
								configs,
								'showFilterAndOrder',
								showFilterAndOrder
							)}
							showSearch
							total={total}
						>
							{get(
								configs,
								'showDropdownRangeKey',
								showDropdownRangeKey
							) && (
								<DropdownRangeKey
									legacy={get(
										configs,
										'legacyDropdownRangeKey',
										true
									)}
									onRangeSelectorChange={
										this.handleRangeSelectorsChange
									}
									rangeKeys={rangeKeys}
									rangeSelectors={rangeSelectors}
								/>
							)}

							{renderNav && renderNav(this.props)}
						</Toolbar>

						<WrappedComponent {...this.props} />
					</>
				);
			}
		}

		return withHistory(WithToolbarBar);
	};
}
