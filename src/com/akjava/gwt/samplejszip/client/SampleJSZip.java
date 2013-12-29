package com.akjava.gwt.samplejszip.client;

import com.akjava.gwt.html5.client.download.HTML5Download;
import com.akjava.gwt.html5.client.file.Blob;
import com.akjava.gwt.jszip.client.GenerateParameter;
import com.akjava.gwt.jszip.client.JSZip;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class SampleJSZip implements EntryPoint {

	@Override
	public void onModuleLoad() {
		TextArea area=new TextArea();
		RootPanel.get().add(area);
		
		JSZip zip=JSZip.newJSZip();
		zip.file("test.txt", "hello world");
		JSZip dir=zip.folder("files");
		dir.file("test2.txt", "test");
		
		
		Blob blob=zip.generateBlob(GenerateParameter.COMPRESSION_DEFLATE);
		Anchor a=new HTML5Download().generateDownloadLink(blob,"application/zip","test1.zip","download blob0",false);
		RootPanel.get().add(a);
		
		
		/*
		 * 
		 * Object obj=zip.generate(GenerateParameter.newGenerateParameter().type(GenerateParameter.TYPE_BLOB));
		 */
		
		/* base64 to Blob
		Object obj=zip.generate();
		area.setText(obj.toString());
		Blob blob=Blob.createBase64Blob(obj.toString(), "application/zip");
		Anchor a=new HTML5Download().generateDownloadLink(blob,"application/zip","test2.zip","download",false);
		RootPanel.get().add(a);
		*/
	}

}
