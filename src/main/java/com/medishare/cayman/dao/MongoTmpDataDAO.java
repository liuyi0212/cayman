package com.medishare.cayman.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medishare.cayman.domain.DoctorQrRelation;

/**
 * @author huijie.deng
 * @date 2017年10月29日
 */
@Component
public class MongoTmpDataDAO {
	
	@Autowired
	Datastore ds;
	/**
	 * 保存临时数据，扫码关注
	 * @param doctorId
	 * @param openid
	 * @return
	 */
	public void saveQrDoctorRelation(String doctorId,String openid){
		DoctorQrRelation dr = new DoctorQrRelation();
		dr.setDoctorId(doctorId);
		dr.setOpenid(openid);
		ds.save(dr);
	}
	/**
	 * 通过openid查询医生id
	 * @param openid
	 * @return
	 */
	public List<DoctorQrRelation> queryDoctorId(String openid){
		Query<DoctorQrRelation> query  = ds.createQuery(DoctorQrRelation.class).field("openid").equal(openid);
		List<DoctorQrRelation> drList = query.asList();
		return drList;
	}
	/**
	 * 删除临时数据
	 * @param dr
	 */
	public void delDoctorQrRelation(DoctorQrRelation dr){
		ds.delete(dr);
	}
	
}
