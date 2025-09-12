package com.clarity.solution;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

/**
 * Manages the queue of account creation requests.
 *
 * @author dnebing
 */
@Service
public class AccountCreationRequestQueueManager {

	public void awaitWork() throws InterruptedException {
		_lock.lock();

		try {
			while (isEmpty()) {
				_notEmpty.await();
			}
		}
		finally {
			_lock.unlock();
		}
	}

	public AccountCreationRequest dequeue() throws InterruptedException {
		return _queue.take();
	}

	public void enqueue(AccountCreationRequest accountCreationRequest) {
		_queue.add(accountCreationRequest);

		_signalExecutor();
	}

	public boolean isEmpty() {
		return _queue.isEmpty();
	}

	private void _signalExecutor() {
		_lock.lock();

		try {
			_notEmpty.signal();
		}
		finally {
			_lock.unlock();
		}
	}

	private final ReentrantLock _lock = new ReentrantLock();
	private final Condition _notEmpty = _lock.newCondition();
	private final BlockingQueue<AccountCreationRequest> _queue =
		new LinkedBlockingQueue<>();

}