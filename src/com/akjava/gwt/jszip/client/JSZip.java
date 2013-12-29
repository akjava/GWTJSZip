package com.akjava.gwt.jszip.client;

import com.akjava.gwt.html5.client.file.Blob;
import com.google.gwt.core.client.JavaScriptObject;

public class JSZip extends JavaScriptObject{
protected JSZip(){}

	public final static native JSZip newJSZip()/*-{
	return new $wnd.JSZip();
	}-*/;
	
	public final native JSFile file(String fileName,String text)/*-{
	return this.file(fileName,text);
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
