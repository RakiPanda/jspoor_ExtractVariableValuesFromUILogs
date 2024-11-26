SET JAVA_HOME=C:\Program Files\Java\jdk-1.8
SET PATH=%JAVA_HOME%\bin;%PATH%
SET CLASSPATH=.;%~dp0dist\terppaint.jar;%~dp0dist\jspoor.jar;
SET ASPECTPATH=./jspoor.jar;
aj5 jspoor.uilog.UILogApplicationStarter TerpPaint
