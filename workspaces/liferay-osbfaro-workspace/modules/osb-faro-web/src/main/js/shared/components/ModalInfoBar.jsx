import React from 'react';

class ModalInfoBar extends React.Component {
	render() {
		return (
			<div
				className={`modal-info-bar-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{this.props.children}
			</div>
		);
	}
}

export default ModalInfoBar;
