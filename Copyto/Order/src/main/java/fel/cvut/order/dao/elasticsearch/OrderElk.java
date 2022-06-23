package fel.cvut.order.dao.elasticsearch;

import fel.cvut.order.model.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderElk extends ElasticsearchRepository<Order, Integer> {
}
