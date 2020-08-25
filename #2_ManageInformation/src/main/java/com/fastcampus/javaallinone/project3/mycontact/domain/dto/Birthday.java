package com.fastcampus.javaallinone.project3.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Data
public class Birthday {

    private Integer year;

    private Integer month;

    private Integer day;

    public Birthday(LocalDate birthday) {
        this.year = birthday.getYear();
        this.month = birthday.getMonthValue();
        this.day = birthday.getDayOfMonth();
    }
}
