/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.bridges.jsf.icefaces;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.component.inputfile.InputFile;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.bridges.jsf.common.FacesMessageUtil;

import java.text.DecimalFormat;

import java.util.EventObject;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * This class is a managed bean that is designed specifically to work with the
 * ICEfaces framework, by utilizing the <code><ice:inputFile/></code> component.
 *
 * @author Neil Griffin
 */
public class FileUploadManagedBean implements Renderable {

	public FileUploadManagedBean() {
		_state = PersistentFacesState.getInstance();
	}

	public void actionListener(ActionEvent actionEvent) {
		InputFile inputFile = (InputFile)actionEvent.getSource();

		int status = inputFile.getStatus();

		try {
			if (status == InputFile.INVALID) {
				addErrorMessage("file-type-is-invalid");

				_percent = 100;
			}
			else if (status == InputFile.SAVED) {
				_percent = 100;
			}
			else if (status == InputFile.SIZE_LIMIT_EXCEEDED) {
				long maxFileSizeInBytes = _inputFile.getSizeMax();

				DecimalFormat decimalFormat = new DecimalFormat();

				decimalFormat.setGroupingUsed(false);
				decimalFormat.setMaximumFractionDigits(2);
				decimalFormat.setMinimumFractionDigits(0);

				String maxFileSizeInMegs = decimalFormat.format(
					(double)maxFileSizeInBytes / 1024 / 1024);

				addErrorMessage(
					"file-size-is-larger-than-x-megabytes", maxFileSizeInMegs);

				_percent = 100;
			}
			else if (status == InputFile.UNKNOWN_SIZE) {
				addErrorMessage("file-size-was-not-specified-in-the-request");

				_percent = 100;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			addErrorMessage(e.getMessage());
		}
	}

	public InputFile getInputFile() {
		return _inputFile;
	}

	public int getPercent() {
		return _percent;
	}

	@Override
	public PersistentFacesState getState() {
		return _state;
	}

	public boolean isComplete() {
		if (_percent == 100) {
			return true;
		}
		else {
			return false;
		}
	}

	public void progressListener(EventObject eventObject) {
		InputFile inputFile = (InputFile)eventObject.getSource();

		_percent = inputFile.getFileInfo().getPercent();

		_renderManager.requestRender(this);
	}

	@Override
	public void renderingException(RenderingException renderingException) {
		_log.error(renderingException.getMessage());
	}

	public void setInputFile(InputFile inputFile) {
		_inputFile = inputFile;
	}

	public void setPercent(int percent) {
		_percent = percent;
	}

	public void setRenderManager(RenderManager renderManager) {
		_renderManager = renderManager;
	}

	protected void addErrorMessage(String key) {
		addErrorMessage(key, null);
	}

	protected void addErrorMessage(String key, String argument) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (_inputFile == null) {
			FacesMessageUtil.error(facesContext, key, argument);
		}
		else {
			FacesMessageUtil.error(
				_inputFile.getClientId(facesContext), facesContext, key,
				argument);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FileUploadManagedBean.class);

	private InputFile _inputFile;
	private int _percent;
	private RenderManager _renderManager;
	private PersistentFacesState _state;

}