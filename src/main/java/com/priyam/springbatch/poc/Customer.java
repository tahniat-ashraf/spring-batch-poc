package com.priyam.springbatch.poc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Tahniat Ashraf Priyam
 * @since 5/28/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String randomNumber;
}
