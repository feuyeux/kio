package org.feuyeux.kio.service;

import lombok.extern.java.Log;
import org.feuyeux.kio.pojo.HelloRequest;
import org.feuyeux.kio.pojo.HelloResponse;
import org.feuyeux.kio.repository.resource.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author feuyeux@gmail.com
 */
@Service
@Log
public class HelloService {
    @Autowired
    private HelloRepository helloRepository;

    public HelloResponse info(long id) {
        String name = helloRepository.read(id);
        HelloResponse response = new HelloResponse();
        response.setId(id);
        response.setValue(name);
        return response;
    }

    public HelloResponse hire(HelloRequest helloRequest) {
        long id = helloRequest.getId();
        String name = helloRequest.getValue();
        helloRepository.store(id, name);
        HelloResponse response = new HelloResponse();
        response.setId(id);
        response.setValue(name);
        return response;
    }

    public HelloResponse fire(HelloRequest helloRequest) {
        long id = helloRequest.getId();
        String name = helloRequest.getValue();
        if (helloRepository.delete(id, name)) {
            HelloResponse response = new HelloResponse();
            response.setId(id);
            response.setValue(name);
            return response;
        }
        return null;
    }

    public Flux<HelloResponse> list() {
        Map<Long, String> all = helloRepository.readAll();
        return Flux.fromStream(all.entrySet().stream()).map(e -> {
            HelloResponse response = new HelloResponse();
            response.setId(e.getKey());
            response.setValue(e.getValue());
            return response;
        });
    }
}
