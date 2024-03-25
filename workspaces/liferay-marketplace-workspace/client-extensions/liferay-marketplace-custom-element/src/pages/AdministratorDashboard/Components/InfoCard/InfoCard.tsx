import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import Button from '@clayui/button';

type InfoCard = {
	title: string;
	value: string | number;
	growth?: number;
	className?: string;
	growthContext: string;
	symbol: string;
	dropDownItems?: any[];
	onSelectDropDown?: any;
};

const InfoCard: React.FC<InfoCard> = ({
	className,
	onSelectDropDown,
	dropDownItems,
	growth = 0,
	growthContext,
	symbol,
	title,
	value,
}) => {
	return (
		<div
			className={classNames(
				`p-4 d-flex flex-column justify-content-between info-card ${className}`
			)}
		>
			<div className="d-flex justify-content-between align-content-center mb-2">
				<span className="d-flex flex-column">
					<div className="d-flex">
						<span className="font-weight-lighter text-black-50 mb-2 mr-2">
							{title}
						</span>
						{dropDownItems?.length && onSelectDropDown && (
							<DropDown
								closeOnClick
								filterKey="name"
								trigger={
									<Button
										aria-label="dropdown"
										displayType={'unstyled'}
									>
										<ClayIcon
											symbol={'caret-bottom'}
											className="text-primary"
										/>
									</Button>
								}
							>
								<DropDown.ItemList items={dropDownItems}>
									{(item) => (
										<DropDown.Item
											key={item as string}
											onClick={() => {
												onSelectDropDown(item);
											}}
										>
											{item as string}
										</DropDown.Item>
									)}
								</DropDown.ItemList>
							</DropDown>
						)}
					</div>
					<span className="font-weight-bold h2">{value}</span>
				</span>
				<span className="d-flex flex-column justify-content-center">
					<ClayIcon
						symbol={symbol}
						fontSize={32}
						className="text-primary"
					/>
				</span>
			</div>

			<div className="font-weight-bold text-black-50">
				<span
					className={classNames('mr-2', {
						'text-success': growth > 0,
						'text-danger': growth < 0,
					})}
				>
					<ClayIcon
						symbol={
							growth > 0 ? 'order-arrow-up' : 'order-arrow-down'
						}
						className="mr-21"
					/>
					{growth}%
				</span>
				<span>{growthContext}</span>
			</div>
		</div>
	);
};

export default InfoCard;
