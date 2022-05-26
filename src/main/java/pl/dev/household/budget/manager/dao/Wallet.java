package pl.dev.household.budget.manager.dao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "amount")
    private BigDecimal amount;

    public Integer getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
