package com.chriniko.example.posts.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;

@EqualsAndHashCode(of = {"id"})
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement

@Entity
public class Post {

    @Id
    private String id;

    private String title;

    private String text;

    private Instant created;

}
