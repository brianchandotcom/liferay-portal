import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import getCN from 'classnames';
import React from 'react';
import {Link} from 'react-router-dom';

interface IToolbarProps {
	backURL: string;
	children?: React.ReactNode;
	className?: string;
	title: string;
}

const Item = ClayToolbar.Item;

const Divider: React.FC = () => (
	<ClayToolbar.Item
		className="align-self-stretch border-left my-1 p-0"
		data-testid="toolbar-divider"
	/>
);

const Cancel: React.FC<{href: string}> = ({href}) => (
	<ClayToolbar.Item>
		<ClayLink
			borderless
			button
			className="rounded-lg"
			displayType="secondary"
			href={href}
			small
		>
			{Liferay.Language.get('cancel')}
		</ClayLink>
	</ClayToolbar.Item>
);

interface ISaveProps {
	disabled?: boolean;
	label: string;
	onClick?: () => void;
	type?: 'button' | 'submit';
}

const Save: React.FC<ISaveProps> = ({
	disabled = false,
	label,
	onClick,
	type = 'button',
}) => (
	<ClayToolbar.Item>
		<ClayButton
			className="rounded-lg"
			disabled={disabled}
			displayType="primary"
			onClick={onClick}
			size="sm"
			type={type}
		>
			{label}
		</ClayButton>
	</ClayToolbar.Item>
);

const Toolbar: React.FC<IToolbarProps> & {
	Cancel: typeof Cancel;
	Divider: typeof Divider;
	Item: typeof Item;
	Save: typeof Save;
} = ({backURL, children, className, title}) => (
	<ClayToolbar className={getCN('align-items-center bg-white', className)}>
		<ClayToolbar.Nav className="align-items-center mx-3">
			<ClayToolbar.Item>
				<Link
					aria-label={Liferay.Language.get('back')}
					className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm rounded-lg"
					data-tooltip-align="bottom"
					title={Liferay.Language.get('back')}
					to={backURL}
				>
					<ClayIcon symbol="angle-left" />
				</Link>
			</ClayToolbar.Item>

			<ClayToolbar.Item className="pl-0">
				<ClayToolbar.Section>
					<h1 className="font-weight-semi-bold m-0 text-5 text-dark text-nowrap">
						{title}
					</h1>
				</ClayToolbar.Section>
			</ClayToolbar.Item>

			<ClayToolbar.Item expand />

			{children}
		</ClayToolbar.Nav>
	</ClayToolbar>
);

Toolbar.Cancel = Cancel;
Toolbar.Divider = Divider;
Toolbar.Item = Item;
Toolbar.Save = Save;

export default Toolbar;
