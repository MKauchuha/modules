package com.coox.springboot.transaction;

import com.coox.springboot.TopicRepository;
import com.coox.springboot.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@EnableAsync
public class AsyncTask {

    private final TopicRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Async
    public void runAsyncTask() {

        List<Topic> topics = repository.findAll();
        topics.forEach(System.out::println);
    }
}
