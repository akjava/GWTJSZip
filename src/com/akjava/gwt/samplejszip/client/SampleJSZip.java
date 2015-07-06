package com.akjava.gwt.samplejszip.client;

import com.akjava.gwt.html5.client.download.HTML5Download;
import com.akjava.gwt.html5.client.file.Blob;
import com.akjava.gwt.html5.client.file.File;
import com.akjava.gwt.html5.client.file.FileHandler;
import com.akjava.gwt.html5.client.file.FileReader;
import com.akjava.gwt.html5.client.file.FileUploadForm;
import com.akjava.gwt.html5.client.file.FileUtils;
import com.akjava.gwt.html5.client.file.FileUtils.DataURLListener;
import com.akjava.gwt.html5.client.file.Uint8Array;
import com.akjava.gwt.html5.client.file.webkit.DirectoryCallback;
import com.akjava.gwt.html5.client.file.webkit.FileEntry;
import com.akjava.gwt.html5.client.file.webkit.FilePathCallback;
import com.akjava.gwt.html5.client.file.webkit.Item;
import com.akjava.gwt.jszip.client.JSFile;
import com.akjava.gwt.jszip.client.JSZip;
import com.akjava.gwt.lib.client.Base64Utils;
import com.akjava.gwt.lib.client.CanvasUtils;
import com.akjava.gwt.lib.client.LogUtils;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SampleJSZip implements EntryPoint {

	private JSZip dropedZip;

	VerticalPanel downloadLinks=new VerticalPanel();
	@Override
	public void onModuleLoad() {
		final VerticalPanel root=new VerticalPanel();
		RootPanel.get().add(root);
		
		
		JSZip zip=JSZip.newJSZip();
		zip.file("test.txt", "hello world");
		
		JSFile file=zip.getFile("test.txt");
		LogUtils.log(file);
		
		LogUtils.log(file.getName());
		LogUtils.log(file.asText());
		LogUtils.log(file.getDate());
		LogUtils.log(file.isDir());
		
		
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
		
		Anchor a=new HTML5Download().generateDownloadLink(blob,"application/zip","test.zip","download sample blob",false);
		root.add(a);
		
		TextArea area=new TextArea();
		root.add(area);
		area.setText("drop file to zip");
		//area.setReadOnly(true);
		final Button bt=new Button("genrerate new download link",new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				Blob blob=dropedZip.generateBlob(null);
				
				Anchor a=new HTML5Download().generateDownloadLink(blob,"application/zip","test.zip","download sample blob",false);
				downloadLinks.add(a);
			}
		});
		bt.setEnabled(false);
		root.add(bt);
		
		
		area.addDragEnterHandler(new DragEnterHandler() {
			
			@Override
			public void onDragEnter(DragEnterEvent event) {
				event.preventDefault();
			}
		});
		area.addDropHandler(new DropHandler() {
			@Override
			public void onDrop(DropEvent event) {
				event.preventDefault();
				bt.setEnabled(true);
				downloadLinks.clear();
				
				dropedZip = JSZip.newJSZip();
				
				//inputArea.setText("");
				final FilePathCallback callback = new FilePathCallback() {
					@Override
					public void callback(File file,String path) {
						if(file==null){
							return;//directory;
						}
						
						//TODO support dir
						//zip2.file(path, file, null);
						LogUtils.log(path+file.getFileName());
						
						readFile(dropedZip,file,path);
					}
				};

				final JsArray<Item> items = FileUtils.transferToItem(event
						.getNativeEvent());
				
				if (items.length() > 0) {
					for (int i = 0; i < items.length(); i++) {
						

						FileEntry entry = items.get(i).webkitGetAsEntry();

						entryCallback(entry,callback,"");

					}

				}

				
				
			}
		});
		
		final JSZip loadZip=JSZip.loadFile("test.zip");
		root.add(new Label("[load test.zip file-list]"));
		/*
		try {
			RequestBuilder builder=new RequestBuilder(RequestBuilder.GET,"voa_wordindex.zip");
			builder.setHeader("content-type", "text/plain; charset=x-user-defined");
			builder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					loadZip.load(response.getText());
					LogUtils.log(loadZip);
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					LogUtils.log(exception.getMessage());
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		JsArrayString files=loadZip.getFiles();
		for(int i=0;i<files.length();i++){
		String fileName=files.get(i);
		if(!fileName.endsWith("/")){
			root.add(new Label(loadZip.getFile(fileName).getName()));
		}
		
		}
		
		final VerticalPanel images=new VerticalPanel();
		
		FileUploadForm upload=FileUtils.createSingleFileUploadForm(new DataURLListener() {
			@Override
			public void uploaded(File file, String value) {
				images.clear();
				final FileReader reader=FileReader.createFileReader();
				reader.setOnLoad(new FileHandler() {
					
					@Override
					public void onLoad() {
						Uint8Array uint=reader.getResultAsBuffer();
						JSZip zip=JSZip.newJSZip(uint);
						JsArrayString fileNames=zip.getFiles();
						for(int i=0;i<fileNames.length();i++){
							String fileName=fileNames.get(i);
							LogUtils.log(fileName);
							if(fileName.endsWith(".png")){
								JSFile file=zip.getFile(fileName);
								Uint8Array imageData=file.asUint8Array();
								LogUtils.log("length:"+imageData.length());
								
								String url=Base64Utils.toDataUrl("image/png", imageData.toByteArray());
								images.add(new Image(url));
							}
						}
						
					}
				});
				reader.readAsArrayBuffer(file);
			}
		}, true);
		upload.setAccept(FileUploadForm.ACCEPT_ZIP);
		
		root.add(new Label("[choose zip and show file names on log]"));
		root.add(upload);
		root.add(images);
		
		/*
		 * i have no idea how to catch every async file-load finished.
		 */
	
		
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
		
		root.add(downloadLinks);
		
	}


	public void readFile(final JSZip zip,final File file,final String path){
		final FileReader reader = FileReader.createFileReader();
		reader.setOnLoad(new FileHandler() {
			@Override
			public void onLoad() {
				LogUtils.log("add zip:"+file.getFileName());
				Uint8Array array = reader.getResultAsBuffer();
				String fixedPath=path.startsWith("/")?path.substring(1):path;
				if(!fixedPath.isEmpty()){
					
					LogUtils.log("create folder:"+fixedPath);
					zip.folder(fixedPath).file(file.getFileName(), array, null);
				}else{
					zip.file(file.getFileName(), array, null);
				}
			}
		});
		reader.readAsArrayBuffer(file);
	}
	
	public void entryCallback(final FileEntry entry,final FilePathCallback callback,String path){
		if (entry.isFile()) {
			entry.file(callback,path);
		} else if (entry.isDirectory()) {
			entry.getReader().readEntries(
					new DirectoryCallback() {
						@Override
						public void callback(
								JsArray<FileEntry> entries) {
							callback.callback(null, entry.getFullPath());
							for (int j = 0; j < entries
									.length(); j++) {
								entryCallback(entries.get(j),callback,entry.getFullPath());
							}
						}
					});
		}
	}

}
