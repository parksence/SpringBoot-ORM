package okestro.hjpark.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "notice_board")
@Getter
public class NoticeBoardEntity {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "PASSWORD")
    private String password;

    @Override
    public String toString() {
        return "NoticeBoardEntity{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", content='" + content + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
