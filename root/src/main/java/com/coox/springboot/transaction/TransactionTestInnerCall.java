package com.coox.springboot.transaction;

import com.coox.springboot.TopicRepository;
import com.coox.springboot.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionTestInnerCall {

    private final TopicRepository repository;

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
    public void innerCall() {
        Topic topic = new Topic();
        topic.setTopicName("Inner Topic");

        repository.save(topic);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional
    public void innerCallWithException() {
        Topic topic = new Topic();
        topic.setTopicName("Inner Topic With Exception");

        repository.save(topic);

        if (true) throw new RuntimeException("Rollback Inner");
    }

    @Transactional(readOnly = true)
    public Topic innerRead() {
        return repository.findById(1016L).get();
    }
}
