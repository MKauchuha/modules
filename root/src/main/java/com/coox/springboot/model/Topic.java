package com.coox.springboot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topic")
@Data
@NoArgsConstructor
public class Topic {

    @Id
    @SequenceGenerator(name = "topicIdGenerator", sequenceName = "seq_topic_id", allocationSize = 1)
    @GeneratedValue(generator = "topicIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "topic_name")
    private String topicName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 1000)
    private List<Comment> comments = new ArrayList<>();
}
