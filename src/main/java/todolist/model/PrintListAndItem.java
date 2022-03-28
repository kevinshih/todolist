package todolist.model;

import java.util.Date;

import todolist.common.DateTimeFormat;

public class PrintListAndItem {
	
	private String listName;
	private String dueDate;
	private String itemName;
	private String description;
	private String deadline;
	private int sortid;
	
	
	public PrintListAndItem(String listName, Date dueDate, String itemName, String description, Date deadline,
			int sortid) throws Exception {
		this.listName = listName;
		this.dueDate = DateTimeFormat.getTime(dueDate);
		this.itemName = itemName;
		this.description = description;
		this.deadline = DateTimeFormat.getTime(deadline);
		this.sortid = sortid;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	
	
	

}
