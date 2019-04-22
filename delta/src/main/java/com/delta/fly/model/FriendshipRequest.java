package com.delta.fly.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(targetEntity = Passenger.class)
    @JoinColumn(name = "sent_from", referencedColumnName = "id")
    private Passenger sentFrom;

    @OneToOne(targetEntity = Passenger.class)
    @JoinColumn(name = "sent_to", referencedColumnName = "id")
    private Passenger sentTo;

    @Column(name = "accepted", unique = false, nullable = false)
    private Boolean accepted;

    @Column(name = "deleted", unique = false, nullable = false)
    private Boolean deleted;

    public FriendshipRequest() {
    }

    public FriendshipRequest(Passenger sentFrom, Passenger sentTo) {
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.accepted = false;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public Passenger getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(Passenger sentFrom) {
        this.sentFrom = sentFrom;
    }

    public Passenger getSentTo() {
        return sentTo;
    }

    public void setSentTo(Passenger sentTo) {
        this.sentTo = sentTo;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
