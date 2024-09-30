package okestro.hjpark.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
public class UserEntity {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_nm")
    private String user_nm;

    @Column(name = "company")
    private String company;

    @Override
    public String toString() {
        return "NoticeBoardEntity{" +
                "user_id=" + id +
                ", user_nm='" + user_nm + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
