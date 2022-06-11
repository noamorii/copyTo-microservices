package fel.cvut.order.dao;

import fel.cvut.order.model.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkplaceDao extends JpaRepository<Workplace, Integer> {
}
