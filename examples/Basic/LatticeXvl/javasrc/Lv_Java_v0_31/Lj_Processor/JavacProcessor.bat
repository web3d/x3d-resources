javac -d ..\CodeBase -classpath ..\CodeBase @SrcList\x3pCom_SrcList.txt         2>  ..\BatOut\JavacProcessor_BatOut.txt
javac -d ..\CodeBase -classpath ..\CodeBase @SrcList\x3pNode_SrcList.txt        2>> ..\BatOut\JavacProcessor_BatOut.txt
javac -d ..\CodeBase -classpath ..\CodeBase @SrcList\x3pParse_SrcList.txt       2>> ..\BatOut\JavacProcessor_BatOut.txt
javac -d ..\CodeBase -classpath ..\CodeBase @SrcList\x3pBase_SrcList.txt        2>> ..\BatOut\JavacProcessor_BatOut.txt
