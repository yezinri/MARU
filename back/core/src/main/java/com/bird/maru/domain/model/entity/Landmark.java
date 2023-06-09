package com.bird.maru.domain.model.entity;

import com.bird.maru.domain.model.type.Coordinate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table(
        name = "landmarks",
        indexes = @Index(name = "landmark_geo_index", columnList = "lng, lat")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Landmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    @Builder.Default
    private Long memberId = 0L; // 현재 랜드마크의 대표 회원

    @NotNull
    private String name;

    @Embedded
    @NotNull
    private Coordinate coordinate; // (경도, 위도)

    @Column(name = "visit_count")
    @NotNull
    @Builder.Default
    private Integer visitCount = 0;

    public void changeOwner(Long memberId) {
        this.memberId = memberId;
    }

    public void addCount() {
        this.visitCount++;
    }

}
