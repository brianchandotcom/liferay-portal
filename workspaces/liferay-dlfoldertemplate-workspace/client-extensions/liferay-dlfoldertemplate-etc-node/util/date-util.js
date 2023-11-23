import moment from 'moment';

const dateFormats = [
    'YYYY-MM-DD',
    'DD/MM/YYYY',
    'MM/DD/YYYY',
    'YYYY/MM/DD',
    'DD.MM.YYYY',
    'MMMM D, YYYY',
    'D MMMM YYYY',
    'YYYY-MM-DD HH:mm:ss',
    'YYYY/MM/DD HH:mm:ss',
    'DD/MM/YYYY HH:mm',
    'MM/DD/YYYY HH:mm',
    'D MMMM YYYY HH:mm:ss',
    'D MMMM YYYY HH:mm',
    'YYYY-MM-DDTHH:mm:ss',
    'YYYY-MM-DDTHH:mm:ss.SSSZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZZZ',
    'DD/MM/YYYY h:mm A',
    'MM/DD/YYYY h:mm A',
    'D MMMM YYYY h:mm A',
    'YYYY-MM-DD HH:mm',
    'YYYY-MM-DDTHH:mm',
    'YYYY-MM-DDTHH:mm:ssZ',
    'YYYY-MM-DDTHH:mm:ss.SSS',
    'YYYY-MM-DDTHH:mm:ss.SSSZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZZ',
    'YYYY-MM-DDTHH:mm:ss.SSSZZZZ',
];

export function parseDate(dateString) {
    for (const format of dateFormats) {
        const parsedDate = moment(dateString, format);
        if (parsedDate.isValid()) {
            return parsedDate.toISOString();
        }
    }
    // If none of the formats worked, return null or handle the error as needed
    return null;
}
export function validateDate(dateString) {
    for (const format of dateFormats) {
        const parsedDate = moment(dateString, format);
        if (parsedDate.isValid()) {
            return true;
        }
    }
    // If none of the formats worked, return null or handle the error as needed
    return false;
}

