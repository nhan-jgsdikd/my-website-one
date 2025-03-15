package assgiment.ass.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import assgiment.ass.Model.*;

public interface ProductDAO extends JpaRepository<Product, Long> {
    
}
