package org.ting.spring.utils;

import org.ting.spring.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统 stomp 连接在线用户集合
 */
public class Online {

    private static Map<String,User> maps = new ConcurrentHashMap<>();

    public static void add(User user){
        maps.put(user.getEmail(),user);
    }

    public static void remove(User user){
        maps.remove(user.getEmail());
    }

    public static Collection<User> onlineUsers(){
        return maps.values();
    }

}
