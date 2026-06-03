import {PropTypes} from 'prop-types';

export const HOC_CARD_PROPTYPES = {
	/**
	 * Filters
	 * @type {object}
	 */
	filters: PropTypes.object.isRequired,

	/**
	 * Router
	 * @type {object}
	 */
	router: PropTypes.object.isRequired
};
