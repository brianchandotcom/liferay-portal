import autobind from 'autobind-decorator';
import FilterAndOrder from 'shared/components/FilterAndOrder';
import React from 'react';
import Row from '../components/Row';
import {Map} from 'immutable';

class FilterAndOrderKit extends React.Component {
	state = {
		filterBy: new Map(),
		orderBy: 1
	};

	@autobind
	handleOrderByChange(orderBy) {
		this.setState({
			orderBy
		});
	}

	@autobind
	handleFilterByChange(filterBy) {
		this.setState({
			filterBy
		});
	}

	render() {
		const {filterBy, orderBy} = this.state;

		return (
			<Row
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<FilterAndOrder
					filterBy={filterBy}
					filterByOptions={[
						{
							key: 'state',
							label: 'Disabled Segments',
							values: [{label: 'Disabled', value: 'DISABLED'}]
						},
						{
							key: 'industry',
							label: 'Industry',
							values: [
								{label: 'Finance', value: 'finance'},
								{label: 'Healthcare', value: 'healthcare'},
								{label: 'Tech', value: 'tech'}
							]
						},
						{
							key: 'region',
							label: 'Region',
							values: [
								{
									label: 'North America',
									value: 'North America'
								},
								{label: 'Europe', value: 'Europe'},
								{label: 'Asia', value: 'Asia'}
							]
						}
					]}
					onFilterByChange={this.handleFilterByChange}
					onOrderByChange={this.handleOrderByChange}
					orderBy={orderBy}
					orderByOptions={[
						{
							label: 'First Seen',
							value: 0
						},
						{
							label: 'Date Created',
							value: 1
						},
						{
							label: 'Industry',
							value: 2
						}
					]}
				/>
			</Row>
		);
	}
}

export default FilterAndOrderKit;
