import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {addContext} from 'shared/util/clay';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';
import {Stack} from 'immutable';
export const CONTEXT = 'navbar';
const DISPLAYS = ['dark', 'light', 'primary'];
const JUSTIFY_CONTENTS = ['start', 'end', 'center', 'around', 'between'];

class Brand extends React.Component {
	static propTypes = {
		href: PropTypes.string
	};

	render() {
		const {children, className, href, ...otherProps} = this.props;

		if (href) {
			return (
				<Link
					{...omitDefinedProps(otherProps, Brand.propTypes)}
					className={`navbar-brand${
						className ? ` ${className}` : ''
					}`}
					to={href}
				>
					{children}
				</Link>
			);
		} else {
			return (
				<div
					{...omitDefinedProps(otherProps, Brand.propTypes)}
					className={`navbar-brand${
						className ? ` ${className}` : ''
					}`}
				>
					{children}
				</div>
			);
		}
	}
}

class NavBar extends React.Component {
	static childContextTypes = {
		clay: PropTypes.instanceOf(Stack)
	};

	static defaultProps = {
		expand: false,
		managementBar: false,
		pageNav: false,
		underline: false
	};

	static propTypes = {
		display: PropTypes.oneOf(DISPLAYS),
		expand: PropTypes.bool,
		justifyContent: PropTypes.oneOf(JUSTIFY_CONTENTS),
		managementBar: PropTypes.bool,
		pageNav: PropTypes.bool,
		underline: PropTypes.bool
	};

	getChildContext() {
		return addContext(this, CONTEXT);
	}

	render() {
		const {
			children,
			className,
			display,
			expand,
			justifyContent,
			managementBar,
			pageNav,
			underline
		} = this.props;

		const classes = getCN('navbar', 'navbar-root', className, {
			'management-bar': managementBar,
			'management-bar-dark': managementBar && display === 'dark',
			'management-bar-light': managementBar && display === 'light',
			'management-bar-primary': managementBar && display === 'primary',
			'navbar-dark': !managementBar && display === 'dark',
			'navbar-expand': expand,
			'navbar-light': !managementBar && display === 'light',
			'navbar-underline': underline,
			'page-nav': pageNav
		});

		const containerClasses = getCN('container-fluid', {
			[`justify-content-${justifyContent}`]: justifyContent
		});

		return (
			<nav className={classes}>
				<div className={containerClasses}>{children}</div>
			</nav>
		);
	}
}

NavBar.Brand = Brand;
export default NavBar;
