package cn.project.base.utils;

/**
 * @authors: 唐辉
 * @description:事件通知标志
 **/
public class Event {
    public String eventMessage;

    public Event() {
    }

    public Event(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }
}
