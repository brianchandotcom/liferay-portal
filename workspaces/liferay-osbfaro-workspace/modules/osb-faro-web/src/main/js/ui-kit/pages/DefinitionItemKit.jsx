import autobind from 'autobind-decorator';
import DefinitionItem from 'shared/components/DefinitionItem';
import Item from 'ui-kit/components/Item';
import React from 'react';
import Row from 'ui-kit/components/Row';

export default class DefinitionItemKit extends React.Component {
	state = {
		name: 'Test Test',
		url: 'www.liferay.com'
	};

	@autobind
	handleSubmit(value, name) {
		return Promise.resolve().then(() => this.setState({[name]: value}));
	}

	render() {
		const {name, url} = this.state;

		return (
			<div>
				<Row>
					<Item>
						<DefinitionItem
							label='URL (Non-Editable)'
							name='url'
							onSubmit={this.handleSubmit}
							value={url}
						/>
					</Item>
				</Row>

				<Row>
					<Item>
						<DefinitionItem
							editable
							label='Name (Editable)'
							name='name'
							onSubmit={this.handleSubmit}
							value={name}
						/>
					</Item>
				</Row>
			</div>
		);
	}
}
