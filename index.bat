echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/fraction_math/Main.java&&java com/krzem/fraction_math/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"