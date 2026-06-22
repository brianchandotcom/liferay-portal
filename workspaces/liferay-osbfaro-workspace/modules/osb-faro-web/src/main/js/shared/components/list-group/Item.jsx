import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class Item extends React.Component {
	static defaultProps = {
		action: false,
		active: false,
		disabled: false,
		flex: false,
		header: false
	};

	static propTypes = {
		accentColor: PropTypes.string,
		action: PropTypes.bool,
		active: PropTypes.bool,
		disabled: PropTypes.bool,
		flex: PropTypes.bool,
		header: PropTypes.bool
	};

	render() {
		const {
			accentColor,
			action,
			active,
			children,
			className,
			disabled,
			flex,
			header,
			...otherProps
		} = this.props;

		const classes = getCN(className, {
			active,
			'list-group-header': header,
			'list-group-item': !header,
			'list-group-item-action': action && !disabled,
			'list-group-item-disabled': disabled,
			'list-group-item-flex': flex
		});

		const style = {
			borderLeft: `4px solid ${accentColor}`
		};

		return (
			<li
				{...omitDefinedProps(otherProps, Item.propTypes)}
				className={classes}
				style={accentColor ? style : undefined}
			>
				{children}
			</li>
		);
	}
}
