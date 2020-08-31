package com.johnbryce.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.johnbryce.app.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	Company findById(int id);

	Company findByEmail(String email);

	Company findByPassword(String password);

	Company findByName(String name);

	Company findByEmailAndPassword(String email, String password);
}
