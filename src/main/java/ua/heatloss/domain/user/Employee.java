package ua.heatloss.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMPL")
public class Employee extends User{
}
