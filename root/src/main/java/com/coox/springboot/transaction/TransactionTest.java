package com.coox.springboot.transaction;

import com.coox.springboot.TopicRepository;
import com.coox.springboot.model.Comment;
import com.coox.springboot.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TransactionTest {

    private final TransactionTestInnerCall transactionTestInnerCall;
    private final TopicRepository repository;
    private final AsyncTask asyncTask;
    private final PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void doCall1() {
        transactionTestInnerCall.innerCall();

        Topic topic = new Topic();
        topic.setTopicName("Outer Topic");

        repository.save(topic);

//        if (true) throw new RuntimeException("Rollback");
    }

    @Transactional
    public void doCall2() {
        try {
            transactionTestInnerCall.innerCallWithException();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Topic topic = new Topic();
        topic.setTopicName("Outer Topic Without Exception");

        repository.save(topic);
    }

    public void doCallTransactionTemplate() {
        new TransactionTemplate(transactionManager).execute(status -> {
//            transactionTestInnerCall.innerCall();
//            transactionTestInnerCall.innerCallWithException();
            Topic topic = transactionTestInnerCall.innerRead();

            Comment comment = new Comment();
            comment.setCommentText("Comment text");
            comment.setTopic(topic);

            topic.getComments().add(comment);

            return repository.save(topic);
        });
    }

    @Transactional
    public void doAsyncRead() {

        Topic topic = new Topic();
        topic.setTopicName("Async Topic Persistance");

        repository.save(topic);

        System.out.println(entityManager);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(asyncTask::runAsyncTask, 5, TimeUnit.SECONDS);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void hiberBug() {
        Topic topic = repository.findById(1L).get();
        Topic topicSource = repository.findById(2L).get();

        topic.getComments().clear();
        topicSource.getComments().forEach(comment -> {
            Comment clonedComment = new Comment();
            clonedComment.setCommentText(comment.getCommentText());
            clonedComment.setTopic(topic);

            topic.getComments().add(clonedComment);
        });

        repository.save(topic);
    }

    public static void main(String[] args) {
//        int [] commands = {0xB7A0, 0xFFFF, 0xAF, 0xAB, 0xBC, 0xEF, 0xEB, 0xE9, 0xF5, 0xAC, 0xFA, 0xA8 };
//
//        Arrays.stream(commands).forEach(i -> System.out.println("// 0x" + Integer.toHexString(i).toUpperCase() + " - " + i));

        Topic topic1 = new Topic();
        topic1.setComments(List.of(new Comment(), new Comment()));
        Topic topic2 = new Topic();
        List<Topic> topics = List.of(topic1, topic2);

        topics.stream()
                .filter(topic -> !CollectionUtils.isEmpty(topic.getComments()))
                .flatMap(topic -> topic.getComments().stream())
                .forEach(System.out::println);
    }
}
