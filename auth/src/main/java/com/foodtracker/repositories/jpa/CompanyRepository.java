package com.foodtracker.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.foodtracker.models.Company;

import java.util.List;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
  @Query("SELECT c FROM Company c WHERE c.id IN( SELECT u.companyUsersId.company FROM CompanyUsers u WHERE u.companyUsersId.user.id = ?1)")
  List<Company> getUserCompanies(String userId);

}
