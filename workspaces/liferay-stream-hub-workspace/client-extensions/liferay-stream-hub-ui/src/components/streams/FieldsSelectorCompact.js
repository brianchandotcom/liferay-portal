import {forwardRef, useCallback, useEffect, useImperativeHandle, useState} from "react";
import {ClayDualListBox, ClaySelectBox} from "@clayui/form";
import {getUserRoles} from "../../services/object/users";
import {getSelectedLanguage} from "../../utils/util";

const FieldsSelectorCompact = forwardRef((props, ref) => {

    let {fields,selectedFields,callback} = props;
    const [items, setItems] = useState();
    const [value, setValue] = useState([]);

    const getFieldLabel = (localizedLabel,name) => {

        const label = localizedLabel[getSelectedLanguage()];
        return label && label.length > 0 ? label: name;

    }

    const loadSelector =  () => {

        let allFields = fields.map(item=> {
            return{
                label:getFieldLabel(item.label,item.name),
                value:item.name
            }
        });

        setItems(allFields);

        setValue(selectedFields);


    }

    const handleSetSelectedFields = (fields) => {

        setValue(fields);

    }

    useImperativeHandle(ref, () => ({
        handleGetSelectedFields,
        handleSetSelectedFields
    }));

    const handleGetSelectedFields = () => {

        return value;
    }

    useEffect(() => {
        loadSelector()
    }, [fields]);

    useEffect(()=>{

        callback();

    },[value])

    return <>
        {items && items.length > 0 && (
            <ClaySelectBox
                items={items}
                label="Select Message Fields"
                multiple
                onItemsChange={setItems}
                onSelectChange={setValue}
                value={value}
            />
        )}
    </>

});

export default FieldsSelectorCompact;
