package com.pingsec.dev.service.impl;

import com.google.gson.Gson;
import com.pingsec.dev.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {
    private StringRedisTemplate stringRedisTemplate;
    private RedisTemplate<String, String> redisTemplate;
    private Gson gson;


    public RedisServiceImpl(
        StringRedisTemplate stringRedisTemplate,
        Gson gson) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.gson = gson;
    }


    // 记录es的链接session
    // 防止掉线
//    @Override
//    public String getESSession(String key) {
////        return redisTemplate.opsForValue().get(String.format("%s:%s", PREFIXESSESSION, key));
//        List<Object> values = stringRedisTemplate.opsForHash().values(PREFIXESSESSION);
//        return values.get(0).toString();
//    }


    @Override
    public void removeInfo(String prerix, String key) {
        stringRedisTemplate.delete(String.format("%s:%s", prerix, key));
    }

    @Override
    public void set(String prerix, String key, String value) {
        stringRedisTemplate.opsForValue().set(String.format("%s:%s", prerix, key), value);
    }

//    public void setProcessAccount(String key, String value) {
//        stringRedisTemplate.opsForHash().put(PREFIXTABLE, key, value);
//    }

    @Override
    // 模糊匹配:根据目标IP和key
    public HashMap<String, String> getFuzzySearch(String key, String IP) {
        HashMap<String, String> result = new HashMap<>();
        Set<String> set = stringRedisTemplate.keys("*"+key+","+IP+"*");
        for(Object item:set)
            result.put(item.toString(),get(item.toString()));
        return result;
    }

    // 这个是仅作为内部使用,进行键值对的数据提取
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(String.format("%s", key));
    }

    @Override
    public void setHash(String prerix, String key, String value){
        // key=bussiness+account
        // 这个地方还是有点奇怪,不确定直接把整个对象塞进去行不行
        // Tip:经过搜索,一个value允许2^32-1bit,反正在撑爆redis之前,设备的内存已经爆了
        stringRedisTemplate.opsForHash().put(prerix, key, value);
    }

//    @Override
//    public ArrayList<LoginStatistic> getHash(String bussiness){
//        List<Object> values = stringRedisTemplate.opsForHash().values(PREFIXAUTOACCOUNT);
//        ArrayList<LoginStatistic> result = new ArrayList<>();
//        for(Object item:values){
//            LoginStatistic temp = gson.fromJson(item.toString().replace("LoginStatistic",""), LoginStatistic.class);
//            if(temp.getBussiness().equals(bussiness)) result.add(temp);
//        }
////        String temp = stringRedisTemplate.opsForHash().get(PREFIXAUTOACCOUNT, bussiness).toString();
//        return result;
//    }
//
//    @Override
//    public boolean hasESSionKey(String key) {
//        return stringRedisTemplate.hasKey(String.format("%s:%s", PREFIXESSESSION, key));
////        return redisTemplate.hasKey(String.format("%s:%s", PREFIXESSESSION, key));
//    }
}
