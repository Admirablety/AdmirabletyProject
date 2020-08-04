package com.Admirablety.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TrackRole {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    private String role;
    
    
    public TrackRole() {}
    
	public TrackRole(String role) {
		super();
		this.role = role;
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

}