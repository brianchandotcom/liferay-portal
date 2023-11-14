import papaparse from 'papaparse';
import {keepLettersAndDigits} from './app-util.js'
import {validateDate, parseDate} from './date-util.js'
export function fileParser(fileContent,jobLog,mapping)
{
    let result = {
        log:jobLog,
        rows:[]
    };
    mapping = mappingArrayToObject(mapping);
    let prom = new Promise((resolve, reject)=>{
        papaparse.parse(fileContent, {
            header: true,
            dynamicTyping: true,
            complete: async function (results) {
                let rows = results.data;
                rows = rows.map((row) => {
                    let newRow = {};
                    let keys = Object.keys(row);
                    for (let index = 0; index < keys.length; index++) {
                        newRow[keepLettersAndDigits(`cF${keys[index]}`.toString())] = row[keys[index]];
                    }
                    return newRow;
                });
                for (let index = 0 ; index < rows.length ; index++)
                {
                    if (isValidRow(rows[index],mapping))
                    {
                        result.rows.push(fixRowValues(rows[index],mapping));
                    }else
                    {
                        console.log(`Row index ${index} is not valid and has been excluded from the data.`);
                        result.log
                            .push
                            (`Row index ${index} is not valid and has been excluded from the data.`);
                    }
                }
                resolve(result)
            },
            error: function (error) {
                result.log
                    .push
                    ('Error parsing CSV:' + error.message);
                resolve(result);
            },
        });
    });
    return prom;
}
function mappingArrayToObject(mapping)
{
    let mappingArr = JSON.parse(mapping);

    let mappingObj = {};
    for (let index = 0 ; index < mappingArr.length ; index++)
    {
        mappingObj[keepLettersAndDigits("cF"+mappingArr[index].fieldId)] = mappingArr[index];
    }
    return mappingObj;
}
function isValidRow(row,mapping)
{
    let rowKeys = Object.keys(row);
    for (let index = 0 ; index < rowKeys.length ; index++)
    {
        let key = rowKeys[index];
        let value = row[key];
        let dbType = mapping[key].value.dbType
        switch (dbType)
        {
            case "String":
            {
                return true;
            }
            case "Date":
            {
                return validateDate(value);
            }
            case "Long":
            {
                return isValidLong(value);
            }
            case "DateTime":
            {
                return validateDate(value);
            }
            case "Integer":
            {
                return true;
            }
            case "BigDecimal":
            {
                return isValidBigDecimal(value);
            }
            case "Double":
            {
                return isValidDouble(value);
            }
            case "Clob":
            {
                return true;
            }
            case "Boolean":
            {
                return isValidBoolean(value);
            }
            case "Clob":
            {
                return true;
            }
            default:
                return false;
        }
    }
}
function fixRowValues(row,mapping)
{
    let rowKeys = Object.keys(row);

    for (let index = 0 ; index < rowKeys.length ; index++)
    {
        let key = rowKeys[index];
        let value = row[key];
        let dbType = mapping[key].value.dbType
        switch (dbType)
        {
            case "String":
            {
                row[key]= value;
                break;
            }
            case "Date":
            {
                row[key]= parseDate(value) ;
                break;
            }
            case "Long":
            {
                row[key]= value;
                break;
            }
            case "DateTime":
            {
                row[key]= parseDate(value);
                break;
            }
            case "Integer":
            {
                row[key]= value;
                break;
            }
            case "BigDecimal":
            {
                row[key]= value;
                break;
            }
            case "Double":
            {
                row[key]= value;
                break;
            }
            case "Clob":
            {
                row[key]= value;
                break;
            }
            case "Boolean":
            {
                row[key]= fixBoolean(value);
                break;
            }
            case "Clob":
            {
                row[key]= value;
                break;
            }
            default:
                console.log('default for fix row values')
                row[key]= "";
        }
    }
    return row;
}

function isValidLong(str) {
    const longPattern = /^-?\d+$/;
    return longPattern.test(str);
}
function isValidBigDecimal(str) {
    const bigDecimalPattern = /^-?\d+(\.\d+)?([eE][-+]?\d+)?$/;
    return bigDecimalPattern.test(str);
}
function isValidDouble(str) {
    const doublePattern = /^-?\d+(\.\d+)?([eE][-+]?\d+)?$/;
    return doublePattern.test(str);
}
function isValidBoolean(str) {
    const lowerCaseStr = str.toLowerCase();
    switch (lowerCaseStr)
    {
        case "yes":
            return true;
        case "true":
            return true;
        case "1":
            return true;
        case "no":
            return true;
        case "false":
            return true;
        case "0":
            return true;
    }
}
function fixBoolean(str) {
    if (str && str.length > 0)
    {
        const lowerCaseStr = str.toLowerCase();
        switch (lowerCaseStr)
        {
            case "yes":
                return true;
            case "true":
                return true;
            case "1":
                return true;
            case "no":
                return false;
            case "false":
                return false;
            case "0":
                return false;
        }
    }
    return false;
}
