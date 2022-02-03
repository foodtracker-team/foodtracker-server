package com.foodtracker.models;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CompanyUsersId implements Serializable {
  @OneToOne()
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;

  @OneToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CompanyUsersId)) return false;
    CompanyUsersId that = (CompanyUsersId) o;
    return Objects.equal(company, that.company) && Objects.equal(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(company, user);
  }
}
