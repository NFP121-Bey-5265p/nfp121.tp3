
echo Execution en ligne de commandes de la classe de tests, une simple verification
java -cp .;./junit.jar junit.textui.TestRunner question1.PileTest
java -cp .;./junit.jar junit.textui.TestRunner question2.PilesAuMemeComportement

@echo off
rd /Q/S instr_tp3
rd /Q/S coverage
@echo on

echo Instrumention des .class afin en vue de produire une trace de l'execution
rem pause
call ./cobertura-2.1.1/cobertura-instrument.bat --basedir . --auxClasspath ./junit.jar -cp . --destination instr_tp3

echo Execution en ligne de commandes des classes de tests instrumentees
rem pause
java -cp ./cobertura-2.1.1/cobertura-2.1.1.jar;./cobertura-2.1.1/lib/slf4j-api-1.7.5.jar;./instr_tp3;./junit.jar;. -Dnet.sourceforge.cobertura.datafile=cobertura_q1.ser junit.textui.TestRunner question1.PileTest
java -cp ./cobertura-2.1.1/cobertura-2.1.1.jar;./cobertura-2.1.1/lib/slf4j-api-1.7.5.jar;./instr_tp3;./junit.jar;. -Dnet.sourceforge.cobertura.datafile=cobertura_q2.ser junit.textui.TestRunner question2.PilesAuMemeComportement

echo Generation du rapport au format HTML
rem pause
call ./cobertura-2.1.1/cobertura-merge.bat --datafile cobertura.ser cobertura_q1.ser cobertura_q2.ser
call ./cobertura-2.1.1/cobertura-report.bat --format html --datafile cobertura.ser --destination coverage .

echo Quelles sont les classes au faible taux de couverture ?
rem pause
call ./cobertura-2.1.1/cobertura-check.bat --branch 50 --totalline 70

@echo off
del /Q cobertura.ser
del /Q cobertura_q1.ser
del /Q cobertura_q2.ser
rd /Q/S instr_tp3
@echo on
echo Execution du navigateur par defaut
rem pause
start ./coverage/index.html