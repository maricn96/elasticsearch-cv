package mas.udd.elasticsearch.handlers;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFHandler extends DocumentHandler {
	
//	@Override
//	public CV getIndexUnit(File file) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public IndexUnit getIndexUnit(File file) {
//		IndexUnit retVal = new IndexUnit();
//		try {
//			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
//			parser.parse();
//			String text = getText(parser);
//			retVal.setText(text);
//
//			// metadata extraction
//			PDDocument pdf = parser.getPDDocument();
//			PDDocumentInformation info = pdf.getDocumentInformation();
//
//			String title = ""+info.getTitle();
//			retVal.setTitle(title);
//
//			String keywords = ""+info.getKeywords();
//			if(keywords != null){
//				String[] splittedKeywords = keywords.split(" ");
//				retVal.setKeywords(new ArrayList<String>(Arrays.asList(splittedKeywords)));
//			}
//			
//			retVal.setFilename(file.getCanonicalPath());
//			
//			String modificationDate=DateTools.dateToString(new Date(file.lastModified()),DateTools.Resolution.DAY);
//			retVal.setFiledate(modificationDate);
//			
//			pdf.close();
//		} catch (IOException e) {
//			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
//		}
//
//		return retVal;
//	}

	@Override
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Error converting to PDF");
		}
		return null;
	}
	
	public String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Error converting to PDF");
		}
		return null;
	}

	

}