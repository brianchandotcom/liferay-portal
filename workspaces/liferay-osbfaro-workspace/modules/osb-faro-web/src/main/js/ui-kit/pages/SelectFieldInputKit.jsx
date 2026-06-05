import autobind from 'autobind-decorator';
import React from 'react';
import SelectFieldInput from 'contacts/components/SelectFieldInput';

class SelectFieldInputKit extends React.Component {
	state = {
		selectedItem: null
	};

	@autobind
	handleSelect(item) {
		this.setState({
			selectedItem: item
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SelectFieldInput
					{...this.props}
					onSelect={this.handleSelect}
					selectedItem={this.state.selectedItem}
				/>
			</div>
		);
	}
}

export default SelectFieldInputKit;
