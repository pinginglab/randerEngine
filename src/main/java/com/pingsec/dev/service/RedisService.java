package com.pingsec.dev.service;


import java.util.HashMap;

public interface RedisService {
//    String getAlarmInfo(String key);
    void removeInfo(String prerix, String key);
    void set(String prerix, String key, String value);
    void setHash(String prerix, String key, String value);
    String get(String key);
    // 模糊搜索
    HashMap getFuzzySearch(String key, String IP);
    // 数据以hashTable的形式存入redis
    // key:bussiness
//    String getESSession(String key);
//    void setESSession(String key, String value);
//    boolean hasESSionKey(String key);
//    void setProcessAccount(String key, String value);

}

