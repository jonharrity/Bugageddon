
package gui;

import java.util.ArrayList;

public class LogManager 
{
	private ArrayList<LogChangeListener> listeners;
	
	public LogManager()
	{
		this(new ArrayList<LogChangeListener>());
	}
	
	public LogManager(ArrayList<LogChangeListener> newListeners)
	{
		listeners = newListeners;
	}
	
	public void addListener(LogChangeListener newListener)
	{
		listeners.add(newListener);
	}
	
	public void removeListener(LogChangeListener listener)
	{
		listeners.remove(listener);
	}
	
	public void addToLog(String newEntry)
	{
		for (LogChangeListener listener : listeners)
		{
			listener.receiveLogEntry(newEntry + "\n");
		}
	}
}
