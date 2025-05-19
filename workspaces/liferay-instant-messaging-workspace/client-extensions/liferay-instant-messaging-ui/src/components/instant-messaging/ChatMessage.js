import {forwardRef, useState} from "react";
import {ChatMessageType} from "../../utils/constants";
import ClayAlert from "@clayui/alert";

const ChatMessage = forwardRef((props, ref) => {

    const {message,self} = props;

    const [error, setError] = useState(false);

    const handleError = (error) => {

        setError(true);

    }

    return (<>
        {message.chatMessageType === ChatMessageType.TEXT && (
            <div className={`chat-bubble ${self.userId == message.from ? 'sent' : 'received'}`}>

             {message.message}

            </div>
        )}

        {message && message.chatMessageType === ChatMessageType.FILE  && error && (
            <>
                <ClayAlert displayType="danger"  title="Error" role={null}>
                    An error occurred while loading the file: {message.file.fileName}.
                </ClayAlert>
            </>
        )}

        {message.chatMessageType === ChatMessageType.FILE && !error && (
            <div className={`chat-bubble ${self.userId == message.from ? 'sent' : 'received'}`}>
                <a href={message.file.fileUrl}>
                    <img onError={handleError} alt={message.file.fileName} className={"w-100 bg-white"} src={message.file.preview}/>
                </a>
            </div>
        )}
    </>)

});

export default ChatMessage;
