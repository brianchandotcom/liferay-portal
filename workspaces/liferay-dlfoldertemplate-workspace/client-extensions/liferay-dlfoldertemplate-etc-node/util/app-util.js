export function keepLettersAndDigits(inputString) {
    return inputString.replace(/[^a-zA-Z0-9]/g, '');
}
