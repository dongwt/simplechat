package com.lmx.chatservice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lmx.chatservice.MsgEvent.Etype;

@RestController
@RequestMapping("chat")
public class ChatCtrl {

    @RequestMapping("/user/get/{roomId}")
    public Object getRoom(@PathVariable("roomId") int rid, @RequestParam String userName) {
        if (Channel.rooms.get(rid) != null) {
            MsgEvent e = new MsgEvent();
            e.et = Etype.USERADD;
            e.pubName = userName;
            Channel.room(rid).broadCast(e);
        }
        Channel.room(rid);
        return "reg room ok";

    }

    @RequestMapping("/user/pub/{roomId}")
    public Object pubMsg(@PathVariable("roomId") int rid, MsgEvent e) {
        Channel.room(rid).pub(e);
        return "pub msg ok";

    }

    @RequestMapping("/stream/{roomId}")
    public void stream(@PathVariable("roomId") int rid, HttpServletResponse resp) throws IOException {
        BlockingQueue<MsgEvent> q = Channel.openListener(rid);
        resp.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        try {
            PrintWriter w = resp.getWriter();
            String json = JSONObject.toJSONString(q.take());
            System.out.println("sse=" + json);
            w.write("data:" + json + "\n\n");// bug? （\n\n）
            w.flush();
            w.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Channel.rmListener(rid, q);
        }
    }

}
