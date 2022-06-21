package fel.cvut.order.dao;

import fel.cvut.order.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionDao extends JpaRepository<Version, Integer> {
}
