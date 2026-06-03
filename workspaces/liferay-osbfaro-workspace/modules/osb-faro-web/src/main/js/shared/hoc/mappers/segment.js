/**
 * Map Segment Growth API response for use in a Component.
 * @param {Array} growth - The Segment Growth history data.
 * @returns {Object} - The remapped Segment Growth history.
 */
export function mapGrowthHistory(growth) {
	return {
		data: growth.map(item => ({
			added: item.addedIndividualsCount,
			anonymousCount: item.anonymousIndividualsCount,
			knownCount: item.knownIndividualsCount,
			modifiedDate: item.intervalInitDate,
			removed: item.removedIndividualsCount,
			value: item.individualsCount
		}))
	};
}
