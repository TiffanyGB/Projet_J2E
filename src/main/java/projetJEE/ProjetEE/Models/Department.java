package projetJEE.ProjetEE.Models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;
	private String code;
	
	public Long getId() {
		return id;
	}
	public void setId(Long departmentId) {
		this.id = departmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String departmentName) {
		this.name = departmentName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String departmentAddress) {
		this.address = departmentAddress;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String departmentCode) {
		this.code = departmentCode;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, code, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(address, other.address)
				&& Objects.equals(code, other.code)
				&& Objects.equals(name, other.name);
	}
}
