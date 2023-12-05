package com.coox.springboot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @SequenceGenerator(name = "commentIdGenerator", sequenceName = "seq_comment_id", allocationSize = 1)
    @GeneratedValue(generator = "commentIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
