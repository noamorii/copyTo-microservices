package fel.cvut.order.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents Order
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "system_orders")
public class Order extends AbstractEntity {

    private double price;
    private LocalDate insertionDate;
    private Date deadline;
    private String link;
    private boolean isOpen;

    private Integer assigneeId;
    private Integer clientId;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy("name")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
    private List<Candidate> candidates = new ArrayList<>();

    private Order(OrderBuilder builder) {
        price = builder.price;
        insertionDate = builder.insertionDate;
        deadline = builder.deadline;
        link = builder.link;
        isOpen = builder.isOpen;
        assigneeId = builder.assigneeId;
        clientId = builder.clientId;
        orderState = builder.orderState;
        categories = builder.categories;
    }

    public static OrderBuilder newBuilder() {
        return new OrderBuilder();
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

    public void removeAllCandidates() {
        candidates.clear();
    }

    public boolean isEditable(){
        return orderState.isEditable();
    }

    /*================BUILDER===================*/

    /**
     * Design pattern Builder
     */
    public static final class OrderBuilder {

        private OrderBuilder() {
        }

        private double price;
        private LocalDate insertionDate;
        private Date deadline;
        private String link;
        private boolean isOpen;
        private List<Category> categories = new ArrayList<>();

        private Integer assigneeId;
        private Integer clientId;

        private OrderState orderState;

        public OrderBuilder addPrice(double price) {
            this.price = price;
            return this;
        }

        public OrderBuilder addInsertionDate(LocalDate insertionDate) {
            this.insertionDate = insertionDate;
            return this;
        }

        public OrderBuilder addDeadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public OrderBuilder addLink(String link) {
            this.link = link;
            return this;
        }

        public OrderBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public OrderBuilder addCategories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public OrderBuilder addCategory(Category category) {
            this.categories.add(category);
            return this;
        }

        public OrderBuilder addAssigneeId(Integer assigneeId) {
            this.assigneeId = assigneeId;
            return this;
        }

        public OrderBuilder addClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public OrderBuilder setOrderState(OrderState orderState) {
            this.orderState = orderState;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
