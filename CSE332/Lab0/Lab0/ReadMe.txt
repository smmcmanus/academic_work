CSE 332s 
Lab 0
Sean McManus
431817

-Errors: When I forgot to return the value returned by usage_massage, I recieved a debug assertion error and Lab0.exe crahsed.
	Spelling a variable wrong: x is undefined
	Didnt't specify "integer" variable as a pointer in isDigit(), recieved a "No suitable conversion function" exception

Trials and Outputs:
1. Lab0.exe
Outout:
Lab0.exe
usage: lab0 <input_file_name>

This is correct behavior
2. Lab0.exe inn.txt (inn.txt does not exist)
Output:
File cannot be opened

This is correct behavior
3. Lab0.exe in.txt
in.txt contents:
iii 392i6 i
i 8723 9 03h
i  4 4 4 4 4434i

Output:
iii
392i6
i
i
03h
i
4434i
8723
9
4
4
4


This is correct behavior

4. Lab0.exe in.txt out.txt
Output:
Lab0.exe
usage: lab0 <input_file_name>

This is correct behavior
 
