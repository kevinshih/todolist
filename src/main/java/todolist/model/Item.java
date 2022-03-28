package todolist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item")
public class Item {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="deadline")
	private Date deadline;
	
	@Column(name="listid")
	private int listid;
	
	@Column(name="sortid")
	private int sortid;
	
	@Column(name="createdate")
	private Date createdate;
	
	@Column(name="modifieddate")
	private Date modifieddate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getListid() {
		return listid;
	}

	public void setListid(int listid) {
		this.listid = listid;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	

}
