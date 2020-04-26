package org.feuyeux.kio.repository.resource;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author feuyeux@gmail.com
 *
 * https://en.wikipedia.org/wiki/Eighteen_Arhats
 */
@Repository
public class HelloRepository {
    private Map<Long, String> employeeTable = new HashMap<>();

    @PostConstruct
    public void init() {
        employeeTable.put(1L, "騎鹿羅漢");
        employeeTable.put(2L, "喜慶羅漢");
        employeeTable.put(3L, "舉缽羅漢");
        employeeTable.put(4L, "托塔羅漢");
        employeeTable.put(5L, "靜座羅漢");
        employeeTable.put(6L, "過江羅漢");
        employeeTable.put(7L, "騎象羅漢");
        employeeTable.put(8L, "笑獅羅漢");
        employeeTable.put(9L, "開心羅漢");
        employeeTable.put(10L, "探手羅漢;");
        employeeTable.put(11L, "沉思羅漢");
        employeeTable.put(12L, "挖耳羅漢");
        employeeTable.put(13L, "布袋羅漢");
        employeeTable.put(14L, "芭蕉羅漢");
        employeeTable.put(15L, "长眉羅漢");
        employeeTable.put(16L, "看門羅漢");
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
