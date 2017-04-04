package com.adonis.data.persons;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;


	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;

	@Column(name = "LOGIN", nullable = true, unique = true)
	private String login;

	@Column(name = "PASSWORD", nullable = true)
	private String password;

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
		updated = new Date(currentTimeMillis());
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

}
