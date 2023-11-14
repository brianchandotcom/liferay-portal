// routes/selfRoutes.js

import express from "express";
import {start,startCustom} from '../util/folder-structure.js'
import config from "../util/configTreePath.js";

const router = express.Router();
const templateId = config['templateId']
router.post('/hr/employee/folder',   (req, res) => {

    let {userName,userId} = req.body;
    let data = req.body;
    const employeeId = userName;
    const templateId = templateId;
    let task = ()=>{
        start(employeeId,templateId);
    };
    setTimeout(async () => {
        await task();
    }, 3000);

    res.status(200).json(req.body.objectEntry);
});
router.post('/create/folder/:employeeId/:templateId',   (req, res) =>{
    const employeeId = req.params.employeeId;
    const templateId = req.params.templateId;
    console.log(req.params);
    let task = ()=>{
        start(employeeId,templateId);
    };
    setTimeout(async () => {
        await task();
    }, 3000);

    res.status(200).json({});
});
router.post('/create/folder/direct/:templateId/:containerId/:rootName',   (req, res) =>{
    const containerId = req.params.containerId;
    const templateId = req.params.templateId;
    const rootName = req.params.rootName;
    console.log(req.params);
    let task = ()=>{
        startCustom(rootName,templateId,containerId);
    };
    setTimeout(async () => {
        await task();
    }, 3000);

    res.status(200).json({});
});
export default router;
