package com.foodtracker.services;

import com.foodtracker.models.Company;
import com.foodtracker.models.User;

import java.util.List;

public interface CompanyService {
  List<Company> getMyCompanies(User user );
  List<Company> getUserCompanies(String userId );
  List<Company> getAll();
  Company getCompanyById(Long id );
}
