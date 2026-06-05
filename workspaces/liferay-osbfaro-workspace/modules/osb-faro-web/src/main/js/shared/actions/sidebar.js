export const actionTypes = {
	COLLAPSE_SIDEBAR: 'COLLAPSE_SIDEBAR'
};

export function collapseSidebar(payload) {
	return {
		payload,
		type: actionTypes.COLLAPSE_SIDEBAR
	};
}
