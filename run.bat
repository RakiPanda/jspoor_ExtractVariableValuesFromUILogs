SET JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
SET JAVA_HOME=C:\Program Files\Java\jdk-1.8
SET PATH=%JAVA_HOME%\bin;%PATH%
SET CLASSPATH=.;%~dp0dist\terppaint.jar;%~dp0dist\jspoor.jar;C:\aspectj1.9\lib\aspectjrt.jar;C:\aspectj1.9\lib\aspectjtools.jar;C:\aspectj1.9\lib\aspectjweaver.jar;

aj5 -Xmx512m -Xms256m -classpath "%CLASSPATH%" jspoor.uilog.UILogApplicationStarter TerpPaint
