import InputWithEditToggle from 'shared/components/InputWithEditToggle';
import React from 'react';

class InputWithEditToggleKit extends React.Component {
	render() {
		return (
			<div>
				<InputWithEditToggle
					onSubmit={this.handleSubmit}
					placeholder='Put some text in here'
					value='foo'
				/>
			</div>
		);
	}
}

export default InputWithEditToggleKit;
