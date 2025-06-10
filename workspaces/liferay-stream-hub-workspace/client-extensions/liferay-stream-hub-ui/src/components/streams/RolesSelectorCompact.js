import {forwardRef, useCallback, useEffect, useImperativeHandle, useState} from "react";
import {ClaySelectBox} from "@clayui/form";

const RolesSelectorCompact = forwardRef((props, ref) => {

    let {roles,selectedRoles,callback} = props;
    const [items, setItems] = useState();
    const [value, setValue] = useState([]);

    const getLiferayRolesList =  () => {

        let allRoles = roles.map(item=> {
            return{
                label:item.name,
                value:item.name
            }
        });

        setItems(allRoles);

        setValue(selectedRoles);


    }

    const handleSetSelectedRoles = (roles) =>{

        setValue(roles);

    }

    useImperativeHandle(ref, () => ({
        handleGetSelectedRole,
        handleSetSelectedRoles
    }));

    const handleGetSelectedRole = () => {

        return value;
    }

    useEffect(() => {
        getLiferayRolesList()
    }, [roles]);

    useEffect(()=>{

        callback();

    },[value])

    return <>
        {items && items.length > 0 && (
            <ClaySelectBox
                items={items}
                label="Select Target Roles"
                multiple
                onItemsChange={setItems}
                onSelectChange={setValue}
                value={value}
            />
        )}
    </>

});

export default RolesSelectorCompact;
