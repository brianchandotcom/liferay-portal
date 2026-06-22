import React from 'react';

export default class ItemTitle extends React.Component {
	render() {
		const {children, className, ...otherProps} = this.props;

		return (
			<div
				{...otherProps}
				className={`h4 list-group-title${
					className ? ` ${className}` : ''
				}`}
			>
				{children}
			</div>
		);
	}
}
