package market.henry.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    @SequenceGenerator(name = "seqAccoutId", sequenceName = "seqAccoutId", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String bvn;
    private String title;
    private String firstName;
    private String middleName;
    private String surname;
    private String phoneNumber;
    private LocalDate dob;
    private String gender;
    private String address;
    private String country;
    private String state;
    private String nextOfKinName;
    private String nextOfKinPhoneNumber;
    private String email;
    private String idType;
    private String idCardNumber;
    private String branch;
    private String annualIncome;
    private String  initialAmount;
    private String accountTier;
    private String accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountDocument")
    private AccountDocument accountDocument;
}
