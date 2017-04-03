package com.adonis.persons;


import com.adonis.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;

/**
 * A domain object example. In a real application this would probably be a JPA
 * entity or DTO.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "persons", schema = "")
@Getter
@Setter
@NoArgsConstructor
public class Person implements Serializable, Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@Column(name = "FIRST_NAME", nullable = false)
//	@NotNull(message = "First Name is required")
//	@Size(min = 3, max = 40, message = "First Name must be longer than 3 and less than 40 characters")
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
//	@NotNull(message = "Last Name is required")
//	@Size(min = 3, max = 40, message = "Last Name must be longer than 3 and less than 40 characters")
	private String lastName;

	@Column(name = "EMAIL", unique = true, nullable = false)
//	@NotNull(message = "Email is required")
//	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
	private String email;

	//@NotNull(message = "Login is required")
	@Column(name = "LOGIN", nullable = true)
	private String login;

	//@NotNull(message = "Login is required")
	@Column(name = "PASSWORD", nullable = true)
	private String password;

//	@Temporal(javax.persistence.TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH", nullable = true)
	private Date dateOfBirth;

	private boolean remind = false;

	@Column(name = "PICTURE", nullable = true)
	private String picture;

	@Lob
	@Column(name = "NOTES", length = 1000, nullable = true)
	private String notes;

	@Column(name = "CREATED")
	private Date created;
	@Column(name = "UPDATED")
	private Date updated;

	@PrePersist
	protected void setCreatedDate() {
		created = new Date(currentTimeMillis());
	}

	@PreUpdate
	protected void setUpdatedDate() {
		updated = new Date(currentTimeMillis());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (this.id == null) {
			return false;
		}

		if (o == null || getClass() != o.getClass()) return false;

		if (o instanceof Person && o.getClass().equals(getClass())) {
			return this.id.equals(((Person) o).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.id);
		int result = id!=null?id.hashCode():hash;
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
		result = 31 * result + (remind ? 1 : 0);
		result = 31 * result + (picture != null ? picture.hashCode() : 0);
		result = 31 * result + (notes != null ? notes.hashCode() : 0);
		result = 31 * result + (created!=null?created.hashCode(): 0);
		result = 31 * result + (updated!=null?updated.hashCode():0);
		return result;
	}

	public Person(String firstName, String lastName, String email, String picture, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.picture = picture;
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getLocalDate(Date date){
		return date == null?null:Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public Date getDate(LocalDate localDate){
		return localDate==null?null:Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public LocalDate getLocalDateBirthDay(){
		return getLocalDate(getDateOfBirth());
	}
	public void setLocalDateBirthDay(LocalDate localDate){
		dateOfBirth = getDate(localDate);
	}

	public LocalDate getLocalDateCreated(){
		return getLocalDate(getCreated());
	}
	public void setLocalDateCreated(LocalDate localDate){
		created = getDate(localDate);
	}
	public void setLocalDateUpdated(LocalDate localDate){
		updated = getDate(localDate);
	}

	public LocalDate getLocalDateUpdated(){
		return getLocalDate(getUpdated());
	}
    public String getBirthDayString(){
		return DateUtils.convertToString(dateOfBirth);
	}
}
