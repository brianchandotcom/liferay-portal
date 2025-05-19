import {forwardRef, useEffect, useImperativeHandle, useRef, useState} from "react";
import ClayForm, {ClayInput} from "@clayui/form";
import {ClayButtonWithIcon} from "@clayui/button";
import {showWarning} from "../../utils/util";
import {ChatMessageType, MessageType} from "../../utils/constants";
import ChatMessage from "./ChatMessage";
import {getChatLog} from "../../services/instant-messaging/messages";
import ClayModal, {useModal} from '@clayui/modal';
import AssetSelector from "./AssetSelector";
import ClayIcon from "@clayui/icon";
import ClayLayout from "@clayui/layout";
import ClayLoadingIndicator from "@clayui/loading-indicator";

const MESSAGE_LENGTH = 500;

const ChatWindow = forwardRef((props, ref) => {

    const {target, connection, self} = props;
    const {type} = target;
    const [chatMessageType, setChatMessageType] = useState(ChatMessageType.TEXT);
    const [chatMessages, setChatMessages] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [isThinking, setIsThinking] = useState(false);
    const [message, setMessage] = useState("");
    const {observer, onOpenChange, open} = useModal();

    const handleBotStatus = (status) => {

        const {from, to} = status.data;

        if (from === target.userId && to === self.userId) {

            setIsThinking(status.type === MessageType.BOTSTARTTHINKING);
        }

    }
    const clearNotifications = () => {

        if (connection && connection.OPEN == 1) {

            let messageBody = {
                data: {
                    from: target.userId,
                    to: self.userId
                },
                name: "Chat",
                type: MessageType.MESSAGE_SEEN,
            }

            connection.send(JSON.stringify(messageBody));

        }

    }

    const loadChatLog = async () => {

        let messagesLog = await getChatLog(self.userId, target.userId);
        setChatMessages(messagesLog);
        clearNotifications();

    }

    const handleKeyDown = (event) => {
        if (event.key === 'Enter' && (!isThinking &&  !isLoading && (message) && (message.length > 0))) {
            event.preventDefault();
            handleSendMessage();
        }
    };

    const handleMessageChange = (event) => {

        if (event.target.value.length < MESSAGE_LENGTH) {

            setMessage(event.target.value);

            setChatMessageType(ChatMessageType.TEXT);

        } else {
            showWarning('Message Length', `Your text message cannot exceed ${MESSAGE_LENGTH} characters`)
        }

    }

    const handleSendAttachment = (file) => {

        setIsLoading(true);


        let messageBody = {
            data: {
                from: self.userId,
                to: target.userId,
                file: {
                    preview: `${file.contentUrl}&${file.encodingFormat.startsWith("image") ? "imagePreview=1" : "previewFileIndex=1"}`,
                    fileName: file.fileName,
                    fileUrl: file.contentUrl,
                },
                chatMessageType: ChatMessageType.FILE
            },
            name: "Chat",
            type: MessageType.MASSAGE,
        }

        connection.send(JSON.stringify(messageBody));


        setIsLoading(false);

        onOpenChange(false);

    }

    const handleSendMessage = () => {

        setIsLoading(true);

        if (message.length <= 0) {
            setIsLoading(false);
            return;
        }

        let messageBody = {
            data: {
                from: self.userId,
                to: target.userId,
                message: message,
                chatMessageType: chatMessageType
            },
            name: "Chat",
            type: MessageType.MASSAGE,
        }

        connection.send(JSON.stringify(messageBody));

        setMessage("");

        setIsLoading(false);
    }

    const handleBotStart = async () => {

        let messageBody = {
            data: {
                from: self.userId,
                to: target.userId,
            },
            name: "Chat",
            type: MessageType.START_BOT_CONNECTION,
        }

        connection.send(JSON.stringify(messageBody));

    }

    const handleBotEnd = async () => {

        let messageBody = {
            data: {
                from: self.userId,
                to: target.userId,
            },
            name: "Chat",
            type: MessageType.END_BOT_CONNECTION,
        }

        connection.send(JSON.stringify(messageBody));

    }

    const handleSeen = () => {
        clearNotifications();
    }

    const getChatWindowId = () => {

        return target.userId;

    }

    const handleShowAssetSelector = () => {

        onOpenChange(true);

    }

    const handleIncomingMessage = (message) => {

        let messageFrom = message.data.from;
        let messageTo = message.data.to;
        if ((self.userId == messageFrom || self.userId == messageTo)
            && (target.userId == messageFrom || target.userId == messageTo)) {

            setChatMessages(prev => [...prev, message.data]);

        }


    }

    useImperativeHandle(ref, () => ({
        handleSeen,
        handleIncomingMessage,
        getChatWindowId,
        handleBotStatus
    }));

    useEffect(() => {

        handleBotStart();
        loadChatLog();

        return async () => await handleBotEnd();

    }, []);

    return (
        <>
            <div className="chat-box">
                <div className="chat-container">
                    {chatMessages.sort((a, b) => b.date - a.date).map((chatMessage, index) =>
                        <ChatMessage self={self} key={`msg_${index}`} message={chatMessage}></ChatMessage>)}
                </div>
                <div className="px-2 py-4 bg-light">
                    <ClayForm.Group className="m-0 p-2">
                        <ClayInput.Group>

                            {isThinking && <>
                                <ClayLayout.Container>
                                    <ClayLayout.Row>
                                        <ClayLayout.ContentCol className={"d-flex"} shrink={true}>
                                            <ClayIcon className={"m-auto"}
                                                      style={{fontSize: "2rem", color: "var(--primary)"}}
                                                      symbol={"chatbot"}></ClayIcon>
                                        </ClayLayout.ContentCol>
                                        <ClayLayout.ContentCol className={"d-flex"} expand={true}>
                                            <span className={"my-auto px-2"} style={{
                                                paddingleft: "15px",
                                                paddingRight: "15px"
                                            }}>Bot is thinking...</span>
                                        </ClayLayout.ContentCol>
                                        <ClayLayout.ContentCol className={"d-flex"} shrink={true}>
                                            <ClayLoadingIndicator displayType="primary" shape="squares" size="sm"/>
                                        </ClayLayout.ContentCol>
                                    </ClayLayout.Row>
                                </ClayLayout.Container>
                            </>}

                            <ClayInput.GroupItem>
                                <ClayInput onKeyDown={handleKeyDown} value={message} onChange={handleMessageChange}
                                           placeholder="Type your message here..." type="text"/>
                            </ClayInput.GroupItem>
                            <ClayInput.GroupItem shrink>
                                <ClayButtonWithIcon symbol="reply" title={'send'}
                                                    disabled={isThinking ||  isLoading || (!message) || (message.length <= 0)}
                                                    onClick={handleSendMessage}>
                                </ClayButtonWithIcon>
                            </ClayInput.GroupItem>


                            {type != "bot" && (
                                <ClayInput.GroupItem shrink>
                                    <ClayButtonWithIcon symbol="paperclip" title={'send'}
                                                        disabled={isLoading || (message) || (message.length > 0)}
                                                        onClick={handleShowAssetSelector}>
                                    </ClayButtonWithIcon>
                                </ClayInput.GroupItem>
                            )}

                        </ClayInput.Group>
                    </ClayForm.Group>
                </div>
            </div>

            {open && (
                <ClayModal
                    observer={observer}
                    size="full-screen"

                    status="info"
                >
                    <ClayModal.Header>Documents</ClayModal.Header>
                    <ClayModal.Body>
                        <AssetSelector handleSendAttachment={handleSendAttachment}></AssetSelector>
                    </ClayModal.Body>

                </ClayModal>
            )}
        </>
    );

});

export default ChatWindow;
