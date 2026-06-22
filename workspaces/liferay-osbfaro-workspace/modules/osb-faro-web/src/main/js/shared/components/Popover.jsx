import getCN from 'classnames';
import PopoverBase from 'shared/components/PopoverBase';
import React from 'react';
import {getAlignPosition} from 'shared/util/util';
import {PropTypes} from 'prop-types';

const CLASSNAME = 'analytics-popover';

/**
 * Popover
 * @class
 */
class Popover extends React.Component {
	static propTypes = {
		alignElement: PropTypes.object,
		className: PropTypes.string,
		content: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
		isDescription: PropTypes.bool,
		title: PropTypes.string,
		visible: PropTypes.bool
	};

	state = {
		position: null,
		width: 240
	};

	constructor(props) {
		super(props);

		this._popoverBaseRef = React.createRef();
	}

	/**
	 * Lifecycle Component Did Mount - ReactJS
	 */
	componentDidMount() {
		return this.setPopoverWidth();
	}

	componentDidUpdate(prevProps) {
		const {alignElement, visible} = this.props;

		if (visible && !prevProps.visible) {
			this.setState({
				position: getAlignPosition(
					this._popoverBaseRef.current._elementRef.current,
					alignElement
				)
			});
		}
	}

	/**
	 * Set Popover Width
	 */
	setPopoverWidth() {
		const {_elementRef} = this._popoverBaseRef.current;

		const width = _elementRef.current.offsetHeight;

		this.setState({
			width
		});
	}

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {className, content, isDescription, title, visible} = this.props;
		const {position, width} = this.state;
		const withoutContent = !content || content === title;
		const classes = getCN(
			CLASSNAME,
			{
				'no-content': withoutContent,
				'popover-large': width > 600
			},
			className
		);

		return (
			<PopoverBase
				className={classes}
				placement={position}
				ref={this._popoverBaseRef}
				visible={visible}
			>
				{isDescription && withoutContent ? (
					<PopoverBase.Body>
						<span className='text-secondary'>{title}</span>
					</PopoverBase.Body>
				) : (
					<PopoverBase.Header>{title}</PopoverBase.Header>
				)}

				{content && content !== title && (
					<PopoverBase.Body>
						<span className='text-secondary'>{content}</span>
					</PopoverBase.Body>
				)}
			</PopoverBase>
		);
	}
}

export default Popover;
