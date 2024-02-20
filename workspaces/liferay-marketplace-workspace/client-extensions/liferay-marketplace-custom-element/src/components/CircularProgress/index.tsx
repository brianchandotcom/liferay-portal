import ClayIcon from '@clayui/icon';
import {CircularProgressbarWithChildren} from 'react-circular-progressbar';

type CircularProgressProps = {
	fontSize: number;
	height: number;
	pathColor: string;
	progress: number;
	progressColor: string;
	width: number;
};

const CircularProgress: React.FC<CircularProgressProps> = ({
	fontSize,
	height,
	pathColor,
	progress,
	progressColor,
	width,
}) => {
	return (
		<CircularProgressbarWithChildren
			styles={{
				path: {
					stroke: progressColor,
					strokeLinecap: 'round',
					transition: 'all ease-in-out 0.5s',
				},
				trail: {
					stroke: pathColor,
					strokeLinecap: 'round',
					transition: 'all ease-in-out 0.5s',
				},
				root: {
					height,
					width,
				},
			}}
			value={progress}
		>
			<div style={{fontSize: fontSize}}>
				<strong>{`${progress}%`}</strong>
			</div>
		</CircularProgressbarWithChildren>
	);
};

export default CircularProgress;
