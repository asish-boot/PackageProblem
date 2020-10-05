package com.tcs.packer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.tcs.exception.APIException;

/**
 * @author Asish
 * rerurn type : String
 * input type : filename
 * Purpose : To read file and generate required output
 */
public class Packer{

	public static String pack(String fileName)throws APIException 
	{ 
		
		String data 		= ""; 
		String resultIndex 	= "";
		try {
			data = new String(Files.readAllBytes(Paths.get(fileName)));
			/* 
			 * Input File get split into separate lines
			 */
			String[] lines = data.split("\\r?\\n");
			/*
			 * Loop through the lines and split by :
			 */
			for(String thing : lines){
				String weightData[] = thing.split(":");
				//Retrieve total weight
				String packageValue = weightData[1];
				//Split inner data between ()
				String thingData[] = packageValue.split("\\)");
				String finalIndex = "";
				double prevWeight = 0.0;
				double prevCost = 0.0;
				double finalWeight = 0.0;
				double finalCost = 0.0;
				//looping through inner data to get individual values as index number/weight/cost
				for(String secInnerData : thingData){
					String thingData1[] = secInnerData.split("\\(");
					String indData[] = thingData1[1].split(",");
					String indexNo = indData[0];
					String indWeight = indData[1];
					String cost = indData[2].substring(3);
					double compareWeight = Double.parseDouble(indWeight.trim());
					double compareCost = Double.parseDouble(cost.trim());

					double resultWeight = 0.0;
					double resultCost = 0.0;
					if(prevWeight == 0.0){
						resultWeight = finalWeight + compareWeight;
					}else{
						resultWeight = prevWeight + compareWeight;
					}

					if(prevCost == 0.0){
						resultCost = finalCost + compareCost;
					}else{
						resultCost = prevCost + compareCost;
					}

					/**
					 * Comparison for Max Weight and Max Cost should not exceed 100
					 */
					if(resultWeight <= 100 ){
						if(resultCost <=100){
							finalIndex = indexNo;
							finalWeight = Double.parseDouble(indWeight);
							finalCost = Double.parseDouble(cost);
							resultIndex = resultIndex.concat(finalIndex+",");
						}
					}
					prevWeight = finalWeight;
					prevCost = finalCost;
				}
				//Appending index number satisfying mentioned criteria and appending new line
				resultIndex = resultIndex + "\r\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 

		System.out.println("Result -->" + resultIndex);
		return resultIndex; 
	} 

	public static void main(String[] args) throws Exception 
	{ 
		try {
			String data = pack("src\\resources\\PackerTest.txt");
		} catch (APIException e) {
			e.printStackTrace();
		} 
	} 
}