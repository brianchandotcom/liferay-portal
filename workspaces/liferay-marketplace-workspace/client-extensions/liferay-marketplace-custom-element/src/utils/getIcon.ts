import {getThumbnailByProductAttachment} from './util';

const getIconUrl = (product?: DeliveryProduct) => {
	const iconURL = product
		? getThumbnailByProductAttachment(product.images)?.split('/o/')
		: '';

	return iconURL ? `/o/${iconURL[1]}` : '';
};

export {getIconUrl};