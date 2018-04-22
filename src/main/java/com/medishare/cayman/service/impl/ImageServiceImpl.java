package com.medishare.cayman.service.impl;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.medishare.cayman.domain.Image;
import com.medishare.cayman.domain.ImageParameter;
import com.medishare.cayman.service.CQLFileUtil;
import com.medishare.cayman.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@Component
public class ImageServiceImpl implements ImageService {

	private final Session session;

	@Autowired
	public ImageServiceImpl(Cluster cluster) throws IOException {
		session = cluster.connect();
		// session.execute("use img_key_space");

		CQLFileUtil.run_cql_file(session, "cql/img_schema.cql");
	}

	@Override
	public Image loadImage(String uri, boolean onlyMeta, ImageParameter param) {
		ResultSet results = session
				.execute("select uri,domain,memo,data,created from img_data where uri='" + uri + "';");

		if (!results.iterator().hasNext()) {
			return null;
		}

		Image img = new Image();
		Row row = results.iterator().next();
		img.setUri(row.getString("uri"));
		img.setDomain(row.getString("domain"));
		img.setMemo(row.getString("memo"));
		img.setData(row.getBytes("data").array());
		img.setCreated(row.getDate("created"));

		return img;
	}

	@Override
	public void saveImage(Image img) {
		PreparedStatement statement = session
				.prepare("insert into img_data (uri,domain,memo,data,created) values(?,?,?,?,?);");

		BoundStatement boundStatement = new BoundStatement(statement).bind(img.getUri(), img.getDomain(), img.getMemo(),
				ByteBuffer.wrap(img.getData()), img.getCreated());

		session.execute(boundStatement);
	}

}
