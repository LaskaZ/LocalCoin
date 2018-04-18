package localCoin.model;

import java.math.BigDecimal;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name="tranfer")
public class Transfer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Account reciver;
	
	@ManyToOne
	private Account sender;
	
	@Column(scale=8, precision=16)
	private BigDecimal ammount;
	
	
	@CreationTimestamp
	@Column(updatable=false)
	private Timestamp created;
	
	private String description;

	private long fee;

	@Override
	public String toString() {
		return "Send " + ammount + " BTC" + "\n To address: " + reciver.getAddress() + "\n" + description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getReciver() {
		return reciver;
	}

	public void setReciver(Account reciver) {
		this.reciver = reciver;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
