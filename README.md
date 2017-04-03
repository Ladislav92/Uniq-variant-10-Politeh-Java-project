# Uniq-variant-10-Politeh-Java-project

Объединение последовательностей одинаковых идущих подряд строк в файле в одну:
-file задаёт имя входного файла. Если параметр отсуствуэт, следуэт выводить результаты на консоль
-Флаг -i ознчает, что при сраврнении строк следует не учитывать регистр символов.
-Флаг -s N означает, что при сравнении строк следует игнорировать первэ N символов кадой страки.
-Флаг -u означает, что следует выводить в качестве результата только уникаьные строки.
-Флаг -c означает, что перед kаждой стракой вывода следует вывести количество строк, котоые были заменены (т.е. если во входных данных
      било 2 одиноковые строки, в выходных данных должна быть одна с префиксом "2").

Command line: uniq[-i][-u][-c][-s num][-o file][file]

В случае, когда какое-нибудь из имён файлов указано неверно, следуеть выдать ошибку.

Кроме самой программы, следует написать автоматические тесты к ней.

====================================================================================================================================

Combining sequences of identical consecutive lines in the file into one:
-file specifies the name of the input file. If the parameter is missing, output on the console is needed
The -i flag means that comparisson of the  lines should not be case sensitive.
The -s flag N indicates that the string comparison should ignore first N characters of each line.
Flag -u means should be taken as a result only unique lines.
The -c flag means that in front of every line of output you should output the number of lines that were replaced (i.e. if in the input
contains 2 same lines in the output should be one with the prefix "2").

Command line: uniq[-i][-u][-c][-s num][-o file][file]

In the case when any of the file names are incorrect, one should generate an error.

In addition to the program itself, you should write automated tests to it.
