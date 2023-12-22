package com.example.YealLMS.Entity.Ann;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "ann")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long not_num;

    @Column
    int lec_code;

    @Column
    Long pro_num;

    @Column
    String pro_name;

    @Column
    String not_title;

    @Column
    String not_content;

    @Column
    String not_file;

    @Column
    @Temporal(TemporalType.DATE)
    LocalDate not_date;
    @PrePersist
    public void setNotDate() {
        this.not_date = LocalDate.now();
    }

    @Column
    int not_cnt;
}
