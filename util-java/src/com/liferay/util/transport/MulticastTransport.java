/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

	public synchronized void disconnect() {

		// Interrupt all processing

		if (_address != null) {
			try {
				_socket.leaveGroup(_address);
				_address = null;
			}
			catch (IOException e) {
				_log.error("Unable to leave group", e);
			}
		}

		_connected = false;

		interrupt();

		_socket.close();
	}

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
		catch (IOException e) {
			_log.error("Unable to process ", e);

			_socket.disconnect();

			_connected = false;

			_handler.errorReceived(e);
		}
	}

	public synchronized void sendMessage(String msg) throws IOException {
		_outboundPacket.setData(msg.getBytes());
		_outboundPacket.setAddress(_address);
		_outboundPacket.setPort(_port);

		_socket.send(_outboundPacket);
	}

	private static Log _log = LogFactory.getLog(MulticastTransport.class);

	private byte[] _inboundBuffer = new byte[4096];
	private DatagramPacket _inboundPacket =
		new DatagramPacket(_inboundBuffer, _inboundBuffer.length);
	private byte[] _outboundBuffer = new byte[4096];
	private DatagramPacket _outboundPacket =
		new DatagramPacket(_outboundBuffer, _outboundBuffer.length);
	private String _host;
	private DatagramHandler _handler;
	private int _port;
	private boolean _connected;
	private MulticastSocket _socket;
	private InetAddress _address;

}