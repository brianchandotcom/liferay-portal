// routes/selfRoutes.js

import express from "express";
import url from 'url';
import multer from 'multer';
import config from '../util/configTreePath.js';
import {logger} from "../util/logger.js";
import {headless_admin_userService} from "../headless-wrapper/headless-admin-user.js";
import {getServerToken} from "../util/silent-authorization.js";
import {chatmessagesService} from "../headless-wrapper/chatmessages.js";
import {uploadFileWithMetadata} from "../util/attachment-util.js";
import MemoryStreams from 'memory-streams';
import FormData from 'form-data';
import {headless_deliveryService} from "../headless-wrapper/headless-delivery.js";
import cache from 'memory-cache';
import axios from 'axios';
const storage = multer.memoryStorage(); // Store the uploaded file in memory
const upload = multer({ storage });
const router = express.Router();




router.get('/contacts',  async (req, res) => {
    try {
        let cacheKey = `contacts`;
        let cachedData = cache.get(cacheKey);
        if (cachedData)
        {
            res.status(200).json(cachedData);
        }else
        {
            const {companyId, languageId, scopeKey, userId, page, pageSize} = req.query;
            const token = await getServerToken(req.user.id);
            let usersService = new headless_admin_userService(`Bearer ${token}`);
            let result = await usersService.getUserAccountsPage("", 0);
            cache.put(cacheKey,result,(1 * 1000 * 60) * 30);
            res.status(200).json(result);
        }

    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message});
    }
});
router.post('/messages',  async (req, res) => {
    try {
        const {clientID, page} = req.query;
        let userId = req.user.id;
        const requestBody = req.body;
        let sort = "dateCreated%3Adesc";
        let filter = encodeURIComponent(`(toClientId eq '${clientID}' and fromClientId eq '${userId}') or (toClientId eq '${userId}' and fromClientId eq '${clientID}')`);
        const token = await getServerToken(req.user.id);
        let chatObject = new chatmessagesService(`${token}`);
        let result = await chatObject.postChatMessage(requestBody);

        res.status(200).json(result);
    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message});
    }
});
router.get('/messages',  async (req, res) => {
    try {
        const {clientID, page} = req.query;
        let userId = req.user.id;
        let sort = "dateCreated%3Adesc";
        let filter = encodeURIComponent(`(toClientId eq '${clientID}' and fromClientId eq '${userId}') or (toClientId eq '${userId}' and fromClientId eq '${clientID}')`);
        const token = await getServerToken(req.user.id);
        let chatObject = new chatmessagesService(`${token}`);
        let result = await chatObject.getChatMessagesPage(null,null,null,null
            ,null,filter,page,null,null,sort);

        res.status(200).json(result);
    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message});
    }
});
router.get('/messages/:messageId',  async (req, res) => {
    try {
        let cacheKey = `message_id_${req.params.messageId}`;
        let cachedData = cache.get(cacheKey);
        if (cachedData)
        {
            res.status(200).json(cachedData);
        }else
        {
            const {clientID, page} = req.query;
            const messageId = req.params.messageId;
            let userId = req.user.id;
            const token = await getServerToken(req.user.id);
            let chatObject = new chatmessagesService(`${token}`);
            let result = await chatObject.getChatMessage(messageId);
            cache.put(cacheKey,result,(1 * 1000 * 60) * 30);
            res.status(200).json(result);
        }

    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message});
    }
});
router.post('/messages/attach/:siteId', upload.single('file'), async (req, res) => {
    try {
        const uploadedFile = req.file;
        if (!uploadedFile) {
            return res.status(400).send('No file uploaded.');
        }
        const bufferStream = new MemoryStreams.ReadableStream(uploadedFile.buffer);

        const siteId = req.params.siteId;
        let userId = req.user.id;
        //building the file object
        let formData = new FormData();
        formData.append('file', uploadedFile.buffer, {
            filename: uploadedFile.originalname,
            contentType: uploadedFile.mimetype,
        });

        let document = await uploadFileWithMetadata(userId,siteId,formData);
        res.status(200).json(document);
    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message,stackTrace:e.stack});
    }
});
router.get('/messages/attachment/download', async (req, res) => {
    try {

        // Get the file URL and auth token from query parameters
        const fileUrl = req.query.fileUrl;

        // Check if the file URL and auth token are provided
        if (!fileUrl) {
            return res.status(400).json({ error: 'Both fileUrl and authToken are required.' });
        }
        let authToken = getServerToken(req.user.id);
        // Create an axios instance with the provided auth token
        const axiosInstance = axios.create({
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });

        // Make a GET request to the file URL with the auth token
        const response = await axiosInstance.get(fileUrl, {
            responseType: 'stream', // Stream the file response
        });

        // Set the appropriate headers for the response
        res.setHeader('Content-Disposition', `attachment; filename="${response.headers['content-disposition']}"`);
        res.setHeader('Content-Type', response.headers['content-type']);

        // Stream the file data to the client
        response.data.pipe(res);
    } catch (error) {
        console.error('Error while downloading file:', error.message);
        console.error(error);
        res.status(500).json({ error: 'Internal server error.' });
    }
});
router.get('/messages/attachment/:fileId', async (req, res) => {
    try {
        let cacheKey = `file_id_${req.params.fileId}`;
        let cachedData = cache.get(cacheKey);
        if (cachedData)
        {
            res.status(200).json(cachedData);
        }else
        {
            const {clientID, page} = req.query;
            const fileId = req.params.fileId;
            let userId = req.user.id;
            const token = await getServerToken(req.user.id);
            let deliveryService = new headless_deliveryService(`${token}`);
            let result = await deliveryService.getDocument(fileId);
            cache.put(cacheKey,result,(1 * 1000 * 60) * 30);
            res.status(200).json(result);
        }

    }catch (e) {
        console.log(e.message);
        res.status(500).json({error:e.message});
    }
});

export default router;
