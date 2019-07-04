package com.altimetrik.email;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractDataFromPdf {
	
	String InvoiceNo;
	String InvoiceDate;
	String CustomerPO;
	String SoldTo;
	String ShipTo;
	String RemitTo;
	String TotalInvoice;
	String Status;
	//InvoiceDatabase invoiceDatabase=new InvoiceDatabase();
	
	public void extractPdfData() throws Exception {
		File file=new File("C:\\Users\\lpriya\\Desktop\\InvoicedataAcushnet.pdf");
		PDDocument document= PDDocument.load(file);
		 PDFTextStripper pdfStripper = new PDFTextStripper();
		 String text = pdfStripper.getText(document);
		 String pdfText[] = text.split("\\r?\\n");
		 for(int i=0;i<=pdfText.length;i++) {
		// int i=0;
		 while(i<pdfText.length) {
			
			 if(pdfText[i].equals("Invoice No")) {
				 System.out.println("Invoice No:\t"+pdfText[i+1]);
				 InvoiceNo=pdfText[i+1];
				// System.out.println(InvoiceNo);
				 i+= 2;
				 break;
			 }
			 i++;
		 }
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Invoice Date")) {
				 System.out.println("Invoice Date:\t"+pdfText[i+1]);
				 InvoiceDate=pdfText[i+1];
				 i+= 2;
				 break;
			 }
			 i++;
		 }
		 
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Customer P.O.")) {
				 System.out.println("Customer P.O:\t"+pdfText[i+1]);
				 CustomerPO=pdfText[i+1];
				 i+= 2;
				 break;
			 }
			 i++;
		 }
		 
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Sold To")) {
				// System.out.print("Sold To:\t"+pdfText[i+1]);
				 i=i+1;
				 System.out.println("sold To:");
				 while(!pdfText[i].equals("Ship To"))
				 {
					 System.out.print(pdfText[i]);
					 SoldTo=pdfText[i];
					 i=i+1;
					  
				 }
				 System.out.println();
				 break;
			 }
			 i++;
		 }
		 
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Ship To")) {
				// System.out.print("Ship To:\t"+pdfText[i+1]);
				 System.out.println("Ship To:");
				 i=i+1;
				 while(!pdfText[i].equals("Remit To"))
				 {
					 System.out.print(pdfText[i]);
					 ShipTo=pdfText[i];
					 i=i+1;
					
				 }
				 System.out.println();
				 break;
			 }
			 i++;
		 }
		 
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Remit To")) {
				// System.out.print("Remit To:\t"+pdfText[i+1]);
				 System.out.println("Remit To");
				 i=i+1;
				 while(!pdfText[i].equals("Payment Terms"))
				 {
					 System.out.print(pdfText[i]);
					 RemitTo=pdfText[i];
					 i=i+1;
				 }
				 System.out.println();
				 break;
			 }
			 i++;
		 }
		 while(i<pdfText.length) {
			 if(pdfText[i].equals("Total Invoice")) {
				 System.out.println("Total Invoice:\t"+pdfText[i+3]);
				//i+=2;
				 TotalInvoice=pdfText[i+3];
				System.out.println();
				 break;
			 }
			 i++;
		 }
		 
		insertInvoiceData(InvoiceNo, InvoiceDate, CustomerPO,SoldTo ,ShipTo,RemitTo,TotalInvoice,Status);
	      document.close();
		 }
		 
		
	}
	Connection connection=null;
	public void insertInvoiceData(String InvoiceNo,String InvoiceDate,String CustomerPO,String SoldTo,String ShipTo,String RemitTo,String TotalInvoice,String Status ) throws Exception{
		//System.out.println(SoldTo);
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
		PreparedStatement stmt=con.prepareStatement("insert into InvoiceData values(?,?,?,?,?,?,?,?)");
		stmt.setString(1,InvoiceNo);
		stmt.setString(2,InvoiceDate);
		stmt.setString(3,CustomerPO);
		stmt.setString(4,SoldTo);
		stmt.setString(5,ShipTo);
		stmt.setString(6,RemitTo);
		stmt.setString(7,TotalInvoice);
		stmt.setString(8,"Notaprroved");
		int DataInserted=stmt.executeUpdate();
		System.out.println("Data Inserted Succesfully:"+DataInserted);
	}catch(SQLException e) {
		e.printStackTrace();
	}
	}
		public void updateInvoiceData(){
	    SendMail sendmail=new SendMail(); 
			Scanner in=new Scanner(System.in);
		String a=in.nextLine();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
				String sql="UPDATE InvoiceData set Status='Approved' WHERE id='a'";
				PreparedStatement stmt=con.prepareStatement(sql);
				int rs = stmt.executeUpdate(sql);
				System.out.println("Updated Sucessfully:"+rs);
				if(rs>=0){
					sendmail.sendMailInteraction();
				}
				else{
					System.out.println("its not approved");
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	
	
	
	}
