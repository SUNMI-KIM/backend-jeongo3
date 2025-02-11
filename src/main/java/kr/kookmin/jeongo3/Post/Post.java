package kr.kookmin.jeongo3.Post;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import kr.kookmin.jeongo3.Comment.Comment;
import kr.kookmin.jeongo3.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "POST_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private PostType postType; // 게시판 이름

    @Column
    private String title;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private int views;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column
    private LocalDateTime time;

    @Column
    private String image;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("time DESC")
    private List<Comment> comments;

    @Builder
    public Post(String id, String title, String content, String image, PostType postType) {
        this.id = id;
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
