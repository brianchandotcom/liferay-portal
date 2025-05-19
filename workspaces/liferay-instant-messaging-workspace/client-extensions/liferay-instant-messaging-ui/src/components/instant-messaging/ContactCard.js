import {forwardRef, useEffect} from "react";
import ClaySticker from "@clayui/sticker";
import ClayList from '@clayui/list';
import ClayBadge from "@clayui/badge";


const ContactCard = forwardRef((props, ref) => {

    const {detail,handleClick,online,notifications} = props;

    const getInitials = (name) =>  {
        if (!name) return "";

        const words = name.trim().split(/\s+/);

        if (words.length === 1) {
            return words[0][0].toUpperCase();
        }

        const initials = words.map(word => word[0].toUpperCase()).join("");

        return initials.slice(0, 2);
    }

    useEffect(()=>{

        console.log(detail);

    },[])


    return (
        <>
            {detail && (
                <>
                    <ClayList.Item onClick={handleClick} flex>
                        <ClayList.ItemField>
                            <div>

                                {detail.type != "bot" &&
                                    <ClayBadge className={"notification-badge"} displayType="info" label={notifications} />
                                }

                                <ClaySticker displayType={'light'} className={detail.type === "bot"?"bg-success": online ? 'bg-success' : ''}  size="xl">
                                    {getInitials(detail.name)}
                                </ClaySticker>
                            </div>

                        </ClayList.ItemField>
                        <ClayList.ItemField expand>
                            <ClayList.ItemTitle className={"m-0"}>
                                <label data-id={detail.userId} className={"text-capitalize m-0"}>{detail.name}</label>
                            </ClayList.ItemTitle>
                            <ClayList.ItemText className={"m-0"}>
                                {detail.type === "bot"?"Online": online ? 'Online' : 'Offline'}
                            </ClayList.ItemText>
                        </ClayList.ItemField>
                    </ClayList.Item>
                </>
            )}
        </>
    );


});

export default ContactCard;


