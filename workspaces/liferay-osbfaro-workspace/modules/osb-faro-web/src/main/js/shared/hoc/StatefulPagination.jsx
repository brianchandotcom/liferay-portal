import autobind from 'autobind-decorator';
import FaroConstants from 'shared/util/constants';
import React from 'react';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {invoke, isFunction} from 'lodash';
import {Map} from 'immutable';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = FaroConstants;

const DEFAULT_PAGINATION_PROPS = {
	initialDelta: DEFAULT_DELTA,
	initialFilterBy: new Map(),
	initialOrderIOMap: createOrderIOMap(NAME),
	initialPage: DEFAULT_PAGE,
	initialQuery: ''
};

export default function withStatefulPagination(
	WrappedComponent,
	initialPaginationProps,
	mapPropsFn,
	useRef = true
) {
	const getInitialProps = props => {
		const initialProps = isFunction(initialPaginationProps)
			? initialPaginationProps(props)
			: initialPaginationProps;

		return {...DEFAULT_PAGINATION_PROPS, ...initialProps};
	};

	class StatefulPagination extends React.Component {
		constructor(props = {}) {
			super(props);

			const {
				initialDelta,
				initialFilterBy,
				initialOrderIOMap,
				initialPage,
				initialQuery
			} = getInitialProps(props);

			this.state = {
				delta: initialDelta,
				filterBy: initialFilterBy,
				orderIOMap: initialOrderIOMap,
				page: initialPage,
				query: initialQuery
			};

			this._wrappedComponentRef = React.createRef();
		}

		@autobind
		handleDeltaChange(delta) {
			this.setState({
				delta,
				page: DEFAULT_PAGE
			});
		}

		@autobind
		handleFilterByChange(value) {
			this.setState({filterBy: value, page: DEFAULT_PAGE});
		}

		@autobind
		handleOrderIOMapChange(orderIOMap) {
			this.setState({
				orderIOMap,
				page: DEFAULT_PAGE
			});
		}

		@autobind
		handlePageChange(page) {
			this.setState({
				page
			});
		}

		@autobind
		handleQueryChange(query) {
			this.setState({
				page: DEFAULT_PAGE,
				query
			});
		}

		/**
		 * Public method for refreshing data
		 */
		reload() {
			invoke(this._wrappedComponentRef.current, 'reload');
		}

		/**
		 * Public method for resetting the page state to default value.
		 */
		resetPage() {
			this.handlePageChange(DEFAULT_PAGE);
		}

		render() {
			const {
				props,
				state: {delta, filterBy, orderIOMap, page, query}
			} = this;

			const statefulProps = {
				delta,
				filterBy,
				onDeltaChange: this.handleDeltaChange,
				onFilterByChange: this.handleFilterByChange,
				onOrderIOMapChange: this.handleOrderIOMapChange,
				onPageChange: this.handlePageChange,
				onQueryChange: this.handleQueryChange,
				onSearchValueChange: this.handleQueryChange,
				orderIOMap,
				page,
				query
			};

			const mappedStatefulProps = mapPropsFn
				? mapPropsFn(statefulProps, this.props)
				: statefulProps;

			const ref = useRef ? {ref: this._wrappedComponentRef} : {};

			return (
				<WrappedComponent
					{...props}
					{...mappedStatefulProps}
					{...ref}
				/>
			);
		}
	}

	return StatefulPagination;
}
