package com.liis.eventio.event;

public class EventNotFoundException extends Throwable{
    public EventNotFoundException(String message)
        {
            super(message);
        }
}
