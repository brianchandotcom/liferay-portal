/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.util.transport;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * The MulticastTransport will send strings across a specified multicast
 * address. It will also listen for messages and hand them to the appropriate
 * DatagramHandler.
 * </p>
 *
 * @author Michael C. Han
 */
public class MulticastTransport extends Thread implements Transport {

	public MulticastTransport(DatagramHandler handler, String host, int port) {
		super("MulticastListener-" + host + port);

		setDaemon(true);
		_handler = handler;
		_host = host;
		_port = port;
	}

	@Override
	public synchronized void connect() throws IOException {
		if (_socket == null) {
			_socket = new MulticastSocket(_port);
		}
		else if (_socket.isConnected() && _socket.isBound()) {
			return;
		}

		_address = InetAddress.getByName(_host);

		_socket.joinGroup(_address);

		_connected = true;

		start();
	}

	@Override
	public synchronized void disconnect() {

		// Interrupt all processing

		if (_address != null) {
			try {
				_socket.leaveGroup(_address);
				_address = null;
			}
			catch (IOException ioe) {
				_log.error("Unable to leave group", ioe);
			}
		}

		_connected = false;

		interrupt();

		_socket.close();
	}

	@Override
	public boolean isConnected() {
		return _connected;
	}

	@Override
	public void run() {
		try {
			while (_connected) {
				_socket.receive(_inboundPacket);
				_handler.process(_inboundPacket);
			}
		}
		catch (IOException ioe) {
			if (!_connected) {
				return;
			}

			_log.error("Unable to process ", ioe);

			_socket.disconnect();

			_connected = false;

			_handler.errorReceived(ioe);
		}
	}

	public synchronized void sendMessage(byte[] bytes) throws IOException {
		_outboundPacket.setData(bytes);
		_outboundPacket.setAddress(_address);
		_outboundPacket.setPort(_port);

		_socket.send(_outboundPacket);
	}

	@Override
	public synchronized void sendMessage(String message) throws IOException {
		sendMessage(message.getBytes());
	}

	private static final Log _log = LogFactory.getLog(MulticastTransport.class);

	private InetAddress _address;
	private boolean _connected;
	private final DatagramHandler _handler;
	private final String _host;
	private final byte[] _inboundBuffer = new byte[4096];
	private final DatagramPacket _inboundPacket = new DatagramPacket(
		_inboundBuffer, _inboundBuffer.length);
	private final byte[] _outboundBuffer = new byte[4096];
	private final DatagramPacket _outboundPacket = new DatagramPacket(
		_outboundBuffer, _outboundBuffer.length);
	private final int _port;
	private MulticastSocket _socket;

}