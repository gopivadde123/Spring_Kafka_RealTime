package net.gopiguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity// To make jpa entity we use annotation
@Table(name = "wikimedia_recentchange")
@Getter
@Setter
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob // to store large data we use this annotation
    private String wikiEventData;
}
