/* global Liferay */

import moment from "moment";

export function showError(title, message) {
    Liferay.Util.openToast({message, title, type: 'danger'});
}

export function showSuccess(
    title,
    message = 'The request has been successfully completed.'
) {
    Liferay.Util.openToast({message, title, type: 'success'});
}

export function showWarning(
    title,
    message = 'The request has been successfully completed.'
) {
    Liferay.Util.openToast({message, title, type: 'warning'});
}

export function getSelectedLanguage(){
    return Liferay.ThemeDisplay.getLanguageId();
}

export function getUserId(){
    return Liferay.ThemeDisplay.getUserId();
}

export function getCurrentSite(){

    return Liferay.ThemeDisplay.getSiteGroupId();

}
