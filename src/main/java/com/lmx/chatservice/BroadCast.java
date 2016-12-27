package com.lmx.chatservice;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class BroadCast {

    /**
     * 当前注册监听列表
     */
    List<BlockingQueue<MsgEvent>> reg = new LinkedList<>();

    List<BlockingQueue<MsgEvent>> unReg = new LinkedList<>();
    /**
     * 全局的channel池
     */
    static public Map<BlockingQueue<MsgEvent>, Boolean> channels = new ConcurrentHashMap<>();

    public synchronized void regListener(BlockingQueue<MsgEvent> q) {
        reg.add(q);
        channels.put(q, true);
    }

    public synchronized void rmListener(BlockingQueue<MsgEvent> q) {
        reg.remove(q);
        channels.remove(q);
    }

    public void broadCast(MsgEvent e) {
        for (BlockingQueue<MsgEvent> ch : channels.keySet()) {
            ch.offer(e);
        }
    }

    public void pub(MsgEvent e) {
        for (BlockingQueue<MsgEvent> ch : channels.keySet()) {
            ch.offer(e);
        }
    }

}
