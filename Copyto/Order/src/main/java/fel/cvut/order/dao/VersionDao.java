package fel.cvut.order.dao;

import fel.cvut.order.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionDao extends JpaRepository<Version, Integer> {
}
