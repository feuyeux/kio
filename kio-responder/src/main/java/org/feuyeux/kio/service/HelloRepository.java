package org.feuyeux.kio.service;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author feuyeux@gmail.com
 */
@Repository
public class HelloRepository {
    private Map<Long, String> employeeTable = new HashMap<>();

    @PostConstruct
    public void init() {
        employeeTable.put(1L, "坐鹿罗汉");
        employeeTable.put(2L, "欢喜罗汉");
        employeeTable.put(3L, "举钵罗汉");
        employeeTable.put(4L, "托塔罗汉");
        employeeTable.put(5L, "静坐罗汉");
        employeeTable.put(6L, "过江罗汉");
        employeeTable.put(7L, "骑象罗汉");
        employeeTable.put(8L, "笑狮罗汉");
        employeeTable.put(9L, "开心罗汉");
        employeeTable.put(10L, "探手罗汉");
        employeeTable.put(11L, "沉思罗汉");
        employeeTable.put(12L, "挖耳罗汉");
        employeeTable.put(13L, "布袋罗汉");
        employeeTable.put(14L, "芭蕉罗汉");
        employeeTable.put(15L, "长眉罗汉");
        employeeTable.put(16L, "看门罗汉");
    }

    public void store(long id, String name) {
        employeeTable.put(id, name);
    }

    public boolean delete(long id, String name) {
        return employeeTable.remove(id, name);
    }

    public String read(long id) {
        return employeeTable.get(id);
    }

    public Map<Long, String> readAll() {
        Map<Long, String> dirtyMap = new HashMap<>(employeeTable);
        long k = -1L;
        dirtyMap.put(k, "Dirty");
        if (employeeTable.get(k) == null) {
            dirtyMap.remove(k);
            return dirtyMap;
        }
        return null;
    }
}
