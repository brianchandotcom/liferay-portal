import autobind from 'autobind-decorator';
import React from 'react';
import Row from '../components/Row';
import SearchableSelect from 'shared/components/SearchableSelect';
import {times} from 'lodash';

const items = times(30, i => ({name: `item${i}`, value: i}));

function includes(source, target) {
	return source.toLocaleUpperCase().includes(target.toLocaleUpperCase());
}

class SearchableSelectKit extends React.Component {
	state = {
		inputValue: '',
		selectedItem: undefined
	};

	@autobind
	handleInput(value) {
		this.setState({
			inputValue: value
		});
	}

	@autobind
	handleSelect(item) {
		this.setState({
			inputValue: '',
			selectedItem: item
		});
	}

	render() {
		const {inputValue, selectedItem} = this.state;

		return (
			<div>
				<Row>
					<SearchableSelect
						buttonPlaceholder='Select an Item'
						inputPlaceholder='Search for...'
						inputValue={inputValue}
						items={items.filter(({name}) =>
							includes(name, inputValue)
						)}
						onSearchChange={this.handleInput}
						onSelect={this.handleSelect}
						selectedItem={selectedItem}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder='Select an Item'
						disabled
						inputPlaceholder='Search for...'
						items={items}
						selectedItem={selectedItem}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder='Select an Item'
						inputPlaceholder='Search for...'
						items={items}
						selectedItem={selectedItem}
						showSearch={false}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder='Has sub-headers'
						inputPlaceholder='Search for...'
						items={[
							{
								name: 'Test Subheader',
								subheader: true
							},
							...items
						]}
						selectedItem={selectedItem}
						showSearch={false}
					/>
				</Row>
			</div>
		);
	}
}

export default SearchableSelectKit;
