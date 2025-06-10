import {forwardRef, useCallback, useEffect, useImperativeHandle, useState} from "react";
import {ClayDualListBox} from "@clayui/form";

const RolesSelector = forwardRef((props, ref) => {

    let {roles,selectedRoles,callback} = props;
    const [items, setItems] = useState();
    const [leftSelected, setLeftSelected] = useState([]);
    const [rightSelected, setRightSelected] = useState([]);


    const getLiferayRolesList =  () => {

        let allRoles = roles.map(item=> {
            return{
                label:item.name,
                value:item.name
            }
        });

        if(selectedRoles  && selectedRoles.length > 0){

            let selectedRolesSet = new Set(selectedRoles);

            allRoles = allRoles.filter(item=> !selectedRolesSet.has(item.value));
        }

        selectedRoles = selectedRoles.map(item=> {
            return {
            label:item,
            value:item
        }});

        setItems([selectedRoles,allRoles]);

    }


    useImperativeHandle(ref, () => ({
        handleGetSelectedRole,
    }));

    const handleGetSelectedRole = () => {
        const [selected,available] = items;
        return selected.map(item=>item.value);

    }

    useEffect(() => {
        getLiferayRolesList()
    }, [roles]);

    useEffect(()=>{

        callback();

    },[items])

    return <>
        {items && items.length > 0 && (
            <ClayDualListBox
                items={items}
                left={{
                    label: "Selected Roles",
                    onSelectChange: setLeftSelected,
                    selected: leftSelected
                }}
                onItemsChange={setItems}
                right={{
                    label: "Available Roles",
                    onSelectChange: setRightSelected,
                    selected: rightSelected
                }}
                size={8}
            />
        )}
    </>

});

export default RolesSelector;
