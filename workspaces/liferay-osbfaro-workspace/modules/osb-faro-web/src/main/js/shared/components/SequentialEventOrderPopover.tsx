import ClayPopover from '@clayui/popover';
import ClaySticker from '@clayui/sticker';
import React, {useState} from 'react';

const SequentialEventOrderPopover: React.FC = () => {
	const [show, setShow] = useState(false);

	return (
		<ClayPopover
			alignPosition="top"
			header={Liferay.Language.get('sequential-event-order')}
			onShowChange={setShow}
			show={show}
			trigger={
				<span
					className="ml-2"
					onMouseEnter={() => setShow(true)}
					onMouseLeave={() => setShow(false)}
				>
					<ClaySticker displayType="info" shape="circle" size="sm">
						{'S'}
					</ClaySticker>
				</span>
			}
		>
			{Liferay.Language.get(
				'when-this-is-enabled,the-second-event-must-come-after-the-first-event,-with-any-number-of-events-in-between'
			)}
		</ClayPopover>
	);
};

export default SequentialEventOrderPopover;
