/*
 *  Copyright 2017 original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hcl.ccp.bigquery;

import com.google.api.Service;
import com.google.api.services.bigquery.BigqueryRequest;
import com.google.cloud.bigquery.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebController {

	private static final Log LOGGER = LogFactory.getLog(WebController.class);

	private BigQuery bigQuery;
	private Dataset dataset = null;

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	@Value("${GOOGLE_CLOUD_BIGQUERY_DATASET}")
	private String datasetName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Value("${GOOGLE_CLOUD_BIGQUERY_TABLE}")
	private String tableName;



	public WebController(BigQuery bigQuery) {
	this.bigQuery = bigQuery;
	}

	@GetMapping("/read-data-big-query")
	public ResponseEntity<List<InputData>> createTopic() {

		try {
			DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();
			LOGGER.info("DataSet Name:  " + datasetName);
			LOGGER.info("Table Name:  " + tableName);
			this.dataset = this.bigQuery.getDataset(datasetName);
			LOGGER.info("Dataset Retrieved " + this.dataset.getDatasetId().getDataset());
			System.out.printf("Dataset %s Retrieved %n", this.dataset.getDatasetId().getDataset());
			QueryJobConfiguration conf = QueryJobConfiguration.newBuilder(" SELECT * FROM " + datasetName + "." + tableName + "  ").build();
		    Iterable<FieldValueList>  rows =  this.dataset.getBigQuery().query(conf).iterateAll();
			List<InputData>  inData = new ArrayList<InputData>();
		    for(FieldValueList singleRow : rows){
				InputData inputData = new InputData();
				inputData.setEmployeeID(singleRow.get("employeeID").getStringValue());

				if(!singleRow.get("employeeName").isNull())
				  inputData.setEmployeeName(singleRow.get("employeeName").getStringValue());

				if(!singleRow.get("gender").isNull())
					inputData.setGender((singleRow.get("gender").getStringValue()));

				inData.add(inputData);
			}
			return new ResponseEntity<List<InputData>>(inData,HttpStatus.OK);

		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return new ResponseEntity<List<InputData>>(HttpStatus.BAD_REQUEST);
	}


}
