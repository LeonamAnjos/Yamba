package com.learning.android.yamba;

import java.util.ArrayList;
import java.util.List;


public class StatusEntity {

	static final String TAG = "StatusEntity";
	private static int count = 1;

	public static List<StatusEntity> getSomeInstances(int numberOfInstances) {
		ArrayList<StatusEntity> entities = new ArrayList<StatusEntity>();
		
		for (int i = 0; i < numberOfInstances; i++) {
			entities.add(new StatusEntity(count, "user " + count, "message " + count, "01/" + ((count % 12)+1) + "/2014" ));
		}
		
		return entities;
	}
	
	private int id;
	private String user;
	private String message;
	private String created_at;
	
	public StatusEntity(int id, String user, String message, String createdAt) {
		count++;
		
		this.setId(id);
		this.setUser(user);
		this.setMessage(message);;
		this.setCreatedAt(createdAt);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int value) {
		this.id = value;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String value) {
		this.user = value;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String value) {
		this.message = value;
	}
	public String getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(String value) {
		this.created_at = value;
	}

}
