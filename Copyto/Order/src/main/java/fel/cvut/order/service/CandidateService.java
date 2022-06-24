package fel.cvut.order.service;

import fel.cvut.order.dao.CandidateDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.exception.NotFoundException;
import fel.cvut.order.model.Candidate;
import fel.cvut.order.model.Order;
import fel.cvut.order.model.OrderState;

import javax.persistence.EntityExistsException;

/**
 * Represents a Candidate Service.
 */
public record CandidateService(CandidateDao candidateDao, OrderDao orderDao) {

    /**
     *  Find candidate by id
     * @param id Integer identifier of candidate
     * @return Candidate
     */
    public Candidate findById(Integer id) {
        Candidate candidate = candidateDao.findById(id).orElse(null);
        if (candidate == null)
            throw new NotFoundException("Candidate with id=" + id + " not found");
        return candidate;
    }

    /**
     * Create new candidate for the order
     * @param order Order which will have a candidate
     * @param candidate Candidate which will request to be added to Order
     */
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

    /**
     * Add candidate to the order
     * @param candidate Candidate which will be added to Order
     * @param order Order which will receive a candidate
     */
    public void acceptCandidate(Candidate candidate, Order order) {
        if (isAlreadyCandidate(order, candidate)) {
            order.setAssigneeId(candidate.getUserId());
            order.removeAllCandidates();
            order.setOrderState(OrderState.IN_PROCESS);
            orderDao.save(order);
        }
    }
}
