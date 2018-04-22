package com.medishare.cayman.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 * @author huijie.deng
 * @date 2017年10月29日
 */
@Entity(value="doctor_qr_relation",noClassnameStored=true)
@Indexes(
	    @Index(fields = @Field("_id"))
	)
public class DoctorQrRelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private ObjectId id;
	
	private String doctorId; 
	private String openid; 
	
	public void setId(String id) {
		if(StringUtils.isEmpty(id) || id.length()<24){
			this.id =  null;
		}else{
			this.id = StringUtils.isEmpty(id)?null:new ObjectId(id);			
		}
	}
	public String getId() {
		return id==null?null:String.valueOf(id);
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
}
