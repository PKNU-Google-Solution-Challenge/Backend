package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private Member fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private Member toUser;
}
