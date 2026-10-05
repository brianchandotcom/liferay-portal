import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import React from 'react';
import {
	ConnectDragPreview,
	ConnectDragSource,
	DragSource as dragSource,
} from 'react-dnd';
import {Criterion} from '../utils/types';
import {DragTypes} from '../utils/drag-types';
import {generateRowId} from '../utils/utils';
import {getEmptyImage} from 'react-dnd-html5-backend';
import {getStickerStyle} from './stickerColors';
import {Property} from 'shared/util/records';
import {PropertyTypes} from '../utils/constants';

const TYPE_ICON_MAP = {
	[PropertyTypes.Behavior]: 'click',
	[PropertyTypes.Boolean]: 'check',
	[PropertyTypes.AccountDate]: 'date',
	[PropertyTypes.AccountSelectText]: 'text',
	[PropertyTypes.AccountNumber]: 'integer',
	[PropertyTypes.AccountText]: 'text',
	[PropertyTypes.Date]: 'date',
	[PropertyTypes.DateTime]: 'date',
	[PropertyTypes.Duration]: 'time',
	[PropertyTypes.Event]: 'click',
	[PropertyTypes.Number]: 'integer',
	[PropertyTypes.OrganizationBoolean]: 'check',
	[PropertyTypes.OrganizationDate]: 'date',
	[PropertyTypes.OrganizationDateTime]: 'date',
	[PropertyTypes.OrganizationNumber]: 'integer',
	[PropertyTypes.OrganizationSelectText]: 'text',
	[PropertyTypes.OrganizationText]: 'text',
	[PropertyTypes.SessionChannel]: 'check',
	[PropertyTypes.SessionDateTime]: 'date',
	[PropertyTypes.SessionNumber]: 'integer',
	[PropertyTypes.SessionText]: 'text',
	[PropertyTypes.SessionUtmParameter]: 'text',
	[PropertyTypes.Vocabulary]: 'text',
	[PropertyTypes.Interest]: 'check',
	[PropertyTypes.SearchTerm]: 'check',
	[PropertyTypes.Tag]: 'text',
	[PropertyTypes.Text]: 'text',
};

export const getTypeIcon = (type: string): string =>
	TYPE_ICON_MAP[type as keyof typeof TYPE_ICON_MAP] || 'text';

/**
 * Passes the required values to the drop target.
 * This method must be called `beginDrag`.
 * @param {Object} props Component's current props
 * @returns {Object} The props to be passed to the drop target.
 */
export const beginDrag = ({
	defaultValue,
	name,
	property,
	type,
}: {
	defaultValue: any;
	name: string;
	property: Property;
	type: PropertyTypes;
}): {
	criterion: Criterion;
	property: Property;
} => {
	let touched: boolean | object = false;
	let valid: boolean | object = true;

	if (type === PropertyTypes.Behavior) {
		touched = {asset: false, dateFilter: false, occurenceCount: false};

		// asset starts invalid: a behavior criterion requires the user to select
		// an asset type (or Page) before the segment can be saved.

		valid = {asset: false, dateFilter: true, occurenceCount: true};
	}
	else if (type === PropertyTypes.Event) {
		touched = {occurenceCount: false};
		valid = {occurenceCount: true};
	}
	else if (type === PropertyTypes.SessionGeolocation) {
		touched = {country: false, dateFilter: false};
		valid = {country: false, dateFilter: true};
	}
	else if (type === PropertyTypes.SessionChannel) {

		// Unlike UTM Parameter, Channel's default value is already a
		// concrete option (the first CHANNEL_OPTIONS entry), so it starts
		// valid without requiring the user to touch the picker first.

		touched = {customInput: false};
		valid = {customInput: true};
	}
	else if (type === PropertyTypes.SessionUtmParameter) {
		touched = {customInput: false};
		valid = {customInput: false};
	}
	else if (
		[PropertyTypes.SessionNumber, PropertyTypes.SessionText].includes(type)
	) {
		touched = {customInput: false, dateFilter: false};
		valid = {customInput: false, dateFilter: true};
	}
	else if (
		[
			PropertyTypes.AccountSelectText,
			PropertyTypes.AccountNumber,
			PropertyTypes.AccountText,
			PropertyTypes.Duration,
			PropertyTypes.Number,
			PropertyTypes.OrganizationNumber,
			PropertyTypes.OrganizationSelectText,
			PropertyTypes.OrganizationText,
			PropertyTypes.SelectText,
			PropertyTypes.Text,
		].includes(type)
	) {
		valid = false;
	}

	return {
		criterion: {
			defaultValue,
			propertyName: name,
			rowId: generateRowId(),
			touched,
			type,
			valid,
		},
		property,
	};
};

interface ICriteriaSidebarItemProps {
	className?: string;
	connectDragPreview?: ConnectDragPreview;
	connectDragSource: ConnectDragSource;
	defaultValue: any;
	dragging: boolean;
	label: string;
	name: string;
	property: Property;
	propertyKey: string;
	type: string;
}

export class CriteriaSidebarItem extends React.Component<ICriteriaSidebarItemProps> {
	componentDidMount() {
		const {connectDragPreview} = this.props;

		if (connectDragPreview) {
			connectDragPreview(getEmptyImage(), {captureDraggingState: true});
		}
	}

	render() {
		const {
			className,
			connectDragSource,
			dragging,
			label,
			propertyKey,
			type,
		} = this.props;

		const classes = getCN(
			'align-items-center c-gap-3 criteria-sidebar-item-root d-flex mb-1 mx-4 px-2 py-1 text-3',
			{dragging},
			className
		);

		return connectDragSource(
			<li className={classes} data-testid={`criteria-item-${label}`}>
				<ClaySticker
					className="criteria-sidebar-item-sticker flex-shrink-0"
					size="sm"
					style={getStickerStyle(propertyKey)}
				>
					<ClayIcon symbol={getTypeIcon(type)} />
				</ClaySticker>

				<span className="autofit-col-expand">{label}</span>
			</li>
		);
	}
}

export default dragSource(
	DragTypes.Property,
	{
		beginDrag,
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging(),
	})
)(CriteriaSidebarItem);
