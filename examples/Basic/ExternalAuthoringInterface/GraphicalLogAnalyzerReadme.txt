README for  Graphical Log Analyzer
created by Lindsey Lack for MV4205

(1) Ensure that the following files are in the same directory:

  - GraphicalLogAnalyzer.java
  - GraphicalLogAnalyzer.class
  - GraphicalLogAnalyzer$JVShape.class
  - GraphicalLogAnalyzerDisplay.html
  - GraphicalLogAnalyzer.wrl


    + The GraphicalLogAnalyzer.wrl file was translated from the GraphicalLogAnalyzer.x3d file.

    + The class files have been compiled from GraphicalLogAnalyzer.java with the Cortona
      "plywood.jar" file in the classpath. If the Cortona VRML Client has been installed,
      "plywood.jar" will typically be found at:
      "C:\Program Files\Netscape\Communicator\Program\Plugins\plywood.jar". An example command 
      to compile the class files is:

       javac -classpath "C:\Program Files\Netscape\Communicator\Program\Plugins\plywood.jar"

      Cortona stores the java classes that support EAI in two files:

        - corteai.zip ("old" and "new" External Authoring Interface (EAI) for Internet Explorer)
        - plywood.jar (SAI and old EAI for Netscape)

      "Plywood" is required, as opposed to "corteai", because the GraphicalLogAnalyzer uses
      Netscape security classes to permit file access from the browser.


(2) Ensure that the following file is in the root of your C: drive:


  - GraphicalLogAnalyzerInputFile.txt

    + the full name should correspond with "C:/GraphicalLogAnalyzerInputFile.txt"


(3) Ensure that your environment is set up as follows:

    + Using Netscape 4.7X or Internet Explorer (* see note below regarding IE)
    + Using Cortona VRML Client that complies with the original VRML97 EAI specifications.
    + If using Internet Explorer, only the Microsoft VM is supported.
    + Note that although Internet Explorer will work for most of the functionality, an error
      will occur when an attempt is made to get data from a file (the "Get File" button is
      pressed) since IE does not support the Netscape security interfaces.

(4) Open the GraphicalLogAnalyzerDisplay.html file.

