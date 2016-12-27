package com.lmx.chatservice;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgEvent {

    String content, pubName;
    Etype et;

    enum Etype {
        USERADD, PUBMSG
    }

    Date time = new Date();
}
