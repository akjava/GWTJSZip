package com.akjava.gwt.jszip.client;

import java.util.Date;

import com.akjava.gwt.html5.client.file.Blob;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;

public class JSZip extends JavaScriptObject{
protected JSZip(){}
	private static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MMMM dd, yyyy HH:mm:ss");
	public static native final boolean exists()/*-{
	return $wnd.JSZip;
	}-*/;

	public final static native JSZip newJSZip()/*-{
	return new $wnd.JSZip();
	}-*/;
	
	
	public final native JSFile getFile(String fileName)/*-{
	return this.file(fileName);
	}-*/;
	
	/**
	 * 
	 * @param fileName
	 * if fileName contain /,directory is created automatically  
	 * @param text
	 * @return
	 */
	public final native JSZip file(String fileName,String text)/*-{
	return this.file(fileName,text);
	}-*/;
	
	public final native JSZip file(String fileName,String text,String dateLabel)/*-{
	return this.file(fileName,text,{date : new $wnd.Date(dateLabel)});
	}-*/;
	
	public final  JSZip file(String fileName,String text,long datetime){
		String dateLabel=dateTimeFormat.format(new Date(datetime));
		return file(fileName,text,dateLabel);
	}
	
	/**
	 * designed for canvas-url
	 * @param fileName
	 * @param url
	 * @return
	 */
	public final  JSZip base64UrlFile(String fileName,String url){
		int index=url.indexOf("base64,");
		String imgData;
		if(index==-1){
			imgData=url;
		}else{
			imgData=url.substring(index+"base64,".length());
		}
		return file(fileName, imgData,JSFileOptions.newJSFileOptions().base64(true));
	}
	
	public final native JSZip file(String fileName,String text,JSFileOptions option)/*-{
	return this.file(fileName,text,option);
	}-*/;
	
	/**
	 * not tested
	 * @param fileName
	 * @param data
	 * @param option
	 * @return
	 */
	public final native JSZip file(String fileName,JavaScriptObject data,JSFileOptions option)/*-{
	return this.file(fileName,data,option);
	}-*/;
	
	public final native JSZip folder(String folderName)/*-{
	return this.folder(folderName);
	}-*/;
	
	public final native JSZip remove(String name)/*-{
	return this.remove(name);
	}-*/;
	
	//TODO cast type
	public final native Object generate()/*-{
	return this.generate();
	}-*/;
	
	public final native Object generate(GenerateParameter parameter)/*-{
	return this.generate(parameter);
	}-*/;
	
	/**
	 * this is very useful but this method need html5 libs
	 
	<inherits name="com.akjava.gwt.lib.Commons"/>
	<inherits name="com.akjava.gwt.html5.Html5widget" />
	 
	 * @param compression
	 * @return
	 */
	public final native Blob generateBlob(String compression)/*-{
	var parameter={type:"blob"};
	if(compression){
		parameter.compression=compression;
	}	
	return this.generate(parameter);
	}-*/;
}
