import autobind from 'autobind-decorator';
import EntityList from 'shared/components/EntityList';
import React from 'react';
import Row from '../components/Row';
import {EntityTypes} from 'shared/util/constants';
import {Set} from 'immutable';

class EntityListKit extends React.Component {
	state = {
		selectedItemsISet: new Set()
	};

	@autobind
	handleSelectItemsChange(newVal) {
		this.setState({
			selectedItemsISet: newVal
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<EntityList
						{...this.props}
						entityType={EntityTypes.IndividualsSegment}
						items={[
							{
								id: 1,
								name: 'Portland',
								properties: {
									global: {total: 321}
								},
								type: EntityTypes.IndividualsSegment
							},
							{
								id: 2,
								name: 'San Diego',
								properties: {
									global: {total: 231}
								},
								type: EntityTypes.IndividualsSegment
							},
							{
								id: 3,
								name: 'Seattle',
								properties: {
									global: {total: 123}
								},
								type: EntityTypes.IndividualsSegment
							}
						]}
						onSelectItemsChange={this.handleSelectItemsChange}
						selectedItemsISet={this.state.selectedItemsISet}
					/>
				</Row>
			</div>
		);
	}
}

export default EntityListKit;
