package ua.heatloss.domain.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User{
}
