#GWTJSZip

merged to https://github.com/akjava/akjava_gwtlib

this is jszip wrapper for GWT
http://stuk.github.io/jszip/

jszip is MIT-LICENSED ziptool for javascript

enjoy!

""HOW TO USE
check com.akjava.gwt.samplejszip module

""TIPS
some method need my other library for Blob and Blob download
https://github.com/akjava/akjava_gwtlib
https://github.com/akjava/html5gwt

I have no idea why this error happen
404 - GET /rawdeflate.min.js.map

##TODO
support date
support load

##PROBLEMS
only ascii-file name work fine on Windows default zip-extractor,so use 7Zip to unzip

##History
150706.jar - build with GWT2.6.1 & Guava 1.8 & JSZip2.5

140511.jar - build with GWT2.5.1 & Guava 1.6 & JSZip2.4

###WARNING
Only Work contained JSZip,not work with newer JSZip3.x

####for GWT2.8
Old Guava not work with newer GWT
remove guava dependency to work with GWT2.8.

change Blob to Object in JsZip.java

public final native Blob generateBlob(String compression)/*-{

remove these line in GWTJSZip.gwt.xml

<inherits name="com.akjava.gwt.html5.Html5widget" />