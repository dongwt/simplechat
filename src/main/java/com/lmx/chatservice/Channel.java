package com.lmx.chatservice;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Channel {

    public static Map<Integer, BroadCast> rooms = new ConcurrentHashMap<>();

    public static BlockingQueue<MsgEvent> openListener(int rid) {
        BlockingQueue<MsgEvent> queue = new LinkedBlockingQueue<>(1);
        room(rid).regListener(queue);
        return queue;
    }

    public static BlockingQueue<MsgEvent> rmListener(int rid, BlockingQueue<MsgEvent> queue) {
        room(rid).rmListener(queue);
        return queue;
    }

    public static BroadCast room(int rid) {
        if (rooms.get(rid) == null) {
            BroadCast bc = new BroadCast();
            rooms.put(rid, bc);
            return bc;
        }
        return rooms.get(rid);
    }

}
