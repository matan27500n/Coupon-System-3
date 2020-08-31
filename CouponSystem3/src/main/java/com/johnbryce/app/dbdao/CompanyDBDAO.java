package com.johnbryce.app.dbdao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.johnbryce.app.beans.Company;
import com.johnbryce.app.repo.CompanyRepository;

@Repository
public class CompanyDBDAO {

	@Autowired
	private CompanyRepository companyRepository;

	public boolean isCompanyExists(int id) {
		return companyRepository.existsById(id);
	}

	public boolean isCompanyExistsByEmailAndPassword(String email, String password) {
		if (companyRepository.findByEmailAndPassword(email, password) != null) {
			return true;
		}
		return false;
	}

	public void addCompany(Company company) {
		companyRepository.save(company);
	}

	public void updateCompany(Company company) {
		companyRepository.saveAndFlush(company);
	}

	public void deleteCompany(Company company) {
		companyRepository.delete(company);
	}

	public Company getOneCompany(int id) {
		return companyRepository.getOne(id);
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getCompanyByEmailAndPassword(String email, String password) {
		return companyRepository.findByEmailAndPassword(email, password);
	}
}
