package com.johnbryce.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.johnbryce.app.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByEmailAndPassword(String email, String password);

	Company findByNameAndEmail(String name, String email);
}
