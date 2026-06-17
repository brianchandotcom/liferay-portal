import ClayIcon from '@clayui/icon';
import ClayToolbar from '@clayui/toolbar';
import React from 'react';
import {Link} from 'react-router-dom';
import {Text} from '@clayui/core';

interface IToolbarProps {
	backURL: {
		url: string;
		label: string;
	};
}

const Toolbar: React.FC<IToolbarProps> = ({backURL}) => (
	<ClayToolbar className="bg-white mb-4">
		<ClayToolbar.Nav className="mx-4">
			<ClayToolbar.Item>
				<div className="component-action">
					<Link
						aria-label={Liferay.Language.get('back')}
						className="text-secondary"
						to={backURL.url}
					>
						<ClayIcon className="mb-1" symbol="angle-left" />
					</Link>
				</div>
			</ClayToolbar.Item>

			<ClayToolbar.Item className="pl-0">
				<ClayToolbar.Section>
					<span className="text-dark">
						<Text size={5} weight="bold">
							{backURL.label}
						</Text>
					</span>
				</ClayToolbar.Section>
			</ClayToolbar.Item>
		</ClayToolbar.Nav>
	</ClayToolbar>
);

export default Toolbar;
