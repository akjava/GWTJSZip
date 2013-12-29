package com.akjava.gwt.samplejszip.client;

import com.akjava.gwt.html5.client.download.HTML5Download;
import com.akjava.gwt.html5.client.file.Blob;
import com.akjava.gwt.jszip.client.GenerateParameter;
import com.akjava.gwt.jszip.client.JSFile;
import com.akjava.gwt.jszip.client.JSFileOptions;
import com.akjava.gwt.jszip.client.JSZip;
import com.akjava.gwt.lib.client.CanvasUtils;
import com.akjava.gwt.lib.client.LogUtils;
import com.google.gwt.canvas.client.Canvas;
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
		
		JSFile file=zip.getFile("test.txt");
		LogUtils.log(file);
		
		LogUtils.log(file.getName());
		LogUtils.log(file.asText());
		LogUtils.log(file.getOptions().getDate());
		
		
		JSZip dir=zip.folder("images");
		
		
		Canvas canvas=CanvasUtils.createCanvas(100, 100);
		canvas.getContext2d().setFillStyle("#ffffff");
		canvas.getContext2d().fillRect(0, 0, 100, 100);
		canvas.getContext2d().setFillStyle("#888888");
		canvas.getContext2d().fillRect(0, 0, 50, 50);
	
		/*
		String url=canvas.toDataUrl();
		int index=url.indexOf("base64,");
		String imgData=url.substring(index+"base64,".length());
		dir.file("image.png", imgData,JSFileOptions.newJSFileOptions().base64(true));
		*/
		dir.base64UrlFile("image.png", canvas.toDataUrl());
		
		
		Blob blob=zip.generateBlob(null);
		
		Anchor a=new HTML5Download().generateDownloadLink(blob,"application/zip","test.zip","download blob",false);
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
