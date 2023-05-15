import { Liferay } from './liferay';

const { REACT_APP_LIFERAY_HOST = window.location.origin } = process.env;

const baseFetch = async (url, options = {}) => {
    // eslint-disable-next-line @liferay/portal/no-global-fetch
    return fetch(REACT_APP_LIFERAY_HOST + '/' + url, {
        headers: {
            'Content-Type': 'application/json',
            'x-csrf-token': Liferay.authToken,
        },
        ...options,
    });
};

export default baseFetch;
