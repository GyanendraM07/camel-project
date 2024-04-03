package com.example.demo.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyCamelRoutes extends RouteBuilder {

//    @Override
//    public void configure() throws Exception {
//        from("timer:foo?period=5000")
//            .setBody().constant("Hello, Camel!")
//            .to("log:output");
//    }
//	@Override
//	public void configure() throws Exception {
//		System.out.println("Inside Configure");
//	    from("file:C:/Key")
//	        .log("Moving file ${file:name} from ${file:parent} to C:/temp/received_csv_files")
//	        .to("file:C:/temp/received_csv_files")
//	        .onCompletion().log("File ${file:name} successfully moved to C:/temp/received_csv_files")
//	        .end()
//	        .onException(Exception.class)
//	            .log("Error occurred while moving file ${file:name}: ${exception.message}")
//	            .handled(true)
//	            .to("direct:errorHandler")
//	        .end();
//	    
//	    from("direct:errorHandler")
//	        .log("Error handling route triggered. Handling error...")
//	        // Add your error handling logic here, such as moving the file to an error directory
//	        .end();
//		 String sftpUrl = String.format(
//		            "sftp://%s@106.51.91.59/Outbox/Test?privateKeyFile=C:/keys/simprivate.ppk&privateKeyPassphrase=abhijithsim&useUserKnownHostsFile=false&noop=true",
//		            "cfg");
//		        String localPath = "C:/temp/received_csv_files";
//	    
//	    from(sftpUrl)
//        .routeId("sftpToLocal")
//        .log("Downloading file ${file:name} from SFTP server")
//        .to("file:" + localPath)
//        .log("File ${file:name} downloaded and saved to " + localPath)
//        .onException(Exception.class)
//            .log("Error occurred while downloading file ${file:name}: ${exception.message}")
//            .handled(true)
//            .to("direct:errorHandler")
//        .end();
//	}

//    @Override
//    public void configure() throws Exception {
//
//		String sftpUrl = String.format(
//				"%s?username=%s&privateKeyFile=%s&privateKeyPassphrase=%s&useUserKnownHostsFile=false&noop=true",
//				"106.51.91.59/Inbox/SIT/01292024_ack/", "cfg", "C:/temp/received_csv_files/simprivate.ppk", "abhijithsim");
//		
//		from("sftp://" + sftpUrl)
//		// .unmarshal()
//		// .pgp("file:" + pgpKeyPath, email + "", pgpPassPhrase + "")
//		 .log("File ${file:name} downloaded and saved to " + "C:/temp/received_csv_files/")
//		.to("file:" + "C:/temp/received_csv_files/");
//            
//    }

//	@Override
//    public void configure() throws Exception {
//		//Working 
//		
////		   from("sftp://106.51.91.59:22/Outbox/Test?username=cfg&privateKeyFile=C:/key/simprivate.ppk&privateKeyPassphrase=abhijithsim&noop=true")
////           .log("Downloaded file ${file:name} to local folder.")
////           .to("file:C:/temp/received_csv_files");
//	        from("sftp://106.51.91.59:22/Inbox/SIT/01292024_ack/?username=cfg&privateKeyFile=C:/key/simprivate.ppk&privateKeyPassphrase=abhijithsim&noop=true")
//		//from("sftp://cfg@106.51.91.59/Inbox/SIT/01292024_ack/?privateKeyFile=C:/key/simprivate.ppk&privateKeyPassphrase=abhijithsim&noop=true")
//     
//		.log("Downloaded file ${file:name} to local folder.")
//	            .to("file:C:/temp/received_csv_files");
//		
//	}
//		String sftpUrl = String.format(
//				"%s?username=%s&privateKeyFile=%s&privateKeyPassphrase=%s&useUserKnownHostsFile=false&noop=true",
//				"106.51.91.59/Inbox/SIT/01292024_ack/", "cfg", "C:/Key/simprivate.ppk", "abhijithsim");
//        // Local directory to save the downloaded file
//        String localDirectory = "C:/temp/received_csv_files";
//        from("sftp://" + sftpUrl).routeId("sftpToLocal").choice().when(header("CamelFileName").startsWith("nrp_int_ob_baseprice_ack_202401290602.csv"))
//            .log("Downloading file: ${file:name}")
//            .to("file:" + localDirectory)
//            .log("File downloaded successfully to: " + localDirectory)
//            .end()
//            .onException(Exception.class)
//                .log("Error occurred while downloading file: ${file:name} - ${exception.message}")
//                .handled(true)
//                .to("direct:errorHandler")
//            .end();
//
//        from("direct:errorHandler")
//            .log("Error handling route triggered. Handling error...")
//            // Add your error handling logic here
//            .end();
//    }

	// Final Testing Configure method Where i will give Everything dynamic
	String EndPoint = "106.51.91.59:22";
	String DirectoryFileTODownload = "/Outbox/Test";
	String userName = "cfg";
	String ppkFileLocation = "C:/key/simprivate.ppk";
	String privateKeyPassphrase = "abhijithsim";
	String useUserKnownHostsFile = "false";
	String noop = "true";

	// Full Working Dont Touch
//	@Override
//	public void configure() throws Exception {
//		String sftpUrl = String.format(
//				"sftp://%s/%s?username=%s&privateKeyFile=%s&privateKeyPassphrase=%s&useUserKnownHostsFile=%s&noop=%s",
//				EndPoint, DirectoryFileTODownload, userName, ppkFileLocation, privateKeyPassphrase,
//				useUserKnownHostsFile, noop);
//
//		from(sftpUrl).log("File downloaded successfully from SFTP server")
//				.log("Downloaded file ${file:name} to local folder.").to("file:C:/temp/received_csv_files")
//				.log("File downloaded successfully to: " + "C:/temp/received_csv_files").end()
//				.onException(Exception.class)
//				.log("Error occurred while downloading file: ${file:name} - ${exception.message}").handled(true)
//				.to("direct:errorHandler").end();
//
//		from("direct:errorHandler").log("Error handling route triggered. Handling error...")
//				// Add your error handling logic here
//				.end();
//	}

	@Override
	public void configure() throws Exception {
		String sftpUrl = String.format(
				"sftp://%s/%s?username=%s&privateKeyFile=%s&privateKeyPassphrase=%s&useUserKnownHostsFile=%s&noop=%s",
				EndPoint, DirectoryFileTODownload, userName, ppkFileLocation, privateKeyPassphrase,
				useUserKnownHostsFile, noop);

		from(sftpUrl).log("File downloaded successfully from SFTP server").unmarshal().csv() // Parse CSV file
				.process(new CsvToKafkaProcessor()).log("CSV File Converted To the  Objects Succesfully ").choice()
				.when(header("isValidData").isEqualTo(true)).toF("kafka:TestTopic2?brokers=localhost:9092").otherwise()
				.toF("kafka:TestTopic1?brokers=localhost:9092")
				// .to("kafka:TestTopic2?brokers=localhost:9092").log("Data pushed to Kafka
				// topic")
				.end().onException(Exception.class)
				.log("Error occurred while processing file: ${file:name} - ${exception.message}").handled(true)
				.to("direct:errorHandler").end();
		from("direct:errorHandler").log("Error handling route triggered. Handling error...")
				// Add your error handling logic here
				.end();
	}
}

//from(sftpUrl).log("File downloaded successfully from SFTP server")
//.log("Downloaded file ${file:name} to local folder.").to("file:C:/temp/received_csv_files")
//.log("File downloaded successfully to: " + "C:/temp/received_csv_files").end()
//.onException(Exception.class)
//.log("Error occurred while downloading file: ${file:name} - ${exception.message}").handled(true)
//.to("direct:errorHandler").end();
