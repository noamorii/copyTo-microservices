package fel.cvut.order.service;

import fel.cvut.order.dao.CandidateDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Candidate;
import fel.cvut.order.model.Order;
import lombok.AllArgsConstructor;

import javax.persistence.EntityExistsException;

@AllArgsConstructor
public record CandidateService(CandidateDao candidateDao, OrderDao orderDao) {

    public Candidate findById(Integer id) {
        return candidateDao.findById(id).orElse(null);
    }

    public void createCandidate(Order order, Candidate candidate) {

        if (isAlreadyCandidate(order, candidate))
            throw new EntityExistsException("Order already has a candidate");

        candidateDao.save(candidate);
        order.addCandidate(candidate);
        orderDao.save(order);
    }

    private boolean isAlreadyCandidate(Order order, Candidate candidate) {
        return order.getCandidates().contains(candidate);
    }
}
